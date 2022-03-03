window.onload = function () {

    fetchStatus(1);

};


function changeStatus(gameIdx) {
    $.get("/game/change/status", {"gameIdx":gameIdx, "req": $("#btn_game").data('req')}, function () {
        fetchStatus(gameIdx);
        window.location.href = '/view/home';
    });

}


function fetchStatus(gameIdx) {
    // 임시로 하드코딩
    $.get("/game/status", {"gameIdx":gameIdx}, function (str) {
        console.log(str);
        if (str === "START") {
            $("#btn_game").val("게임종료");
            $("#btn_game").data("req", "STOP");
        } else if (str === "WAIT") {
            $("#btn_game").val("게임시작");
            $("#btn_game").data("req", "START");
        } else {
            $("#btn_game").val("끝");
            $("#btn_game").attr("disabled", true);
            $("#btn_game").data("req", "");
        }

    });
}