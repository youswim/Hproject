var imageNr = 0; // Serial number of current image
var finished = new Array(); // References to img objects which have finished downloading
var paused = false;

function createImageLayer() {
    var img = new Image();
    img.style.position = "absolute";
    img.style.zIndex = -1;
    img.onload = imageOnload;
    img.onclick = imageOnclick;


    img.src = "http://192.168.55.2:8091/?action=snapshot&n=" + (++imageNr);

    var webcam = document.getElementById("webcam");
    img.width = webcam.offsetWidth;
    //$('#imag *').remove();
    //$('#imag').append(webcam);
    webcam.insertBefore(img, webcam.firstChild);
}

function imageOnload() {
    this.style.zIndex = imageNr;
    while (1 < finished.length) {
        var del = finished.shift();
        del.parentNode.removeChild(del);
    }
    finished.push(this);
    if (!paused) createImageLayer();
}

function imageOnclick() {
    paused = !paused;
    if (!paused) createImageLayer();
}