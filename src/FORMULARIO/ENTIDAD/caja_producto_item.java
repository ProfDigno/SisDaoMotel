	package FORMULARIO.ENTIDAD;
public class caja_producto_item {

//---------------DECLARAR VARIABLES---------------
private int C1idcaja_producto_item;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private double C5precio_venta;
private double C6stock_actual;
private double C7cant_vendido;
private double C8cant_cargado;
private int C9fk_idcaja_cierre;
private int C10fk_idproducto;
private int C11fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public caja_producto_item() {
		setTb_caja_producto_item("caja_producto_item");
		setId_idcaja_producto_item("idcaja_producto_item");
	}
	public static String getTb_caja_producto_item(){
		return nom_tabla;
	}
	public static void setTb_caja_producto_item(String nom_tabla){
		caja_producto_item.nom_tabla = nom_tabla;
	}
	public static String getId_idcaja_producto_item(){
		return nom_idtabla;
	}
	public static void setId_idcaja_producto_item(String nom_idtabla){
		caja_producto_item.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcaja_producto_item(){
		return C1idcaja_producto_item;
	}
	public void setC1idcaja_producto_item(int C1idcaja_producto_item){
		this.C1idcaja_producto_item = C1idcaja_producto_item;
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
	public double getC5precio_venta(){
		return C5precio_venta;
	}
	public void setC5precio_venta(double C5precio_venta){
		this.C5precio_venta = C5precio_venta;
	}
	public double getC6stock_actual(){
		return C6stock_actual;
	}
	public void setC6stock_actual(double C6stock_actual){
		this.C6stock_actual = C6stock_actual;
	}
	public double getC7cant_vendido(){
		return C7cant_vendido;
	}
	public void setC7cant_vendido(double C7cant_vendido){
		this.C7cant_vendido = C7cant_vendido;
	}
	public double getC8cant_cargado(){
		return C8cant_cargado;
	}
	public void setC8cant_cargado(double C8cant_cargado){
		this.C8cant_cargado = C8cant_cargado;
	}
	public int getC9fk_idcaja_cierre(){
		return C9fk_idcaja_cierre;
	}
	public void setC9fk_idcaja_cierre(int C9fk_idcaja_cierre){
		this.C9fk_idcaja_cierre = C9fk_idcaja_cierre;
	}
	public int getC10fk_idproducto(){
		return C10fk_idproducto;
	}
	public void setC10fk_idproducto(int C10fk_idproducto){
		this.C10fk_idproducto = C10fk_idproducto;
	}
	public int getC11fk_idusuario(){
		return C11fk_idusuario;
	}
	public void setC11fk_idusuario(int C11fk_idusuario){
		this.C11fk_idusuario = C11fk_idusuario;
	}
	public String toString() {
		return "caja_producto_item(" + ",idcaja_producto_item=" + C1idcaja_producto_item + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,precio_venta=" + C5precio_venta + " ,stock_actual=" + C6stock_actual + " ,cant_vendido=" + C7cant_vendido + " ,cant_cargado=" + C8cant_cargado + " ,fk_idcaja_cierre=" + C9fk_idcaja_cierre + " ,fk_idproducto=" + C10fk_idproducto + " ,fk_idusuario=" + C11fk_idusuario + " )";
	}
}
