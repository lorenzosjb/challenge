<% 
    String variable = "";
    if (session.getAttribute("result") != null) {
        variable = (String) session.getAttribute("result");
    }
%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>HP's PSR Championships</title>
        <link href="styles.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <section class='container'>
            <hgroup>
                <h1>Welcome to HP's Paper Scissors and Rock Championships</h1>
            </hgroup>
            <div class="row">
                <section class='col-xs-12 col-sm-6 col-md-6'>
                    <p><a href="index.html">Return</a></p>
                    <h2>Process a Championship<br/></h2>
                    <form action="api/championship/upload" method="post" enctype="multipart/form-data">
                        <input type="file" name="file"/>
                        <br/><br/>
                        <textarea name="data" rows="7" cols="40"><%= variable %></textarea>
                        <br/><br/>
                        <input type="submit" value="Process championship" />
                    </form>
                </section>
                <section class="col-xs-12 col-sm-6 col-md-6">
                    <h2>Top Players</h2>
                    <h3 id='top'></h3>
                </section>
            </div>
        </section>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script>
            $(document).ready(function(){
                $.getJSON("api/championship/top", function(data) {
                    var usuarios = "<ul>";
                    $.each(data.players, function( key, val ) {
                        usuarios += "<li>" + val + "</li>";
                    });
                    $("#top").html(usuarios + "</ul>");
                });
            });
        </script>
    </body>
</html>


