def region = api.customer("attribute5", out.Customer)
return api.vLookup("TaxAdj", region)