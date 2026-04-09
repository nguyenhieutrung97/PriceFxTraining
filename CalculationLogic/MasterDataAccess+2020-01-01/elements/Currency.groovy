//def filter = Filter.equal("customerId", out.Customer)
//def cus = api.find("C", 0, 1, "customerId", filter)

//return cus ? cus?.attribute13 : null

return api.customer("attribute13", out.Customer)