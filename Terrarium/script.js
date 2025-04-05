function moveObject(terrariumObject) {
    let pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    terrariumObject.onpointerdown = pointerMove;
}
moveObject(document.getElementById('plant1'));
moveObject(document.getElementById('plant2'));

function pointerMove(e) {
    e.preventDefaukt();
    console.log(e);
    pos3 = e.clientX;
    pos4 = e.cleintY;
    document.onpointermove = objectMove;
    document.onpointerup = stopObject
}