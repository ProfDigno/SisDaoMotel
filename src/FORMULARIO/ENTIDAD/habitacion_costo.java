	package FORMULARIO.ENTIDAD;
public class habitacion_costo {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_costo;
private String C2fecha_creado;
private String C3creado_por;
private boolean C4activo;
private String C5nombre;
private String C6nivel_lujo;
private double C7monto_por_hora_minimo;
private double C8monto_por_hora_adicional;
private double C9monto_por_dormir_minimo;
private double C10monto_por_dormir_adicional;
private int C11minuto_minimo;
private int C12minuto_adicional;
private int C13minuto_cancelar;
private String C14hs_dormir_ingreso_inicio;
private String C15hs_dormir_ingreso_final;
private String C16hs_dormir_salida_final;
private int C17minuto_tolerancia;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public habitacion_costo() {
		setTb_habitacion_costo("habitacion_costo");
		setId_idhabitacion_costo("idhabitacion_costo");
	}
	public static String getTb_habitacion_costo(){
		return nom_tabla;
	}
	public static void setTb_habitacion_costo(String nom_tabla){
		habitacion_costo.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_costo(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_costo(String nom_idtabla){
		habitacion_costo.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------

    public int getC17minuto_tolerancia() {
        return C17minuto_tolerancia;
    }

    public void setC17minuto_tolerancia(int C17minuto_tolerancia) {
        this.C17minuto_tolerancia = C17minuto_tolerancia;
    }
        
	public int getC1idhabitacion_costo(){
		return C1idhabitacion_costo;
	}
	public void setC1idhabitacion_costo(int C1idhabitacion_costo){
		this.C1idhabitacion_costo = C1idhabitacion_costo;
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
	public boolean getC4activo(){
		return C4activo;
	}
	public void setC4activo(boolean C4activo){
		this.C4activo = C4activo;
	}
	public String getC5nombre(){
		return C5nombre;
	}
	public void setC5nombre(String C5nombre){
		this.C5nombre = C5nombre;
	}
	public String getC6nivel_lujo(){
		return C6nivel_lujo;
	}
	public void setC6nivel_lujo(String C6nivel_lujo){
		this.C6nivel_lujo = C6nivel_lujo;
	}
	public double getC7monto_por_hora_minimo(){
		return C7monto_por_hora_minimo;
	}
	public void setC7monto_por_hora_minimo(double C7monto_por_hora_minimo){
		this.C7monto_por_hora_minimo = C7monto_por_hora_minimo;
	}
	public double getC8monto_por_hora_adicional(){
		return C8monto_por_hora_adicional;
	}
	public void setC8monto_por_hora_adicional(double C8monto_por_hora_adicional){
		this.C8monto_por_hora_adicional = C8monto_por_hora_adicional;
	}
	public double getC9monto_por_dormir_minimo(){
		return C9monto_por_dormir_minimo;
	}
	public void setC9monto_por_dormir_minimo(double C9monto_por_dormir_minimo){
		this.C9monto_por_dormir_minimo = C9monto_por_dormir_minimo;
	}
	public double getC10monto_por_dormir_adicional(){
		return C10monto_por_dormir_adicional;
	}
	public void setC10monto_por_dormir_adicional(double C10monto_por_dormir_adicional){
		this.C10monto_por_dormir_adicional = C10monto_por_dormir_adicional;
	}
	public int getC11minuto_minimo(){
		return C11minuto_minimo;
	}
	public void setC11minuto_minimo(int C11minuto_minimo){
		this.C11minuto_minimo = C11minuto_minimo;
	}
	public int getC12minuto_adicional(){
		return C12minuto_adicional;
	}
	public void setC12minuto_adicional(int C12minuto_adicional){
		this.C12minuto_adicional = C12minuto_adicional;
	}
	public int getC13minuto_cancelar(){
		return C13minuto_cancelar;
	}
	public void setC13minuto_cancelar(int C13minuto_cancelar){
		this.C13minuto_cancelar = C13minuto_cancelar;
	}
	public String getC14hs_dormir_ingreso_inicio(){
		return C14hs_dormir_ingreso_inicio;
	}
	public void setC14hs_dormir_ingreso_inicio(String C14hs_dormir_ingreso_inicio){
		this.C14hs_dormir_ingreso_inicio = C14hs_dormir_ingreso_inicio;
	}
	public String getC15hs_dormir_ingreso_final(){
		return C15hs_dormir_ingreso_final;
	}
	public void setC15hs_dormir_ingreso_final(String C15hs_dormir_ingreso_final){
		this.C15hs_dormir_ingreso_final = C15hs_dormir_ingreso_final;
	}
	public String getC16hs_dormir_salida_final(){
		return C16hs_dormir_salida_final;
	}
	public void setC16hs_dormir_salida_final(String C16hs_dormir_salida_final){
		this.C16hs_dormir_salida_final = C16hs_dormir_salida_final;
	}
	public String toString() {
		return "habitacion_costo(" + ",idhabitacion_costo=" + C1idhabitacion_costo + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,activo=" + C4activo + " ,nombre=" + C5nombre + " ,nivel_lujo=" + C6nivel_lujo + " ,monto_por_hora_minimo=" + C7monto_por_hora_minimo + " ,monto_por_hora_adicional=" + C8monto_por_hora_adicional + " ,monto_por_dormir_minimo=" + C9monto_por_dormir_minimo + " ,monto_por_dormir_adicional=" + C10monto_por_dormir_adicional + " ,minuto_minimo=" + C11minuto_minimo + " ,minuto_adicional=" + C12minuto_adicional + " ,minuto_cancelar=" + C13minuto_cancelar + " ,hs_dormir_ingreso_inicio=" + C14hs_dormir_ingreso_inicio + " ,hs_dormir_ingreso_final=" + C15hs_dormir_ingreso_final + " ,hs_dormir_salida_final=" + C16hs_dormir_salida_final + " )";
	}
}
