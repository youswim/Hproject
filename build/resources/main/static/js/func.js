var imageNr = 0; // Serial number of current image
var finished = new Array(); // References to img objects which have finished downloading
var paused = false;

var state = "init";
var ledtime = "init";

function adm_fun(){
    check_led_time();
    check_state();
    createImageLayer();

}
function check_led_time(){
    const timer = setInterval(function (){
        $.ajax({
            type: 'GET',
            url: '/ledtime/',
            success: function (response){
                if(ledtime == response[0])
                    return;
                if(ledtime != response.ledTime) {
                    console.log(response.ledTime)
                    ledtime = response.ledTime
                    let tempHtml = Addstate(ledtime);
                    $('#ledtime *').remove();
                    $('#ledtime').append(tempHtml);
                }
            }

        })

    }, 100)
}



function check_state(){
    console.log("chect_state 실행!!");
    const timer = setInterval(function(){
        $.ajax({
            type: 'GET',
            url: '/state/',
            success: function (response) {
                if (state == response[0])
                    return;
                if (state != response.state) {
                    console.log(response.state)
                    state = response.state;
                    let tempHtml = Addstate(state);
                    $('#state *').remove()
                    $('#state').append(tempHtml)//append(tempHtml);
                }
            }
        })
    }, 500);
}


function Addstate(state){
    return `<div>${state}</div>`
}

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

function ShowRoads() {
    $.ajax({
        type: 'GET',
        url: '/api/roads',
        success: function (response) {
            console.log("반응 성공");
            for (let i = 0; i < response.length; i++) {
                let road = response[i];
                let tempHtml = AddRoad(road);
                $('#roads').append(tempHtml);
            }
        }
    })
}

function AddRoad(road) {
    return `<div>${road.spot_nm}</div>
            <div>${road.spot_num}</div>`
}

function ShowRoadInfo() {
    let roadId = $('#roadId').val();
    let date = $('#date').val();
    let time = $('#time').val();
    if (roadId == "" || date == "" || time == "") {
        alert('모든 값을 채워주세요');
        return;
    }

    $.ajax({
        type: 'GET',
        url: `/api/road_info/${roadId}/${date}/${time}`,
        success: function (response) {
            console.log("반응 성공");
            $('#roadInfo *').remove();
            for (let i = 0; i < response.length; i++) {
                let roadInfo = response[i];
                let tempHtml = AddRoadInfo(roadInfo);

                $('#roadInfo').append(tempHtml);
            }
        }
    })
}

function AddRoadInfo(roadInfo) {
    return `<div>io type : ${roadInfo.io_type}</div>
            <div>lane num : ${roadInfo.lane_num}</div>
            <div>vol : ${roadInfo.vol}</div>`
}

function led1_ON() {
    $.ajax({
        type: 'GET',
        url: '/led1/on',
        success: function (response) {
            console.log(response);
        }
    })
}

function led2_ON() {
    $.ajax({
        type: 'GET',
        url: '/led2/on',
        success: function (response) {
            console.log(response);
        }
    })
}
