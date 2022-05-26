const mariadb = require('mariadb');
const { Client } = require('ssh2');
const sshClient = new Client();
// const tunnelConfig = {
//     host: '187.20.157.119',
//     port: '13777',
//     username: 'pi',
//     password: 'raissalinda'
// }
const pool = mariadb.createPool( {
    host: '127.0.0.1', 
    port:'3306', // 13777
    user:'root', 
    password: 'raissalinda',
    database:'TI5',
    connectionLimit: 5
})
module.exports = Object.freeze({
    pool: pool
});
