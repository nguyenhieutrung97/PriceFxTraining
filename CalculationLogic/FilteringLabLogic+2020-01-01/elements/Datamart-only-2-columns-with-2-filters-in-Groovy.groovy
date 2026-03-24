def qapi = api.queryApi()
def t1 = qapi.tables().datamart("Transaction")

def data = qapi.source(t1, [ t1.CustomerId, t1.ProductId])
        .stream { it.collect { it } }

return data.findAll {
    it.ProductId == "MB-0001" && it.CustomerId == "CD-00001"
}