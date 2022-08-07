	package FORMULARIO.ENTIDAD;
public class caja_cierre_detalle {

//---------------DECLARAR VARIABLES---------------
private int C1idcaja_cierre_detalle;
private String C2fecha_creado;
private String C3creado_por;
private String C4cerrado_por;
private boolean C5es_cerrado;
private double C6monto_venta;
private double C7monto_gasto;
private double C8monto_compra;
private double C9monto_apertura;
private double C10monto_vale;
private double C11monto_cierre;
private double C12monto_liquidacion;
private String C13estado;
private String C14descripcion;
private int C15fk_idgasto;
private int C16fk_idcompra;
private int C17fk_idventa;
private int C18fk_idusuario;
private int C19fk_idrh_vale;
private int C20fk_idrh_liquidacion;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public caja_cierre_detalle() {
		setTb_caja_cierre_detalle("caja_cierre_detalle");
		setId_idcaja_cierre_detalle("idcaja_cierre_detalle");
	}
	public static String getTb_caja_cierre_detalle(){
		return nom_tabla;
	}
	public static void setTb_caja_cierre_detalle(String nom_tabla){
		caja_cierre_detalle.nom_tabla = nom_tabla;
	}
	public static String getId_idcaja_cierre_detalle(){
		return nom_idtabla;
	}
	public static void setId_idcaja_cierre_detalle(String nom_idtabla){
		caja_cierre_detalle.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcaja_cierre_detalle(){
		return C1idcaja_cierre_detalle;
	}
	public void setC1idcaja_cierre_detalle(int C1idcaja_cierre_detalle){
		this.C1idcaja_cierre_detalle = C1idcaja_cierre_detalle;
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
	public String getC4cerrado_por(){
		return C4cerrado_por;
	}
	public void setC4cerrado_por(String C4cerrado_por){
		this.C4cerrado_por = C4cerrado_por;
	}
	public boolean getC5es_cerrado(){
		return C5es_cerrado;
	}
	public void setC5es_cerrado(boolean C5es_cerrado){
		this.C5es_cerrado = C5es_cerrado;
	}
	public double getC6monto_venta(){
		return C6monto_venta;
	}
	public void setC6monto_venta(double C6monto_venta){
		this.C6monto_venta = C6monto_venta;
	}
	public double getC7monto_gasto(){
		return C7monto_gasto;
	}
	public void setC7monto_gasto(double C7monto_gasto){
		this.C7monto_gasto = C7monto_gasto;
	}
	public double getC8monto_compra(){
		return C8monto_compra;
	}
	public void setC8monto_compra(double C8monto_compra){
		this.C8monto_compra = C8monto_compra;
	}
	public double getC9monto_apertura(){
		return C9monto_apertura;
	}
	public void setC9monto_apertura(double C9monto_apertura){
		this.C9monto_apertura = C9monto_apertura;
	}
	public double getC10monto_vale(){
		return C10monto_vale;
	}
	public void setC10monto_vale(double C10monto_vale){
		this.C10monto_vale = C10monto_vale;
	}
	public double getC11monto_cierre(){
		return C11monto_cierre;
	}
	public void setC11monto_cierre(double C11monto_cierre){
		this.C11monto_cierre = C11monto_cierre;
	}
	public double getC12monto_liquidacion(){
		return C12monto_liquidacion;
	}
	public void setC12monto_liquidacion(double C12monto_liquidacion){
		this.C12monto_liquidacion = C12monto_liquidacion;
	}
	public String getC13estado(){
		return C13estado;
	}
	public void setC13estado(String C13estado){
		this.C13estado = C13estado;
	}
	public String getC14descripcion(){
		return C14descripcion;
	}
	public void setC14descripcion(String C14descripcion){
		this.C14descripcion = C14descripcion;
	}
	public int getC15fk_idgasto(){
		return C15fk_idgasto;
	}
	public void setC15fk_idgasto(int C15fk_idgasto){
		this.C15fk_idgasto = C15fk_idgasto;
	}
	public int getC16fk_idcompra(){
		return C16fk_idcompra;
	}
	public void setC16fk_idcompra(int C16fk_idcompra){
		this.C16fk_idcompra = C16fk_idcompra;
	}
	public int getC17fk_idventa(){
		return C17fk_idventa;
	}
	public void setC17fk_idventa(int C17fk_idventa){
		this.C17fk_idventa = C17fk_idventa;
	}
	public int getC18fk_idusuario(){
		return C18fk_idusuario;
	}
	public void setC18fk_idusuario(int C18fk_idusuario){
		this.C18fk_idusuario = C18fk_idusuario;
	}
	public int getC19fk_idrh_vale(){
		return C19fk_idrh_vale;
	}
	public void setC19fk_idrh_vale(int C19fk_idrh_vale){
		this.C19fk_idrh_vale = C19fk_idrh_vale;
	}
	public int getC20fk_idrh_liquidacion(){
		return C20fk_idrh_liquidacion;
	}
	public void setC20fk_idrh_liquidacion(int C20fk_idrh_liquidacion){
		this.C20fk_idrh_liquidacion = C20fk_idrh_liquidacion;
	}
	public String toString() {
		return "caja_cierre_detalle(" + ",idcaja_cierre_detalle=" + C1idcaja_cierre_detalle + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,cerrado_por=" + C4cerrado_por + " ,es_cerrado=" + C5es_cerrado + " ,monto_venta=" + C6monto_venta + " ,monto_gasto=" + C7monto_gasto + " ,monto_compra=" + C8monto_compra + " ,monto_apertura=" + C9monto_apertura + " ,monto_vale=" + C10monto_vale + " ,monto_cierre=" + C11monto_cierre + " ,monto_liquidacion=" + C12monto_liquidacion + " ,estado=" + C13estado + " ,descripcion=" + C14descripcion + " ,fk_idgasto=" + C15fk_idgasto + " ,fk_idcompra=" + C16fk_idcompra + " ,fk_idventa=" + C17fk_idventa + " ,fk_idusuario=" + C18fk_idusuario + " ,fk_idrh_vale=" + C19fk_idrh_vale + " ,fk_idrh_liquidacion=" + C20fk_idrh_liquidacion + " )";
	}
}
