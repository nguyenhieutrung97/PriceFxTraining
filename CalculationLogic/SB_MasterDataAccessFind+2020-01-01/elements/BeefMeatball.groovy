def qapi = api.queryApi()
def t1 = qapi.tables().products()
return qapi.source(t1, [t1.sku(), t1.ProductGroup, t1.BusinessUnit, t1.label().as("My Lable")]
        , t1.ProductGroup.equal("Beef"))
        .take(5)
        .traceQuery()
        .stream { it.toList() }