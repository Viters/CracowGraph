var request = new XMLHttpRequest();
request.open("GET", "../output.json", false);
request.send(null);
var data = JSON.parse(request.responseText);

normalizeData();

var stage = new createjs.Stage("canvas");
stage.enableMouseOver(100);
var nodesList = [];
var waysList = [];

data["ways"].forEach(w => {
    var previous = getNodeById(w.nodesRef[0]);
    w.nodesRef.forEach(n => {
        var node = getNodeById(n);
        if (node.visited != true) {
            var c = new createjs.Shape();
            c.graphics.f("#3498db").dc(1 + node.lat, 98 - (1 + node.lon), 0.2);
            nodesList.push(c);
        }
        node.visited = true;

        var line = new createjs.Shape();
        line.graphics.setStrokeStyle(0.1);
        line.graphics.beginStroke("#34495e");
        line.graphics.moveTo(1 + previous.lat, 98 - (1 + previous.lon));
        line.graphics.lineTo(1 + node.lat, 98 - (1 + node.lon));
        line.graphics.endStroke();
        line.data = {name: w.name, type: w.type, distance: w.distance};
        waysList.push(line);

        previous = node;
    })
});

waysList.forEach(n => stage.addChild(n));
nodesList.forEach(n => stage.addChild(n));

waysList.forEach(n => n.on("mouseover", function() {
    document.getElementById('tooltip').innerHTML ="Ta ulica nazywa się: " + this.data.name + "<br>Jest typu: " + this.data.type + "<br>Długość tego odcinka wynosi: " + this.data.distance + "m";
}));

window.addEventListener("resize", handleResize);
function handleResize() {
    var w = window.innerWidth - 2; // -2 accounts for the border
    var h = window.innerHeight - 2;
    stage.canvas.width = w;
    stage.canvas.height = h;
    //
    var ratio = 100 / 100; // 100 is the width and height of the circle content.
    var windowRatio = w / h;
    var scale = w / 100;
    if (windowRatio > ratio) {
        scale = h / 100;
    }
    // Scale up to fit width or height
    waysList.forEach(n => n.scaleX = n.scaleY = scale);
    nodesList.forEach(n => n.scaleX = n.scaleY = scale);

    stage.update();
}

function calculateRange() {
    var nodes = data["nodes"];
    var minLat = nodes[0].lat;
    var maxLat = nodes[0].lat;
    var minLon = nodes[0].lon;
    var maxLon = nodes[0].lon;
    nodes.forEach(n => {
        if (n.lat < minLat)
            minLat = n.lat;
        if (n.lat > maxLat)
            maxLat = n.lat;
        if (n.lon < minLon)
            minLon = n.lon;
        if (n.lon > maxLon)
            maxLon = n.lon;
    });
    return {minLat, maxLat, minLon, maxLon};
}

function calculateScale() {
    var range = calculateRange();

    var distanceY = range.maxLat - range.minLat;
    var scaleY = 200 / distanceY;

    var distanceX = range.maxLon - range.minLon;
    var scaleX = 98 / distanceX;

    var minLat = range.minLat;
    var minLon = range.minLon;

    return {minLat, scaleX, minLon, scaleY};
}

function normalizeData() {
    var scale = calculateScale();
    var nodes = data["nodes"];

    nodes.forEach(n => {
        n.lat = (n.lat - scale.minLat) * scale.scaleY;
        n.lon = (n.lon - scale.minLon) * scale.scaleX;
    });
}

function getNodeById(id) {
    return data["nodes"].find(n => n.id == id);
}

handleResize(); // First draw

document.addEventListener('mousemove', function(ev){
    document.getElementById('tooltip').style.transform = 'translateY('+(ev.clientY-130)+'px)';
    document.getElementById('tooltip').style.transform += 'translateX('+(ev.clientX-260)+'px)';
},false);
