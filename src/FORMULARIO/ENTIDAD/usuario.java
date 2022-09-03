	package FORMULARIO.ENTIDAD;
public class usuario {

//---------------DECLARAR VARIABLES---------------
private int C1idusuario;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private String C5usuario;
private String C6password;
private boolean C7activo;
private int C8fk_idusuario_rol;
private int C9fk_idpersona;
private static String nom_tabla;
private static String nom_idtabla;
private static int global_idusuario;
private static String global_nombre;
//---------------TABLA-ID---------------
	public usuario() {
		setTb_usuario("usuario");
		setId_idusuario("idusuario");
	}
	public static String getTb_usuario(){
		return nom_tabla;
	}
	public static void setTb_usuario(String nom_tabla){
		usuario.nom_tabla = nom_tabla;
	}
	public static String getId_idusuario(){
		return nom_idtabla;
	}
	public static void setId_idusuario(String nom_idtabla){
		usuario.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idusuario(){
		return C1idusuario;
	}
	public void setC1idusuario(int C1idusuario){
		this.C1idusuario = C1idusuario;
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
	public String getC5usuario(){
		return C5usuario;
	}
	public void setC5usuario(String C5usuario){
		this.C5usuario = C5usuario;
	}
	public String getC6password(){
		return C6password;
	}
	public void setC6password(String C6password){
		this.C6password = C6password;
	}
	public boolean getC7activo(){
		return C7activo;
	}
	public void setC7activo(boolean C7activo){
		this.C7activo = C7activo;
	}
	public int getC8fk_idusuario_rol(){
		return C8fk_idusuario_rol;
	}
	public void setC8fk_idusuario_rol(int C8fk_idusuario_rol){
		this.C8fk_idusuario_rol = C8fk_idusuario_rol;
	}
	public int getC9fk_idpersona(){
		return C9fk_idpersona;
	}
	public void setC9fk_idpersona(int C9fk_idpersona){
		this.C9fk_idpersona = C9fk_idpersona;
	}

    public static int getGlobal_idusuario() {
        return global_idusuario;
    }

    public static void setGlobal_idusuario(int global_idusuario) {
        usuario.global_idusuario = global_idusuario;
    }

    public static String getGlobal_nombre() {
        return global_nombre;
    }

    public static void setGlobal_nombre(String global_nombre) {
        usuario.global_nombre = global_nombre;
    }
        
	public String toString() {
		return "usuario(" + ",idusuario=" + C1idusuario + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,usuario=" + C5usuario + " ,password=" + C6password + " ,activo=" + C7activo + " ,fk_idusuario_rol=" + C8fk_idusuario_rol + " ,fk_idpersona=" + C9fk_idpersona + " )";
	}
}
