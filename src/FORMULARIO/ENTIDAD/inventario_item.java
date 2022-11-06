	package FORMULARIO.ENTIDAD;
public class inventario_item {

//---------------DECLARAR VARIABLES---------------
private int C1idinventario_item;
private String C2fecha_creado;
private String C3creado_por;
private int C4stock_sistema;
private int C5stock_contado;
private double C6precio_venta;
private double C7precio_compra;
private boolean C8es_temp;
private boolean C9es_cargado;
private int C10fk_idinventario;
private int C11fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public inventario_item() {
		setTb_inventario_item("inventario_item");
		setId_idinventario_item("idinventario_item");
	}
	public static String getTb_inventario_item(){
		return nom_tabla;
	}
	public static void setTb_inventario_item(String nom_tabla){
		inventario_item.nom_tabla = nom_tabla;
	}
	public static String getId_idinventario_item(){
		return nom_idtabla;
	}
	public static void setId_idinventario_item(String nom_idtabla){
		inventario_item.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idinventario_item(){
		return C1idinventario_item;
	}
	public void setC1idinventario_item(int C1idinventario_item){
		this.C1idinventario_item = C1idinventario_item;
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
	public int getC4stock_sistema(){
		return C4stock_sistema;
	}
	public void setC4stock_sistema(int C4stock_sistema){
		this.C4stock_sistema = C4stock_sistema;
	}
	public int getC5stock_contado(){
		return C5stock_contado;
	}
	public void setC5stock_contado(int C5stock_contado){
		this.C5stock_contado = C5stock_contado;
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
	public boolean getC8es_temp(){
		return C8es_temp;
	}
	public void setC8es_temp(boolean C8es_temp){
		this.C8es_temp = C8es_temp;
	}
	public boolean getC9es_cargado(){
		return C9es_cargado;
	}
	public void setC9es_cargado(boolean C9es_cargado){
		this.C9es_cargado = C9es_cargado;
	}
	public int getC10fk_idinventario(){
		return C10fk_idinventario;
	}
	public void setC10fk_idinventario(int C10fk_idinventario){
		this.C10fk_idinventario = C10fk_idinventario;
	}
	public int getC11fk_idproducto(){
		return C11fk_idproducto;
	}
	public void setC11fk_idproducto(int C11fk_idproducto){
		this.C11fk_idproducto = C11fk_idproducto;
	}
	public String toString() {
		return "inventario_item(" + ",idinventario_item=" + C1idinventario_item + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,stock_sistema=" + C4stock_sistema + " ,stock_contado=" + C5stock_contado + " ,precio_venta=" + C6precio_venta + " ,precio_compra=" + C7precio_compra + " ,es_temp=" + C8es_temp + " ,es_cargado=" + C9es_cargado + " ,fk_idinventario=" + C10fk_idinventario + " ,fk_idproducto=" + C11fk_idproducto + " )";
	}
}
