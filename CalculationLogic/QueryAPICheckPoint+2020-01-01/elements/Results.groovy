def qapi = api.queryApi()
def exprs = qapi.exprs()
def sku = api.currentItem("sku")
def customerId = out.Customer


if (sku == null) {
    api.addWarning("No product found in context (sku/ProductId)")
    return null
}

def tProdCost = qapi.tables().productExtensionRows("ProductCost")
def tProducts = qapi.tables().products()
def tCustomers = qapi.tables().customers()
def tMarginAdj = qapi.tables().companyParameterRows("MarginAdj")
def tAttributeAdj = qapi.tables().companyParameterRows("AttributeAdj")
def tPackagingAdj = qapi.tables().companyParameterRows("PackagingAdj")
def tTaxAdj = qapi.tables().companyParameterRows("TaxAdj")

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
    .leftOuterJoin(tMarginAdj, { cols -> [tMarginAdj.value().as("MarginPCT")] },
        { cols ->
            exprs.and(
                tMarginAdj.key1().equal(cols.ProductGroup)
            )
        }
    )
    .leftOuterJoin(tAttributeAdj, { cols -> [tAttributeAdj.AttributeAdj.as("AttributePCT")] },
        { cols ->
            exprs.and(
                tAttributeAdj.key1().equal(cols.ProductLifeCycle)
            )
        }
    )
    .leftOuterJoin(tPackagingAdj, { cols -> [tPackagingAdj.PackagingAdj.as("PackagingPCT")] },
        { cols ->
            exprs.and(
                tPackagingAdj.key1().equal(cols.Size)
            )
        }
    )
    .leftOuterJoin(tTaxAdj, { cols -> [tTaxAdj.value().as("TaxTariffPCT")] },
        { cols ->
            exprs.and(
                tTaxAdj.key1().equal(cols.Region)
            )
        }
    )

return pipeline.take(1).stream { it.find() }
