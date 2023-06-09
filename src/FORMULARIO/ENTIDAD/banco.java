	package FORMULARIO.ENTIDAD;
public class banco {

//---------------DECLARAR VARIABLES---------------
private int C1idbanco;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public banco() {
		setTb_banco("banco");
		setId_idbanco("idbanco");
	}
	public static String getTb_banco(){
		return nom_tabla;
	}
	public static void setTb_banco(String nom_tabla){
		banco.nom_tabla = nom_tabla;
	}
	public static String getId_idbanco(){
		return nom_idtabla;
	}
	public static void setId_idbanco(String nom_idtabla){
		banco.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idbanco(){
		return C1idbanco;
	}
	public void setC1idbanco(int C1idbanco){
		this.C1idbanco = C1idbanco;
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
	public String toString() {
		return "banco(" + ",idbanco=" + C1idbanco + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " )";
	}
}
