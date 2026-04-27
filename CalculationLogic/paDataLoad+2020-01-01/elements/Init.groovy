// Define a TARGET ( destination ) for the records to be copied
def target = api.getDatamartRowSet("target") // define -target- list object
def qapi = api.queryApi()

// Instantiate a reference to the PriceList table ( .priceLists() )
def t1 = qapi.tables().priceLists()  // Get Price List Table

// instantiate an object to hold a ew row to be added to Analytic table
def newRow

// Iterate over the Pricelists table entries
qapi.source(t1, [t1.id, t1.targetDate, t1.approvalState])
    .stream {

        it.each { row ->
            // Ensure we pickup only pricelists that are APPROVED,
            if (row.approvalState == "APPROVED" && row.targetDate != null) {

                // Pickup a reference to the PriceList LINEITEMS table.
                def t2 = qapi.tables().priceListLineItems(row.id)

                // Pickup from the PriceList LineItems table the fields
                // we shall be storing into the Datamart.
                qapi.source(t2, [t2.sku, t2.resultPrice, t2.currency]).stream {
                    it.each {
                        pli ->
                            /* Add the desired fields from the PriceList
                               Items table to the -target- List object.
                               ( see line 2 of code )
                               Note that this object ( target ) will be
                               picked up and used by the CDL we
                               will define in the Pricefx UI
                              */
                            newRow = [
                                "ProductID"  : pli.sku,
                                "Currency"   : pli.currency,
                                "Targetdate" : row.targetDate,
                                "ResultPrice": pli.resultPrice,
                            ]
                            api.trace("-NewRow-", newRow)
                    }
                }
            }
        }
    }