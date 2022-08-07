	package FORMULARIO.ENTIDAD;
public class rh_liquidacion {

//---------------DECLARAR VARIABLES---------------
private int C1idrh_liquidacion;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_desde;
private String C5fecha_hasta;
private double C6monto_vale;
private double C7monto_descuento;
private double C8monto_liquidacion;
private String C9monto_letra;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public rh_liquidacion() {
		setTb_rh_liquidacion("rh_liquidacion");
		setId_idrh_liquidacion("idrh_liquidacion");
	}
	public static String getTb_rh_liquidacion(){
		return nom_tabla;
	}
	public static void setTb_rh_liquidacion(String nom_tabla){
		rh_liquidacion.nom_tabla = nom_tabla;
	}
	public static String getId_idrh_liquidacion(){
		return nom_idtabla;
	}
	public static void setId_idrh_liquidacion(String nom_idtabla){
		rh_liquidacion.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idrh_liquidacion(){
		return C1idrh_liquidacion;
	}
	public void setC1idrh_liquidacion(int C1idrh_liquidacion){
		this.C1idrh_liquidacion = C1idrh_liquidacion;
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
	public String getC4fecha_desde(){
		return C4fecha_desde;
	}
	public void setC4fecha_desde(String C4fecha_desde){
		this.C4fecha_desde = C4fecha_desde;
	}
	public String getC5fecha_hasta(){
		return C5fecha_hasta;
	}
	public void setC5fecha_hasta(String C5fecha_hasta){
		this.C5fecha_hasta = C5fecha_hasta;
	}
	public double getC6monto_vale(){
		return C6monto_vale;
	}
	public void setC6monto_vale(double C6monto_vale){
		this.C6monto_vale = C6monto_vale;
	}
	public double getC7monto_descuento(){
		return C7monto_descuento;
	}
	public void setC7monto_descuento(double C7monto_descuento){
		this.C7monto_descuento = C7monto_descuento;
	}
	public double getC8monto_liquidacion(){
		return C8monto_liquidacion;
	}
	public void setC8monto_liquidacion(double C8monto_liquidacion){
		this.C8monto_liquidacion = C8monto_liquidacion;
	}
	public String getC9monto_letra(){
		return C9monto_letra;
	}
	public void setC9monto_letra(String C9monto_letra){
		this.C9monto_letra = C9monto_letra;
	}
	public String toString() {
		return "rh_liquidacion(" + ",idrh_liquidacion=" + C1idrh_liquidacion + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_desde=" + C4fecha_desde + " ,fecha_hasta=" + C5fecha_hasta + " ,monto_vale=" + C6monto_vale + " ,monto_descuento=" + C7monto_descuento + " ,monto_liquidacion=" + C8monto_liquidacion + " ,monto_letra=" + C9monto_letra + " )";
	}
}
