def qapi = api.queryApi()
def exprs = qapi.exprs()
def sku = api.product("sku") ?: api.product("ProductId")
def customerId = out.Customer

def companyParamValueCol = { table, String parameterName ->
        try {
                return table."${parameterName}"
        } catch (Exception ignored) {
                return table.value()
        }
}

if (sku == null) {
        api.addWarning("No product found in context (sku/ProductId)")
        return null
}

def tProdCost = qapi.tables().productExtensionRows("ProductCost")
def tProducts = qapi.tables().products()
def tCustomers = qapi.tables().customers()
def tMarginAdj = qapi.tables().companyParameterRows("MarginAdj")
def tAttributeAdj = qapi.tables().companyParameterRows("AttributeAdj")
def tPackagingAdj = null
def tTaxAdj = null

try {
        tPackagingAdj = qapi.tables().companyParameterRows("PackagingAdj")
} catch (Exception ignored) {
        api.addWarning("Company Parameter 'PackagingAdj' not found")
}

try {
        tTaxAdj = qapi.tables().companyParameterRows("TaxAdj")
} catch (Exception ignored) {
        api.addWarning("Company Parameter 'TaxAdj' not found")
}

def pipeline = qapi.source(tProdCost, [tProdCost.AvgCost, tProdCost.sku()],
        exprs.and(
                tProdCost.sku().equal(sku)
        )
)
        .leftOuterJoin(tProducts, { cols -> [tProducts.ProductGroup, tProducts.ProductLifeCycle, tProducts.Size] },
                { cols ->
                    exprs.and(
                            tProducts.sku().equal(cols.sku)
                    )
                }
        )
        .leftOuterJoin(tCustomers, { cols -> [tCustomers.Region] },
                { cols ->
                    exprs.and(
                            tCustomers.customerId().equal(customerId)
                    )
                }
        )
        .leftOuterJoin(tMarginAdj, { cols -> [companyParamValueCol(tMarginAdj, "MarginAdj").as("MarginPCT")] },
                { cols ->
                    exprs.and(
                            tMarginAdj.key1().equal(cols.ProductGroup)
                    )
                }
        )
        .leftOuterJoin(tAttributeAdj, { cols -> [companyParamValueCol(tAttributeAdj, "AttributeAdj").as("AttributePCT")] },
                { cols ->
                    exprs.and(
                            tAttributeAdj.key1().equal(cols.ProductLifeCycle)
                    )
                }
        )

if (tPackagingAdj != null) {
    pipeline = pipeline.leftOuterJoin(tPackagingAdj, { cols -> [companyParamValueCol(tPackagingAdj, "PackagingAdj").as("PackagingPCT")] },
            { cols ->
                exprs.and(
                        tPackagingAdj.key1().equal(cols.Size)
                )
            }
    )
}

if (tTaxAdj != null) {
    pipeline = pipeline.leftOuterJoin(tTaxAdj, { cols -> [companyParamValueCol(tTaxAdj, "TaxAdj").as("TaxTariffPCT")] },
            { cols ->
                exprs.and(
                        tTaxAdj.key1().equal(cols.Region)
                )
            }
    )
}

def results = pipeline.stream { it.collect { it } }

return results?.find()
