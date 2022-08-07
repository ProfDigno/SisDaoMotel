	package FORMULARIO.ENTIDAD;
public class producto_habitacion_insumo {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_habitacion_insumo;
private String C2fecha_creado;
private String C3creado_por;
private double C4cantidad;
private String C5descripcion;
private double C6precio_venta;
private double C7precio_compra;
private boolean C8activo;
private int C9fk_idproducto;
private int C10fk_idhabitacion_dato;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto_habitacion_insumo() {
		setTb_producto_habitacion_insumo("producto_habitacion_insumo");
		setId_idproducto_habitacion_insumo("idproducto_habitacion_insumo");
	}
	public static String getTb_producto_habitacion_insumo(){
		return nom_tabla;
	}
	public static void setTb_producto_habitacion_insumo(String nom_tabla){
		producto_habitacion_insumo.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_habitacion_insumo(){
		return nom_idtabla;
	}
	public static void setId_idproducto_habitacion_insumo(String nom_idtabla){
		producto_habitacion_insumo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_habitacion_insumo(){
		return C1idproducto_habitacion_insumo;
	}
	public void setC1idproducto_habitacion_insumo(int C1idproducto_habitacion_insumo){
		this.C1idproducto_habitacion_insumo = C1idproducto_habitacion_insumo;
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
	public double getC4cantidad(){
		return C4cantidad;
	}
	public void setC4cantidad(double C4cantidad){
		this.C4cantidad = C4cantidad;
	}
	public String getC5descripcion(){
		return C5descripcion;
	}
	public void setC5descripcion(String C5descripcion){
		this.C5descripcion = C5descripcion;
	}
	public double getC6precio_venta(){
		return C6precio_venta;
	}
	public void setC6precio_venta(double C6precio_venta){
		this.C6precio_venta = C6precio_venta;
	}
	public double getC7precio_compra(){
		return C7precio_compra;
	}
	public void setC7precio_compra(double C7precio_compra){
		this.C7precio_compra = C7precio_compra;
	}
	public boolean getC8activo(){
		return C8activo;
	}
	public void setC8activo(boolean C8activo){
		this.C8activo = C8activo;
	}
	public int getC9fk_idproducto(){
		return C9fk_idproducto;
	}
	public void setC9fk_idproducto(int C9fk_idproducto){
		this.C9fk_idproducto = C9fk_idproducto;
	}
	public int getC10fk_idhabitacion_dato(){
		return C10fk_idhabitacion_dato;
	}
	public void setC10fk_idhabitacion_dato(int C10fk_idhabitacion_dato){
		this.C10fk_idhabitacion_dato = C10fk_idhabitacion_dato;
	}
	public String toString() {
		return "producto_habitacion_insumo(" + ",idproducto_habitacion_insumo=" + C1idproducto_habitacion_insumo + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,cantidad=" + C4cantidad + " ,descripcion=" + C5descripcion + " ,precio_venta=" + C6precio_venta + " ,precio_compra=" + C7precio_compra + " ,activo=" + C8activo + " ,fk_idproducto=" + C9fk_idproducto + " ,fk_idhabitacion_dato=" + C10fk_idhabitacion_dato + " )";
	}
}
