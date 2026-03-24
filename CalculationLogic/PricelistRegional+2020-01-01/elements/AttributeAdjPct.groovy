
def attributeAdjPct = out.Calculations?.AttributeAdj

if(attributeAdjPct == null) {
    api.yellowAlert("Unable to look up Attribute Adjustment with the Product Life Cycle")
    return 0
}

return attributeAdjPct as BigDecimal