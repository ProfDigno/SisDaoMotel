	package FORMULARIO.ENTIDAD;
public class producto_habitacion_patrimonio {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_habitacion_patrimonio;
private String C2fecha_creado;
private String C3creado_por;
private double C4cantidad;
private String C5descripcion;
private double C6precio_compra;
private boolean C7activo;
private String C8fecha_ingreso;
private String C9fecha_salida;
private String C10motivo_salida;
private int C11fk_idproducto;
private int C12fk_idhabitacion_dato;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto_habitacion_patrimonio() {
		setTb_producto_habitacion_patrimonio("producto_habitacion_patrimonio");
		setId_idproducto_habitacion_patrimonio("idproducto_habitacion_patrimonio");
	}
	public static String getTb_producto_habitacion_patrimonio(){
		return nom_tabla;
	}
	public static void setTb_producto_habitacion_patrimonio(String nom_tabla){
		producto_habitacion_patrimonio.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_habitacion_patrimonio(){
		return nom_idtabla;
	}
	public static void setId_idproducto_habitacion_patrimonio(String nom_idtabla){
		producto_habitacion_patrimonio.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_habitacion_patrimonio(){
		return C1idproducto_habitacion_patrimonio;
	}
	public void setC1idproducto_habitacion_patrimonio(int C1idproducto_habitacion_patrimonio){
		this.C1idproducto_habitacion_patrimonio = C1idproducto_habitacion_patrimonio;
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
	public double getC6precio_compra(){
		return C6precio_compra;
	}
	public void setC6precio_compra(double C6precio_compra){
		this.C6precio_compra = C6precio_compra;
	}
	public boolean getC7activo(){
		return C7activo;
	}
	public void setC7activo(boolean C7activo){
		this.C7activo = C7activo;
	}
	public String getC8fecha_ingreso(){
		return C8fecha_ingreso;
	}
	public void setC8fecha_ingreso(String C8fecha_ingreso){
		this.C8fecha_ingreso = C8fecha_ingreso;
	}
	public String getC9fecha_salida(){
		return C9fecha_salida;
	}
	public void setC9fecha_salida(String C9fecha_salida){
		this.C9fecha_salida = C9fecha_salida;
	}
	public String getC10motivo_salida(){
		return C10motivo_salida;
	}
	public void setC10motivo_salida(String C10motivo_salida){
		this.C10motivo_salida = C10motivo_salida;
	}
	public int getC11fk_idproducto(){
		return C11fk_idproducto;
	}
	public void setC11fk_idproducto(int C11fk_idproducto){
		this.C11fk_idproducto = C11fk_idproducto;
	}
	public int getC12fk_idhabitacion_dato(){
		return C12fk_idhabitacion_dato;
	}
	public void setC12fk_idhabitacion_dato(int C12fk_idhabitacion_dato){
		this.C12fk_idhabitacion_dato = C12fk_idhabitacion_dato;
	}
	public String toString() {
		return "producto_habitacion_patrimonio(" + ",idproducto_habitacion_patrimonio=" + C1idproducto_habitacion_patrimonio + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,cantidad=" + C4cantidad + " ,descripcion=" + C5descripcion + " ,precio_compra=" + C6precio_compra + " ,activo=" + C7activo + " ,fecha_ingreso=" + C8fecha_ingreso + " ,fecha_salida=" + C9fecha_salida + " ,motivo_salida=" + C10motivo_salida + " ,fk_idproducto=" + C11fk_idproducto + " ,fk_idhabitacion_dato=" + C12fk_idhabitacion_dato + " )";
	}
}
