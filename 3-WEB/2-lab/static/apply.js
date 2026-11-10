async function handleFromSubmit(event){
    event.preventDefault()
    const formData = new FormData(form)
    formData.set("labelY", formData.get("labelY").replace(',', '.'))
    const radioX = document.querySelector('input[name="labelX"]:checked')
    const radioR = document.querySelector('input[name="selectR"]:checked')

    if (checkRadio(radioX, setX) && checkCoord(-3, 5, labelY) && checkRadio(radioR, setR)) {
        const x = radioX.value
        const y = formData.get("labelY")
        const r = radioR.value
        const status = await checkPointInFigure(x, y, r)
        if (status !== -1){
            rawPoint(x, y, r, status)
            addPointRow(formData.get("labelX"), formData.get("labelY"), formData.get("selectR"), status)
        }
    }
}

function updateCanvas(){
    const radio = document.querySelector('input[name="selectR"]:checked')
    if (!checkRadio(radio, setR)){
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

function checkRadio(radio, setValuesOfSelect){
    console.log(radio.value, typeof radio.value, !setValuesOfSelect.has(radio.value))
    if (!setValuesOfSelect.has(radio.value)){
        radio.setCustomValidity("Разрешается использовать только значения установленные по умолчания")
        return false
    }
    radio.setCustomValidity("")
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
    addCellWithTextInRow(row, x, y, r, Intl.DateTimeFormat("ru-RU").format((timeNow)),
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
    locStore.setItem(locStore.length + 1, texts.join(","))
}

function restoreTableData(){
    for (var ind = locStore.length + 1; ind > 0; ind--){
        var rowData = locStore.getItem(ind)
        if (rowData != null){
            rowData = rowData.split(",")
            rawPoint(rowData[0], rowData[1], rowData[2], rowData[5] === "Попадание")
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
            rawPoint(rowData[0], rowData[1], rowData[2], rowData[5] === "Попадание")
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

const radioX_5 = document.getElementById("radioX-5")
radioX_5.onchange = () => checkRadio(radioX_5, setX)

const radioX_4 = document.getElementById("radioX-4")
radioX_4.onchange = () => checkRadio(radioX_4, setX)

const radioX_3 = document.getElementById("radioX-3")
radioX_3.onchange = () => checkRadio(radioX_3, setX)

const radioX_2 = document.getElementById("radioX-2")
radioX_2.onchange = () => checkRadio(radioX_2, setX)

const radioX_1 = document.getElementById("radioX-1")
radioX_1.onchange = () => checkRadio(radioX_1, setX)

const radioX0 = document.getElementById("radioX0")
radioX0.onchange = () => checkRadio(radioX0, setX)

const radioX1 = document.getElementById("radioX1")
radioX1.onchange = () => checkRadio(radioX1, setX)

const radioX2 = document.getElementById("radioX2")
radioX2.onchange = () => checkRadio(radioX2, setX)

const radioX3 = document.getElementById("radioX3")
radioX3.onchange = () => checkRadio(radioX3, setX)


const radioR1 = document.getElementById("inputR1")
radioR1.onchange = () => checkRadio(radioR1, setR)

const radioR1_5 = document.getElementById("inputR1_5")
radioR1_5.onchange = () => checkRadio(radioR1_5, setR)

const radioR2 = document.getElementById("inputR2")
radioR2.onchange = () => checkRadio(radioR2, setR)

const radioR2_5 = document.getElementById("inputR2_5")
radioR2_5.onchange = () => checkRadio(radioR2_5, setR)

const radioR3 = document.getElementById("inputR3")
radioR3.onchange = () => checkRadio(radioR3, setR)


radioR1.onchange = () => updateCanvas()
radioR1_5.onchange = () => updateCanvas()
radioR2.onchange = () => updateCanvas()
radioR2_5.onchange = () => updateCanvas()
radioR3.onchange = () => updateCanvas()



const labelY = document.getElementsByName("labelY")[0]
labelY.onchange = () => checkCoord(-3, 5, labelY)

const locStore = window.localStorage;

const tableForRow = document.getElementById("resultTable")

const setR = new Set(['1', '1.5', '2', '2.5', '3'])
const setX = new Set(['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'])