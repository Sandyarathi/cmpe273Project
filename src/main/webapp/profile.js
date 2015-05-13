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
        $('#friend-pics').append('<table>');
        for (var i = 0; i < data.length; i=i+3) {
            var tr = $('<tr>');
            tr.append('<td style="padding-left: 5px;" ><img src="' + data[i].link + '"/><br/><span style="font-size: xx-small; " >' + data[i].name + '</span></td>');
            if(i+1 < data.length){
                tr.append('<td style="padding-left: 5px;"><img src="' + data[i+1].link + '"/><br/><span style="font-size: xx-small; " >' + data[i+1].name + '</span></td>');
            }
            if(i+2 < data.length){
                tr.append('<td style="padding-left: 5px;"><img src="' + data[i+2].link + '"/><br/><span style="font-size: xx-small; " >' + data[i+2].name + '</span></td>');
            }
            tr.append('</tr>')
            $('#friend-pics').append(tr);
        }
    });
});


$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/highlights"
    }).then(function(data) {
        $('#moments').append('<table>');
        for (var i = 0; i < data.Posts.length; i++) {

            $('#moments').append('<tr><td style="font-size: medium;vertical-align:middle " >' + data.Posts[i].Month + '</td><td style="width:100%;"><hr></td></tr>');

            for (var j = 0; j < data.Posts[i].Post.length; j++)
            {
                var tr = $('<tr>');
                tr.append('<td style="padding-left: 5px; padding-top: 5px" ><img src="' + data.Posts[i].Post[j].postImage + '"/></td>');
                tr.append('<td style="padding-left: 10px; padding-top: 5px"> <span><i>'+ data.Posts[i].Post[j].postMessage +'</i></span></td>');
                tr.append('</tr>')
                $('#moments').append(tr);
            }

        }
        $('#photo').append('<table>');
            for (var i = 0; i < data.Pics.length; i=i+3) {
            var tr = $('<tr>');
            tr.append('<td style="padding-left: 5px;" ><img src="' + data.Pics[i] + '"/>');
            if(i+1 < data.Pics.length){
               tr.append('<td style="padding-left: 5px;"><img src="' + data.Pics[i+1] + '"/>');
            }
            if(i+2 < data.Pics.length){
               tr.append('<td style="padding-left: 5px;"><img src="' + data.Pics[i+2] + '"/>');
            }
            tr.append('</tr>')
            $('#photo').append(tr);
        }
    });
});





