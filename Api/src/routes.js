const express = require('express');
const {celebrate,Segments,Joi} = require('celebrate')

const Controller = require('./controller')

const routes = express.Router();
//traz todos os registros do banco
routes.get('/getAll',celebrate({
    [Segments.QUERY] :Joi.object().keys({
        data: Joi.string()
    })
 }), Controller.index)

//traz o maior o menor e o atual do dia de hj
routes.get('/getMaxMin',celebrate({
    [Segments.QUERY] :Joi.object().keys({
        unidade: Joi.string(),
    })
 }), Controller.MinMaxAtual)

// traz a lista de unidades
routes.get('/getUnidades', Controller.Unidades)

//traz todos os registros de uma unidade
routes.get('/getAllFromUnidade',celebrate({
    [Segments.QUERY] :Joi.object().keys({
        unidade: Joi.string(),
        data: Joi.string()
    })
 }),Controller.FromUnidade);

//traz o maior e o menor de todos os dias ate a hj - dias
routes.get('/getAllFromData',celebrate({
    [Segments.QUERY] :Joi.object().keys({
        dias: Joi.number(),
    })
 }),Controller.Intevalo);

module.exports = routes;