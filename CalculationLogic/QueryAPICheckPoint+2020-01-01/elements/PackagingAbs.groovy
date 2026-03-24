def packagingPct = null
try {
    packagingPct = out.Results?.PackagingPCT
} catch (Exception ignored) {
    packagingPct = null
}

if (out.BasePrice == null || packagingPct == null) {
    return null
}
return out.BasePrice * packagingPct
