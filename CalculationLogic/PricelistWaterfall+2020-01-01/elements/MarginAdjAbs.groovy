// we checked for null in MarginAdjPct and return 0 so no need to check here,
// but if this value could be null you need to do something to make sure
// you don't have a null in your final calculation.

if(out.MarginAdjPct == 1) {
    api.addWarning("Margin Adjustment cannot be 100% -> division by 0")
    return 0
}

return out.BasePrice * out.MarginAdjPct