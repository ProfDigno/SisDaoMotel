	package FORMULARIO.ENTIDAD;
public class habitacion_arduino {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_arduino;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private String C5usb_puerto;
private int C6usb_bps;
private int C7fk_idhabitacion_mini_pc;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public habitacion_arduino() {
		setTb_habitacion_arduino("habitacion_arduino");
		setId_idhabitacion_arduino("idhabitacion_arduino");
	}
	public static String getTb_habitacion_arduino(){
		return nom_tabla;
	}
	public static void setTb_habitacion_arduino(String nom_tabla){
		habitacion_arduino.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_arduino(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_arduino(String nom_idtabla){
		habitacion_arduino.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idhabitacion_arduino(){
		return C1idhabitacion_arduino;
	}
	public void setC1idhabitacion_arduino(int C1idhabitacion_arduino){
		this.C1idhabitacion_arduino = C1idhabitacion_arduino;
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
	public String getC5usb_puerto(){
		return C5usb_puerto;
	}
	public void setC5usb_puerto(String C5usb_puerto){
		this.C5usb_puerto = C5usb_puerto;
	}
	public int getC6usb_bps(){
		return C6usb_bps;
	}
	public void setC6usb_bps(int C6usb_bps){
		this.C6usb_bps = C6usb_bps;
	}
	public int getC7fk_idhabitacion_mini_pc(){
		return C7fk_idhabitacion_mini_pc;
	}
	public void setC7fk_idhabitacion_mini_pc(int C7fk_idhabitacion_mini_pc){
		this.C7fk_idhabitacion_mini_pc = C7fk_idhabitacion_mini_pc;
	}
	public String toString() {
		return "habitacion_arduino(" + ",idhabitacion_arduino=" + C1idhabitacion_arduino + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,usb_puerto=" + C5usb_puerto + " ,usb_bps=" + C6usb_bps + " ,fk_idhabitacion_mini_pc=" + C7fk_idhabitacion_mini_pc + " )";
	}
}
