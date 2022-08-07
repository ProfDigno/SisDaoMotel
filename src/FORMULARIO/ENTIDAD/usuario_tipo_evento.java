	package FORMULARIO.ENTIDAD;
public class usuario_tipo_evento {

//---------------DECLARAR VARIABLES---------------
private int C1idusuario_tipo_evento;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public usuario_tipo_evento() {
		setTb_usuario_tipo_evento("usuario_tipo_evento");
		setId_idusuario_tipo_evento("idusuario_tipo_evento");
	}
	public static String getTb_usuario_tipo_evento(){
		return nom_tabla;
	}
	public static void setTb_usuario_tipo_evento(String nom_tabla){
		usuario_tipo_evento.nom_tabla = nom_tabla;
	}
	public static String getId_idusuario_tipo_evento(){
		return nom_idtabla;
	}
	public static void setId_idusuario_tipo_evento(String nom_idtabla){
		usuario_tipo_evento.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idusuario_tipo_evento(){
		return C1idusuario_tipo_evento;
	}
	public void setC1idusuario_tipo_evento(int C1idusuario_tipo_evento){
		this.C1idusuario_tipo_evento = C1idusuario_tipo_evento;
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
		return "usuario_tipo_evento(" + ",idusuario_tipo_evento=" + C1idusuario_tipo_evento + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " )";
	}
}
