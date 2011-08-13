<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Traveling Salesman Problem</title>
        <script type="text/javascript" src="js/raphael-min.js"></script>
        <script type="text/javascript" src="js/dracula_graffle.js"></script>
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/json2.js"></script>
        <script type="text/javascript" src="js/dracula_graph.js"></script>
        <script type="text/javascript" src="tsp.js"></script>
        <link rel="stylesheet" href="styles.css" type="text/css" media="all" />
    </head>
    <body>
        <h1>Traveling Salesman Problem</h1>
        <div id="commandPane">
            Graph Size (Min: 3, Max: 30) <input type="text" id="nodeCount" value="7" maxlength="2" size="5" />
            <button id="reloadBtn">Reload</button>
        </div>
        <div id="canvas"></div>
        <div id="log"></div>
    </body>
</html>
