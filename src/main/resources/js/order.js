function buy() {
    swal({title: "Спасибо за покупку!",
        text: "С Вами обязательно свяжутся!",
        icon: "success",
        buttons: {
            ok: "ОК",
        },
    })
        .then((value) => {
            switch (value) {
                case "ok":
                    window.location.href="/basket";
                    break;
                default :
                    window.location.href="/basket";
            }
        });
}

function fail() {
    swal({
        title: "Ошибка!",
        text: "Извините, произошла ошибка!",
        icon: "error",
        button: "Закрыть",
    })
}

function buying(form) {
    var fail = false;
    var name = form.name.value.trim();
    var surname = form.surname.value.trim();
    var address = form.address.value.trim();
    var index = form.index.value.trim();
    var phone = form.phone.value.trim();
    var email = form.email.value.trim();
    if(name==="" || surname==="" || address==="" || index==="" || phone==="" || email==="") {
        swal({
            title: "Ошибка!",
            text: "Заполнены не все поля!",
            icon: "error",
            button: "Закрыть",
        });
        event.preventDefault();
        return false;
    }

    var xhr = new XMLHttpRequest();
    xhr.onload = function(){ buy() }; // success case
    xhr.onerror = function(){ fail() }; // failure case
    xhr.open (form.method, form.action, true);
    xhr.setRequestHeader("x-csrf-token", form._csrf);
    xhr.send (new FormData (form));
}