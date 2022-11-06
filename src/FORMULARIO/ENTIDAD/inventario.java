	package FORMULARIO.ENTIDAD;
public class inventario {

//---------------DECLARAR VARIABLES---------------
private int C1idinventario;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_inicio;
private String C5fecha_fin;
private String C6descripcion;
private boolean C7es_abierto;
private boolean C8es_cerrado;
private double C9total_precio_venta;
private double C10total_precio_compra;
private int C11fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public inventario() {
		setTb_inventario("inventario");
		setId_idinventario("idinventario");
	}
	public static String getTb_inventario(){
		return nom_tabla;
	}
	public static void setTb_inventario(String nom_tabla){
		inventario.nom_tabla = nom_tabla;
	}
	public static String getId_idinventario(){
		return nom_idtabla;
	}
	public static void setId_idinventario(String nom_idtabla){
		inventario.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idinventario(){
		return C1idinventario;
	}
	public void setC1idinventario(int C1idinventario){
		this.C1idinventario = C1idinventario;
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
	public String getC4fecha_inicio(){
		return C4fecha_inicio;
	}
	public void setC4fecha_inicio(String C4fecha_inicio){
		this.C4fecha_inicio = C4fecha_inicio;
	}
	public String getC5fecha_fin(){
		return C5fecha_fin;
	}
	public void setC5fecha_fin(String C5fecha_fin){
		this.C5fecha_fin = C5fecha_fin;
	}
	public String getC6descripcion(){
		return C6descripcion;
	}
	public void setC6descripcion(String C6descripcion){
		this.C6descripcion = C6descripcion;
	}
	public boolean getC7es_abierto(){
		return C7es_abierto;
	}
	public void setC7es_abierto(boolean C7es_abierto){
		this.C7es_abierto = C7es_abierto;
	}
	public boolean getC8es_cerrado(){
		return C8es_cerrado;
	}
	public void setC8es_cerrado(boolean C8es_cerrado){
		this.C8es_cerrado = C8es_cerrado;
	}
	public double getC9total_precio_venta(){
		return C9total_precio_venta;
	}
	public void setC9total_precio_venta(double C9total_precio_venta){
		this.C9total_precio_venta = C9total_precio_venta;
	}
	public double getC10total_precio_compra(){
		return C10total_precio_compra;
	}
	public void setC10total_precio_compra(double C10total_precio_compra){
		this.C10total_precio_compra = C10total_precio_compra;
	}
	public int getC11fk_idusuario(){
		return C11fk_idusuario;
	}
	public void setC11fk_idusuario(int C11fk_idusuario){
		this.C11fk_idusuario = C11fk_idusuario;
	}
	public String toString() {
		return "inventario(" + ",idinventario=" + C1idinventario + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_inicio=" + C4fecha_inicio + " ,fecha_fin=" + C5fecha_fin + " ,descripcion=" + C6descripcion + " ,es_abierto=" + C7es_abierto + " ,es_cerrado=" + C8es_cerrado + " ,total_precio_venta=" + C9total_precio_venta + " ,total_precio_compra=" + C10total_precio_compra + " ,fk_idusuario=" + C11fk_idusuario + " )";
	}
}
