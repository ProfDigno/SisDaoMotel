	package FORMULARIO.ENTIDAD;
public class rh_vale {

//---------------DECLARAR VARIABLES---------------
private int C1idrh_vale;
private String C2fecha_creado;
private String C3creado_por;
private String C4descripcion;
private double C5monto_vale;
private String C6estado;
private boolean C7es_cerrado;
private String C8monto_letra;
private int C9fk_idpersona;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public rh_vale() {
		setTb_rh_vale("rh_vale");
		setId_idrh_vale("idrh_vale");
	}
	public static String getTb_rh_vale(){
		return nom_tabla;
	}
	public static void setTb_rh_vale(String nom_tabla){
		rh_vale.nom_tabla = nom_tabla;
	}
	public static String getId_idrh_vale(){
		return nom_idtabla;
	}
	public static void setId_idrh_vale(String nom_idtabla){
		rh_vale.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idrh_vale(){
		return C1idrh_vale;
	}
	public void setC1idrh_vale(int C1idrh_vale){
		this.C1idrh_vale = C1idrh_vale;
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
	public String getC4descripcion(){
		return C4descripcion;
	}
	public void setC4descripcion(String C4descripcion){
		this.C4descripcion = C4descripcion;
	}
	public double getC5monto_vale(){
		return C5monto_vale;
	}
	public void setC5monto_vale(double C5monto_vale){
		this.C5monto_vale = C5monto_vale;
	}
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public boolean getC7es_cerrado(){
		return C7es_cerrado;
	}
	public void setC7es_cerrado(boolean C7es_cerrado){
		this.C7es_cerrado = C7es_cerrado;
	}
	public String getC8monto_letra(){
		return C8monto_letra;
	}
	public void setC8monto_letra(String C8monto_letra){
		this.C8monto_letra = C8monto_letra;
	}
	public int getC9fk_idpersona(){
		return C9fk_idpersona;
	}
	public void setC9fk_idpersona(int C9fk_idpersona){
		this.C9fk_idpersona = C9fk_idpersona;
	}
	public String toString() {
		return "rh_vale(" + ",idrh_vale=" + C1idrh_vale + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,descripcion=" + C4descripcion + " ,monto_vale=" + C5monto_vale + " ,estado=" + C6estado + " ,es_cerrado=" + C7es_cerrado + " ,monto_letra=" + C8monto_letra + " ,fk_idpersona=" + C9fk_idpersona + " )";
	}
}
