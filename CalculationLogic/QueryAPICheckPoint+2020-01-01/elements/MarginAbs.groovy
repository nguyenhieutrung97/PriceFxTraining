if (out.BasePrice == null || out.Results?.MarginPCT == null) {
    return null
}
return out.BasePrice * out.Results.MarginPCT
