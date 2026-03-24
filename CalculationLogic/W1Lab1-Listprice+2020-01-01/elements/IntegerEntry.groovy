if (api.isInputGenerationExecution()) {
    return api.inputBuilderFactory()
            .createIntegerUserEntry("IntegerEntry")
            .setLabel("IntegerEntry")
            .getInput()
}

return input.IntegerEntry
