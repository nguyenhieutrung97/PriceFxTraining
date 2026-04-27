def selectedCustomerId = input.Customer

def qapi = api.queryApi()
def c = qapi.tables().customers()

def queryResult = qapi.source(c, [c.CustomerCurrency], c.customerId().equal(selectedCustomerId))
    .take(1)
    .stream { it.find() }

def customerCurrency = queryResult ? queryResult.CustomerCurrency : null

if (customerCurrency == "EUR" || customerCurrency == "USD") {
    return customerCurrency
} else {
    return "USD"
}