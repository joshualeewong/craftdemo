function generateDivResultsHTML(data) {
    if (data) {
        var len = data.movies.length;
        var txt = "";
        if (len > 0) {
            txt += "<div class=\"row\"><h2>Movies</h2>";
            for (var i = 0; i < len; i++) {
                if (data.movies && data.movies[i].id) {
                    txt += "<div class=\"column\">";
                    txt += "<h3>" + data.movies[i].id + "</h3>";
                    txt += "<p class=\"description\">" + data.movies[i].description + "<br></p>";
                    txt += "<p class=\"category\">" + data.movies[i].category + "</p>";
                    txt += "<div class=\"item\"><i id=\"movies"+i+"upIcon\" class=\"material-icons\" onclick='SubmitVote(\"movies\","+ i +",\""+data.movies[i].id+"\", \"1\")'>thumb_up</i><span id=\"movies"+i+"upSpan\" style=\"display:none;\"></span></div>";
                    txt += "<div class=\"item\"><i id=\"movies"+i+"downIcon\" class=\"material-icons\" onclick='SubmitVote(\"movies\","+ i +",\""+data.movies[i].id+"\", \"-1\")'>thumb_down</i><span id=\"movies"+i+"downSpan\" style=\"display:none;\"></span></div>";
                    txt += "</div>";
                }
            }
            txt += "</div>";
        }
        len = data.tv.length;
        if (len > 0) {
            txt += "<div class=\"row\"><h2>TV</h2>";
            for (var j = 0; j < len; j++) {
                if (data.tv && data.tv[j].id) {
                    txt += "<div class=\"column\">";
                    txt += "<h3>" + data.tv[j].id + "</h3>";
                    txt += "<p class=\"description\">" + data.tv[j].description + "<br></p>";
                    txt += "<p class=\"category\">" + data.tv[j].category + "</p>";
                    txt += "<div class=\"item\"><i id=\"tv"+j+"upIcon\" class=\"material-icons\" onclick='SubmitVote(\"tv\","+ j +",\""+data.tv[j].id+"\", \"1\")'>thumb_up</i><span id=\"tv"+j+"upSpan\" style=\"display:none;\"></span></div>";
                    txt += "<div class=\"item\"><i id=\"tv"+j+"downIcon\" class=\"material-icons\" onclick='SubmitVote(\"tv\","+ j +",\""+data.tv[j].id+"\", \"-1\")'>thumb_down</i><span id=\"tv"+j+"downSpan\" style=\"display:none;\"></span></div>";
                    txt += "</div>";
                }
            }
            txt += "</div>";
        }
        if (txt != "") {
            $("#mediacontent").append(txt).removeClass("hidden");
        }
    }
}

function SubmitVote(type, id, title, vote) {
    var params = {};
    params['ID'] = title;
    params['VOTE'] = vote;
    document.getElementById(type + id +'upIcon').removeAttribute("onclick");
    document.getElementById(type + id +'downIcon').removeAttribute("onclick");
    if (vote > 0) {
        document.getElementById(type + id + 'upSpan').style.color = "dodgerblue";
        document.getElementById(type + id + 'upIcon').style.color = "dodgerblue";
    } else {
        document.getElementById(type + id + 'downSpan').style.color = "dodgerblue";
        document.getElementById(type + id + 'downIcon').style.color = "dodgerblue";
    }
    console.log(JSON.stringify(params));
    $.ajax({
        type: "POST",
        url: '/craftdemo.do',
        dataType: 'json',
        data: {
            ACTION_NAME: 'CAST_VOTE',
            PARAMS: JSON.stringify(params)
        },
        success: function (data) {
            console.log("Vote counts: " + JSON.stringify(data));
            document.getElementById(type + id +'upSpan').style.display = 'block';
            document.getElementById(type + id + 'upSpan').textContent = data.THUMBS_UP;
            document.getElementById(type + id + 'downSpan').style.display = 'block';
            document.getElementById(type + id + 'downSpan').textContent = data.THUMBS_DOWN;
        },
        error: function() {
            alert("Vote error");
        }
    });
    console.log("You clicked on id " + id + " " + title +" and voted: " + vote);
}
