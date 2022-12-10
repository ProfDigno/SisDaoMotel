import RPi.GPIO as gpio
import time
import socket
print("INTENTAR CONECTAR AL SERVIDOR ESPERAR...")
time.sleep(20)
from conn_psql import getBoo_conexion
if getBoo_conexion():    
    from update_habitacion_item_sensor_gpio import update_puerta_habitacion
    from update_habitacion_item_sensor_gpio import update_ult_conexion
tiempo_piscar_led=0
from datetime import datetime   
gpio.setmode(gpio.BCM)
vInt_gpio_input  = [4,17,10,9,27,22,13,19,5,6,26,21,2,3,20,11,0,0,0,0,0,0,0,0,0,0]
vInt_nro_habitacion = [9,9,10,10,11,11,12,12,13,13,14,14,15,15,16,16,0,0,0,0,0,0,0,0,0,0]
# vInt_gpio_input  = [2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27]
# vInt_nro_habitacion = [15,15,9,13,13,0,0,10,10,16,0,12,0,0,0,9,0,12,16,14,11,0,0,0,14,11]
est_puerta = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
vInt_Hab_Puerta = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
vInt_Ult_Est_Puerta = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
fk_idhabitacion_mini_pc=3
tiempo_ciclo_seg=1
PL="LIMPIEZA"
PO="CLIENTE"
IPL=1   # cambiar de normal abierto sensor Puerta tarjeta limpieza
IPC=0   # cambiar de normal abierto sensor puerta magnetico cliente
SPL=3   # sensor Puerta tarjeta limpieza
SPC=2   # sensor puerta magnetico cliente
cant_inicio=0
es_nuevo="NO"
getString_Est_Puerta = "sin-estado"
vString_Nom_Puerta = [PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO,PL,PO]
vInt_sensor_Puerta = [SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC,SPL,SPC]
vInt_Inv_Puerta = [IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC,IPL,IPC]
print("INICIO RASPBERRY PABELLON 1:")
gpio.setwarnings(False)
for fila in range(len(vInt_gpio_input)):
    gpio.setup(vInt_gpio_input[fila], gpio.IN)
while True:
    for fila in range(len(vInt_nro_habitacion)):
        if vInt_Hab_Puerta[fila] == 1:
            today = datetime.now()
            solo_hora = today.strftime("%H:%M:%S")
            if gpio.input(vInt_gpio_input[fila]) == gpio.LOW:
                abierto=0
            else:
                abierto=1     
            pino_GPIO = vInt_gpio_input[fila]
            fk_idhabitacion_dato=vInt_nro_habitacion[fila]
            getString_Nom_Puerta=vString_Nom_Puerta[fila]
            getInt_Inv_Puerta=vInt_Inv_Puerta[fila]
            fk_idhabitacion_sensor=vInt_sensor_Puerta[fila]
            cant_inicio=cant_inicio+1
            if vInt_Ult_Est_Puerta[fila] != abierto or cant_inicio<=16:
                es_nuevo="SI"
                getString_Est_Puerta="sin-est"
                getInt_abierto=2
                if abierto == 1 and getInt_Inv_Puerta == 1:
                    getString_Est_Puerta="abierto"
                    getInt_abierto=1
                    alto_bajo=True 
                if abierto == 0 and getInt_Inv_Puerta == 1:
                    getString_Est_Puerta="cerrado"
                    getInt_abierto=0
                    alto_bajo=False
                if abierto == 1 and getInt_Inv_Puerta == 0:
                    getString_Est_Puerta="cerrado"
                    getInt_abierto=0
                    alto_bajo=False
                if abierto == 0 and getInt_Inv_Puerta == 0:
                    getString_Est_Puerta="abierto"
                    getInt_abierto=1
                    alto_bajo=True 
                print(fila," hs:",solo_hora,
                      ",fk_idhabitacion_dato:",fk_idhabitacion_dato,"->:",
                      ",GPIO:",pino_GPIO,
                      ",alto_bajo:",alto_bajo,
                      ",fk_idhabitacion_sensor:",fk_idhabitacion_sensor,
                      ",fk_idhabitacion_mini_pc:",fk_idhabitacion_mini_pc)
                if getBoo_conexion():    
                    update_puerta_habitacion(alto_bajo,pino_GPIO,fk_idhabitacion_dato,fk_idhabitacion_sensor,fk_idhabitacion_mini_pc)
            else:
                es_nuevo="NO"
            vInt_Ult_Est_Puerta[fila]=abierto 
    time.sleep(tiempo_ciclo_seg)
    if getBoo_conexion():
        update_ult_conexion(str(fk_idhabitacion_mini_pc))
gpio.cleanup()
        



