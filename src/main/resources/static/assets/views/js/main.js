function join(userIdx,gameIdx) {

    $.get("/game/join", {"gameIdx":gameIdx, "userIdx" : userIdx}, function (participationIdx) {

        // subscribe(participationIdx);
        window.location.href = '/view/home';
    });
}
function outGame(userIdx, gameIdx) {

    $.get("/game/out", {"gameIdx":gameIdx, "userIdx" : userIdx}, function () {
        window.location.href = '/view/home';
    }).fail(function (error) {
        alert(error.responseJSON.message);
    })
}



function subscribe(participationIdx) {
    console.log(participationIdx);

    const eventSource = new EventSource('/notification/subscribe/' + participationIdx);

    eventSource.onopen = (e) => {
        console.log(e);
    };

    eventSource.onerror = (e) => {
        console.log(e);
    };

    eventSource.onmessage = (e) => {
        console.log(e);
    };

}


