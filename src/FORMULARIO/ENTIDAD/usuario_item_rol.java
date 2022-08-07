	package FORMULARIO.ENTIDAD;
public class usuario_item_rol {

//---------------DECLARAR VARIABLES---------------
private int C1idusuario_item_rol;
private String C2fecha_creado;
private String C3creado_por;
private boolean C4activo;
private int C5fk_idusuario_rol;
private int C6fk_idusuario_evento;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public usuario_item_rol() {
		setTb_usuario_item_rol("usuario_item_rol");
		setId_idusuario_item_rol("idusuario_item_rol");
	}
	public static String getTb_usuario_item_rol(){
		return nom_tabla;
	}
	public static void setTb_usuario_item_rol(String nom_tabla){
		usuario_item_rol.nom_tabla = nom_tabla;
	}
	public static String getId_idusuario_item_rol(){
		return nom_idtabla;
	}
	public static void setId_idusuario_item_rol(String nom_idtabla){
		usuario_item_rol.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idusuario_item_rol(){
		return C1idusuario_item_rol;
	}
	public void setC1idusuario_item_rol(int C1idusuario_item_rol){
		this.C1idusuario_item_rol = C1idusuario_item_rol;
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
	public boolean getC4activo(){
		return C4activo;
	}
	public void setC4activo(boolean C4activo){
		this.C4activo = C4activo;
	}
	public int getC5fk_idusuario_rol(){
		return C5fk_idusuario_rol;
	}
	public void setC5fk_idusuario_rol(int C5fk_idusuario_rol){
		this.C5fk_idusuario_rol = C5fk_idusuario_rol;
	}
	public int getC6fk_idusuario_evento(){
		return C6fk_idusuario_evento;
	}
	public void setC6fk_idusuario_evento(int C6fk_idusuario_evento){
		this.C6fk_idusuario_evento = C6fk_idusuario_evento;
	}
	public String toString() {
		return "usuario_item_rol(" + ",idusuario_item_rol=" + C1idusuario_item_rol + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,activo=" + C4activo + " ,fk_idusuario_rol=" + C5fk_idusuario_rol + " ,fk_idusuario_evento=" + C6fk_idusuario_evento + " )";
	}
}
