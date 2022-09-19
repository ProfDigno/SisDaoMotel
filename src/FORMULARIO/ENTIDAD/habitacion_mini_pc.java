	package FORMULARIO.ENTIDAD;
public class habitacion_mini_pc {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_mini_pc;
private String C2fecha_creado;
private String C3creado_por;
private String C4placa_nombre;
private String C5placa_ip;
private String C6placa_ubicacion;
private String C7ssh_usuario;
private String C8ssh_password;
private String C9ult_conexion;
private static String nom_tabla;
private static String nom_idtabla;

//---------------TABLA-ID---------------
	public habitacion_mini_pc() {
		setTb_habitacion_mini_pc("habitacion_mini_pc");
		setId_idhabitacion_mini_pc("idhabitacion_mini_pc");
	}
	public static String getTb_habitacion_mini_pc(){
		return nom_tabla;
	}
	public static void setTb_habitacion_mini_pc(String nom_tabla){
		habitacion_mini_pc.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_mini_pc(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_mini_pc(String nom_idtabla){
		habitacion_mini_pc.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idhabitacion_mini_pc(){
		return C1idhabitacion_mini_pc;
	}
	public void setC1idhabitacion_mini_pc(int C1idhabitacion_mini_pc){
		this.C1idhabitacion_mini_pc = C1idhabitacion_mini_pc;
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
	public String getC4placa_nombre(){
		return C4placa_nombre;
	}
	public void setC4placa_nombre(String C4placa_nombre){
		this.C4placa_nombre = C4placa_nombre;
	}
	public String getC5placa_ip(){
		return C5placa_ip;
	}
	public void setC5placa_ip(String C5placa_ip){
		this.C5placa_ip = C5placa_ip;
	}
	public String getC6placa_ubicacion(){
		return C6placa_ubicacion;
	}
	public void setC6placa_ubicacion(String C6placa_ubicacion){
		this.C6placa_ubicacion = C6placa_ubicacion;
	}
	public String getC7ssh_usuario(){
		return C7ssh_usuario;
	}
	public void setC7ssh_usuario(String C7ssh_usuario){
		this.C7ssh_usuario = C7ssh_usuario;
	}
	public String getC8ssh_password(){
		return C8ssh_password;
	}
	public void setC8ssh_password(String C8ssh_password){
		this.C8ssh_password = C8ssh_password;
	}

    public String getC9ult_conexion() {
        return C9ult_conexion;
    }

    public void setC9ult_conexion(String C9ult_conexion) {
        this.C9ult_conexion = C9ult_conexion;
    }
        
	public String toString() {
		return "habitacion_mini_pc(" + ",idhabitacion_mini_pc=" + C1idhabitacion_mini_pc + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,placa_nombre=" + C4placa_nombre + " ,placa_ip=" + C5placa_ip + " ,placa_ubicacion=" + C6placa_ubicacion + " ,ssh_usuario=" + C7ssh_usuario + " ,ssh_password=" + C8ssh_password + " )";
	}
}
