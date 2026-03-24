def getAllFreightSurcharges() {
    //original code
    return api.namedEntities(
            api.findLookupTableValues("FreightSurcharge")
    )
}


def getCachedAllFreightSurcharges() {
    final key = "FreightSurchargeDataCacheKey"


    if (api.global.containsKey(key)) {   //(1`23yt)
        return api.global[key]           //(2)
    }


    def data = getAllFreightSurcharges() //(3)

    api.global[key] = data               //(4)

    return data                          //(5)
}
return getCachedAllFreightSurcharges()