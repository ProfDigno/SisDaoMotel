	package FORMULARIO.ENTIDAD;
public class timbrado {

//---------------DECLARAR VARIABLES---------------
private int C1idtimbrado;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private int C5numero_timbrado;
private String C6ruc;
private String C7fec_fin_vigente;
private String C8cod_establecimiento;
private String C9punto_expedicion;
private int C10numero_inicio;
private int C11numero_fin;
private int C12numero_actual;
private boolean C13activo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public timbrado() {
		setTb_timbrado("timbrado");
		setId_idtimbrado("idtimbrado");
	}
	public static String getTb_timbrado(){
		return nom_tabla;
	}
	public static void setTb_timbrado(String nom_tabla){
		timbrado.nom_tabla = nom_tabla;
	}
	public static String getId_idtimbrado(){
		return nom_idtabla;
	}
	public static void setId_idtimbrado(String nom_idtabla){
		timbrado.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idtimbrado(){
		return C1idtimbrado;
	}
	public void setC1idtimbrado(int C1idtimbrado){
		this.C1idtimbrado = C1idtimbrado;
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
	public int getC5numero_timbrado(){
		return C5numero_timbrado;
	}
	public void setC5numero_timbrado(int C5numero_timbrado){
		this.C5numero_timbrado = C5numero_timbrado;
	}
	public String getC6ruc(){
		return C6ruc;
	}
	public void setC6ruc(String C6ruc){
		this.C6ruc = C6ruc;
	}
	public String getC7fec_fin_vigente(){
		return C7fec_fin_vigente;
	}
	public void setC7fec_fin_vigente(String C7fec_fin_vigente){
		this.C7fec_fin_vigente = C7fec_fin_vigente;
	}
	public String getC8cod_establecimiento(){
		return C8cod_establecimiento;
	}
	public void setC8cod_establecimiento(String C8cod_establecimiento){
		this.C8cod_establecimiento = C8cod_establecimiento;
	}
	public String getC9punto_expedicion(){
		return C9punto_expedicion;
	}
	public void setC9punto_expedicion(String C9punto_expedicion){
		this.C9punto_expedicion = C9punto_expedicion;
	}
	public int getC10numero_inicio(){
		return C10numero_inicio;
	}
	public void setC10numero_inicio(int C10numero_inicio){
		this.C10numero_inicio = C10numero_inicio;
	}
	public int getC11numero_fin(){
		return C11numero_fin;
	}
	public void setC11numero_fin(int C11numero_fin){
		this.C11numero_fin = C11numero_fin;
	}
	public int getC12numero_actual(){
		return C12numero_actual;
	}
	public void setC12numero_actual(int C12numero_actual){
		this.C12numero_actual = C12numero_actual;
	}
	public boolean getC13activo(){
		return C13activo;
	}
	public void setC13activo(boolean C13activo){
		this.C13activo = C13activo;
	}
	public String toString() {
		return "timbrado(" + ",idtimbrado=" + C1idtimbrado + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,numero_timbrado=" + C5numero_timbrado + " ,ruc=" + C6ruc + " ,fec_fin_vigente=" + C7fec_fin_vigente + " ,cod_establecimiento=" + C8cod_establecimiento + " ,punto_expedicion=" + C9punto_expedicion + " ,numero_inicio=" + C10numero_inicio + " ,numero_fin=" + C11numero_fin + " ,numero_actual=" + C12numero_actual + " ,activo=" + C13activo + " )";
	}
}
