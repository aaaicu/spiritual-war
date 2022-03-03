window.onload = function () {


};

function genDevilCode(memberIdx) {
    $.get("/game/devil-code", {"memberIdx": memberIdx}, function (code) {

        if (code === "") {
            alert('코드를 생성할 수 없습니다.');
        } else {

            $('#devil_code').text(code);
        }
    });
}


function saveDevilCode(memberIdx) {
    if ($('#input_save_code').val() === "") {
        alert('코드를 입력하세요');
        return;
    }

    $.get("/game/register/devil-code", {
        "memberIdx": memberIdx, "devilCode": $('#input_save_code').val()
    }, function (result) {
        alert(result);
    });
}
