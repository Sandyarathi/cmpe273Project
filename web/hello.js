$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/user"
    }).then(function(data) {
        $('.name-id').append(data);
    });
});