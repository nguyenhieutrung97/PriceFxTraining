def qapi = api.queryApi()
def exprs = qapi.exprs()
def orders = qapi.orders()
def t1 = qapi.tables().products()
def t2 = qapi.tables().productExtensionRows("ListPrice")
def t3 = qapi.tables().companyParameterRows("AttributeAdj")
def t4 = qapi.tables().companyParameterRows("MarginAdj")
def t5 = qapi.tables().companyParameterRows("MarginApproval")
def t6 = qapi.tables().companyParameterRows("Region")
def date = api.targetDate()
return qapi.source(t1, [t1.sku(), t1.ProductGroup, t1.ProductLifeCycle])

        .innerJoin(t2, { cols -> [t2.ValidFrom, t2.ListPrice.as("AverageCost")]},
                { cols ->
                    exprs.and(
                            t2.sku().equal(cols.sku),
                            t2.ValidFrom.lessOrEqual(exprs.dateOnly(date)),
                            t2.Currency.equal(out.Currency)
                    )
                }
        )
        .leftOuterJoin(t3, { cols -> [t3.AttributeAdj]},
                { cols ->
                    exprs.and(
                            t3.key1().equal(cols.ProductLifeCycle)
                    )
                }
        )
        .leftOuterJoin(t4, { cols -> [t4.key1(), t4.value().as("MarginAdjPCT")]},
                { cols ->
                    exprs.and(
                            t4.key1().equal(cols.ProductGroup)
                    )
                }
        )

        .leftOuterJoin(t5, { cols -> [t5.MinMarginThreshold.as("Threshold")]},
                { cols ->
                    exprs.and(
                            t5.ProductGroup.equal(cols.ProductGroup),
                            t5.Region.equal(out.Region)
                    )
                }
        )
        .filter { cols ->
            exprs.and(
                    cols.sku.equal(api.product("ProductId"))
            )
        }
        .sortBy { cols -> [orders.descNullsFirst(cols.ValidFrom)] }
        .stream { it.find()}