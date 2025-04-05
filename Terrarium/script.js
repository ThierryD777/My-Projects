function moveObject(terrariumObject) {
    let pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    terrariumObject.onpointerdown = pointerMove;


    function pointerMove(e) {
        e.preventDefault();
        console.log(e);
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onpointermove = objectMove;
        document.onpointerup = stopObject
    }

    function objectMove(e) {
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        pos3 = e.clientX;
        pos4 = e.clientY;
        
        console.log(pos1, pos2, pos3, pos4);
        terrariumObject.style.top = (terrariumObject.offsetTop - pos2) + 'px';
        terrariumObject.style.left = (terrariumObject.offsetLeft - pos1) + 'px';
    }

    function stopObject() {
        document.onpointerup = null;
        document.onpointermove = null;
    }
}
moveObject(document.getElementById('plant1'));
moveObject(document.getElementById('plant2'));
moveObject(document.getElementById('plant3'));
moveObject(document.getElementById('plant4'));
moveObject(document.getElementById('plant5'));
moveObject(document.getElementById('plant6'));
moveObject(document.getElementById('plant7'));
moveObject(document.getElementById('plant8'));
moveObject(document.getElementById('plant9'));
moveObject(document.getElementById('plant10'));
moveObject(document.getElementById('plant11'));
moveObject(document.getElementById('plant12'));
moveObject(document.getElementById('plant13'));
moveObject(document.getElementById('plant14'));
