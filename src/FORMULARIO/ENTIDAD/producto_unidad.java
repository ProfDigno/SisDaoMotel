	package FORMULARIO.ENTIDAD;
public class producto_unidad {

//---------------DECLARAR VARIABLES---------------
private int C1idproducto_unidad;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private int C5orden;
private boolean C6activo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public producto_unidad() {
		setTb_producto_unidad("producto_unidad");
		setId_idproducto_unidad("idproducto_unidad");
	}
	public static String getTb_producto_unidad(){
		return nom_tabla;
	}
	public static void setTb_producto_unidad(String nom_tabla){
		producto_unidad.nom_tabla = nom_tabla;
	}
	public static String getId_idproducto_unidad(){
		return nom_idtabla;
	}
	public static void setId_idproducto_unidad(String nom_idtabla){
		producto_unidad.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idproducto_unidad(){
		return C1idproducto_unidad;
	}
	public void setC1idproducto_unidad(int C1idproducto_unidad){
		this.C1idproducto_unidad = C1idproducto_unidad;
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
		return "producto_unidad(" + ",idproducto_unidad=" + C1idproducto_unidad + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,orden=" + C5orden + " ,activo=" + C6activo + " )";
	}
}
