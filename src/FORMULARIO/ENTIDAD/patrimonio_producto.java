	package FORMULARIO.ENTIDAD;
public class patrimonio_producto {

//---------------DECLARAR VARIABLES---------------
private int C1idpatrimonio_producto;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private String C5referencia;
private double C6precio_compra;
private String C7tipo;
private String C8estado;
private int C9stock;
private int C10fk_idpatrimonio_ubicacion;
private int C11fk_idpatrimonio_categoria;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public patrimonio_producto() {
		setTb_patrimonio_producto("patrimonio_producto");
		setId_idpatrimonio_producto("idpatrimonio_producto");
	}
	public static String getTb_patrimonio_producto(){
		return nom_tabla;
	}
	public static void setTb_patrimonio_producto(String nom_tabla){
		patrimonio_producto.nom_tabla = nom_tabla;
	}
	public static String getId_idpatrimonio_producto(){
		return nom_idtabla;
	}
	public static void setId_idpatrimonio_producto(String nom_idtabla){
		patrimonio_producto.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idpatrimonio_producto(){
		return C1idpatrimonio_producto;
	}
	public void setC1idpatrimonio_producto(int C1idpatrimonio_producto){
		this.C1idpatrimonio_producto = C1idpatrimonio_producto;
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
	public String getC4nombre(){
		return C4nombre;
	}
	public void setC4nombre(String C4nombre){
		this.C4nombre = C4nombre;
	}
	public String getC5referencia(){
		return C5referencia;
	}
	public void setC5referencia(String C5referencia){
		this.C5referencia = C5referencia;
	}
	public double getC6precio_compra(){
		return C6precio_compra;
	}
	public void setC6precio_compra(double C6precio_compra){
		this.C6precio_compra = C6precio_compra;
	}
	public String getC7tipo(){
		return C7tipo;
	}
	public void setC7tipo(String C7tipo){
		this.C7tipo = C7tipo;
	}
	public String getC8estado(){
		return C8estado;
	}
	public void setC8estado(String C8estado){
		this.C8estado = C8estado;
	}
	public int getC9stock(){
		return C9stock;
	}
	public void setC9stock(int C9stock){
		this.C9stock = C9stock;
	}
	public int getC10fk_idpatrimonio_ubicacion(){
		return C10fk_idpatrimonio_ubicacion;
	}
	public void setC10fk_idpatrimonio_ubicacion(int C10fk_idpatrimonio_ubicacion){
		this.C10fk_idpatrimonio_ubicacion = C10fk_idpatrimonio_ubicacion;
	}
	public int getC11fk_idpatrimonio_categoria(){
		return C11fk_idpatrimonio_categoria;
	}
	public void setC11fk_idpatrimonio_categoria(int C11fk_idpatrimonio_categoria){
		this.C11fk_idpatrimonio_categoria = C11fk_idpatrimonio_categoria;
	}
	public String toString() {
		return "patrimonio_producto(" + ",idpatrimonio_producto=" + C1idpatrimonio_producto + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,referencia=" + C5referencia + " ,precio_compra=" + C6precio_compra + " ,tipo=" + C7tipo + " ,estado=" + C8estado + " ,stock=" + C9stock + " ,fk_idpatrimonio_ubicacion=" + C10fk_idpatrimonio_ubicacion + " ,fk_idpatrimonio_categoria=" + C11fk_idpatrimonio_categoria + " )";
	}
}
