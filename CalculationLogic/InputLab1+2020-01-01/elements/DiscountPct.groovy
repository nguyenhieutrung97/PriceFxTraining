if (api.isInputGenerationExecution()) {
    return api.inputBuilderFactory()
            .createUserEntry("DiscountPct")
            .setLabel("Discount")
            .setFormatType("PERCENT")
            .setTo(0.25)
            .getInput()
}

return input.DiscountPct
