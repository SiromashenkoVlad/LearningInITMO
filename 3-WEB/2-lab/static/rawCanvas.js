const canvas = document.getElementById("cns")
const ctx = canvas.getContext("2d")

const offset = 20, widthCanvas = canvas.width, widthBigLine = widthCanvas / 20
const widthSmallLine = widthBigLine / 2
const cntSteps = 10, space = (widthCanvas - (2 * offset)) / cntSteps

function rawAxisCoordinates() {
    ctx.moveTo(widthCanvas / 2, offset)
    ctx.lineTo(widthCanvas / 2, widthCanvas - offset)

    ctx.moveTo(offset, widthCanvas / 2)
    ctx.lineTo(widthCanvas - offset, widthCanvas / 2)

    ctx.moveTo(widthCanvas / 2 - offset, offset + offset)
    ctx.lineTo(widthCanvas / 2, offset)
    ctx.lineTo(widthCanvas / 2 + offset, offset + offset)

    ctx.moveTo(widthCanvas - 2 * offset, widthCanvas / 2 - offset)
    ctx.lineTo(widthCanvas - offset, widthCanvas / 2)
    ctx.lineTo(widthCanvas - 2 * offset, widthCanvas / 2 + offset)
}

function rawBigSteps() {
    for (var step = 0; step < cntSteps - 1; step++) {
        ctx.moveTo((widthCanvas / 2) - (widthBigLine / 2), offset + (step + 1) * space)
        ctx.lineTo((widthCanvas / 2) + (widthBigLine / 2), offset + (step + 1) * space)

        ctx.moveTo(offset + (step + 1) * space, (widthCanvas / 2) - (widthBigLine / 2))
        ctx.lineTo(offset + (step + 1) * space, (widthCanvas / 2) + (widthBigLine / 2))
    }
}

function rawSmallSteps() {
    for (var step = 0; step < cntSteps; step++) {
        ctx.moveTo(offset + (step * space) + space / 2, (widthCanvas / 2) - (widthSmallLine / 2))
        ctx.lineTo(offset + (step * space) + space / 2, (widthCanvas / 2) + (widthSmallLine / 2))

        ctx.moveTo((widthCanvas / 2) - (widthSmallLine / 2), offset + (step * space) + space / 2)
        ctx.lineTo((widthCanvas / 2) + (widthSmallLine / 2), offset + (step * space) + space / 2)
    }
}

function rawFigure(rad = 1) {
    ctx.moveTo(widthCanvas / 2, widthCanvas / 2)
    ctx.lineTo(widthCanvas / 2 + (space * rad), widthCanvas / 2)
    ctx.lineTo(widthCanvas / 2 + (space * rad), widthCanvas / 2 - (space * rad))
    ctx.lineTo(widthCanvas / 2, widthCanvas / 2 - (space * rad))
    ctx.arc(widthCanvas / 2, widthCanvas / 2, rad * space, -Math.PI / 2, -Math.PI, true)
    ctx.lineTo(widthCanvas / 2 - (space * rad) / 2, widthCanvas / 2)
    ctx.lineTo(widthCanvas / 2, widthCanvas / 2 + (space * rad) / 2)
    ctx.lineTo(widthCanvas / 2, widthCanvas / 2)
}

function rawXYZeroWords() {
    ctx.font = "22px sans-serif"

    ctx.textAlign = "left";
    ctx.textBaseline = "alphabetic";
    ctx.fillText("0", widthCanvas / 2, widthCanvas / 2)
    ctx.fillText("y", widthCanvas / 2, offset)
    ctx.fillText("x", widthCanvas - offset, widthCanvas / 2)

    ctx.textAlign = "center"
    for (var step = 1; step < cntSteps / 2; step++) {
      ctx.fillText(step,widthCanvas / 2 + step * space, widthCanvas / 2 - widthBigLine / 2)
      ctx.fillText(-step,widthCanvas / 2 - step * space, widthCanvas / 2  - widthBigLine / 2)
    }

    ctx.textBaseline= "middle"
    ctx.textAlign = "left"
    for (step = 1; step < cntSteps / 2; step++) {
      ctx.fillText(-step, widthCanvas / 2 + widthBigLine / 2, widthCanvas / 2 + step * space)
      ctx.fillText(step, widthCanvas / 2  + widthBigLine / 2, widthCanvas / 2 - step * space)
    }
}

function rawPoint(x, y, r, status){
    ctx.beginPath()
    if (status){
        ctx.fillStyle = "red"
    }
    ctx.arc(widthCanvas/ 2 + x * space, widthCanvas / 2 - y * space,
        3, 0, Math.PI * 2)
    ctx.stroke()
    ctx.fill()
    ctx.closePath()
    ctx.fillStyle = "black"
    return status
}

async function checkPointInFigure(x, y, r){
    const params = new URLSearchParams();
    params.append("x", x)
    params.append("y", y)
    params.append("r", r)
    const resp = await makeRequest(params)
    // console.log(await resp.text())
    const json = await resp.json()
    if (json.status === "ok"){
        return json.hit
    }
    console.log(json.message)
    return -1
}

async function makeRequest(params){
    const url = "fcgi-bin/server.jar"
    try{
        const req = new Request(url, {method : "POST", body : params})
        const responce = await fetch(req)
        if(!responce.ok){
            throw new Error(`Responce status: ${responce.status}`)
        }
        return responce

    } catch (e) {
        console.error(e.message)
        throw new Error(`Cant send request: ${e.message}`)
    }
}

function rawCanvas(rad = 1){
    ctx.beginPath()
    rawFigure(rad)
    ctx.closePath()
    ctx.fillStyle = "green"
    ctx.fill()

    ctx.fillStyle = "black"
    ctx.beginPath()
    rawAxisCoordinates()
    rawBigSteps()
    rawSmallSteps()
    ctx.closePath()
    ctx.stroke()

    ctx.fillStyle = "black"
    rawXYZeroWords()
}


