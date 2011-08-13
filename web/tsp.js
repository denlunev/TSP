$(document).ready(function() {
    
    $("#reloadBtn").click(function(){
        $("#canvas").html("");
        $("#log").html("Loading...");
        var count = parseInt($("#nodeCount").val());
        if(count < 3 || count > 30){
            count = 7;
            $("#nodeCount").val(count)
        }
        
        loadGraph(count);
    }).click();
});


var loadGraph = function(nodeCount){

    var width = 800;
    var height = 700;

    $.ajax({
        url: "/graph?count=" + nodeCount,
        success: function(data) {

            var g = new Graph();

            var edges = JSON.parse(data).edges;
            var log = JSON.parse(data).log;

            for (i = 0; i < edges.length; i++) {

                var color = edges[i].is_solution ? "#ff0000" : "#999";
                var fill = edges[i].is_solution ? "#ff0000" : "";
                g.addEdge(edges[i].source, edges[i].target, {
                    weight: edges[i].weight,
                    directed: false,
                    stroke : color,
                    fill : fill
                });
            }

            var layouter = new Graph.Layout.Spring(g);
            layouter.layout();

            var renderer = new Graph.Renderer.Raphael('canvas', g, width, height);
            renderer.draw();

            $("#log").html(log);

        }
    });
}