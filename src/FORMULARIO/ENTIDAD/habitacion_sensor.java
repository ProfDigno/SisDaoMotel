	package FORMULARIO.ENTIDAD;
public class habitacion_sensor {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_sensor;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private boolean C5es_digital_entrada;
private boolean C6es_digital_salida;
private boolean C7activo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public habitacion_sensor() {
		setTb_habitacion_sensor("habitacion_sensor");
		setId_idhabitacion_sensor("idhabitacion_sensor");
	}
	public static String getTb_habitacion_sensor(){
		return nom_tabla;
	}
	public static void setTb_habitacion_sensor(String nom_tabla){
		habitacion_sensor.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_sensor(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_sensor(String nom_idtabla){
		habitacion_sensor.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idhabitacion_sensor(){
		return C1idhabitacion_sensor;
	}
	public void setC1idhabitacion_sensor(int C1idhabitacion_sensor){
		this.C1idhabitacion_sensor = C1idhabitacion_sensor;
	}
	public String getC2fecha_creado(){
		return C2fecha_creado;
	}
	public void setC2fecha_creado(String C2fecha_creado){
		this.C2fecha_creado = C2fecha_creado;
	}
	public String getC3creado_por(){
		return C3creado_por;
	}
	public void setC3creado_por(String C3creado_por){
		this.C3creado_por = C3creado_por;
	}
	public String getC4nombre(){
		return C4nombre;
	}
	public void setC4nombre(String C4nombre){
		this.C4nombre = C4nombre;
	}
	public boolean getC5es_digital_entrada(){
		return C5es_digital_entrada;
	}
	public void setC5es_digital_entrada(boolean C5es_digital_entrada){
		this.C5es_digital_entrada = C5es_digital_entrada;
	}
	public boolean getC6es_digital_salida(){
		return C6es_digital_salida;
	}
	public void setC6es_digital_salida(boolean C6es_digital_salida){
		this.C6es_digital_salida = C6es_digital_salida;
	}
	public boolean getC7activo(){
		return C7activo;
	}
	public void setC7activo(boolean C7activo){
		this.C7activo = C7activo;
	}
	public String toString() {
		return "habitacion_sensor(" + ",idhabitacion_sensor=" + C1idhabitacion_sensor + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,es_digital_entrada=" + C5es_digital_entrada + " ,es_digital_salida=" + C6es_digital_salida + " ,activo=" + C7activo + " )";
	}
}
