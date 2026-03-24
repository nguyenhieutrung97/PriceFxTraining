def qapi = api.queryApi()
def exprs = qapi.exprs()

def t1 = qapi.tables().datamart("Transaction")

return qapi.source(t1, [t1.CustomerId, t1.ProductId],
        exprs.and(t1.ProductId.equal("MB-0001"),
                t1.CustomerId.equal("CD-00001")))
        .stream { it.collect { it } }