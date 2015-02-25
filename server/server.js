  var express = require('express')
  var path = require('path')
  var app = express()
  bodyParser = require('body-parser')

  var rootDir = __dirname + '/../';
  var clientbuild = rootDir + 'client';

  app.use(bodyParser.urlencoded({extended: false}))
  app.use(bodyParser.json())

  app.use(express.static(clientbuild))
  
  app.engine('html', require('ejs').renderFile);
  app.set('views', path.join(rootDir, 'views_html'))
  //app.set('view engine', 'jade')

  app.get('/', function(req, res) {
    res.render('index.html')
  })
  app.listen(3001)

