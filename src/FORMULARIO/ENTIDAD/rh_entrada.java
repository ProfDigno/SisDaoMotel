	package FORMULARIO.ENTIDAD;
public class rh_entrada {

//---------------DECLARAR VARIABLES---------------
private int C1idrh_entrada;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_entrada;
private String C5fecha_salida;
private String C6turno;
private boolean C7es_cerrado;
private boolean C8es_entrada;
private boolean C9es_salida;
private int C10fk_idpersona;
private int C11fk_idusuario;
private int C12fk_idrh_turno;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public rh_entrada() {
		setTb_rh_entrada("rh_entrada");
		setId_idrh_entrada("idrh_entrada");
	}
	public static String getTb_rh_entrada(){
		return nom_tabla;
	}
	public static void setTb_rh_entrada(String nom_tabla){
		rh_entrada.nom_tabla = nom_tabla;
	}
	public static String getId_idrh_entrada(){
		return nom_idtabla;
	}
	public static void setId_idrh_entrada(String nom_idtabla){
		rh_entrada.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idrh_entrada(){
		return C1idrh_entrada;
	}
	public void setC1idrh_entrada(int C1idrh_entrada){
		this.C1idrh_entrada = C1idrh_entrada;
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
	public String getC4fecha_entrada(){
		return C4fecha_entrada;
	}
	public void setC4fecha_entrada(String C4fecha_entrada){
		this.C4fecha_entrada = C4fecha_entrada;
	}
	public String getC5fecha_salida(){
		return C5fecha_salida;
	}
	public void setC5fecha_salida(String C5fecha_salida){
		this.C5fecha_salida = C5fecha_salida;
	}
	public String getC6turno(){
		return C6turno;
	}
	public void setC6turno(String C6turno){
		this.C6turno = C6turno;
	}
	public boolean getC7es_cerrado(){
		return C7es_cerrado;
	}
	public void setC7es_cerrado(boolean C7es_cerrado){
		this.C7es_cerrado = C7es_cerrado;
	}
	public boolean getC8es_entrada(){
		return C8es_entrada;
	}
	public void setC8es_entrada(boolean C8es_entrada){
		this.C8es_entrada = C8es_entrada;
	}
	public boolean getC9es_salida(){
		return C9es_salida;
	}
	public void setC9es_salida(boolean C9es_salida){
		this.C9es_salida = C9es_salida;
	}
	public int getC10fk_idpersona(){
		return C10fk_idpersona;
	}
	public void setC10fk_idpersona(int C10fk_idpersona){
		this.C10fk_idpersona = C10fk_idpersona;
	}
	public int getC11fk_idusuario(){
		return C11fk_idusuario;
	}
	public void setC11fk_idusuario(int C11fk_idusuario){
		this.C11fk_idusuario = C11fk_idusuario;
	}
	public int getC12fk_idrh_turno(){
		return C12fk_idrh_turno;
	}
	public void setC12fk_idrh_turno(int C12fk_idrh_turno){
		this.C12fk_idrh_turno = C12fk_idrh_turno;
	}
	public String toString() {
		return "rh_entrada(" + ",idrh_entrada=" + C1idrh_entrada + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_entrada=" + C4fecha_entrada + " ,fecha_salida=" + C5fecha_salida + " ,turno=" + C6turno + " ,es_cerrado=" + C7es_cerrado + " ,es_entrada=" + C8es_entrada + " ,es_salida=" + C9es_salida + " ,fk_idpersona=" + C10fk_idpersona + " ,fk_idusuario=" + C11fk_idusuario + " ,fk_idrh_turno=" + C12fk_idrh_turno + " )";
	}
}
