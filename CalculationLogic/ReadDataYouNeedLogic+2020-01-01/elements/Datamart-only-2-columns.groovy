def qapi = api.queryApi()
def exprs = qapi.exprs()

def t1 = qapi.tables().datamart("Transaction")

return qapi.source(t1, [t1.TransactionId, t1.Cost])
        .stream { it.collect { it } }