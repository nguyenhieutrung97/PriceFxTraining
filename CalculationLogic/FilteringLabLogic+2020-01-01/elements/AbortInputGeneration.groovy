if (api.isInputGenerationExecution()) {
    // Generate input
} else {
    // Use input value
}

if (api.isInputGenerationExecution()) {
    api.inputBuilderFactory()
            .createUserEntry("SalesDiscountPct")    // Define input name
            .setLabel("Sales Discount (%)")         // Display label
            .setFormatType("PERCENT")               // Format configuration
            .getInput()                             // Finalize and return input
} else {
    input.SalesDiscountPct                     // Use provided value
}