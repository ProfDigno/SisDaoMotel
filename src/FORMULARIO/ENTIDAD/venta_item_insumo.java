	package FORMULARIO.ENTIDAD;
public class venta_item_insumo {

//---------------DECLARAR VARIABLES---------------
private int C1idventa_item_insumo;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private double C5cantidad;
private double C6precio_venta;
private double C7precio_compra;
private int C8fk_idventa;
private int C9fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public venta_item_insumo() {
		setTb_venta_item_insumo("venta_item_insumo");
		setId_idventa_item_insumo("idventa_item_insumo");
	}
	public static String getTb_venta_item_insumo(){
		return nom_tabla;
	}
	public static void setTb_venta_item_insumo(String nom_tabla){
		venta_item_insumo.nom_tabla = nom_tabla;
	}
	public static String getId_idventa_item_insumo(){
		return nom_idtabla;
	}
	public static void setId_idventa_item_insumo(String nom_idtabla){
		venta_item_insumo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idventa_item_insumo(){
		return C1idventa_item_insumo;
	}
	public void setC1idventa_item_insumo(int C1idventa_item_insumo){
		this.C1idventa_item_insumo = C1idventa_item_insumo;
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
	public double getC5cantidad(){
		return C5cantidad;
	}
	public void setC5cantidad(double C5cantidad){
		this.C5cantidad = C5cantidad;
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
	public int getC8fk_idventa(){
		return C8fk_idventa;
	}
	public void setC8fk_idventa(int C8fk_idventa){
		this.C8fk_idventa = C8fk_idventa;
	}
	public int getC9fk_idproducto(){
		return C9fk_idproducto;
	}
	public void setC9fk_idproducto(int C9fk_idproducto){
		this.C9fk_idproducto = C9fk_idproducto;
	}
	public String toString() {
		return "venta_item_insumo(" + ",idventa_item_insumo=" + C1idventa_item_insumo + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,cantidad=" + C5cantidad + " ,precio_venta=" + C6precio_venta + " ,precio_compra=" + C7precio_compra + " ,fk_idventa=" + C8fk_idventa + " ,fk_idproducto=" + C9fk_idproducto + " )";
	}
}
