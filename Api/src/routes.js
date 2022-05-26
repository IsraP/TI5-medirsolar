const express = require('express');
const {celebrate,Segments,Joi} = require('celebrate')

const Controller = require('./controller')

const routes = express.Router();

routes.get('/getAll', Controller.index)

routes.get('/getMaxMin',celebrate({
    [Segments.QUERY] :Joi.object().keys({
        unidade: Joi.string(),
    })
 }), Controller.MinMaxAtual)
routes.get('/getUnidades', Controller.Unidades)

routes.get('/getAllFromUnidade',celebrate({
    [Segments.QUERY] :Joi.object().keys({
        unidade: Joi.string(),
    })
 }),Controller.FromUnidade);

module.exports = routes;