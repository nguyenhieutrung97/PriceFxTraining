if (api.isInputGenerationExecution()) {
    return api.inputBuilderFactory()
        .createCustomerEntry("Customer")
        .setLabel("Customer")
        .setRequired(true)
        .getInput()
}

return input.Customer
