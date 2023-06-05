	package FORMULARIO.ENTIDAD;
public class venta_item_eliminar {

//---------------DECLARAR VARIABLES---------------
private int C1idventa_item_eliminar;
private String C2fecha_creado;
private String C3creado_por;
private String C4tipo_item;
private String C5descripcion;
private double C6cantidad;
private double C7precio_venta;
private double C8precio_compra;
private int C9fk_idproducto;
private int C10fk_idventa_eliminar;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public venta_item_eliminar() {
		setTb_venta_item_eliminar("venta_item_eliminar");
		setId_idventa_item_eliminar("idventa_item_eliminar");
	}
	public static String getTb_venta_item_eliminar(){
		return nom_tabla;
	}
	public static void setTb_venta_item_eliminar(String nom_tabla){
		venta_item_eliminar.nom_tabla = nom_tabla;
	}
	public static String getId_idventa_item_eliminar(){
		return nom_idtabla;
	}
	public static void setId_idventa_item_eliminar(String nom_idtabla){
		venta_item_eliminar.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idventa_item_eliminar(){
		return C1idventa_item_eliminar;
	}
	public void setC1idventa_item_eliminar(int C1idventa_item_eliminar){
		this.C1idventa_item_eliminar = C1idventa_item_eliminar;
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
	public String getC4tipo_item(){
		return C4tipo_item;
	}
	public void setC4tipo_item(String C4tipo_item){
		this.C4tipo_item = C4tipo_item;
	}
	public String getC5descripcion(){
		return C5descripcion;
	}
	public void setC5descripcion(String C5descripcion){
		this.C5descripcion = C5descripcion;
	}
	public double getC6cantidad(){
		return C6cantidad;
	}
	public void setC6cantidad(double C6cantidad){
		this.C6cantidad = C6cantidad;
	}
	public double getC7precio_venta(){
		return C7precio_venta;
	}
	public void setC7precio_venta(double C7precio_venta){
		this.C7precio_venta = C7precio_venta;
	}
	public double getC8precio_compra(){
		return C8precio_compra;
	}
	public void setC8precio_compra(double C8precio_compra){
		this.C8precio_compra = C8precio_compra;
	}
	public int getC9fk_idproducto(){
		return C9fk_idproducto;
	}
	public void setC9fk_idproducto(int C9fk_idproducto){
		this.C9fk_idproducto = C9fk_idproducto;
	}
	public int getC10fk_idventa_eliminar(){
		return C10fk_idventa_eliminar;
	}
	public void setC10fk_idventa_eliminar(int C10fk_idventa_eliminar){
		this.C10fk_idventa_eliminar = C10fk_idventa_eliminar;
	}
	public String toString() {
		return "venta_item_eliminar(" + ",idventa_item_eliminar=" + C1idventa_item_eliminar + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,tipo_item=" + C4tipo_item + " ,descripcion=" + C5descripcion + " ,cantidad=" + C6cantidad + " ,precio_venta=" + C7precio_venta + " ,precio_compra=" + C8precio_compra + " ,fk_idproducto=" + C9fk_idproducto + " ,fk_idventa_eliminar=" + C10fk_idventa_eliminar + " )";
	}
}
