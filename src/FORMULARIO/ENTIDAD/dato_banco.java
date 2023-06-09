	package FORMULARIO.ENTIDAD;
public class dato_banco {

//---------------DECLARAR VARIABLES---------------
private int C1iddato_banco;
private String C2fecha_creado;
private String C3creado_por;
private String C4titular;
private String C5documento;
private String C6nro_cuenta;
private boolean C7activo;
private boolean C8es_guarani;
private boolean C9es_dolar;
private int C10fk_idbanco;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public dato_banco() {
		setTb_dato_banco("dato_banco");
		setId_iddato_banco("iddato_banco");
	}
	public static String getTb_dato_banco(){
		return nom_tabla;
	}
	public static void setTb_dato_banco(String nom_tabla){
		dato_banco.nom_tabla = nom_tabla;
	}
	public static String getId_iddato_banco(){
		return nom_idtabla;
	}
	public static void setId_iddato_banco(String nom_idtabla){
		dato_banco.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1iddato_banco(){
		return C1iddato_banco;
	}
	public void setC1iddato_banco(int C1iddato_banco){
		this.C1iddato_banco = C1iddato_banco;
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
	public String getC4titular(){
		return C4titular;
	}
	public void setC4titular(String C4titular){
		this.C4titular = C4titular;
	}
	public String getC5documento(){
		return C5documento;
	}
	public void setC5documento(String C5documento){
		this.C5documento = C5documento;
	}
	public String getC6nro_cuenta(){
		return C6nro_cuenta;
	}
	public void setC6nro_cuenta(String C6nro_cuenta){
		this.C6nro_cuenta = C6nro_cuenta;
	}
	public boolean getC7activo(){
		return C7activo;
	}
	public void setC7activo(boolean C7activo){
		this.C7activo = C7activo;
	}
	public boolean getC8es_guarani(){
		return C8es_guarani;
	}
	public void setC8es_guarani(boolean C8es_guarani){
		this.C8es_guarani = C8es_guarani;
	}
	public boolean getC9es_dolar(){
		return C9es_dolar;
	}
	public void setC9es_dolar(boolean C9es_dolar){
		this.C9es_dolar = C9es_dolar;
	}
	public int getC10fk_idbanco(){
		return C10fk_idbanco;
	}
	public void setC10fk_idbanco(int C10fk_idbanco){
		this.C10fk_idbanco = C10fk_idbanco;
	}
	public String toString() {
		return "dato_banco(" + ",iddato_banco=" + C1iddato_banco + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,titular=" + C4titular + " ,documento=" + C5documento + " ,nro_cuenta=" + C6nro_cuenta + " ,activo=" + C7activo + " ,es_guarani=" + C8es_guarani + " ,es_dolar=" + C9es_dolar + " ,fk_idbanco=" + C10fk_idbanco + " )";
	}
}
