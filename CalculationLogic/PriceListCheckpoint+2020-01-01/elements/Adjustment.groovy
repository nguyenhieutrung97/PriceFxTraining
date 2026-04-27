if (api.isInputGenerationExecution()) {
    return api.inputBuilderFactory()
        .createUserEntry("Adjustment")
        .setLabel("Adjustment")
        .setFormatType("PERCENT")
        .setValue(0.0)
        .getInput()
}

return input.Adjustment
