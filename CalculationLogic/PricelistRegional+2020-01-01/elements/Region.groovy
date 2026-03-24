if (api.isInputGenerationExecution()) {

    def region=["Europe","America"]

    return api.inputBuilderFactory()
            .createOptionEntry("Region")
            .setOptions(region)
            .setLabel("Region")
            .setRequired(true)
            .getInput()
}

return input.Region
