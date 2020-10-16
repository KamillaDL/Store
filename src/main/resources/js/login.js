function valid(form) {
    var fail = false;
    let div = document.getElementById('alert');
    if (div == null) {
        div = document.createElement('div');
        div.className = 'alert';
        div.id = 'alert';
    }
    var username = form.username.value.trim();
    var password = form.password.value.trim();
    if (username == "") {
        fail = true;
        document.getElementById('username').style.border = '1px solid red';
        div.innerHTML = '<strong>Ошибка:</strong> вы не ввели логин';
        document.getElementById('window').append(div);
        event.preventDefault();
    }
    else if (password == "") {
        fail = true;
        document.getElementById('password').style.border = '1px solid red';
        div.innerHTML = '<strong>Ошибка:</strong> вы не ввели пароль';
        document.getElementById('window').append(div);
        event.preventDefault();
    }
    else {
        return !fail;
    }
}