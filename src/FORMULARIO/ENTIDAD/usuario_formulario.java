	package FORMULARIO.ENTIDAD;
public class usuario_formulario {

//---------------DECLARAR VARIABLES---------------
private int C1idusuario_formulario;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public usuario_formulario() {
		setTb_usuario_formulario("usuario_formulario");
		setId_idusuario_formulario("idusuario_formulario");
	}
	public static String getTb_usuario_formulario(){
		return nom_tabla;
	}
	public static void setTb_usuario_formulario(String nom_tabla){
		usuario_formulario.nom_tabla = nom_tabla;
	}
	public static String getId_idusuario_formulario(){
		return nom_idtabla;
	}
	public static void setId_idusuario_formulario(String nom_idtabla){
		usuario_formulario.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idusuario_formulario(){
		return C1idusuario_formulario;
	}
	public void setC1idusuario_formulario(int C1idusuario_formulario){
		this.C1idusuario_formulario = C1idusuario_formulario;
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
	public String toString() {
		return "usuario_formulario(" + ",idusuario_formulario=" + C1idusuario_formulario + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " )";
	}
}
