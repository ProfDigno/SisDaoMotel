	package FORMULARIO.ENTIDAD;
public class usuario_evento {

//---------------DECLARAR VARIABLES---------------
private int C1idusuario_evento;
private String C2fecha_creado;
private String C3creado_por;
private int C4codigo;
private String C5nombre;
private String C6descripcion;
private int C7fk_idusuario_tipo_evento;
private int C8fk_idusuario_formulario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public usuario_evento() {
		setTb_usuario_evento("usuario_evento");
		setId_idusuario_evento("idusuario_evento");
	}
	public static String getTb_usuario_evento(){
		return nom_tabla;
	}
	public static void setTb_usuario_evento(String nom_tabla){
		usuario_evento.nom_tabla = nom_tabla;
	}
	public static String getId_idusuario_evento(){
		return nom_idtabla;
	}
	public static void setId_idusuario_evento(String nom_idtabla){
		usuario_evento.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idusuario_evento(){
		return C1idusuario_evento;
	}
	public void setC1idusuario_evento(int C1idusuario_evento){
		this.C1idusuario_evento = C1idusuario_evento;
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
	public int getC4codigo(){
		return C4codigo;
	}
	public void setC4codigo(int C4codigo){
		this.C4codigo = C4codigo;
	}
	public String getC5nombre(){
		return C5nombre;
	}
	public void setC5nombre(String C5nombre){
		this.C5nombre = C5nombre;
	}
	public String getC6descripcion(){
		return C6descripcion;
	}
	public void setC6descripcion(String C6descripcion){
		this.C6descripcion = C6descripcion;
	}
	public int getC7fk_idusuario_tipo_evento(){
		return C7fk_idusuario_tipo_evento;
	}
	public void setC7fk_idusuario_tipo_evento(int C7fk_idusuario_tipo_evento){
		this.C7fk_idusuario_tipo_evento = C7fk_idusuario_tipo_evento;
	}
	public int getC8fk_idusuario_formulario(){
		return C8fk_idusuario_formulario;
	}
	public void setC8fk_idusuario_formulario(int C8fk_idusuario_formulario){
		this.C8fk_idusuario_formulario = C8fk_idusuario_formulario;
	}
	public String toString() {
		return "usuario_evento(" + ",idusuario_evento=" + C1idusuario_evento + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,codigo=" + C4codigo + " ,nombre=" + C5nombre + " ,descripcion=" + C6descripcion + " ,fk_idusuario_tipo_evento=" + C7fk_idusuario_tipo_evento + " ,fk_idusuario_formulario=" + C8fk_idusuario_formulario + " )";
	}
}
