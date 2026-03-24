if (api.isInputGenerationExecution()) {
    return api.inputBuilderFactory()
            .createUserEntry("Price")
            .setLabel("Price")
            .setRequired(true)
            .getInput()
}

return input.Price
