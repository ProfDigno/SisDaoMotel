	package FORMULARIO.ENTIDAD;
public class rh_liquidacion_entrada {

//---------------DECLARAR VARIABLES---------------
private int C1idrh_liquidacion_entrada;
private String C2fecha_creado;
private String C3creado_por;
private int C4fk_idrh_liquidacion;
private int C5fk_idrh_entrada;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public rh_liquidacion_entrada() {
		setTb_rh_liquidacion_entrada("rh_liquidacion_entrada");
		setId_idrh_liquidacion_entrada("idrh_liquidacion_entrada");
	}
	public static String getTb_rh_liquidacion_entrada(){
		return nom_tabla;
	}
	public static void setTb_rh_liquidacion_entrada(String nom_tabla){
		rh_liquidacion_entrada.nom_tabla = nom_tabla;
	}
	public static String getId_idrh_liquidacion_entrada(){
		return nom_idtabla;
	}
	public static void setId_idrh_liquidacion_entrada(String nom_idtabla){
		rh_liquidacion_entrada.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idrh_liquidacion_entrada(){
		return C1idrh_liquidacion_entrada;
	}
	public void setC1idrh_liquidacion_entrada(int C1idrh_liquidacion_entrada){
		this.C1idrh_liquidacion_entrada = C1idrh_liquidacion_entrada;
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
	public int getC5fk_idrh_entrada(){
		return C5fk_idrh_entrada;
	}
	public void setC5fk_idrh_entrada(int C5fk_idrh_entrada){
		this.C5fk_idrh_entrada = C5fk_idrh_entrada;
	}
	public String toString() {
		return "rh_liquidacion_entrada(" + ",idrh_liquidacion_entrada=" + C1idrh_liquidacion_entrada + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fk_idrh_liquidacion=" + C4fk_idrh_liquidacion + " ,fk_idrh_entrada=" + C5fk_idrh_entrada + " )";
	}
}
