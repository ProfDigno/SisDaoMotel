	package FORMULARIO.ENTIDAD;
public class producto_marca {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_marca;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private int C5orden;
private boolean C6activo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto_marca() {
		setTb_producto_marca("producto_marca");
		setId_idproducto_marca("idproducto_marca");
	}
	public static String getTb_producto_marca(){
		return nom_tabla;
	}
	public static void setTb_producto_marca(String nom_tabla){
		producto_marca.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_marca(){
		return nom_idtabla;
	}
	public static void setId_idproducto_marca(String nom_idtabla){
		producto_marca.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_marca(){
		return C1idproducto_marca;
	}
	public void setC1idproducto_marca(int C1idproducto_marca){
		this.C1idproducto_marca = C1idproducto_marca;
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
	public int getC5orden(){
		return C5orden;
	}
	public void setC5orden(int C5orden){
		this.C5orden = C5orden;
	}
	public boolean getC6activo(){
		return C6activo;
	}
	public void setC6activo(boolean C6activo){
		this.C6activo = C6activo;
	}
	public String toString() {
		return "producto_marca(" + ",idproducto_marca=" + C1idproducto_marca + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,orden=" + C5orden + " ,activo=" + C6activo + " )";
	}
}
