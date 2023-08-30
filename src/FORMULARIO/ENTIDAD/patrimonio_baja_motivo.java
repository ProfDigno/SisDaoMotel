	package FORMULARIO.ENTIDAD;
public class patrimonio_baja_motivo {

//---------------DECLARAR VARIABLES---------------
private int C1idpatrimonio_baja_motivo;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private boolean C5activo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public patrimonio_baja_motivo() {
		setTb_patrimonio_baja_motivo("patrimonio_baja_motivo");
		setId_idpatrimonio_baja_motivo("idpatrimonio_baja_motivo");
	}
	public static String getTb_patrimonio_baja_motivo(){
		return nom_tabla;
	}
	public static void setTb_patrimonio_baja_motivo(String nom_tabla){
		patrimonio_baja_motivo.nom_tabla = nom_tabla;
	}
	public static String getId_idpatrimonio_baja_motivo(){
		return nom_idtabla;
	}
	public static void setId_idpatrimonio_baja_motivo(String nom_idtabla){
		patrimonio_baja_motivo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idpatrimonio_baja_motivo(){
		return C1idpatrimonio_baja_motivo;
	}
	public void setC1idpatrimonio_baja_motivo(int C1idpatrimonio_baja_motivo){
		this.C1idpatrimonio_baja_motivo = C1idpatrimonio_baja_motivo;
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
		return "patrimonio_baja_motivo(" + ",idpatrimonio_baja_motivo=" + C1idpatrimonio_baja_motivo + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,activo=" + C5activo + " )";
	}
}
