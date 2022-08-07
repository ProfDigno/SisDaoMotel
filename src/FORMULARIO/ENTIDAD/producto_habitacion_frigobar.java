	package FORMULARIO.ENTIDAD;
public class producto_habitacion_frigobar {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_habitacion_frigobar;
private String C2fecha_creado;
private String C3creado_por;
private double C4cantidad;
private String C5descripcion;
private double C6precio_venta;
private double C7precio_compra;
private boolean C8activo;
private int C9fk_idhabitacion_dato;
private int C10fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto_habitacion_frigobar() {
		setTb_producto_habitacion_frigobar("producto_habitacion_frigobar");
		setId_idproducto_habitacion_frigobar("idproducto_habitacion_frigobar");
	}
	public static String getTb_producto_habitacion_frigobar(){
		return nom_tabla;
	}
	public static void setTb_producto_habitacion_frigobar(String nom_tabla){
		producto_habitacion_frigobar.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_habitacion_frigobar(){
		return nom_idtabla;
	}
	public static void setId_idproducto_habitacion_frigobar(String nom_idtabla){
		producto_habitacion_frigobar.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_habitacion_frigobar(){
		return C1idproducto_habitacion_frigobar;
	}
	public void setC1idproducto_habitacion_frigobar(int C1idproducto_habitacion_frigobar){
		this.C1idproducto_habitacion_frigobar = C1idproducto_habitacion_frigobar;
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
	public int getC9fk_idhabitacion_dato(){
		return C9fk_idhabitacion_dato;
	}
	public void setC9fk_idhabitacion_dato(int C9fk_idhabitacion_dato){
		this.C9fk_idhabitacion_dato = C9fk_idhabitacion_dato;
	}
	public int getC10fk_idproducto(){
		return C10fk_idproducto;
	}
	public void setC10fk_idproducto(int C10fk_idproducto){
		this.C10fk_idproducto = C10fk_idproducto;
	}
	public String toString() {
		return "producto_habitacion_frigobar(" + ",idproducto_habitacion_frigobar=" + C1idproducto_habitacion_frigobar + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,cantidad=" + C4cantidad + " ,descripcion=" + C5descripcion + " ,precio_venta=" + C6precio_venta + " ,precio_compra=" + C7precio_compra + " ,activo=" + C8activo + " ,fk_idhabitacion_dato=" + C9fk_idhabitacion_dato + " ,fk_idproducto=" + C10fk_idproducto + " )";
	}
}
