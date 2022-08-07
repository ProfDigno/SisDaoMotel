	package FORMULARIO.ENTIDAD;
public class habitacion_estado_gpio_temp {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_estado_gpio_temp;
private String C2fecha_update;
private boolean C3es_update;
private int C4nro_habitacion;
private boolean C5alto_bajo;
private int C6sensor;
private int C7gpio;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public habitacion_estado_gpio_temp() {
		setTb_habitacion_estado_gpio_temp("habitacion_estado_gpio_temp");
		setId_idhabitacion_estado_gpio_temp("idhabitacion_estado_gpio_temp");
	}
	public static String getTb_habitacion_estado_gpio_temp(){
		return nom_tabla;
	}
	public static void setTb_habitacion_estado_gpio_temp(String nom_tabla){
		habitacion_estado_gpio_temp.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_estado_gpio_temp(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_estado_gpio_temp(String nom_idtabla){
		habitacion_estado_gpio_temp.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idhabitacion_estado_gpio_temp(){
		return C1idhabitacion_estado_gpio_temp;
	}
	public void setC1idhabitacion_estado_gpio_temp(int C1idhabitacion_estado_gpio_temp){
		this.C1idhabitacion_estado_gpio_temp = C1idhabitacion_estado_gpio_temp;
	}
	public String getC2fecha_update(){
		return C2fecha_update;
	}
	public void setC2fecha_update(String C2fecha_update){
		this.C2fecha_update = C2fecha_update;
	}
	public boolean getC3es_update(){
		return C3es_update;
	}
	public void setC3es_update(boolean C3es_update){
		this.C3es_update = C3es_update;
	}
	public int getC4nro_habitacion(){
		return C4nro_habitacion;
	}
	public void setC4nro_habitacion(int C4nro_habitacion){
		this.C4nro_habitacion = C4nro_habitacion;
	}
	public boolean getC5alto_bajo(){
		return C5alto_bajo;
	}
	public void setC5alto_bajo(boolean C5alto_bajo){
		this.C5alto_bajo = C5alto_bajo;
	}
	public int getC6sensor(){
		return C6sensor;
	}
	public void setC6sensor(int C6sensor){
		this.C6sensor = C6sensor;
	}
	public int getC7gpio(){
		return C7gpio;
	}
	public void setC7gpio(int C7gpio){
		this.C7gpio = C7gpio;
	}
	public String toString() {
		return "habitacion_estado_gpio_temp(" + ",idhabitacion_estado_gpio_temp=" + C1idhabitacion_estado_gpio_temp + " ,fecha_update=" + C2fecha_update + " ,es_update=" + C3es_update + " ,nro_habitacion=" + C4nro_habitacion + " ,alto_bajo=" + C5alto_bajo + " ,sensor=" + C6sensor + " ,gpio=" + C7gpio + " )";
	}
}
