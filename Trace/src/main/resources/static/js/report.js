/*function openWindowPop(url, name, listNo) {
    var options = 'top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no';
    
    var urlWithParam = url + '?list_no=' + encodeURIComponent(listNo);
    window.open(urlWithParam, name, options);
}*/

function openWindowPop(category, no) {
    var options = 'top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no';
    
    var urlWithParam = 'report?no=' + encodeURIComponent(no) + '&cate2=' + encodeURIComponent(category);
    window.open(urlWithParam, 'qwe', options);
}

