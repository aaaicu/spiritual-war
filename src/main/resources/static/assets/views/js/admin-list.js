function newGame() {
    if (confirm("새게임을 만들겠습니까") === false ) return;

    requestCreateGame(getGameSetting());
}

function getGameSetting() {
    return {
        "gameSetting": {
            totalRound: 5,
            afternoonMinute: 8,
            nightMinute: 4,
            holidayNightMinute: 12,
            citizen: 12,
            devil: 3
        }
    };
}

function requestCreateGame(data) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
        , type: "POST"
        , url: "/game/open"
        , data: JSON.stringify(data)
        , success: function (resultGame) {

            // TODO 일단 Reload 하는 방식으로 구현
            plusNewGameToList();
        }
        , dataType: "json"
    });
}


function plusNewGameToList() {
    window.location.href = '/view/admin/game/list';
}

function goGameDetail(gameIdx) {
    window.location.href = '/view/admin/game/detail?gameIdx='+gameIdx;
}