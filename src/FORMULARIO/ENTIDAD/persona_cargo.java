	package FORMULARIO.ENTIDAD;
public class persona_cargo {

//---------------DECLARAR VARIABLES---------------
private int C1idpersona_cargo;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private boolean C5activo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public persona_cargo() {
		setTb_persona_cargo("persona_cargo");
		setId_idpersona_cargo("idpersona_cargo");
	}
	public static String getTb_persona_cargo(){
		return nom_tabla;
	}
	public static void setTb_persona_cargo(String nom_tabla){
		persona_cargo.nom_tabla = nom_tabla;
	}
	public static String getId_idpersona_cargo(){
		return nom_idtabla;
	}
	public static void setId_idpersona_cargo(String nom_idtabla){
		persona_cargo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idpersona_cargo(){
		return C1idpersona_cargo;
	}
	public void setC1idpersona_cargo(int C1idpersona_cargo){
		this.C1idpersona_cargo = C1idpersona_cargo;
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
	public boolean getC5activo(){
		return C5activo;
	}
	public void setC5activo(boolean C5activo){
		this.C5activo = C5activo;
	}
	public String toString() {
		return "persona_cargo(" + ",idpersona_cargo=" + C1idpersona_cargo + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,activo=" + C5activo + " )";
	}
}
