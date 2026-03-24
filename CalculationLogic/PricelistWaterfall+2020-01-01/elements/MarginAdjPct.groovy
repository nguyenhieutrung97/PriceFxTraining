def marginAdjPct = out.Calculations?.MarginAdjPCT


if(marginAdjPct == null) {
    api.addWarning("Unable to look up Margin Adjustment with the Product Group")

    return 0
}

return marginAdjPct
