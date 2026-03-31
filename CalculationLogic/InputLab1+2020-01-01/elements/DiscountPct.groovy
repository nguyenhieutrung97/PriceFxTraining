if (api.isInputGenerationExecution()) {
    def qapi = api.queryApi()

    def t1 = qapi.tables().companyParameterRows("Discounts")

    def salesOrgs =  qapi.source(t1)
            .stream { it.collectEntries {
                [(it.value as String): it.key1]
            } }

    return api.inputBuilderFactory()
            .createOptionEntry("DiscountPct")
            .setOptions(salesOrgs.keySet() as List)
            .setLabels(salesOrgs)
            .setLabel("DiscountPct")
            .getInput()
}

return input.DiscountPct as BigDecimal
