def start = 0
def data = null

def customerInfo=[:]
List<String> fields = ["name", "attribute5"]

while (data = api.find("C", start, api.getMaxFindResultsLimit(), "name", fields, Filter.equal("name", "Spagetti M"))) {
    start += data.size()
    for (row in data) {
        customerInfo= [
                "name": row.name,
                "region": row.attribute5,
        ]
        api.trace("Row: ", customerInfo)
    }
}
return customerInfo