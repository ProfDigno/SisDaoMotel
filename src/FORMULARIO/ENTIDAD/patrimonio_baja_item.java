	package FORMULARIO.ENTIDAD;
public class patrimonio_baja_item {

//---------------DECLARAR VARIABLES---------------
private int C1idpatrimonio_baja_item;
private String C2fecha_creado;
private String C3creado_por;
private int C4cantidad;
private String C5descripcion;
private double C6precio_compra;
private String C7referencia;
private int C8fk_idpatrimonio_producto;
private int C9fk_idpatrimonio_baja;
private int C10fk_idpatrimonio_baja_motivo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public patrimonio_baja_item() {
		setTb_patrimonio_baja_item("patrimonio_baja_item");
		setId_idpatrimonio_baja_item("idpatrimonio_baja_item");
	}
	public static String getTb_patrimonio_baja_item(){
		return nom_tabla;
	}
	public static void setTb_patrimonio_baja_item(String nom_tabla){
		patrimonio_baja_item.nom_tabla = nom_tabla;
	}
	public static String getId_idpatrimonio_baja_item(){
		return nom_idtabla;
	}
	public static void setId_idpatrimonio_baja_item(String nom_idtabla){
		patrimonio_baja_item.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idpatrimonio_baja_item(){
		return C1idpatrimonio_baja_item;
	}
	public void setC1idpatrimonio_baja_item(int C1idpatrimonio_baja_item){
		this.C1idpatrimonio_baja_item = C1idpatrimonio_baja_item;
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
	public int getC4cantidad(){
		return C4cantidad;
	}
	public void setC4cantidad(int C4cantidad){
		this.C4cantidad = C4cantidad;
	}
	public String getC5descripcion(){
		return C5descripcion;
	}
	public void setC5descripcion(String C5descripcion){
		this.C5descripcion = C5descripcion;
	}
	public double getC6precio_compra(){
		return C6precio_compra;
	}
	public void setC6precio_compra(double C6precio_compra){
		this.C6precio_compra = C6precio_compra;
	}
	public String getC7referencia(){
		return C7referencia;
	}
	public void setC7referencia(String C7referencia){
		this.C7referencia = C7referencia;
	}
	public int getC8fk_idpatrimonio_producto(){
		return C8fk_idpatrimonio_producto;
	}
	public void setC8fk_idpatrimonio_producto(int C8fk_idpatrimonio_producto){
		this.C8fk_idpatrimonio_producto = C8fk_idpatrimonio_producto;
	}
	public int getC9fk_idpatrimonio_baja(){
		return C9fk_idpatrimonio_baja;
	}
	public void setC9fk_idpatrimonio_baja(int C9fk_idpatrimonio_baja){
		this.C9fk_idpatrimonio_baja = C9fk_idpatrimonio_baja;
	}
	public int getC10fk_idpatrimonio_baja_motivo(){
		return C10fk_idpatrimonio_baja_motivo;
	}
	public void setC10fk_idpatrimonio_baja_motivo(int C10fk_idpatrimonio_baja_motivo){
		this.C10fk_idpatrimonio_baja_motivo = C10fk_idpatrimonio_baja_motivo;
	}
	public String toString() {
		return "patrimonio_baja_item(" + ",idpatrimonio_baja_item=" + C1idpatrimonio_baja_item + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,cantidad=" + C4cantidad + " ,descripcion=" + C5descripcion + " ,precio_compra=" + C6precio_compra + " ,referencia=" + C7referencia + " ,fk_idpatrimonio_producto=" + C8fk_idpatrimonio_producto + " ,fk_idpatrimonio_baja=" + C9fk_idpatrimonio_baja + " ,fk_idpatrimonio_baja_motivo=" + C10fk_idpatrimonio_baja_motivo + " )";
	}
}
