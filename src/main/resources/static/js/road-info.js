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
        url: `/api/road-info`,
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
            console.log(response.status)
            resetErrorMessages();
            if(response.status == 400){ // 400은 사용자가 잘못된 형식의 form-data 를 보냈을 때.
                let errors = response.responseJSON.errors
                console.log(errors)
                addRoadInfoErrors(errors)
                return
            }
            if(response.status == 404){ // 404는 요청한 데이터가 서버와 외부 API 에 존재하지 않을 때.
                console.log(response.responseText)
                document.getElementById("404").innerText = response.responseText;
            }
        }
    })
}

function AddRoadInfo(roadInfo) {
    // return `<div>io type : ${roadInfo.io_type}</div>
    //         <div>lane num : ${roadInfo.lane_num}</div>
    //         <div>vol : ${roadInfo.vol}</div>`
    return `<tr>
                <td class="td">${roadInfo.ioType}</td>
                <td class="td">${roadInfo.laneNum}</td>
                <td class="td">${roadInfo.vol}
            </td>`
}

function resetErrorMessages() {
    let errorClassElements = document.getElementsByClassName("error");
    console.log(errorClassElements)
    for (let i = 0; i < errorClassElements.length; i++) {
        console.log("hello")
        console.log(errorClassElements.item(i).id);
        document.getElementById(errorClassElements.item(i).id).innerHTML = "";
    }
    document.getElementById("404").innerText = ""
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
