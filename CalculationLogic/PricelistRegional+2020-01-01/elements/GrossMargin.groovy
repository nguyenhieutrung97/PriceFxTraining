def grossMargin= (out.ListPrice-out.BasePrice)/out.BasePrice

def threshold=out.Calculations?.Threshold
if (threshold!=null){
    if (grossMargin<=threshold){
        api.redAlert("Margin under acceptable threshold")
    }
}
return grossMargin