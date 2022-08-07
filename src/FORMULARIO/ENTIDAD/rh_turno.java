	package FORMULARIO.ENTIDAD;
public class rh_turno {

//---------------DECLARAR VARIABLES---------------
private int C1idrh_turno;
private String C2fecha_creado;
private String C3creado_por;
private String C4turno;
private String C5hora_inicio;
private String C6hora_salida;
private int C7tolera_llega_tarde;
private int C8tolera_sale_antes;
private boolean C9domindo;
private boolean C10lunes;
private boolean C11martes;
private boolean C12miercoles;
private boolean C13jueves;
private boolean C14viernes;
private boolean C15sabado;
private boolean C16activo;
private boolean C17incluye_dos_dia;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public rh_turno() {
		setTb_rh_turno("rh_turno");
		setId_idrh_turno("idrh_turno");
	}
	public static String getTb_rh_turno(){
		return nom_tabla;
	}
	public static void setTb_rh_turno(String nom_tabla){
		rh_turno.nom_tabla = nom_tabla;
	}
	public static String getId_idrh_turno(){
		return nom_idtabla;
	}
	public static void setId_idrh_turno(String nom_idtabla){
		rh_turno.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idrh_turno(){
		return C1idrh_turno;
	}
	public void setC1idrh_turno(int C1idrh_turno){
		this.C1idrh_turno = C1idrh_turno;
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
	public String getC4turno(){
		return C4turno;
	}
	public void setC4turno(String C4turno){
		this.C4turno = C4turno;
	}
	public String getC5hora_inicio(){
		return C5hora_inicio;
	}
	public void setC5hora_inicio(String C5hora_inicio){
		this.C5hora_inicio = C5hora_inicio;
	}
	public String getC6hora_salida(){
		return C6hora_salida;
	}
	public void setC6hora_salida(String C6hora_salida){
		this.C6hora_salida = C6hora_salida;
	}
	public int getC7tolera_llega_tarde(){
		return C7tolera_llega_tarde;
	}
	public void setC7tolera_llega_tarde(int C7tolera_llega_tarde){
		this.C7tolera_llega_tarde = C7tolera_llega_tarde;
	}
	public int getC8tolera_sale_antes(){
		return C8tolera_sale_antes;
	}
	public void setC8tolera_sale_antes(int C8tolera_sale_antes){
		this.C8tolera_sale_antes = C8tolera_sale_antes;
	}
	public boolean getC9domindo(){
		return C9domindo;
	}
	public void setC9domindo(boolean C9domindo){
		this.C9domindo = C9domindo;
	}
	public boolean getC10lunes(){
		return C10lunes;
	}
	public void setC10lunes(boolean C10lunes){
		this.C10lunes = C10lunes;
	}
	public boolean getC11martes(){
		return C11martes;
	}
	public void setC11martes(boolean C11martes){
		this.C11martes = C11martes;
	}
	public boolean getC12miercoles(){
		return C12miercoles;
	}
	public void setC12miercoles(boolean C12miercoles){
		this.C12miercoles = C12miercoles;
	}
	public boolean getC13jueves(){
		return C13jueves;
	}
	public void setC13jueves(boolean C13jueves){
		this.C13jueves = C13jueves;
	}
	public boolean getC14viernes(){
		return C14viernes;
	}
	public void setC14viernes(boolean C14viernes){
		this.C14viernes = C14viernes;
	}
	public boolean getC15sabado(){
		return C15sabado;
	}
	public void setC15sabado(boolean C15sabado){
		this.C15sabado = C15sabado;
	}
	public boolean getC16activo(){
		return C16activo;
	}
	public void setC16activo(boolean C16activo){
		this.C16activo = C16activo;
	}
	public boolean getC17incluye_dos_dia(){
		return C17incluye_dos_dia;
	}
	public void setC17incluye_dos_dia(boolean C17incluye_dos_dia){
		this.C17incluye_dos_dia = C17incluye_dos_dia;
	}
	public String toString() {
		return "rh_turno(" + ",idrh_turno=" + C1idrh_turno + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,turno=" + C4turno + " ,hora_inicio=" + C5hora_inicio + " ,hora_salida=" + C6hora_salida + " ,tolera_llega_tarde=" + C7tolera_llega_tarde + " ,tolera_sale_antes=" + C8tolera_sale_antes + " ,domindo=" + C9domindo + " ,lunes=" + C10lunes + " ,martes=" + C11martes + " ,miercoles=" + C12miercoles + " ,jueves=" + C13jueves + " ,viernes=" + C14viernes + " ,sabado=" + C15sabado + " ,activo=" + C16activo + " ,incluye_dos_dia=" + C17incluye_dos_dia + " )";
	}
}
