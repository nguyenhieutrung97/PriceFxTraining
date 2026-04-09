def prodSize = api.product("attribute5")
return api.vLookup("PackagingAdj", "attribute1", prodSize)