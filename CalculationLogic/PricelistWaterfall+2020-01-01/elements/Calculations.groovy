def qapi = api.queryApi()
def exprs = qapi.exprs()
def sku = api.product("ProductId")
def t1 = qapi.tables().productExtensionRows("ProductCost")
def t2 = qapi.tables().products()
def t3 = qapi.tables().companyParameterRows("MarginAdj")
def t4 = qapi.tables().companyParameterRows("AttributeAdj")

def calculations= qapi.source(t1, [t1.AvgCost, t1.sku()],
        exprs.and(
                t1.sku().equal(sku)
        )
)
        .leftOuterJoin(t2, { cols -> [t2.ProductGroup, t2.ProductLifeCycle]},
                { cols ->
                    exprs.and(
                            t2.sku().equal(cols.sku)
                    )
                }
        )
        .leftOuterJoin(t3, { cols -> [t3.value().as("MarginAdjPCT")]},  //1
                { cols ->
                    exprs.and(
                            t3.key1().equal(cols.ProductGroup)  //2
                    )
                }
        )
        .leftOuterJoin(t4, { cols -> [t4.AttributeAdj]},
                { cols ->
                    exprs.and(
                            t4.key1().equal(cols.ProductLifeCycle)
                    )
                }
        )
        .stream { it.collect { it } }

return calculations?.find()