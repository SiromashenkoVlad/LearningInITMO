function handleFromSubmit(event){
    event.preventDefault()
    const formData = new FormData(form)
    formData.set("labelX", formData.get("labelX").replace(',', '.'))
    formData.set("labelY", formData.get("labelY").replace(',', '.'))
    if (checkCoord(-3, 3, labelX) && checkCoord(-5, 5, labelY) && checkSelectR()) {
        const status = rawPoint(formData.get("labelX"), formData.get("labelY"), formData.get("selectR"))
        addPointRow(formData.get("labelX"), formData.get("labelY"), formData.get("selectR"), status)

    }
}

function updateCanvas(){
    if (!checkSelectR()){
        return
    }
    ctx.clearRect(0, 0, widthCanvas, widthCanvas);
    rawCanvas(select.value)
    restorePoints()
}

function checkCoord(leftBorder, rightBorder, inputLabel){
    const regex = /^-?\d+([.,]\d+)?$/

    var coordX = inputLabel.value;
    if (coordX === "") inputLabel.setCustomValidity("")

    if(coordX.match(regex) == null) {
        inputLabel.setCustomValidity("Введенные данные не являются корректно введённым числом")
        return false
    }
    coordX = coordX.replace(',', '.')
    coordX = Number(coordX)
    if (coordX < leftBorder || coordX > rightBorder){
        inputLabel.setCustomValidity
        (`Введенное число не принадлежит диапозону: [${leftBorder}; ${rightBorder}]`)
    } else {
        inputLabel.setCustomValidity("")
        return true
    }
    return false
}

function checkSelectR(){
    let setValuesOfSelectR = new Set(['1', '1.5', '2', '2.5', '3'])
    if (!setValuesOfSelectR.has(select.value)){
        select.setCustomValidity("Разрешается использовать только значения установленные по умолчания")
        return false
    }
    return true

}

function addPointRow(x, y, r, status){
    var row = tableForRow.insertRow(1)
    var timeNow = Date.now()
    if (status === true){
        status = "Попадание"
    } else {
        status = "Промах"
    }
    addCellWithTextInRow(row, Number(x)+ Number(r), y, r, Intl.DateTimeFormat("ru-RU").format((timeNow)),
        new Intl.DateTimeFormat('ru-RU', { timeStyle: 'medium' }).format(new Date()), status)
}

function addCellWithTextInRow(row, ...texts){
    makeRowWithCells(row, texts)
    saveDataOfPoints(row, texts)
}

function makeRowWithCells(row, dataCells){
    for (var el of dataCells){
        var newtext = document.createTextNode(el)
        var cell = row.insertCell()
        cell.appendChild(newtext)
    }
}

function saveDataOfPoints(row, ...texts){
    console.log(locStore.length + 1)
    locStore.setItem(locStore.length + 1, texts.join(","))
}

function restoreTableData(){
    console.log(locStore.length)
    for (var ind = locStore.length + 1; ind > 0; ind--){
        var rowData = locStore.getItem(ind)
        if (rowData != null){
            rowData = rowData.split(",")
            rawPoint(rowData[0], rowData[1], rowData[2])
            var row = tableForRow.insertRow()
            makeRowWithCells(row, rowData)
        }
    }
}

function restorePoints(){
    for (let ind = locStore.length + 1; ind > 0; ind--){
        let rowData = locStore.getItem(ind);
        if (rowData != null){
            rowData = rowData.split(",")
            rawPoint(rowData[0], rowData[1], rowData[2])
        }
    }
}

window.onload = () => {
    updateCanvas()
    restoreTableData()
}
const form = document.getElementById("form")
form.addEventListener('submit', handleFromSubmit)

const select = document.getElementsByName("selectR")[0]
select.onchange = updateCanvas

const labelX = document.getElementsByName("labelX")[0]
labelX.onchange = () => checkCoord(-3, 3, labelX)

const labelY = document.getElementsByName("labelY")[0]
labelY.onchange = () => checkCoord(-5, 5, labelY)

const locStore = window.localStorage;

const tableForRow = document.getElementById("resultTable")