def selectedCustomerId = input.Customer

def qapi = api.queryApi()
def c = qapi.tables().customers()

def queryResult = qapi.source(c, [c.Region], c.customerId().equal(selectedCustomerId))
    .take(1)
    .stream { it.find() }

api.trace("Customer", queryResult)
return queryResult ? queryResult.Region : null