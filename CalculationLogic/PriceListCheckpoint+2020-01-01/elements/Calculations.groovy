def sku = api.currentItem("sku") ?: api.product("sku")
def targetDate = api.targetDate() ?: new Date()

def currentCurrency = out.Currency
def currentRegion = out.Region

def qapi = api.queryApi()
def exprs = qapi.exprs()
def orders = qapi.orders()

def tTax = qapi.tables().companyParameterRows("TaxAdj")
def tLP = qapi.tables().productExtensionRows("ListPrice")

def queryResult = qapi.source(tLP, [tLP.ListPrice, tLP.ValidFrom],
    exprs.and(
        tLP.sku().equal(sku),
        tLP.Currency.equal(currentCurrency),
        tLP.ValidFrom.lessOrEqual(targetDate)
    )
)
    .leftOuterJoin(tTax, { cols -> [tTax.value().as("TaxPCT")] }, { cols ->
        tTax.key1().equal(currentRegion)
    })
    .sortBy({ cols ->
        [orders.descNullsLast(cols.ValidFrom)]
    })
    .take(1)
    .stream { it.find() }

def listPrice = queryResult?.ListPrice ?: 0.0
def taxPct = queryResult?.TaxPCT ?: 0.0

return [
    "ListPrice": listPrice,
    "TaxPct"   : taxPct
]