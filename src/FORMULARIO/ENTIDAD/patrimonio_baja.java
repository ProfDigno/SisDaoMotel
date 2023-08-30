	package FORMULARIO.ENTIDAD;
public class patrimonio_baja {

//---------------DECLARAR VARIABLES---------------
private int C1idpatrimonio_baja;
private String C2fecha_creado;
private String C3creado_por;
private String C4estado;
private String C5observacion;
private double C6monto;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public patrimonio_baja() {
		setTb_patrimonio_baja("patrimonio_baja");
		setId_idpatrimonio_baja("idpatrimonio_baja");
	}
	public static String getTb_patrimonio_baja(){
		return nom_tabla;
	}
	public static void setTb_patrimonio_baja(String nom_tabla){
		patrimonio_baja.nom_tabla = nom_tabla;
	}
	public static String getId_idpatrimonio_baja(){
		return nom_idtabla;
	}
	public static void setId_idpatrimonio_baja(String nom_idtabla){
		patrimonio_baja.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idpatrimonio_baja(){
		return C1idpatrimonio_baja;
	}
	public void setC1idpatrimonio_baja(int C1idpatrimonio_baja){
		this.C1idpatrimonio_baja = C1idpatrimonio_baja;
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
	public String getC4estado(){
		return C4estado;
	}
	public void setC4estado(String C4estado){
		this.C4estado = C4estado;
	}
	public String getC5observacion(){
		return C5observacion;
	}
	public void setC5observacion(String C5observacion){
		this.C5observacion = C5observacion;
	}
	public double getC6monto(){
		return C6monto;
	}
	public void setC6monto(double C6monto){
		this.C6monto = C6monto;
	}
	public String toString() {
		return "patrimonio_baja(" + ",idpatrimonio_baja=" + C1idpatrimonio_baja + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,estado=" + C4estado + " ,observacion=" + C5observacion + " ,monto=" + C6monto + " )";
	}
}
