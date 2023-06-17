	package FORMULARIO.ENTIDAD;
public class factura_item {

//---------------DECLARAR VARIABLES---------------
private int C1idfactura_item;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private double C5cantidad;
private double C6precio_iva5;
private double C7precio_iva10;
private double C8precio_exenta;
private String C9tipo_item;
private int C10fk_idfactura;
private int C11fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public factura_item() {
		setTb_factura_item("factura_item");
		setId_idfactura_item("idfactura_item");
	}
	public static String getTb_factura_item(){
		return nom_tabla;
	}
	public static void setTb_factura_item(String nom_tabla){
		factura_item.nom_tabla = nom_tabla;
	}
	public static String getId_idfactura_item(){
		return nom_idtabla;
	}
	public static void setId_idfactura_item(String nom_idtabla){
		factura_item.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idfactura_item(){
		return C1idfactura_item;
	}
	public void setC1idfactura_item(int C1idfactura_item){
		this.C1idfactura_item = C1idfactura_item;
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
	public double getC6precio_iva5(){
		return C6precio_iva5;
	}
	public void setC6precio_iva5(double C6precio_iva5){
		this.C6precio_iva5 = C6precio_iva5;
	}
	public double getC7precio_iva10(){
		return C7precio_iva10;
	}
	public void setC7precio_iva10(double C7precio_iva10){
		this.C7precio_iva10 = C7precio_iva10;
	}
	public double getC8precio_exenta(){
		return C8precio_exenta;
	}
	public void setC8precio_exenta(double C8precio_exenta){
		this.C8precio_exenta = C8precio_exenta;
	}
	public String getC9tipo_item(){
		return C9tipo_item;
	}
	public void setC9tipo_item(String C9tipo_item){
		this.C9tipo_item = C9tipo_item;
	}
	public int getC10fk_idfactura(){
		return C10fk_idfactura;
	}
	public void setC10fk_idfactura(int C10fk_idfactura){
		this.C10fk_idfactura = C10fk_idfactura;
	}
	public int getC11fk_idproducto(){
		return C11fk_idproducto;
	}
	public void setC11fk_idproducto(int C11fk_idproducto){
		this.C11fk_idproducto = C11fk_idproducto;
	}
	public String toString() {
		return "factura_item(" + ",idfactura_item=" + C1idfactura_item + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,cantidad=" + C5cantidad + " ,precio_iva5=" + C6precio_iva5 + " ,precio_iva10=" + C7precio_iva10 + " ,precio_exenta=" + C8precio_exenta + " ,tipo_item=" + C9tipo_item + " ,fk_idfactura=" + C10fk_idfactura + " ,fk_idproducto=" + C11fk_idproducto + " )";
	}
}
