def priceListId = pricelist.id

def qapi = api.queryApi()
def exprs = qapi.exprs()
def pli = qapi.tables().priceListLineItems(priceListId)
def al = qapi.tables().companyParameterRows("PriceListApprovalLevels")
def p = qapi.tables().products()


def queryData = qapi.source(pli, [pli.sku(), pli."MarginAdjPct"])
    .leftOuterJoin(p, { cols -> [p.BusinessUnit] },
        { cols ->
            p.sku().equal(cols.sku)
        }
    )
    .innerJoin(al, { cols -> [al.MarginThreshold] },
        { cols ->
            exprs.and(
                al.BusinessUnit.equal(cols.BusinessUnit),
                al.MarginThreshold.greaterThan(cols.MarginAdjPct),
            )
        }
    )
    .stream { it.collect { it } }

queryData.each {
    workflow.addApprovalStep("Approval level ${it.MarginThreshold} not met for product ${it.sku}")
        .withUserGroupApprovers("ProductManager")
        .withMinApprovalsNeeded(1)
        .withReasons("Margin for ${it.sku} is only ${it.MarginAdjPct}, should be at least ${it.MarginThreshold}")
}