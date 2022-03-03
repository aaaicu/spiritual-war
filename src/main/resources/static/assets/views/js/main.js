function join(userIdx,gameIdx) {

    $.get("/game/join", {"gameIdx":gameIdx, "userIdx" : userIdx}, function () {
        window.location.href = '/view/home';
    });
}

