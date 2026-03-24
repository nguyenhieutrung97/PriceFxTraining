def avgCost=[:]

def sku = "MB-0002"

def filters = [
        Filter.equal("sku", sku)
]

def fields = ["attribute2", "attribute3"]

def sortBy = "attribute3"

api.stream("PX", sortBy, fields, *filters)
        .withCloseable { iterator ->
            iterator.each { record ->
                avgCost = [
                        "list price": record.attribute2,
                        "currency": record.attribute3,
                ]
                api.trace("avgCost", avgCost)
            }
        }
return avgCost