def qapi = api.queryApi()
def exprs = qapi.exprs()
def sku = api.product("sku")  //1

//2 3
def isNewBatch =    api.global.currentBatch == null   || !api.global.currentBatch.contains(sku)
//(4)
if (isNewBatch) {
//(5,6)
    api.global.currentBatch =
            api.getBatchInfo()?.collect { it.first() }?.unique() ?: ([sku] as Set)
}
//(7)
if (isNewBatch) {


    api.logInfo("NewBatchOfSKUs: ", api.jsonEncode(api.global.currentBatch))


    def t1 = qapi.tables().productExtensionRows("ProductCost")
//(8,9)
    api.global.productCosts = qapi.source(t1, [t1.AvgCost, t1.sku()],
            exprs.and(
                    t1.sku().in(api.global.currentBatch as List)
            )
    )
            .stream {
                it.collectEntries { [(it.sku): (it.AvgCost as BigDecimal)] }
                //(10)
            }
}
//(11)
return api.global.productCosts[sku]