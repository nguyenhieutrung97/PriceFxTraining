def item = api.currentItem()
api.trace("currentItem = ${item}")

def cost = item?.attribute1
def exchangeRate = 1.1

api.trace("cost = ${cost}")
return cost != null ? cost * exchangeRate : null