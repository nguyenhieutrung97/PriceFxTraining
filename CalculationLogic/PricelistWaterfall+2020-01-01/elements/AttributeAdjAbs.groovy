// we checked for null in AttributeAdjPct and return 0 so no need to check here,
// but if this value could be null you need to do something to make sure
// you don't have a null in your final calculation.

if(out.AttributeAdjPct == 1) {
    api.addWarning("Attribute Adjustment cannot be 100% -> division by 0")
    return null
}

return out.BasePrice * out.AttributeAdjPct