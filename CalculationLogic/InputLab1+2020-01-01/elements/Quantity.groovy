if (api.isInputGenerationExecution()) {
    return api.inputBuilderFactory()
            .createIntegerUserEntry("Quantity")
            .setLabel("Quantity")
            .setRequired(true)
            .getInput()
}

return input.Quantity
