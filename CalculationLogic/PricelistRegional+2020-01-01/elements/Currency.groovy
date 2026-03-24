def qapi = api.queryApi()
def exprs = qapi.exprs()

def t1 = qapi.tables().companyParameterRows("Region")

return qapi.source(t1, [t1.Currency],t1.key1().equal(out.Region))
        .stream { it.find() }?.Currency