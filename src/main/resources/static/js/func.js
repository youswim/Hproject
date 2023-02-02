var imageNr = 0; // Serial number of current image
var finished = new Array(); // References to img objects which have finished downloading
var paused = false;

var state = "init";
var ledtime = "init";

function adm_fun() {
    check_light_state()
    createImageLayer();

}

function check_light_state() {
    const timer = setInterval(
        function () {
            $.ajax({
                type: 'GET',
                url: '/ledtime/',
                success: function (response) {
                    const obj = JSON.parse(response)
                    addState(obj.light_number + "번 신호등, " + obj.time + "초")
                }
            });
        }, 100
    );
}

function addState(state) {
    document.getElementById("light-state").innerText = state;
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
    return `<div>${road.spot_nm} : ${road.spot_num}</div><br>`
}

function ShowRoadInfo() {
    let rid = $('#rid').val();
    let date = $('#date').val();
    let time = $('#time').val();
    if (rid == "" || date == "" || time == "") {
        alert('모든 값을 채워주세요');
        return;
    }


    $.ajax({
        type: 'GET',
        url: `/api/road_info`,
        data: {
            rid: rid,
            date: date,
            time: time
        },
        success: function (response) {
            $('#road_info_table_tbody *').remove();
            resetErrorMessages();
            for (let i = 0; i < response.length; i++) {
                let roadInfo = response[i];
                let tempHtml = AddRoadInfo(roadInfo);

                $('#road_info_table_tbody').append(tempHtml);
            }
        },
        error: function (response) {
            resetErrorMessages();
            let errors = response.responseJSON.errors
            console.log(errors)
            addRoadInfoErrors(errors)
        }
    })
}

function AddRoadInfo(roadInfo) {
    // return `<div>io type : ${roadInfo.io_type}</div>
    //         <div>lane num : ${roadInfo.lane_num}</div>
    //         <div>vol : ${roadInfo.vol}</div>`
    return `<tr>
                <td class="td">${roadInfo.io_type}</td>
                <td class="td">${roadInfo.lane_num}</td>
                <td class="td">${roadInfo.vol}
            </td>`
}

function addRoadInfoErrors(errors) {
    console.log(errors)
    for (let i = 0; i < errors.length; i++) {
        let error = errors[i];
        let errorElementId = error.field + "-error";
        let errorElementMessage = error.message;
        console.log(errorElementId);
        console.log(errorElementMessage);
        document.getElementById(errorElementId).innerText = errorElementMessage;
    }
}

function resetErrorMessages() {
    let errorClassElements = document.getElementsByClassName("error");
    console.log(errorClassElements)
    for (let i = 0; i < errorClassElements.length; i++) {
        console.log("hello")
        console.log(errorClassElements.item(i).id);
        document.getElementById(errorClassElements.item(i).id).innerHTML = "";
    }
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
