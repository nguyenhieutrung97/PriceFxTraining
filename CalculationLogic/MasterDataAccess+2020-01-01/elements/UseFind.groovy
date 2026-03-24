def start = 0
def data = null

def productInfo=[:]
List<String> fields = ["sku", "attribute1", "attribute2"]

while (data = api.find("P", start, api.getMaxFindResultsLimit(), "sku", fields)) {
    start += data.size()
    for (row in data) {
        productInfo= [
                "sku"         : row.sku,
                "ProductGroup": row.attribute1,
                "BusinessUnit": row.attribute2,
        ]
        api.trace("Row: ", productInfo)
    }
}
return productInfo