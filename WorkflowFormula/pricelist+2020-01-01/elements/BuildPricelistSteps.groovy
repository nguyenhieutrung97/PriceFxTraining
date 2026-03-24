def priceListId = pricelist.id

def qapi = api.queryApi()
def pli = qapi.tables().priceListLineItems(priceListId)

def queryData = qapi.source(pli, [pli.sku(), pli.completeCalculationResults])
        .stream { it.collect { it } }

// Extract MarginAdjPct from completeCalculationResults for each row
queryData?.each { row ->
    def results = api.parseJSON(row.completeCalculationResults)
    def marginAdjPct = results?.MarginAdjPct
    api.trace("MarginAdjPct", marginAdjPct)
}