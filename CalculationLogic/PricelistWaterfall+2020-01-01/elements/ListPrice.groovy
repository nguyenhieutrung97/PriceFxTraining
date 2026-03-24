if(null in [out.BasePrice, out.MarginAdjAbs, out.AttributeAdjAbs]) {
    api.criticalAlert("List Price cannot be calculated: missing parameter(s).")
    return null
}

return (out.BasePrice + out.MarginAdjAbs + out.AttributeAdjAbs)