
const express = require('express');
const routes = require('./routes');
const cors = require('cors');
const {errors} = require('celebrate')
const app = express();
app.use(cors());
app.use(express.json());
app.use(routes);
app.use(errors())
process.env.TZ = 'America/Sao_Paulo'; // UTC +00:00
console.log(new Date().toString())

module.exports = app;