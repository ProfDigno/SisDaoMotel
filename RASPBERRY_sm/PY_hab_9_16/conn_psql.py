import psycopg2
est_conn = False
try:
    conexion=psycopg2.connect(
        host='192.168.0.200',
        user='postgres',
        password='MAJJJSMG',
        database='bddaomotel_1'
    )
    print("conexion exitosa 2")
    cursor=conexion.cursor()
    cursor.execute("select version()")
    row=cursor.fetchone()
    print(row)
    est_conn = True
except psycopg2.Error as e:
    print("Ocurrió un error al conectar a PostgreSQL: ", e)
    est_conn = False
    
def getBoo_conexion():
    return est_conn
print(getBoo_conexion())