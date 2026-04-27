def target = api.getDatamartRowSet("target")
def qapi = api.queryApi()

def t1 = qapi.tables().productExtensionRows("ProductCost")

qapi.source(t1, [t1.AvgCost, t1.sku()])
    .stream {
        it.each { row ->
            if (!row.sku) {
                return
            }
            newRow = [
                "ProductID": row.sku,
                "AvgCost"  : row.AvgCost
            ]
            target?.addRow(newRow)
        }
    }