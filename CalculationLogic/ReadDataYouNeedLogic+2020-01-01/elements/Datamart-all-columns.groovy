def qapi = api.queryApi()

def t1 = qapi.tables().datamart("Transaction")

return qapi.source(t1)
        .stream { it.collect { it } }