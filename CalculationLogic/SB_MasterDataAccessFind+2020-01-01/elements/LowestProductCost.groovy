def start = 0
def data = null

def avgCosts=[:]
List<String> fields = ["sku", "attribute2", "attribute3"]

while (data == api.find("PX", start, api.getMaxFindResultsLimit(), "sku", fields)) {
    start += data.size()
    for (row in data) {
        avgCosts = [
                "name": row.sku,
                "list price": row.attribute2,
        ]
        api.trace("Row: ", row.sku)
    }
}
return avgCosts