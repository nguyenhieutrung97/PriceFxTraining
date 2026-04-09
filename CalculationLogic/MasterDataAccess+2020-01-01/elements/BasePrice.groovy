def filters = [
    Filter.equal("name", "ProductCost"),
    Filter.equal("sku", out.Product)
]

def px = api.find("PX", 0, 1, "sku", *filters)
return px ? px.getAt(0)?.attribute1 : null