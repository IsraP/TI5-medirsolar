const express = require('express');
const {celebrate,Segments,Joi} = require('celebrate')

const Controller = require('./controller')

const routes = express.Router();

routes.get('/getAll', Controller.index)


module.exports = routes;