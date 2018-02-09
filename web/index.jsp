<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="craftdemo/craftdemo.css" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SRE Developer Craft Demo</title>
    <noscript>
        <h3>This site requires JavaScript</h3>
    </noscript>
</head>
<body style="background-color:whitesmoke">
    <div class="header">
        <h1>SRE Developer Craft Demo</h1>
    </div>

    <div id="mediacontent" class="hidden"></div>
</body>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="craftdemo/craftdemo.js"></script>
<script type="text/javascript">
    $(document)
        .ready(
            function () {
                $.ajax({
                    type: 'POST',
                    url: '/craftdemo.do',
                    dataType: 'json',
                    data: {
                        ACTION_NAME: 'LOAD_MEDIA_JSON'
                    },
                    success: function (data) {
                        generateDivResultsHTML(data);
                    },
                    error: function () {
                        alert('JS load error');
                    }
                });
            });
</script>
</html>