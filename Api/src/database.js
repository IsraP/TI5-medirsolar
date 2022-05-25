const mariadb = require('mariadb');
const { Client } = require('ssh2');
const sshClient = new Client();
const tunnelConfig = {
    host: '187.20.157.119',
    port: '13777',
    username: 'pi',
    password: 'raissalinda'
}
const forwardConfig = {
    host: '127.0.0.1', 
    port:'3306', // 13777
    user:'root', 
    password: 'raissalinda',
    database:'TI5',
    connectionLimit: 5
}

const SSHConnection = new Promise((resolve, reject) => {
    sshClient.on('ready', () => {
        console.log("222");
        sshClient.forwardOut(
        "127.0.0.1",
        forwardConfig.port,
        forwardConfig.host,
        forwardConfig.port,
        async (err, stream) => {
            console.log("maria");
             if (err) reject(err);
            
            const pool = mariadb.createPool(forwardConfig)
            console.log("pool aaaa ",pool);
            const conn = await pool.getConnection();
            const a = await conn.query('select * from medicao')
            console.log("Amem ",a);
                resolve(pool);   
                
       });
    }).connect(tunnelConfig);
});
module.exports = SSHConnection;
// const pool 
// 

// pool.getConnection()
//     .then(conn => {
    
//       conn.query("SELECT 1 as val")
//         .then((rows) => {
//           console.log(rows); //[ {val: 1}, meta: ... ]
//           //Table must have been created before 
//           // " CREATE TABLE myTable (id int, val varchar(255)) "
//           return conn.query("INSERT INTO myTable value (?, ?)", [1, "mariadb"]);
//         })
//         .then((res) => {
//           console.log(res); // { affectedRows: 1, insertId: 1, warningStatus: 0 }
//           conn.end();
//         })
//         .catch(err => {
//           //handle error
//           console.log(err); 
//           conn.end();
//         })
//     }).catch(err => {
//       //not connected
//     });