	package FORMULARIO.ENTIDAD;
public class caja_cierre_item {

//---------------DECLARAR VARIABLES---------------
private int C1idcaja_cierre_item;
private String C2fecha_creado;
private String C3creado_por;
private int C4fk_idcaja_cierre;
private int C5fk_idcaja_cierre_detalle;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public caja_cierre_item() {
		setTb_caja_cierre_item("caja_cierre_item");
		setId_idcaja_cierre_item("idcaja_cierre_item");
	}
	public static String getTb_caja_cierre_item(){
		return nom_tabla;
	}
	public static void setTb_caja_cierre_item(String nom_tabla){
		caja_cierre_item.nom_tabla = nom_tabla;
	}
	public static String getId_idcaja_cierre_item(){
		return nom_idtabla;
	}
	public static void setId_idcaja_cierre_item(String nom_idtabla){
		caja_cierre_item.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcaja_cierre_item(){
		return C1idcaja_cierre_item;
	}
	public void setC1idcaja_cierre_item(int C1idcaja_cierre_item){
		this.C1idcaja_cierre_item = C1idcaja_cierre_item;
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
	public int getC4fk_idcaja_cierre(){
		return C4fk_idcaja_cierre;
	}
	public void setC4fk_idcaja_cierre(int C4fk_idcaja_cierre){
		this.C4fk_idcaja_cierre = C4fk_idcaja_cierre;
	}
	public int getC5fk_idcaja_cierre_detalle(){
		return C5fk_idcaja_cierre_detalle;
	}
	public void setC5fk_idcaja_cierre_detalle(int C5fk_idcaja_cierre_detalle){
		this.C5fk_idcaja_cierre_detalle = C5fk_idcaja_cierre_detalle;
	}
	public String toString() {
		return "caja_cierre_item(" + ",idcaja_cierre_item=" + C1idcaja_cierre_item + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fk_idcaja_cierre=" + C4fk_idcaja_cierre + " ,fk_idcaja_cierre_detalle=" + C5fk_idcaja_cierre_detalle + " )";
	}
}
