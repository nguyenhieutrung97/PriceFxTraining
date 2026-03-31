def lineItemSku = pricegriditem?.sku
def MarginAdjPct = pricegriditem?.attribute5
api.trace("MarginAdjPct = ${MarginAdjPct}")
def qapi = api.queryApi()
def exprs = qapi.exprs()

def al = qapi.tables().companyParameterRows("PriceListApprovalLevels")
def p = qapi.tables().products()

def queryData = qapi.source(p, [p.sku(), p."BusinessUnit"], p.sku().equal(lineItemSku))
    .innerJoin(al, { cols -> [al.MarginThreshold] },
        { cols ->
            exprs.and(
                al.BusinessUnit.equal(cols.BusinessUnit),
                al.MarginThreshold.greaterThan(MarginAdjPct),
            )
        }
    )
    .stream { it.toList() }

queryData.each {
    workflow.addApprovalStep("Approval level ${it.MarginThreshold} not met for product ${it.sku}")
        .withUserGroupApprovers("ProductManager")
        .withReasons("Margin for ${it.sku} is only ${MarginAdjPct}, should be at least ${it.MarginThreshold}")
}