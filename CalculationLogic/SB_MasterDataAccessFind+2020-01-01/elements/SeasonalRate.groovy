def productGroupResult = out.ProductGroup
def regionResult = out.CustomerRegion

def productGroup = productGroupResult?.productGroup
def region = regionResult?.region

api.trace("productGroup: ", productGroup)
api.trace("region: ", region)

if (!productGroup || !region) {
    return null
}

def targetDate = api.targetDate()
api.trace("targetDate: ", targetDate)

def seasonalRateValue = [:]

def data = api.findLookupTableValues("SeasonalRate",
        Filter.equal("key1", region),
        Filter.equal("key2", productGroup),
        Filter.lessOrEqual("key3", targetDate))

for (row in data) {
    // Last row wins — sorted by key3 ascending, so the latest valid date is last
    seasonalRateValue = [
        "region"      : row.key1,
        "productGroup": row.key2,
        "validFrom"   : row.key3,
        "seasonalRate": row.attribute1,
    ]
    api.trace("Row: ", seasonalRateValue)
}

return seasonalRateValue



