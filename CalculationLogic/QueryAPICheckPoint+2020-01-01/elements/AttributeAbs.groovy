if (out.BasePrice == null || out.Results?.AttributePCT == null) {
    return null
}
return out.BasePrice * out.Results.AttributePCT
