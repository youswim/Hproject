function adm_fun() {
    check_light_state()
    // createImageLayer();
    setEvents();
}

function check_light_state() {
    const timer = setInterval(
        function () {
            $.ajax({
                type: 'GET',
                url: '/led-time/',
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

function setEvents() {

    let elements = document.getElementsByClassName("btn");
    for(let i = 0; i<elements.length; i++){
        elements.item(i).onclick = function (event){
            let lightNumber = event.target.value;
            console.log(lightNumber)
            $.ajax({
                url: '/light',
                type: 'POST',
                data: {
                    lightNumber: lightNumber
                }
            });
        }
    }
}