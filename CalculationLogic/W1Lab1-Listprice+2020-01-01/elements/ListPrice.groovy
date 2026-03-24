def qapi = api.queryApi()
def exprs = qapi.exprs()
def orders= qapi.orders()

def t1 = qapi.tables().products()
def t2 = qapi.tables().productExtensionRows("ProductCost")
def t3 = qapi.tables().companyParameterRows("MarginAdj")

return qapi.source(t1, [t1.sku(),  t1.ProductGroup, t1.ProductClass])
        .innerJoin(t2, { cols -> [t2.AvgCost]},
                { cols ->
                    exprs.and(
                            t2.sku().equal(cols.sku)
                    )
                }
        )
        .innerJoin(t3, { cols -> [t3.value()]},
                { cols ->
                    exprs.and(
                            t3.key1().equal(cols.ProductGroup)
                    )
                }
        )
        .filter {cols-> exprs.and(
                cols.ProductClass.equal("B"))}
        .sortBy { cols -> [orders.ascNullsLast(cols.ProductGroup)] }
        .stream { it.collect { it } }