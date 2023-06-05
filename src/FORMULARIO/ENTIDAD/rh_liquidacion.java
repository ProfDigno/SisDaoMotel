	package FORMULARIO.ENTIDAD;
public class rh_liquidacion {

//---------------DECLARAR VARIABLES---------------
private int C1idrh_liquidacion;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_desde;
private String C5fecha_hasta;
private String C6estado;
private boolean C7es_cerrado;
private double C8monto_vale;
private double C9monto_descuento;
private double C10monto_liquidacion;
private double C11salario_base;
private String C12monto_letra;
private int C13fk_idpersona;
private static String nom_tabla;
private static String nom_idtabla;
private double sum_descuento;
private double sum_vale;
//---------------TABLA-ID---------------
	public rh_liquidacion() {
		setTb_rh_liquidacion("rh_liquidacion");
		setId_idrh_liquidacion("idrh_liquidacion");
	}
	public static String getTb_rh_liquidacion(){
		return nom_tabla;
	}
	public static void setTb_rh_liquidacion(String nom_tabla){
		rh_liquidacion.nom_tabla = nom_tabla;
	}
	public static String getId_idrh_liquidacion(){
		return nom_idtabla;
	}
	public static void setId_idrh_liquidacion(String nom_idtabla){
		rh_liquidacion.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------

    public double getSum_descuento() {
        return sum_descuento;
    }

    public void setSum_descuento(double sum_descuento) {
        this.sum_descuento = sum_descuento;
    }

    public double getSum_vale() {
        return sum_vale;
    }

    public void setSum_vale(double sum_vale) {
        this.sum_vale = sum_vale;
    }
        
	public int getC1idrh_liquidacion(){
		return C1idrh_liquidacion;
	}
	public void setC1idrh_liquidacion(int C1idrh_liquidacion){
		this.C1idrh_liquidacion = C1idrh_liquidacion;
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
	public String getC4fecha_desde(){
		return C4fecha_desde;
	}
	public void setC4fecha_desde(String C4fecha_desde){
		this.C4fecha_desde = C4fecha_desde;
	}
	public String getC5fecha_hasta(){
		return C5fecha_hasta;
	}
	public void setC5fecha_hasta(String C5fecha_hasta){
		this.C5fecha_hasta = C5fecha_hasta;
	}
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public boolean getC7es_cerrado(){
		return C7es_cerrado;
	}
	public void setC7es_cerrado(boolean C7es_cerrado){
		this.C7es_cerrado = C7es_cerrado;
	}
	public double getC8monto_vale(){
		return C8monto_vale;
	}
	public void setC8monto_vale(double C8monto_vale){
		this.C8monto_vale = C8monto_vale;
	}
	public double getC9monto_descuento(){
		return C9monto_descuento;
	}
	public void setC9monto_descuento(double C9monto_descuento){
		this.C9monto_descuento = C9monto_descuento;
	}
	public double getC10monto_liquidacion(){
		return C10monto_liquidacion;
	}
	public void setC10monto_liquidacion(double C10monto_liquidacion){
		this.C10monto_liquidacion = C10monto_liquidacion;
	}
	public double getC11salario_base(){
		return C11salario_base;
	}
	public void setC11salario_base(double C11salario_base){
		this.C11salario_base = C11salario_base;
	}
	public String getC12monto_letra(){
		return C12monto_letra;
	}
	public void setC12monto_letra(String C12monto_letra){
		this.C12monto_letra = C12monto_letra;
	}
	public int getC13fk_idpersona(){
		return C13fk_idpersona;
	}
	public void setC13fk_idpersona(int C13fk_idpersona){
		this.C13fk_idpersona = C13fk_idpersona;
	}
	public String toString() {
		return "rh_liquidacion(" + ",idrh_liquidacion=" + C1idrh_liquidacion + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_desde=" + C4fecha_desde + " ,fecha_hasta=" + C5fecha_hasta + " ,estado=" + C6estado + " ,es_cerrado=" + C7es_cerrado + " ,monto_vale=" + C8monto_vale + " ,monto_descuento=" + C9monto_descuento + " ,monto_liquidacion=" + C10monto_liquidacion + " ,salario_base=" + C11salario_base + " ,monto_letra=" + C12monto_letra + " ,fk_idpersona=" + C13fk_idpersona + " )";
	}
}
