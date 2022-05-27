import serial
import re

import sys
import socket

arduino = serial.Serial(port='COM5', baudrate=9600, timeout=.1)

TCP_IP = '192.168.0.136'
TCP_PORT = 5005
BUFFER_SIZE = 1024

def leitura():
    strSerial = arduino.readline()
    data = strSerial.decode('UTF-8')
    if(len(data) > 15):
        x = re.sub(r'.', '', data, count = 2)
        return data

while True:
    str = leitura()
    if(str):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            
            s.connect((TCP_IP, TCP_PORT))
            s.sendall(bytes(str, 'utf-8'))
            
            data = s.recv(BUFFER_SIZE)
            print("DADOS: ", repr(data))
            print(str, " Mandou?")
