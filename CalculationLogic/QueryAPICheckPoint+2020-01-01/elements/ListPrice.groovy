if (out.BasePrice == null) {
    return null
}
return out.BasePrice + (out.MarginAbs ?: 0) + (out.AttributeAbs ?: 0) + (out.PackagingAbs ?: 0) + (out.TaxTariffAbs ?: 0)
