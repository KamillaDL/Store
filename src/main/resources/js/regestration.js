function valid(form) {
    var fail = false;
    let div = document.getElementById('alert');
    if(div==null)
    {
        div=document.createElement('div');
        div.className='alert';
        div.id = 'alert';
    }
    var username = form.username.value.trim();
    var password = form.password.value.trim();
    var password2 = form.password2.value.trim();
    var email = form.email.value.trim();
    Array.prototype.forEach.call(document.getElementsByClassName("input"), function(el) {
        el.style.border = '1px solid black';
    });
    if(username=="") {
        fail = true;
        document.getElementById('username').style.border = '1px solid red';
        div.innerHTML = '<strong>Ошибка:</strong> вы не ввели логин';
        document.getElementById('window').append(div);
        event.preventDefault();
    }
    else if(email==""){
        fail = true;
        document.getElementById('email').style.border = '1px solid red';
        div.innerHTML = '<strong>Ошибка:</strong> вы не ввели email';
        document.getElementById('window').append(div);
        event.preventDefault();
    }
    else if (!email.match(/^[0-9a-z-\.]+\@[0-9a-z-]{2,}\.[a-z]{2,}$/i)){
        fail = true;
        document.getElementById('email').style.border = '1px solid red';
        div.innerHTML = '<strong>Ошибка:</strong> вы ввели некорректный email';
        document.getElementById('window').append(div);
        event.preventDefault();
    }
    else if(password=="") {
        fail = true;
        document.getElementById('password').style.border = '1px solid red';
        div.innerHTML = '<strong>Ошибка:</strong> вы не ввели пароль';
        document.getElementById('window').append(div);
        event.preventDefault();
    }
    else if(password != password2){
        fail = true;
        document.getElementById('password2').style.border = '1px solid red';
        div.innerHTML = '<strong>Ошибка:</strong> пароли не совпадают';
        document.getElementById('window').append(div);
        event.preventDefault();
    }
    else{
        return !fail;
    }
}