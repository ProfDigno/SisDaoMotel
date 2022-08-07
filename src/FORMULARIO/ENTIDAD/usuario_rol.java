	package FORMULARIO.ENTIDAD;
public class usuario_rol {

//---------------DECLARAR VARIABLES---------------
private int C1idusuario_rol;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private String C5descripcion;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public usuario_rol() {
		setTb_usuario_rol("usuario_rol");
		setId_idusuario_rol("idusuario_rol");
	}
	public static String getTb_usuario_rol(){
		return nom_tabla;
	}
	public static void setTb_usuario_rol(String nom_tabla){
		usuario_rol.nom_tabla = nom_tabla;
	}
	public static String getId_idusuario_rol(){
		return nom_idtabla;
	}
	public static void setId_idusuario_rol(String nom_idtabla){
		usuario_rol.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idusuario_rol(){
		return C1idusuario_rol;
	}
	public void setC1idusuario_rol(int C1idusuario_rol){
		this.C1idusuario_rol = C1idusuario_rol;
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
	public String getC5descripcion(){
		return C5descripcion;
	}
	public void setC5descripcion(String C5descripcion){
		this.C5descripcion = C5descripcion;
	}
	public String toString() {
		return "usuario_rol(" + ",idusuario_rol=" + C1idusuario_rol + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,descripcion=" + C5descripcion + " )";
	}
}
