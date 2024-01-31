	package FORMULARIO.ENTIDAD;
public class admin_caja_cierre_detalle {

//---------------DECLARAR VARIABLES---------------
private int C1idadmin_caja_cierre_detalle;
private int C2idcaja_cierre_detalle;
private String C3fecha_creado;
private String C4creado_por;
private String C5cerrado_por;
private boolean C6es_cerrado;
private double C7monto_apertura_caja;
private double C8monto_cierre_caja;
private double C9monto_ocupa_minimo;
private double C10monto_ocupa_adicional;
private double C11monto_ocupa_consumo;
private double C12monto_ocupa_descuento;
private double C13monto_ocupa_adelanto;
private double C14monto_gasto;
private double C15monto_compra;
private double C16monto_vale;
private double C17monto_liquidacion;
private double C18monto_interno;
private double C19monto_solo_adelanto;
private double C20monto_garantia;
private String C21estado;
private String C22descripcion;
private String C23maquina;
private String C24id_maquina;
private int C25fk_idgasto;
private int C26fk_idcompra;
private int C27fk_idventa;
private int C28fk_idusuario;
private int C29fk_idrh_vale;
private int C30fk_idrh_liquidacion;
private int C31fk_idventa_interno;
private int C32fk_idgarantia;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public admin_caja_cierre_detalle() {
		setTb_admin_caja_cierre_detalle("admin_caja_cierre_detalle");
		setId_idadmin_caja_cierre_detalle("idadmin_caja_cierre_detalle");
	}
	public static String getTb_admin_caja_cierre_detalle(){
		return nom_tabla;
	}
	public static void setTb_admin_caja_cierre_detalle(String nom_tabla){
		admin_caja_cierre_detalle.nom_tabla = nom_tabla;
	}
	public static String getId_idadmin_caja_cierre_detalle(){
		return nom_idtabla;
	}
	public static void setId_idadmin_caja_cierre_detalle(String nom_idtabla){
		admin_caja_cierre_detalle.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idadmin_caja_cierre_detalle(){
		return C1idadmin_caja_cierre_detalle;
	}
	public void setC1idadmin_caja_cierre_detalle(int C1idadmin_caja_cierre_detalle){
		this.C1idadmin_caja_cierre_detalle = C1idadmin_caja_cierre_detalle;
	}
	public int getC2idcaja_cierre_detalle(){
		return C2idcaja_cierre_detalle;
	}
	public void setC2idcaja_cierre_detalle(int C2idcaja_cierre_detalle){
		this.C2idcaja_cierre_detalle = C2idcaja_cierre_detalle;
	}
	public String getC3fecha_creado(){
		return C3fecha_creado;
	}
	public void setC3fecha_creado(String C3fecha_creado){
		this.C3fecha_creado = C3fecha_creado;
	}
	public String getC4creado_por(){
		return C4creado_por;
	}
	public void setC4creado_por(String C4creado_por){
		this.C4creado_por = C4creado_por;
	}
	public String getC5cerrado_por(){
		return C5cerrado_por;
	}
	public void setC5cerrado_por(String C5cerrado_por){
		this.C5cerrado_por = C5cerrado_por;
	}
	public boolean getC6es_cerrado(){
		return C6es_cerrado;
	}
	public void setC6es_cerrado(boolean C6es_cerrado){
		this.C6es_cerrado = C6es_cerrado;
	}
	public double getC7monto_apertura_caja(){
		return C7monto_apertura_caja;
	}
	public void setC7monto_apertura_caja(double C7monto_apertura_caja){
		this.C7monto_apertura_caja = C7monto_apertura_caja;
	}
	public double getC8monto_cierre_caja(){
		return C8monto_cierre_caja;
	}
	public void setC8monto_cierre_caja(double C8monto_cierre_caja){
		this.C8monto_cierre_caja = C8monto_cierre_caja;
	}
	public double getC9monto_ocupa_minimo(){
		return C9monto_ocupa_minimo;
	}
	public void setC9monto_ocupa_minimo(double C9monto_ocupa_minimo){
		this.C9monto_ocupa_minimo = C9monto_ocupa_minimo;
	}
	public double getC10monto_ocupa_adicional(){
		return C10monto_ocupa_adicional;
	}
	public void setC10monto_ocupa_adicional(double C10monto_ocupa_adicional){
		this.C10monto_ocupa_adicional = C10monto_ocupa_adicional;
	}
	public double getC11monto_ocupa_consumo(){
		return C11monto_ocupa_consumo;
	}
	public void setC11monto_ocupa_consumo(double C11monto_ocupa_consumo){
		this.C11monto_ocupa_consumo = C11monto_ocupa_consumo;
	}
	public double getC12monto_ocupa_descuento(){
		return C12monto_ocupa_descuento;
	}
	public void setC12monto_ocupa_descuento(double C12monto_ocupa_descuento){
		this.C12monto_ocupa_descuento = C12monto_ocupa_descuento;
	}
	public double getC13monto_ocupa_adelanto(){
		return C13monto_ocupa_adelanto;
	}
	public void setC13monto_ocupa_adelanto(double C13monto_ocupa_adelanto){
		this.C13monto_ocupa_adelanto = C13monto_ocupa_adelanto;
	}
	public double getC14monto_gasto(){
		return C14monto_gasto;
	}
	public void setC14monto_gasto(double C14monto_gasto){
		this.C14monto_gasto = C14monto_gasto;
	}
	public double getC15monto_compra(){
		return C15monto_compra;
	}
	public void setC15monto_compra(double C15monto_compra){
		this.C15monto_compra = C15monto_compra;
	}
	public double getC16monto_vale(){
		return C16monto_vale;
	}
	public void setC16monto_vale(double C16monto_vale){
		this.C16monto_vale = C16monto_vale;
	}
	public double getC17monto_liquidacion(){
		return C17monto_liquidacion;
	}
	public void setC17monto_liquidacion(double C17monto_liquidacion){
		this.C17monto_liquidacion = C17monto_liquidacion;
	}
	public double getC18monto_interno(){
		return C18monto_interno;
	}
	public void setC18monto_interno(double C18monto_interno){
		this.C18monto_interno = C18monto_interno;
	}
	public double getC19monto_solo_adelanto(){
		return C19monto_solo_adelanto;
	}
	public void setC19monto_solo_adelanto(double C19monto_solo_adelanto){
		this.C19monto_solo_adelanto = C19monto_solo_adelanto;
	}
	public double getC20monto_garantia(){
		return C20monto_garantia;
	}
	public void setC20monto_garantia(double C20monto_garantia){
		this.C20monto_garantia = C20monto_garantia;
	}
	public String getC21estado(){
		return C21estado;
	}
	public void setC21estado(String C21estado){
		this.C21estado = C21estado;
	}
	public String getC22descripcion(){
		return C22descripcion;
	}
	public void setC22descripcion(String C22descripcion){
		this.C22descripcion = C22descripcion;
	}
	public String getC23maquina(){
		return C23maquina;
	}
	public void setC23maquina(String C23maquina){
		this.C23maquina = C23maquina;
	}
	public String getC24id_maquina(){
		return C24id_maquina;
	}
	public void setC24id_maquina(String C24id_maquina){
		this.C24id_maquina = C24id_maquina;
	}
	public int getC25fk_idgasto(){
		return C25fk_idgasto;
	}
	public void setC25fk_idgasto(int C25fk_idgasto){
		this.C25fk_idgasto = C25fk_idgasto;
	}
	public int getC26fk_idcompra(){
		return C26fk_idcompra;
	}
	public void setC26fk_idcompra(int C26fk_idcompra){
		this.C26fk_idcompra = C26fk_idcompra;
	}
	public int getC27fk_idventa(){
		return C27fk_idventa;
	}
	public void setC27fk_idventa(int C27fk_idventa){
		this.C27fk_idventa = C27fk_idventa;
	}
	public int getC28fk_idusuario(){
		return C28fk_idusuario;
	}
	public void setC28fk_idusuario(int C28fk_idusuario){
		this.C28fk_idusuario = C28fk_idusuario;
	}
	public int getC29fk_idrh_vale(){
		return C29fk_idrh_vale;
	}
	public void setC29fk_idrh_vale(int C29fk_idrh_vale){
		this.C29fk_idrh_vale = C29fk_idrh_vale;
	}
	public int getC30fk_idrh_liquidacion(){
		return C30fk_idrh_liquidacion;
	}
	public void setC30fk_idrh_liquidacion(int C30fk_idrh_liquidacion){
		this.C30fk_idrh_liquidacion = C30fk_idrh_liquidacion;
	}
	public int getC31fk_idventa_interno(){
		return C31fk_idventa_interno;
	}
	public void setC31fk_idventa_interno(int C31fk_idventa_interno){
		this.C31fk_idventa_interno = C31fk_idventa_interno;
	}
	public int getC32fk_idgarantia(){
		return C32fk_idgarantia;
	}
	public void setC32fk_idgarantia(int C32fk_idgarantia){
		this.C32fk_idgarantia = C32fk_idgarantia;
	}
	public String toString() {
		return "admin_caja_cierre_detalle(" + ",idadmin_caja_cierre_detalle=" + C1idadmin_caja_cierre_detalle + " ,idcaja_cierre_detalle=" + C2idcaja_cierre_detalle + " ,fecha_creado=" + C3fecha_creado + " ,creado_por=" + C4creado_por + " ,cerrado_por=" + C5cerrado_por + " ,es_cerrado=" + C6es_cerrado + " ,monto_apertura_caja=" + C7monto_apertura_caja + " ,monto_cierre_caja=" + C8monto_cierre_caja + " ,monto_ocupa_minimo=" + C9monto_ocupa_minimo + " ,monto_ocupa_adicional=" + C10monto_ocupa_adicional + " ,monto_ocupa_consumo=" + C11monto_ocupa_consumo + " ,monto_ocupa_descuento=" + C12monto_ocupa_descuento + " ,monto_ocupa_adelanto=" + C13monto_ocupa_adelanto + " ,monto_gasto=" + C14monto_gasto + " ,monto_compra=" + C15monto_compra + " ,monto_vale=" + C16monto_vale + " ,monto_liquidacion=" + C17monto_liquidacion + " ,monto_interno=" + C18monto_interno + " ,monto_solo_adelanto=" + C19monto_solo_adelanto + " ,monto_garantia=" + C20monto_garantia + " ,estado=" + C21estado + " ,descripcion=" + C22descripcion + " ,maquina=" + C23maquina + " ,id_maquina=" + C24id_maquina + " ,fk_idgasto=" + C25fk_idgasto + " ,fk_idcompra=" + C26fk_idcompra + " ,fk_idventa=" + C27fk_idventa + " ,fk_idusuario=" + C28fk_idusuario + " ,fk_idrh_vale=" + C29fk_idrh_vale + " ,fk_idrh_liquidacion=" + C30fk_idrh_liquidacion + " ,fk_idventa_interno=" + C31fk_idventa_interno + " ,fk_idgarantia=" + C32fk_idgarantia + " )";
	}
}
