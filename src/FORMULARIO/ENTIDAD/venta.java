	package FORMULARIO.ENTIDAD;
public class venta {

//---------------DECLARAR VARIABLES---------------
private int C1idventa;
private String C2fecha_creado;
private String C3creado_por;
private String C4monto_letra;
private String C5estado;
private String C6observacion;
private String C7tipo_persona;
private String C8motivo_anulacion;
private String C9motivo_mudar_habitacion;
private double C10monto_minimo;
private double C11monto_adicional;
private double C12cant_adicional;
private double C13monto_consumo;
private double C14monto_insumo;
private double C15monto_descuento;
private double C16monto_adelanto;
private int C17fk_idhabitacion_recepcion;
private int C18fk_idpersona;
private int C19fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public venta() {
		setTb_venta("venta");
		setId_idventa("idventa");
	}
	public static String getTb_venta(){
		return nom_tabla;
	}
	public static void setTb_venta(String nom_tabla){
		venta.nom_tabla = nom_tabla;
	}
	public static String getId_idventa(){
		return nom_idtabla;
	}
	public static void setId_idventa(String nom_idtabla){
		venta.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idventa(){
		return C1idventa;
	}
	public void setC1idventa(int C1idventa){
		this.C1idventa = C1idventa;
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
	public String getC4monto_letra(){
		return C4monto_letra;
	}
	public void setC4monto_letra(String C4monto_letra){
		this.C4monto_letra = C4monto_letra;
	}
	public String getC5estado(){
		return C5estado;
	}
	public void setC5estado(String C5estado){
		this.C5estado = C5estado;
	}
	public String getC6observacion(){
		return C6observacion;
	}
	public void setC6observacion(String C6observacion){
		this.C6observacion = C6observacion;
	}
	public String getC7tipo_persona(){
		return C7tipo_persona;
	}
	public void setC7tipo_persona(String C7tipo_persona){
		this.C7tipo_persona = C7tipo_persona;
	}
	public String getC8motivo_anulacion(){
		return C8motivo_anulacion;
	}
	public void setC8motivo_anulacion(String C8motivo_anulacion){
		this.C8motivo_anulacion = C8motivo_anulacion;
	}
	public String getC9motivo_mudar_habitacion(){
		return C9motivo_mudar_habitacion;
	}
	public void setC9motivo_mudar_habitacion(String C9motivo_mudar_habitacion){
		this.C9motivo_mudar_habitacion = C9motivo_mudar_habitacion;
	}
	public double getC10monto_minimo(){
		return C10monto_minimo;
	}
	public void setC10monto_minimo(double C10monto_minimo){
		this.C10monto_minimo = C10monto_minimo;
	}
	public double getC11monto_adicional(){
		return C11monto_adicional;
	}
	public void setC11monto_adicional(double C11monto_adicional){
		this.C11monto_adicional = C11monto_adicional;
	}
	public double getC12cant_adicional(){
		return C12cant_adicional;
	}
	public void setC12cant_adicional(double C12cant_adicional){
		this.C12cant_adicional = C12cant_adicional;
	}
	public double getC13monto_consumo(){
		return C13monto_consumo;
	}
	public void setC13monto_consumo(double C13monto_consumo){
		this.C13monto_consumo = C13monto_consumo;
	}
	public double getC14monto_insumo(){
		return C14monto_insumo;
	}
	public void setC14monto_insumo(double C14monto_insumo){
		this.C14monto_insumo = C14monto_insumo;
	}
	public double getC15monto_descuento(){
		return C15monto_descuento;
	}
	public void setC15monto_descuento(double C15monto_descuento){
		this.C15monto_descuento = C15monto_descuento;
	}
	public double getC16monto_adelanto(){
		return C16monto_adelanto;
	}
	public void setC16monto_adelanto(double C16monto_adelanto){
		this.C16monto_adelanto = C16monto_adelanto;
	}
	public int getC17fk_idhabitacion_recepcion(){
		return C17fk_idhabitacion_recepcion;
	}
	public void setC17fk_idhabitacion_recepcion(int C17fk_idhabitacion_recepcion){
		this.C17fk_idhabitacion_recepcion = C17fk_idhabitacion_recepcion;
	}
	public int getC18fk_idpersona(){
		return C18fk_idpersona;
	}
	public void setC18fk_idpersona(int C18fk_idpersona){
		this.C18fk_idpersona = C18fk_idpersona;
	}
	public int getC19fk_idusuario(){
		return C19fk_idusuario;
	}
	public void setC19fk_idusuario(int C19fk_idusuario){
		this.C19fk_idusuario = C19fk_idusuario;
	}
	public String toString() {
		return "venta(" + ",idventa=" + C1idventa + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,monto_letra=" + C4monto_letra + " ,estado=" + C5estado + " ,observacion=" + C6observacion + " ,tipo_persona=" + C7tipo_persona + " ,motivo_anulacion=" + C8motivo_anulacion + " ,motivo_mudar_habitacion=" + C9motivo_mudar_habitacion + " ,monto_minimo=" + C10monto_minimo + " ,monto_adicional=" + C11monto_adicional + " ,cant_adicional=" + C12cant_adicional + " ,monto_consumo=" + C13monto_consumo + " ,monto_insumo=" + C14monto_insumo + " ,monto_descuento=" + C15monto_descuento + " ,monto_adelanto=" + C16monto_adelanto + " ,fk_idhabitacion_recepcion=" + C17fk_idhabitacion_recepcion + " ,fk_idpersona=" + C18fk_idpersona + " ,fk_idusuario=" + C19fk_idusuario + " )";
	}
}
