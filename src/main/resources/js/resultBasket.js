function resultBasket(){
    var els = document.getElementsByClassName("basketSteper");
    var all = 0;
    Array.prototype.forEach.call(els, function(el) {
        var v = el.price.value*el.number.value;
        all += v;
        document.getElementById(el.id_basket.value).innerHTML = v;
    });
    document.getElementById('result').innerHTML=all;
    // var idd = document.getElementById('id').value;
    // alert("idd-" + idd + "sign-" + sign);
    // changeCount(sign, idd);
}

function changeCount(sign, id){
    resultBasket();
    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', '/changeCount?sign=' + sign + '&id=' + id);
    xhttp.send();
}

window.onload = resultBasket;


