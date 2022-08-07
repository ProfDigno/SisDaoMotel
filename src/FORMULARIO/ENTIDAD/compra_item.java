	package FORMULARIO.ENTIDAD;
public class compra_item {

//---------------DECLARAR VARIABLES---------------
private int C1idcompra_item;
private String C2fecha_creado;
private String C3creado_por;
private String C4tipo_item;
private String C5descripcion;
private double C6cantidad;
private double C7precio_compra;
private int C8fk_idproducto;
private int C9fk_idcompra;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public compra_item() {
		setTb_compra_item("compra_item");
		setId_idcompra_item("idcompra_item");
	}
	public static String getTb_compra_item(){
		return nom_tabla;
	}
	public static void setTb_compra_item(String nom_tabla){
		compra_item.nom_tabla = nom_tabla;
	}
	public static String getId_idcompra_item(){
		return nom_idtabla;
	}
	public static void setId_idcompra_item(String nom_idtabla){
		compra_item.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcompra_item(){
		return C1idcompra_item;
	}
	public void setC1idcompra_item(int C1idcompra_item){
		this.C1idcompra_item = C1idcompra_item;
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
	public double getC7precio_compra(){
		return C7precio_compra;
	}
	public void setC7precio_compra(double C7precio_compra){
		this.C7precio_compra = C7precio_compra;
	}
	public int getC8fk_idproducto(){
		return C8fk_idproducto;
	}
	public void setC8fk_idproducto(int C8fk_idproducto){
		this.C8fk_idproducto = C8fk_idproducto;
	}
	public int getC9fk_idcompra(){
		return C9fk_idcompra;
	}
	public void setC9fk_idcompra(int C9fk_idcompra){
		this.C9fk_idcompra = C9fk_idcompra;
	}
	public String toString() {
		return "compra_item(" + ",idcompra_item=" + C1idcompra_item + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,tipo_item=" + C4tipo_item + " ,descripcion=" + C5descripcion + " ,cantidad=" + C6cantidad + " ,precio_compra=" + C7precio_compra + " ,fk_idproducto=" + C8fk_idproducto + " ,fk_idcompra=" + C9fk_idcompra + " )";
	}
}
