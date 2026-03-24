def avgCost = out.Calculations?.AvgCost
if(avgCost == null) {
    api.addWarning("Could not find Average Cost in PX table ProductCost")
    return null
}
return avgCost