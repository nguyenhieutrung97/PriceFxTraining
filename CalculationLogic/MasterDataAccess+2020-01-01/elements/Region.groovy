//def filter = Filter.equal("customerId", out.Customer)
//def cus = api.find("C", 0, 1, "customerId", filter)
//
//return cus ? cus?.attribute5 : null

return api.customer("attribute5", out.Customer, true)