try {
    // 1. Context and Validation
    // Retrieve the pricelist ID and the Region, which corresponds to the Pricelist Name (label).
    def pricelistId = pricelist?.id
    def region = pricelist?.label

    if (!pricelistId || !region) {
        api.throwException("Workflow aborted: Pricelist context or Region (Label) is missing.")
    }

    def qapi = api.queryApi()
    def exprs = qapi.exprs()

    // 2. Define Tables
    def pli = qapi.tables().priceListLineItems(pricelistId)
    def p = qapi.tables().products()
    def cp = qapi.tables().companyParameterRows("MarginApproval")

    def missingDataErrors = []
    def workflowItems = []

    // 3. Single QueryAPI Stream with Joins
    qapi.source(pli, [pli.sku(), pli."GrossMargin"])
        .leftOuterJoin(p, { cols -> [p."ProductGroup"] }, { cols ->
            p.sku().equal(cols.sku)
        })
        .leftOuterJoin(cp, { cols -> [cp."MinMarginThreshold", cp."Group"] }, { cols ->
            exprs.and(
                cp."Region".equal(region),
                cp."ProductGroup".equal(cols.ProductGroup)
            )
        })
        .stream { stream ->
            stream.each { row ->
                if (row.GrossMargin == null) {
                    missingDataErrors << "SKU: ${row.sku} is missing a GrossMargin calculation."
                } else if (row.MinMarginThreshold == null) {
                    missingDataErrors << "SKU: ${row.sku} is missing a MarginApproval Threshold for ProductGroup: ${row.ProductGroup} in Region: ${region}."
                } else if (row.GrossMargin < row.MinMarginThreshold) {
                    workflowItems << row
                }
            }
        }

    // 4. Post-Stream Error Handling
    if (missingDataErrors) {
        api.throwException("Workflow aborted due to missing data required for evaluation:\n" + missingDataErrors.join("\n"))
    }

    // 5. Sorting
    // Sort the collected workflow items by GrossMargin in Ascending order
    workflowItems.sort { it.GrossMargin }

    // 6. Generate Workflow Steps
    workflowItems.each { item ->
        def approverGrp = item.Group ?: "PricingManager"
        def message = "Margin threshold not met. SKU: ${item.sku} | GrossMargin: ${item.GrossMargin} | Threshold: ${item.MinMarginThreshold}"
        workflow.addApprovalStep("Margin Approval required for ${item.sku}")
            .withReasons(message)
            .withUserGroupApprovers(approverGrp)
    }

} catch (Exception e) {
    // 7. Prevent Unhandled Exceptions
    // Ensure that if any unexpected calculation or retrieval error occurs, the process fails gracefully
    api.throwException("Workflow execution aborted safely due to an unexpected error: " + e.getMessage())
}