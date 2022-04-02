var express = require("express");
var app = express();
const path = require('path');
var bodyParser = require('body-parser');

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }))

// parse application/json
app.use(bodyParser.json())

//serve static file (index.html, images, css)
app.use(express.static(__dirname + '/dist/asao-web-app'));

app.get('*', (req, res) => {
    res.sendFile(path.join(__dirname, '/dist/asao-web-app/index.html'));
});

var port = process.env.PORT || 8080
app.listen(port, function() {
    console.log("To view your app, open this link in your browser: http://localhost:" + port);
});