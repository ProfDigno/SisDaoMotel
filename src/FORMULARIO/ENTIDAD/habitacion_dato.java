	package FORMULARIO.ENTIDAD;
public class habitacion_dato {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_dato;
private String C2fecha_creado;
private String C3creado_por;
private int C4nro_habitacion;
private String C5tipo_habitacion;
private String C6estado_actual;
private String C7descripcion;
private String C8ubicacion;
private boolean C9activo;
private boolean C10con_frigobar;
private int C11fk_idhabitacion_costo;
private boolean C12es_manual;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public habitacion_dato() {
		setTb_habitacion_dato("habitacion_dato");
		setId_idhabitacion_dato("idhabitacion_dato");
	}

    public boolean getC12es_manual() {
        return C12es_manual;
    }

    public void setC12es_manual(boolean C12es_manual) {
        this.C12es_manual = C12es_manual;
    }
        
	public static String getTb_habitacion_dato(){
		return nom_tabla;
	}
	public static void setTb_habitacion_dato(String nom_tabla){
		habitacion_dato.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_dato(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_dato(String nom_idtabla){
		habitacion_dato.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idhabitacion_dato(){
		return C1idhabitacion_dato;
	}
	public void setC1idhabitacion_dato(int C1idhabitacion_dato){
		this.C1idhabitacion_dato = C1idhabitacion_dato;
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
	public int getC4nro_habitacion(){
		return C4nro_habitacion;
	}
	public void setC4nro_habitacion(int C4nro_habitacion){
		this.C4nro_habitacion = C4nro_habitacion;
	}
	public String getC5tipo_habitacion(){
		return C5tipo_habitacion;
	}
	public void setC5tipo_habitacion(String C5tipo_habitacion){
		this.C5tipo_habitacion = C5tipo_habitacion;
	}
	public String getC6estado_actual(){
		return C6estado_actual;
	}
	public void setC6estado_actual(String C6estado_actual){
		this.C6estado_actual = C6estado_actual;
	}
	public String getC7descripcion(){
		return C7descripcion;
	}
	public void setC7descripcion(String C7descripcion){
		this.C7descripcion = C7descripcion;
	}
	public String getC8ubicacion(){
		return C8ubicacion;
	}
	public void setC8ubicacion(String C8ubicacion){
		this.C8ubicacion = C8ubicacion;
	}
	public boolean getC9activo(){
		return C9activo;
	}
	public void setC9activo(boolean C9activo){
		this.C9activo = C9activo;
	}
	public boolean getC10con_frigobar(){
		return C10con_frigobar;
	}
	public void setC10con_frigobar(boolean C10con_frigobar){
		this.C10con_frigobar = C10con_frigobar;
	}
	public int getC11fk_idhabitacion_costo(){
		return C11fk_idhabitacion_costo;
	}
	public void setC11fk_idhabitacion_costo(int C11fk_idhabitacion_costo){
		this.C11fk_idhabitacion_costo = C11fk_idhabitacion_costo;
	}
	public String toString() {
		return "habitacion_dato(" + ",idhabitacion_dato=" + C1idhabitacion_dato + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nro_habitacion=" + C4nro_habitacion + " ,tipo_habitacion=" + C5tipo_habitacion + " ,estado_actual=" + C6estado_actual + " ,descripcion=" + C7descripcion + " ,ubicacion=" + C8ubicacion + " ,activo=" + C9activo + " ,con_frigobar=" + C10con_frigobar + " ,fk_idhabitacion_costo=" + C11fk_idhabitacion_costo + " )";
	}
}
