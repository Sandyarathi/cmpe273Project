$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/userName"
    }).then(function(data) {
        $('.name-id').append(data);
    });
});


$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/userInfo"
    }).then(function(data) {
        $('.birth-id').append(data.BirthDate);
        $('.home-id').append(data.Hometown);
        $('.gender-id').append(data.Gender);
        $('.interest-id').append(data.Interested);
        $('.relation-id').append(data.Relationship);
    });
});

$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/profilePic"
    }).then(function(data) {
        $('#profile-pic').attr("src",data);
    });
});


$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/friendPics"
    }).then(function(data) {
        $('#manualCsoCodes').append('<table>');
        for (var i = 0; i < data.length; i=i+3) {
            var tr = $('<tr>');
            tr.append('<td style="padding-left: 5px;" ><img src="' + data[i].link + '"/><br/><span style="font-size: xx-small; " >' + data[i].name + '</span></td>');
            tr.append('<td style="padding-left: 5px;"><img src="' + data[i+1].link + '"/><br/><span style="font-size: xx-small; " >' + data[i+1].name + '</span></td>');
            tr.append('<td style="padding-left: 5px;"><img src="' + data[i+2].link + '"/><br/><span style="font-size: xx-small; " >' + data[i+2].name + '</span></td>');
            tr.append('</tr>')
            $('#manualCsoCodes').append(tr);
        }
    });
});


$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/highlights"
    }).then(function(data) {
        $('#moments').append('<table>');
        for (var i = 0; i < data.length; i++) {

            $('#moments').append('<tr><td colspan="2"  style="font-size: small;" >January 2015</td><td style="width:100%;"><hr></td></tr>');

            for (var j = 0; j < data[i].Post.length; j++)
            {
                var tr = $('<tr>');
                tr.append('<td style="padding-left: 5px; padding-top: 5px" ><img src="' + data[i].Post[j].postImage + '"/></td>');
                tr.append('<td style="padding-left: 10px; padding-top: 5px"> <span><i>'+ data[i].Post[j].postMessage +'</i></span></td>');
                tr.append('</tr>')
                $('#moments').append(tr);
            }

        }
    });
});



