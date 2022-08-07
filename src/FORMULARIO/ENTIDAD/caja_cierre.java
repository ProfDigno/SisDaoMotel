	package FORMULARIO.ENTIDAD;
public class caja_cierre {

//---------------DECLARAR VARIABLES---------------
private int C1idcaja_cierre;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_inicio;
private String C5fecha_fin;
private String C6estado;
private int C7fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public caja_cierre() {
		setTb_caja_cierre("caja_cierre");
		setId_idcaja_cierre("idcaja_cierre");
	}
	public static String getTb_caja_cierre(){
		return nom_tabla;
	}
	public static void setTb_caja_cierre(String nom_tabla){
		caja_cierre.nom_tabla = nom_tabla;
	}
	public static String getId_idcaja_cierre(){
		return nom_idtabla;
	}
	public static void setId_idcaja_cierre(String nom_idtabla){
		caja_cierre.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcaja_cierre(){
		return C1idcaja_cierre;
	}
	public void setC1idcaja_cierre(int C1idcaja_cierre){
		this.C1idcaja_cierre = C1idcaja_cierre;
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
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public int getC7fk_idusuario(){
		return C7fk_idusuario;
	}
	public void setC7fk_idusuario(int C7fk_idusuario){
		this.C7fk_idusuario = C7fk_idusuario;
	}
	public String toString() {
		return "caja_cierre(" + ",idcaja_cierre=" + C1idcaja_cierre + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_inicio=" + C4fecha_inicio + " ,fecha_fin=" + C5fecha_fin + " ,estado=" + C6estado + " ,fk_idusuario=" + C7fk_idusuario + " )";
	}
}
