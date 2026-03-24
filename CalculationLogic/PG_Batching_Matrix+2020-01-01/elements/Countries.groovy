if (api.global.countryCodeList == null) {
    def qapi = api.queryApi()
    def t1 = qapi.tables().companyParameterRows("CountryAdjustment")

    def CountryCodeList= qapi.source(t1, [t1.CountryCode])
            .stream { it.collect { it } }
    api.global.countryCodeList =  CountryCodeList?.CountryCode
}

return api.global.countryCodeList
