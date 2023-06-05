	package FORMULARIO.ENTIDAD;
public class rh_liquidacion_detalle {

//---------------DECLARAR VARIABLES---------------
private int C1idrh_liquidacion_detalle;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private double C5monto_descuento;
private double C6monto_vale;
private String C7tabla;
private String C8estado;
private int C9fk_idrh_liquidacion;
private int C10fk_idrh_descuento;
private int C11fk_idrh_vale;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public rh_liquidacion_detalle() {
		setTb_rh_liquidacion_detalle("rh_liquidacion_detalle");
		setId_idrh_liquidacion_detalle("idrh_liquidacion_detalle");
	}
	public static String getTb_rh_liquidacion_detalle(){
		return nom_tabla;
	}
	public static void setTb_rh_liquidacion_detalle(String nom_tabla){
		rh_liquidacion_detalle.nom_tabla = nom_tabla;
	}
	public static String getId_idrh_liquidacion_detalle(){
		return nom_idtabla;
	}
	public static void setId_idrh_liquidacion_detalle(String nom_idtabla){
		rh_liquidacion_detalle.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idrh_liquidacion_detalle(){
		return C1idrh_liquidacion_detalle;
	}
	public void setC1idrh_liquidacion_detalle(int C1idrh_liquidacion_detalle){
		this.C1idrh_liquidacion_detalle = C1idrh_liquidacion_detalle;
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
	public String getC4descripcion(){
		return C4descripcion;
	}
	public void setC4descripcion(String C4descripcion){
		this.C4descripcion = C4descripcion;
	}
	public double getC5monto_descuento(){
		return C5monto_descuento;
	}
	public void setC5monto_descuento(double C5monto_descuento){
		this.C5monto_descuento = C5monto_descuento;
	}
	public double getC6monto_vale(){
		return C6monto_vale;
	}
	public void setC6monto_vale(double C6monto_vale){
		this.C6monto_vale = C6monto_vale;
	}
	public String getC7tabla(){
		return C7tabla;
	}
	public void setC7tabla(String C7tabla){
		this.C7tabla = C7tabla;
	}
	public String getC8estado(){
		return C8estado;
	}
	public void setC8estado(String C8estado){
		this.C8estado = C8estado;
	}
	public int getC9fk_idrh_liquidacion(){
		return C9fk_idrh_liquidacion;
	}
	public void setC9fk_idrh_liquidacion(int C9fk_idrh_liquidacion){
		this.C9fk_idrh_liquidacion = C9fk_idrh_liquidacion;
	}
	public int getC10fk_idrh_descuento(){
		return C10fk_idrh_descuento;
	}
	public void setC10fk_idrh_descuento(int C10fk_idrh_descuento){
		this.C10fk_idrh_descuento = C10fk_idrh_descuento;
	}
	public int getC11fk_idrh_vale(){
		return C11fk_idrh_vale;
	}
	public void setC11fk_idrh_vale(int C11fk_idrh_vale){
		this.C11fk_idrh_vale = C11fk_idrh_vale;
	}
	public String toString() {
		return "rh_liquidacion_detalle(" + ",idrh_liquidacion_detalle=" + C1idrh_liquidacion_detalle + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,monto_descuento=" + C5monto_descuento + " ,monto_vale=" + C6monto_vale + " ,tabla=" + C7tabla + " ,estado=" + C8estado + " ,fk_idrh_liquidacion=" + C9fk_idrh_liquidacion + " ,fk_idrh_descuento=" + C10fk_idrh_descuento + " ,fk_idrh_vale=" + C11fk_idrh_vale + " )";
	}
}
