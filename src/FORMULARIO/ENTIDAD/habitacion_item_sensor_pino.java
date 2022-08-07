	package FORMULARIO.ENTIDAD;
public class habitacion_item_sensor_pino {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_item_sensor_pino;
private String C2fecha_update;
private boolean C3alto_bajo;
private int C4pino;
private boolean C5activar;
private int C6fk_idhabitacion_sensor;
private int C7fk_idhabitacion_dato;
private int C8fk_idhabitacion_arduino;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public habitacion_item_sensor_pino() {
		setTb_habitacion_item_sensor_pino("habitacion_item_sensor_pino");
		setId_idhabitacion_item_sensor_pino("idhabitacion_item_sensor_pino");
	}
	public static String getTb_habitacion_item_sensor_pino(){
		return nom_tabla;
	}
	public static void setTb_habitacion_item_sensor_pino(String nom_tabla){
		habitacion_item_sensor_pino.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_item_sensor_pino(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_item_sensor_pino(String nom_idtabla){
		habitacion_item_sensor_pino.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idhabitacion_item_sensor_pino(){
		return C1idhabitacion_item_sensor_pino;
	}
	public void setC1idhabitacion_item_sensor_pino(int C1idhabitacion_item_sensor_pino){
		this.C1idhabitacion_item_sensor_pino = C1idhabitacion_item_sensor_pino;
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
	public int getC4pino(){
		return C4pino;
	}
	public void setC4pino(int C4pino){
		this.C4pino = C4pino;
	}
	public boolean getC5activar(){
		return C5activar;
	}
	public void setC5activar(boolean C5activar){
		this.C5activar = C5activar;
	}
	public int getC6fk_idhabitacion_sensor(){
		return C6fk_idhabitacion_sensor;
	}
	public void setC6fk_idhabitacion_sensor(int C6fk_idhabitacion_sensor){
		this.C6fk_idhabitacion_sensor = C6fk_idhabitacion_sensor;
	}
	public int getC7fk_idhabitacion_dato(){
		return C7fk_idhabitacion_dato;
	}
	public void setC7fk_idhabitacion_dato(int C7fk_idhabitacion_dato){
		this.C7fk_idhabitacion_dato = C7fk_idhabitacion_dato;
	}
	public int getC8fk_idhabitacion_arduino(){
		return C8fk_idhabitacion_arduino;
	}
	public void setC8fk_idhabitacion_arduino(int C8fk_idhabitacion_arduino){
		this.C8fk_idhabitacion_arduino = C8fk_idhabitacion_arduino;
	}
	public String toString() {
		return "habitacion_item_sensor_pino(" + ",idhabitacion_item_sensor_pino=" + C1idhabitacion_item_sensor_pino + " ,fecha_update=" + C2fecha_update + " ,alto_bajo=" + C3alto_bajo + " ,pino=" + C4pino + " ,activar=" + C5activar + " ,fk_idhabitacion_sensor=" + C6fk_idhabitacion_sensor + " ,fk_idhabitacion_dato=" + C7fk_idhabitacion_dato + " ,fk_idhabitacion_arduino=" + C8fk_idhabitacion_arduino + " )";
	}
}
