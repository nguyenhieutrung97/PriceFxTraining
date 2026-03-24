def taxTariffPct = null
try {
    taxTariffPct = out.Results?.TaxTariffPCT
} catch (Exception ignored) {
    taxTariffPct = null
}

if (out.BasePrice == null || taxTariffPct == null) {
    return null
}
return out.BasePrice * taxTariffPct
