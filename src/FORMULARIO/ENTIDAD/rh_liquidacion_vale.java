	package FORMULARIO.ENTIDAD;
public class rh_liquidacion_vale {

//---------------DECLARAR VARIABLES---------------
private int C1idrh_liquidacion_vale;
private String C2fecha_creado;
private String C3creado_por;
private int C4fk_idrh_liquidacion;
private int C5fk_idrh_vale;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public rh_liquidacion_vale() {
		setTb_rh_liquidacion_vale("rh_liquidacion_vale");
		setId_idrh_liquidacion_vale("idrh_liquidacion_vale");
	}
	public static String getTb_rh_liquidacion_vale(){
		return nom_tabla;
	}
	public static void setTb_rh_liquidacion_vale(String nom_tabla){
		rh_liquidacion_vale.nom_tabla = nom_tabla;
	}
	public static String getId_idrh_liquidacion_vale(){
		return nom_idtabla;
	}
	public static void setId_idrh_liquidacion_vale(String nom_idtabla){
		rh_liquidacion_vale.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idrh_liquidacion_vale(){
		return C1idrh_liquidacion_vale;
	}
	public void setC1idrh_liquidacion_vale(int C1idrh_liquidacion_vale){
		this.C1idrh_liquidacion_vale = C1idrh_liquidacion_vale;
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
	public int getC4fk_idrh_liquidacion(){
		return C4fk_idrh_liquidacion;
	}
	public void setC4fk_idrh_liquidacion(int C4fk_idrh_liquidacion){
		this.C4fk_idrh_liquidacion = C4fk_idrh_liquidacion;
	}
	public int getC5fk_idrh_vale(){
		return C5fk_idrh_vale;
	}
	public void setC5fk_idrh_vale(int C5fk_idrh_vale){
		this.C5fk_idrh_vale = C5fk_idrh_vale;
	}
	public String toString() {
		return "rh_liquidacion_vale(" + ",idrh_liquidacion_vale=" + C1idrh_liquidacion_vale + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fk_idrh_liquidacion=" + C4fk_idrh_liquidacion + " ,fk_idrh_vale=" + C5fk_idrh_vale + " )";
	}
}
