const connection = require('./database')

module.exports = {
    async index(request, response) {
        const { data = null } = request.query;
        console.log(data);
        if ( data == null || data == '') {
            const valores = await connection.pool.query('select * from medicao')
            return response.json(
                valores
            );
        } else {
            const data2 = new Date(data);
            const valores = await connection.pool.query(`select * from medicao where data > '${data2.toISOString()}'`)
            return response.json(
                valores
            );
        }
        
    },
    async FromUnidade(request, response) {
        const { unidade = 's', data = null } = request.query;
        if ( data == null || data == '') {
            const valores = await connection.pool.query(`select * from medicao where unidade = '${unidade}' `)
            return response.json(
                valores
            );
        } else {
            const data2 = new Date(data);
            const valores = await connection.pool.query(`select * from medicao where unidade = '${unidade}' AND data > '${data2.toISOString()}' `)
            return response.json(
                valores
            );
        }
    },
    async MinMaxAtual(request, response) {
        const { unidade = 's' } = request.query;
        var hj = new Date(Date.now());
        hj.setHours(1, 0, 1, 0)
        console.log(hj)
        const valores = await connection.pool.query(`select * from medicao 
        where data > '${hj.toISOString()}' 
        AND unidade = '${unidade}' order by data desc`)
        if (valores.length > 0) {


            const Metricas = {
                tempUmiAtual: valores[0],
                tempMaior: null,
                tempMenor: null,
                umiMaior: null,
                umiMenor: null
            }
            valores.sort((c1, c2) => (c1.temperatura < c2.temperatura) ? 1 : (c1.temperatura > c2.temperatura) ? -1 : 0)
            Metricas.tempMaior = valores[0];
            Metricas.tempMenor = valores[valores.length - 1];
            valores.sort((c1, c2) => (c1.umidade < c2.umidade) ? 1 : (c1.umidade > c2.umidade) ? -1 : 0)
            Metricas.umiMaior = valores[0];
            Metricas.umiMenor = valores[valores.length - 1];
            return response.json(
                Metricas
            );
        } else {
            return response.json(
                null
            );
        }
    },
    async Unidades(request, response) {
        const valores = await connection.pool.query(`select unidade from medicao group by unidade`)
        return response.json(
            valores
        );
    },

    async Intevalo(request, response) {
        const { dias = 1 } = request.query;
        var hj = new Date(Date.now());
        var dataFim = new Date(Date.now());
        hj.setHours(-3, 0, 0, 0)
        dataFim.setHours(-3, 0, 0, 0)
        dataFim.setDate(hj.getDate() - dias);
        console.log(hj.toISOString());

        const valores = await connection.pool.query(`
            SELECT MAX(temperatura) as tempMaior, 
            MIN(temperatura) as tempMenor, 
            MAX(umidade) as umiMaior, 
            MIN(umidade) as umiMenor, 
            DATE(data) as dia
            from medicao
            WHERE data > '${dataFim.toISOString()}'
            group by DATE(data) , unidade`
        )
        return response.json(
            valores
        );
    },
}


// {
//     "id": 2,
//     "temperatura": 0,
//     "umidade": 22,
//     "data": "2022-05-25T04:36:55.000Z",
//     "unidade": "S"
// }