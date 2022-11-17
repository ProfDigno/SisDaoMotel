	package FORMULARIO.ENTIDAD;
public class garantia {

//---------------DECLARAR VARIABLES---------------
private int C1idgarantia;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_inicio;
private String C5fecha_fin;
private String C6responsable;
private String C7descripcion_objeto;
private double C8monto_garantia;
private String C9estado;
private int C10fk_idusuario;
private int C11fk_idventa;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public garantia() {
		setTb_garantia("garantia");
		setId_idgarantia("idgarantia");
	}
	public static String getTb_garantia(){
		return nom_tabla;
	}
	public static void setTb_garantia(String nom_tabla){
		garantia.nom_tabla = nom_tabla;
	}
	public static String getId_idgarantia(){
		return nom_idtabla;
	}
	public static void setId_idgarantia(String nom_idtabla){
		garantia.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idgarantia(){
		return C1idgarantia;
	}
	public void setC1idgarantia(int C1idgarantia){
		this.C1idgarantia = C1idgarantia;
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
	public String getC4fecha_inicio(){
		return C4fecha_inicio;
	}
	public void setC4fecha_inicio(String C4fecha_inicio){
		this.C4fecha_inicio = C4fecha_inicio;
	}
	public String getC5fecha_fin(){
		return C5fecha_fin;
	}
	public void setC5fecha_fin(String C5fecha_fin){
		this.C5fecha_fin = C5fecha_fin;
	}
	public String getC6responsable(){
		return C6responsable;
	}
	public void setC6responsable(String C6responsable){
		this.C6responsable = C6responsable;
	}
	public String getC7descripcion_objeto(){
		return C7descripcion_objeto;
	}
	public void setC7descripcion_objeto(String C7descripcion_objeto){
		this.C7descripcion_objeto = C7descripcion_objeto;
	}
	public double getC8monto_garantia(){
		return C8monto_garantia;
	}
	public void setC8monto_garantia(double C8monto_garantia){
		this.C8monto_garantia = C8monto_garantia;
	}
	public String getC9estado(){
		return C9estado;
	}
	public void setC9estado(String C9estado){
		this.C9estado = C9estado;
	}
	public int getC10fk_idusuario(){
		return C10fk_idusuario;
	}
	public void setC10fk_idusuario(int C10fk_idusuario){
		this.C10fk_idusuario = C10fk_idusuario;
	}
	public int getC11fk_idventa(){
		return C11fk_idventa;
	}
	public void setC11fk_idventa(int C11fk_idventa){
		this.C11fk_idventa = C11fk_idventa;
	}
	public String toString() {
		return "garantia(" + ",idgarantia=" + C1idgarantia + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_inicio=" + C4fecha_inicio + " ,fecha_fin=" + C5fecha_fin + " ,responsable=" + C6responsable + " ,descripcion_objeto=" + C7descripcion_objeto + " ,monto_garantia=" + C8monto_garantia + " ,estado=" + C9estado + " ,fk_idusuario=" + C10fk_idusuario + " ,fk_idventa=" + C11fk_idventa + " )";
	}
}
