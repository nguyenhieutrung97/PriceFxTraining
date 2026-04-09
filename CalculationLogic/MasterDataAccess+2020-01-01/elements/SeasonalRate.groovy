def targetDate = new Date()
def prodGroup = api.product("attribute1")
def filter = [
    Filter.equal("key1", out.Region),
    Filter.equal("key2", prodGroup),
    Filter.lessOrEqual("key3", targetDate)
]

def seasonRate = api.findLookupTableValues("SeasonalRate", "attribute1", *filter)

return seasonRate.getFirst().attribute1