import psycopg2
from conn_psql import conexion
def update_puerta_habitacion(alto_bajo,gpio,fk_idhabitacion_dato,fk_idhabitacion_sensor,fk_idhabitacion_mini_pc):
    try:
        with conexion.cursor() as cursor:
            sql_update = "update public.habitacion_item_sensor_gpio set alto_bajo=%s where gpio=%s and fk_idhabitacion_dato=%s and fk_idhabitacion_sensor=%s and fk_idhabitacion_mini_pc=%s;"
            cursor.execute(sql_update, (alto_bajo, gpio,fk_idhabitacion_dato,fk_idhabitacion_sensor,fk_idhabitacion_mini_pc))
#             print("ciclo: ", ciclo,"-abierto: ", abierto,"-nro_habitacion: ", nro_habitacion,"-nom_puerta: ", nom_puerta,"false")
        conexion.commit()  # Si no haces commit, los cambios no se guardan
    except psycopg2.Error as e:
        print("Ocurrió un error al insertar: ", e)
    #finally:
       # conexion.close()
def update_ult_conexion(idhabitacion_mini_pc):
    try:
        with conexion.cursor() as cursor:
            sql_update = "update public.habitacion_mini_pc set ult_conexion=current_timestamp WHERE idhabitacion_mini_pc=%s;"
            cursor.execute(sql_update,(idhabitacion_mini_pc))
        conexion.commit()  # Si no haces commit, los cambios no se guardan
    except psycopg2.Error as e:
        print("Ocurrió un error al update_ult_conexion: ", e)