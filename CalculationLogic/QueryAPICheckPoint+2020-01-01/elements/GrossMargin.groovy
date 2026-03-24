if (out.ListPrice == null || out.BasePrice == null || out.ListPrice == 0) {
    return null
}
return (out.ListPrice - out.BasePrice) / out.ListPrice
