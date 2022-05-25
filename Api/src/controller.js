const connection = require('./database')

module.exports = {
    async index (request,response){
        console.log("aaaa");
        const valores = await connection.then(async (res)=>{
            console.log(res);
            var a = await res.query('select * from medicao')
            return response.json(
                a
            );
        })
        
    },
 
}