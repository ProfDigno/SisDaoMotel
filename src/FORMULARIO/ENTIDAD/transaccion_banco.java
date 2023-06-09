	package FORMULARIO.ENTIDAD;
public class transaccion_banco {

//---------------DECLARAR VARIABLES---------------
private int C1idtransaccion_banco;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_transaccion;
private String C5nro_transaccion;
private double C6monto_guarani;
private double C7monto_dolar;
private String C8observacion;
private String C9concepto;
private String C10estado;
private int C11fk_iddato_banco;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public transaccion_banco() {
		setTb_transaccion_banco("transaccion_banco");
		setId_idtransaccion_banco("idtransaccion_banco");
	}
	public static String getTb_transaccion_banco(){
		return nom_tabla;
	}
	public static void setTb_transaccion_banco(String nom_tabla){
		transaccion_banco.nom_tabla = nom_tabla;
	}
	public static String getId_idtransaccion_banco(){
		return nom_idtabla;
	}
	public static void setId_idtransaccion_banco(String nom_idtabla){
		transaccion_banco.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idtransaccion_banco(){
		return C1idtransaccion_banco;
	}
	public void setC1idtransaccion_banco(int C1idtransaccion_banco){
		this.C1idtransaccion_banco = C1idtransaccion_banco;
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
	public String getC4fecha_transaccion(){
		return C4fecha_transaccion;
	}
	public void setC4fecha_transaccion(String C4fecha_transaccion){
		this.C4fecha_transaccion = C4fecha_transaccion;
	}
	public String getC5nro_transaccion(){
		return C5nro_transaccion;
	}
	public void setC5nro_transaccion(String C5nro_transaccion){
		this.C5nro_transaccion = C5nro_transaccion;
	}
	public double getC6monto_guarani(){
		return C6monto_guarani;
	}
	public void setC6monto_guarani(double C6monto_guarani){
		this.C6monto_guarani = C6monto_guarani;
	}
	public double getC7monto_dolar(){
		return C7monto_dolar;
	}
	public void setC7monto_dolar(double C7monto_dolar){
		this.C7monto_dolar = C7monto_dolar;
	}
	public String getC8observacion(){
		return C8observacion;
	}
	public void setC8observacion(String C8observacion){
		this.C8observacion = C8observacion;
	}
	public String getC9concepto(){
		return C9concepto;
	}
	public void setC9concepto(String C9concepto){
		this.C9concepto = C9concepto;
	}
	public String getC10estado(){
		return C10estado;
	}
	public void setC10estado(String C10estado){
		this.C10estado = C10estado;
	}
	public int getC11fk_iddato_banco(){
		return C11fk_iddato_banco;
	}
	public void setC11fk_iddato_banco(int C11fk_iddato_banco){
		this.C11fk_iddato_banco = C11fk_iddato_banco;
	}
	public String toString() {
		return "transaccion_banco(" + ",idtransaccion_banco=" + C1idtransaccion_banco + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_transaccion=" + C4fecha_transaccion + " ,nro_transaccion=" + C5nro_transaccion + " ,monto_guarani=" + C6monto_guarani + " ,monto_dolar=" + C7monto_dolar + " ,observacion=" + C8observacion + " ,concepto=" + C9concepto + " ,estado=" + C10estado + " ,fk_iddato_banco=" + C11fk_iddato_banco + " )";
	}
}
