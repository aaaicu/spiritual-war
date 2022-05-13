window.onload = function () {
    fetchStatus(getGameIdxFromUrlParams());
};

function plusNewGameToList() {
    window.location.href = '/view/admin/game/list';
}

function fetchStatus(gameIdx) {

    $.get("/game/status", {"gameIdx":gameIdx}, function (str) {
        console.log(str);
        if (str === "START") {
            $("#btn_game").val("게임종료");
            $("#btn_game").data("req", "STOP");
        } else if (str === "WAIT") {
            $("#btn_game").val("게임시작");
            $("#btn_game").data("req", "START");
        } else {
            $("#btn_game").val("종료된 게임");
            $("#btn_game").attr("disabled", true);
            $("#btn_game").data("req", "");
        }
    });
}

function changeStatus(gameIdx) {
    $.get("/game/change/status", {"gameIdx": gameIdx, "req": $("#btn_game").data('req')}, function () {
        fetchStatus(gameIdx);
        goGameDetail(gameIdx);

    }).fail(function (error) {
        alert(error.responseJSON.message);
    })
}

function getGameIdxFromUrlParams() {
    const queryString = window.location.search;
    console.log(queryString);

    const urlParams = new URLSearchParams(queryString);

    return urlParams.get('gameIdx');
}

function goGameDetail(gameIdx) {
    window.location.href = '/view/admin/game/detail?gameIdx='+gameIdx;
}