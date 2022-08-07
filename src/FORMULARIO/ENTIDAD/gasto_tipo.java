	package FORMULARIO.ENTIDAD;
public class gasto_tipo {

//---------------DECLARAR VARIABLES---------------
private int C1idgasto_tipo;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private boolean C5activo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public gasto_tipo() {
		setTb_gasto_tipo("gasto_tipo");
		setId_idgasto_tipo("idgasto_tipo");
	}
	public static String getTb_gasto_tipo(){
		return nom_tabla;
	}
	public static void setTb_gasto_tipo(String nom_tabla){
		gasto_tipo.nom_tabla = nom_tabla;
	}
	public static String getId_idgasto_tipo(){
		return nom_idtabla;
	}
	public static void setId_idgasto_tipo(String nom_idtabla){
		gasto_tipo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idgasto_tipo(){
		return C1idgasto_tipo;
	}
	public void setC1idgasto_tipo(int C1idgasto_tipo){
		this.C1idgasto_tipo = C1idgasto_tipo;
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
		return "gasto_tipo(" + ",idgasto_tipo=" + C1idgasto_tipo + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,activo=" + C5activo + " )";
	}
}
