	package FORMULARIO.ENTIDAD;
public class persona {

//---------------DECLARAR VARIABLES---------------
private int C1idpersona;
private String C2fecha_creado;
private String C3creado_por;
private String C4nombre;
private String C5ruc;
private String C6direccion;
private String C7telefono;
private String C8tipo_persona;
private int C9dia_libre;
private double C10salario_base;
private int C11fk_idpersona_cargo;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public persona() {
		setTb_persona("persona");
		setId_idpersona("idpersona");
	}
	public static String getTb_persona(){
		return nom_tabla;
	}
	public static void setTb_persona(String nom_tabla){
		persona.nom_tabla = nom_tabla;
	}
	public static String getId_idpersona(){
		return nom_idtabla;
	}
	public static void setId_idpersona(String nom_idtabla){
		persona.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idpersona(){
		return C1idpersona;
	}
	public void setC1idpersona(int C1idpersona){
		this.C1idpersona = C1idpersona;
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
	public String getC5ruc(){
		return C5ruc;
	}
	public void setC5ruc(String C5ruc){
		this.C5ruc = C5ruc;
	}
	public String getC6direccion(){
		return C6direccion;
	}
	public void setC6direccion(String C6direccion){
		this.C6direccion = C6direccion;
	}
	public String getC7telefono(){
		return C7telefono;
	}
	public void setC7telefono(String C7telefono){
		this.C7telefono = C7telefono;
	}
	public String getC8tipo_persona(){
		return C8tipo_persona;
	}
	public void setC8tipo_persona(String C8tipo_persona){
		this.C8tipo_persona = C8tipo_persona;
	}
	public int getC9dia_libre(){
		return C9dia_libre;
	}
	public void setC9dia_libre(int C9dia_libre){
		this.C9dia_libre = C9dia_libre;
	}
	public double getC10salario_base(){
		return C10salario_base;
	}
	public void setC10salario_base(double C10salario_base){
		this.C10salario_base = C10salario_base;
	}
	public int getC11fk_idpersona_cargo(){
		return C11fk_idpersona_cargo;
	}
	public void setC11fk_idpersona_cargo(int C11fk_idpersona_cargo){
		this.C11fk_idpersona_cargo = C11fk_idpersona_cargo;
	}
	public String toString() {
		return "persona(" + ",idpersona=" + C1idpersona + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,ruc=" + C5ruc + " ,direccion=" + C6direccion + " ,telefono=" + C7telefono + " ,tipo_persona=" + C8tipo_persona + " ,dia_libre=" + C9dia_libre + " ,salario_base=" + C10salario_base + " ,fk_idpersona_cargo=" + C11fk_idpersona_cargo + " )";
	}
}
