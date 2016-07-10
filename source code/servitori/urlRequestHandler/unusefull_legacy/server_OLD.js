var express = require('express');
var app = express()
var bodyParser = require('body-parser');

app.use(bodyParser.json())

app.get('/ciao', function (req, res) {
	res.status(200).end()
})

// AppInfoProvider

app.get('/appInfo', function (req, res) {
	var email       = 'beaconstrips.swe@gmail.com'
	var site        = '52.58.6.246'
	var description = 'Quest\'app ti permette di giocare una serie di percorsi in una serie di edifici abilitati, se sei interessato a creare il tuo percorso contattaci su ' + site + ' oppure scrivici a ' + email
	res.send({description: description, supportEmail : email, websiteURL : site})
})

// LoginHandler

app.post('/login', function (req, res) {
	console.log(req.body);
	// var json  = JSON.parse(req.body)
	var email = req.body.email
	var pass  = req.body.password
	console.log('email:    ' + email);
	console.log('password: ' + pass );
	if (email == '' || pass == '') {
		res.status(400).send({message : 'invalid email or password'})
	} else {
		res.status(200).send({user : email})
	}
})

app.listen(1234)

console.log('started');
