function ShowRoads() {
    $.ajax({
        type: 'GET',
        url: '/api/roads',
        success: function (response) {
            console.log("반응 성공");
            console.log(response)
            for (let i = 0; i < response.length; i++) {
                let road = response[i];
                let tempHtml = AddRoad(road);
                $('#roads').append(tempHtml);
            }
        }
    })
}

function AddRoad(road) {
    return `<div>${road.roadId} : ${road.roadName}</div><br>`
}