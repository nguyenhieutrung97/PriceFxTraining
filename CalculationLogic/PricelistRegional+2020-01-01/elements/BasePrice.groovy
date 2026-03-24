def avgCost = out.Calculations?.AverageCost
if(avgCost == null && out.Region=="Europe") {
    api.addWarning("Could not find Average Cost in PX table ProductCost Aborting Calculation")
    api.abortCalculation()
    return null
}
else if(avgCost == null ){
    avgCost=2.00
}
else{
    return avgCost
}