	package FORMULARIO.ENTIDAD;
public class venta_interno {

//---------------DECLARAR VARIABLES---------------
private int C1idventa_interno;
private String C2fecha_creado;
private String C3creado_por;
private String C4monto_letra;
private String C5estado;
private String C6observacion;
private String C7motivo_anulacion;
private double C8monto_interno;
private int C9fk_idusuario;
private int C10fk_idpersona;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public venta_interno() {
		setTb_venta_interno("venta_interno");
		setId_idventa_interno("idventa_interno");
	}
	public static String getTb_venta_interno(){
		return nom_tabla;
	}
	public static void setTb_venta_interno(String nom_tabla){
		venta_interno.nom_tabla = nom_tabla;
	}
	public static String getId_idventa_interno(){
		return nom_idtabla;
	}
	public static void setId_idventa_interno(String nom_idtabla){
		venta_interno.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idventa_interno(){
		return C1idventa_interno;
	}
	public void setC1idventa_interno(int C1idventa_interno){
		this.C1idventa_interno = C1idventa_interno;
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
	public String getC4monto_letra(){
		return C4monto_letra;
	}
	public void setC4monto_letra(String C4monto_letra){
		this.C4monto_letra = C4monto_letra;
	}
	public String getC5estado(){
		return C5estado;
	}
	public void setC5estado(String C5estado){
		this.C5estado = C5estado;
	}
	public String getC6observacion(){
		return C6observacion;
	}
	public void setC6observacion(String C6observacion){
		this.C6observacion = C6observacion;
	}
	public String getC7motivo_anulacion(){
		return C7motivo_anulacion;
	}
	public void setC7motivo_anulacion(String C7motivo_anulacion){
		this.C7motivo_anulacion = C7motivo_anulacion;
	}
	public double getC8monto_interno(){
		return C8monto_interno;
	}
	public void setC8monto_interno(double C8monto_interno){
		this.C8monto_interno = C8monto_interno;
	}
	public int getC9fk_idusuario(){
		return C9fk_idusuario;
	}
	public void setC9fk_idusuario(int C9fk_idusuario){
		this.C9fk_idusuario = C9fk_idusuario;
	}
	public int getC10fk_idpersona(){
		return C10fk_idpersona;
	}
	public void setC10fk_idpersona(int C10fk_idpersona){
		this.C10fk_idpersona = C10fk_idpersona;
	}
	public String toString() {
		return "venta_interno(" + ",idventa_interno=" + C1idventa_interno + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,monto_letra=" + C4monto_letra + " ,estado=" + C5estado + " ,observacion=" + C6observacion + " ,motivo_anulacion=" + C7motivo_anulacion + " ,monto_interno=" + C8monto_interno + " ,fk_idusuario=" + C9fk_idusuario + " ,fk_idpersona=" + C10fk_idpersona + " )";
	}
}
