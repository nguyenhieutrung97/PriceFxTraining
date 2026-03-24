def start = 0
def data = null

def productInfo=[:]
List<String> fields = ["sku", "attribute5"]

while (data = api.find("P", start, api.getMaxFindResultsLimit(), "sku", fields, Filter.equal("sku", "MB-0001"))) {
    start += data.size()
    for (row in data) {
        productInfo= [
                "sku": row.sku,
                "productGroup": row.attribute5,
        ]
        api.trace("Row: ", productInfo)
    }
}
return productInfo