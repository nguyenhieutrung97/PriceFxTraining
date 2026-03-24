def qapi = api.queryApi()
def exprs = qapi.exprs()

def t1 = qapi.tables().datamart("Transaction")

return qapi.source(t1, [t1.TransactionId, t1.alerts, t1.BusinessUnit, t1.BuyingGroup, t1.Competition, t1.completeResultsAvailable, t1.Cost, t1.Country, t1.Currency, t1.CustomerClass, t1.CustomerCurrency, t1.CustomerGroup, t1.CustomerId, t1.CustomerInfo, t1.CustomerName, t1.CustomerType, t1.Discount, t1.formulaDetailedResults, t1.formulaResult, t1.IncoTerms, t1.InvoiceDate, t1.InvoiceDateMonth, t1.InvoiceDateQuarter, t1.InvoiceDateWeek, t1.InvoiceDateYear, t1.InvoicePrice, t1.isDeleted, t1.Label, t1.lastUpdateDate, t1.ListPrice, t1.Margin, t1.PaymentTerms, t1.ProductClass, t1.ProductGroup, t1.ProductId, t1.ProductLifeCycle, t1.ProductUnit, t1.Quantity, t1.Region, t1.SalesOrg, t1.SalesPerson, t1.Segmentation, t1.Service, t1.Size, t1.warnings])
        .stream { it.collect { it } }