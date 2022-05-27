import socket
import sys
import re
import mariadb
from datetime import datetime, timedelta

HOST = ''
PORT = 5005
s = None

try:
    connDb = mariadb.connect(
            user="root",
            password="raissalinda",
            host="127.0.0.1",
            port=3306,
            database="TI5"
    )
except mariadb.Error as e:
    print(f"Erro p krl: {e}")
    sys.exit(1)

cur = connDb.cursor()

def saveBanco(strData):
    #print(strData)
    data = strData.decode('utf-8')
    x = data.split(" - ")
    d = datetime.now() - timedelta(hours = 4)
    #print(x)
    try:
        cur.execute(
                "INSERT INTO medicao (temperatura, umidade, data, unidade) VALUES (?, ?, ?, 'I')",
                (x[2], x[1], d))
        #print(x[1], " Celcius, ", x[2], " Umidade, ", d)
        connDb.commit()
    except mariadb.Error as e:
        print(f"Erro: {e}")

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    while True:
        s.listen(1)
        conn, addr = s.accept()
        with conn:
            data = conn.recv(1024)
            saveBanco(data)
