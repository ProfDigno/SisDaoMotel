	package FORMULARIO.ENTIDAD;
public class habitacion_item_sensor_gpio {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_item_sensor_gpio;
private String C2fecha_update;
private boolean C3alto_bajo;
private int C4gpio;
private boolean C5activar;
private int C6fk_idhabitacion_dato;
private int C7fk_idhabitacion_sensor;
private int C8fk_idhabitacion_mini_pc;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public habitacion_item_sensor_gpio() {
		setTb_habitacion_item_sensor_gpio("habitacion_item_sensor_gpio");
		setId_idhabitacion_item_sensor_gpio("idhabitacion_item_sensor_gpio");
	}
	public static String getTb_habitacion_item_sensor_gpio(){
		return nom_tabla;
	}
	public static void setTb_habitacion_item_sensor_gpio(String nom_tabla){
		habitacion_item_sensor_gpio.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_item_sensor_gpio(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_item_sensor_gpio(String nom_idtabla){
		habitacion_item_sensor_gpio.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idhabitacion_item_sensor_gpio(){
		return C1idhabitacion_item_sensor_gpio;
	}
	public void setC1idhabitacion_item_sensor_gpio(int C1idhabitacion_item_sensor_gpio){
		this.C1idhabitacion_item_sensor_gpio = C1idhabitacion_item_sensor_gpio;
	}
	public String getC2fecha_update(){
		return C2fecha_update;
	}
	public void setC2fecha_update(String C2fecha_update){
		this.C2fecha_update = C2fecha_update;
	}
	public boolean getC3alto_bajo(){
		return C3alto_bajo;
	}
	public void setC3alto_bajo(boolean C3alto_bajo){
		this.C3alto_bajo = C3alto_bajo;
	}
	public int getC4gpio(){
		return C4gpio;
	}
	public void setC4gpio(int C4gpio){
		this.C4gpio = C4gpio;
	}
	public boolean getC5activar(){
		return C5activar;
	}
	public void setC5activar(boolean C5activar){
		this.C5activar = C5activar;
	}
	public int getC6fk_idhabitacion_dato(){
		return C6fk_idhabitacion_dato;
	}
	public void setC6fk_idhabitacion_dato(int C6fk_idhabitacion_dato){
		this.C6fk_idhabitacion_dato = C6fk_idhabitacion_dato;
	}
	public int getC7fk_idhabitacion_sensor(){
		return C7fk_idhabitacion_sensor;
	}
	public void setC7fk_idhabitacion_sensor(int C7fk_idhabitacion_sensor){
		this.C7fk_idhabitacion_sensor = C7fk_idhabitacion_sensor;
	}
	public int getC8fk_idhabitacion_mini_pc(){
		return C8fk_idhabitacion_mini_pc;
	}
	public void setC8fk_idhabitacion_mini_pc(int C8fk_idhabitacion_mini_pc){
		this.C8fk_idhabitacion_mini_pc = C8fk_idhabitacion_mini_pc;
	}
	public String toString() {
		return "habitacion_item_sensor_gpio(" + ",idhabitacion_item_sensor_gpio=" + C1idhabitacion_item_sensor_gpio + " ,fecha_update=" + C2fecha_update + " ,alto_bajo=" + C3alto_bajo + " ,gpio=" + C4gpio + " ,activar=" + C5activar + " ,fk_idhabitacion_dato=" + C6fk_idhabitacion_dato + " ,fk_idhabitacion_sensor=" + C7fk_idhabitacion_sensor + " ,fk_idhabitacion_mini_pc=" + C8fk_idhabitacion_mini_pc + " )";
	}
}
