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

function ShowRoads(){
    $.ajax({
        type: 'GET',
        url: '/api/roads',
        success: function(response){
            console.log("반응 성공");
            for(let i=0; i<response.length; i++){
                let road = response[i];
                let tempHtml = AddRoad(road);
                $('#roads').append(tempHtml);
            }
        }
    })
}

function AddRoad(road){
    return `<div>${road.spot_nm}</div>
            <div>${road.spot_num}</div>`
}

function ShowRoadInfo(){
    let roadId = $('#roadId').val();
    let date = $('#date').val();
    let time = $('#time').val();
    if(roadId=="" || date=="" || time==""){
        alert('모든 값을 채워주세요');
        return;
    }

    $.ajax({
        type: 'GET',
        url: `/api/road_info/${roadId}/${date}/${time}`,
        success: function (response){
            console.log("반응 성공");
            for(let i=0; i<response.length; i++){
                let roadInfo = response[i];
                let tempHtml = AddRoadInfo(roadInfo);
                $('#roadInfo').append(tempHtml);
            }
        }
    })
}

function AddRoadInfo(roadInfo){
    return `<div>io type : ${roadInfo.io_type}</div>
            <div>lane num : ${roadInfo.lane_num}</div>
            <div>vol : ${roadInfo.vol}</div>`
}

function LedInit(){

    $.ajax({
        type: 'GET',
        url: '/led/init',
        success: function(response){
            console.log("반응성공")
            alert(response);
        }
    })
}

function LedOn(){
    $.ajax({
        type: 'GET',
        url: '/led/on',
        success: function(response){
            alert(response);
        }
    })
}
function LedOff(){
    $.ajax({
        type: 'GET',
        url: '/led/off',
        success: function(response){
            alert(response);
        }
    })
}

function Buon(){
    alert("버튼을 클릭했습니다.");
}
