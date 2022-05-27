const app = require('./app')
const port = 3000
process.env.TZ = 'America/Sao_Paulo'; // UTC +00:00
console.log(new Date().toString())

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})