function add() {
    var millisecondsToWait = 500;
    setTimeout(function() {
        swal({
            title: "Добавлено в корзину!",
            text: "Вы можете посмотреть товар в корзине!",
            icon: "success",
            button: "Закрыть",
        })
    }, millisecondsToWait);
}

function fail() {
    swal({
        title: "Ошибка!",
        text: "Ошибка на стороне сервера. Повторите ошибку позже.",
        icon: "error",
        button: "Закрыть",
    })
}

function adding(form) {
    var xhr = new XMLHttpRequest();
    xhr.onload = function(){ add() }; // success case
    xhr.onerror = function(){ fail() }; // failure case
    xhr.open (form.method, form.action, true);
    xhr.setRequestHeader("x-csrf-token", form._csrf);
    xhr.send (new FormData (form));
}
