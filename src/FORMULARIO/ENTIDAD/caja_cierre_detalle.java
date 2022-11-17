	package FORMULARIO.ENTIDAD;
public class caja_cierre_detalle {

//---------------DECLARAR VARIABLES---------------
private int C1idcaja_cierre_detalle;
private String C2fecha_creado;
private String C3creado_por;
private String C4cerrado_por;
private boolean C5es_cerrado;
private double C6monto_apertura_caja;
private double C7monto_cierre_caja;
private double C8monto_ocupa_minimo;
private double C9monto_ocupa_adicional;
private double C10monto_ocupa_consumo;
private double C11monto_ocupa_descuento;
private double C12monto_ocupa_adelanto;
private double C13monto_gasto;
private double C14monto_compra;
private double C15monto_vale;
private double C16monto_liquidacion;
private String C17estado;
private String C18descripcion;
private int C19fk_idgasto;
private int C20fk_idcompra;
private int C21fk_idventa;
private int C22fk_idusuario;
private int C23fk_idrh_vale;
private int C24fk_idrh_liquidacion;
private double C25monto_solo_adelanto;
private double C26monto_interno;
private int C27fk_idventa_interno;
private double C28monto_garantia;
private int C29fk_idgarantia;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public caja_cierre_detalle() {
		setTb_caja_cierre_detalle("caja_cierre_detalle");
		setId_idcaja_cierre_detalle("idcaja_cierre_detalle");
	}
	public static String getTb_caja_cierre_detalle(){
		return nom_tabla;
	}
	public static void setTb_caja_cierre_detalle(String nom_tabla){
		caja_cierre_detalle.nom_tabla = nom_tabla;
	}
	public static String getId_idcaja_cierre_detalle(){
		return nom_idtabla;
	}
	public static void setId_idcaja_cierre_detalle(String nom_idtabla){
		caja_cierre_detalle.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------

    public double getC28monto_garantia() {
        return C28monto_garantia;
    }

    public void setC28monto_garantia(double C28monto_garantia) {
        this.C28monto_garantia = C28monto_garantia;
    }

    public int getC29fk_idgarantia() {
        return C29fk_idgarantia;
    }

    public void setC29fk_idgarantia(int C29fk_idgarantia) {
        this.C29fk_idgarantia = C29fk_idgarantia;
    }

    public double getC26monto_interno() {
        return C26monto_interno;
    }

    public void setC26monto_interno(double C26monto_interno) {
        this.C26monto_interno = C26monto_interno;
    }

    public int getC27fk_idventa_interno() {
        return C27fk_idventa_interno;
    }

    public void setC27fk_idventa_interno(int C27fk_idventa_interno) {
        this.C27fk_idventa_interno = C27fk_idventa_interno;
    }
        
	public int getC1idcaja_cierre_detalle(){
		return C1idcaja_cierre_detalle;
	}
	public void setC1idcaja_cierre_detalle(int C1idcaja_cierre_detalle){
		this.C1idcaja_cierre_detalle = C1idcaja_cierre_detalle;
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
	public String getC4cerrado_por(){
		return C4cerrado_por;
	}
	public void setC4cerrado_por(String C4cerrado_por){
		this.C4cerrado_por = C4cerrado_por;
	}
	public boolean getC5es_cerrado(){
		return C5es_cerrado;
	}
	public void setC5es_cerrado(boolean C5es_cerrado){
		this.C5es_cerrado = C5es_cerrado;
	}
	public double getC6monto_apertura_caja(){
		return C6monto_apertura_caja;
	}
	public void setC6monto_apertura_caja(double C6monto_apertura_caja){
		this.C6monto_apertura_caja = C6monto_apertura_caja;
	}
	public double getC7monto_cierre_caja(){
		return C7monto_cierre_caja;
	}
	public void setC7monto_cierre_caja(double C7monto_cierre_caja){
		this.C7monto_cierre_caja = C7monto_cierre_caja;
	}
	public double getC8monto_ocupa_minimo(){
		return C8monto_ocupa_minimo;
	}
	public void setC8monto_ocupa_minimo(double C8monto_ocupa_minimo){
		this.C8monto_ocupa_minimo = C8monto_ocupa_minimo;
	}
	public double getC9monto_ocupa_adicional(){
		return C9monto_ocupa_adicional;
	}
	public void setC9monto_ocupa_adicional(double C9monto_ocupa_adicional){
		this.C9monto_ocupa_adicional = C9monto_ocupa_adicional;
	}
	public double getC10monto_ocupa_consumo(){
		return C10monto_ocupa_consumo;
	}
	public void setC10monto_ocupa_consumo(double C10monto_ocupa_consumo){
		this.C10monto_ocupa_consumo = C10monto_ocupa_consumo;
	}
	public double getC11monto_ocupa_descuento(){
		return C11monto_ocupa_descuento;
	}
	public void setC11monto_ocupa_descuento(double C11monto_ocupa_descuento){
		this.C11monto_ocupa_descuento = C11monto_ocupa_descuento;
	}
	public double getC12monto_ocupa_adelanto(){
		return C12monto_ocupa_adelanto;
	}
	public void setC12monto_ocupa_adelanto(double C12monto_ocupa_adelanto){
		this.C12monto_ocupa_adelanto = C12monto_ocupa_adelanto;
	}
	public double getC13monto_gasto(){
		return C13monto_gasto;
	}
	public void setC13monto_gasto(double C13monto_gasto){
		this.C13monto_gasto = C13monto_gasto;
	}
	public double getC14monto_compra(){
		return C14monto_compra;
	}
	public void setC14monto_compra(double C14monto_compra){
		this.C14monto_compra = C14monto_compra;
	}
	public double getC15monto_vale(){
		return C15monto_vale;
	}
	public void setC15monto_vale(double C15monto_vale){
		this.C15monto_vale = C15monto_vale;
	}
	public double getC16monto_liquidacion(){
		return C16monto_liquidacion;
	}
	public void setC16monto_liquidacion(double C16monto_liquidacion){
		this.C16monto_liquidacion = C16monto_liquidacion;
	}
	public String getC17estado(){
		return C17estado;
	}
	public void setC17estado(String C17estado){
		this.C17estado = C17estado;
	}
	public String getC18descripcion(){
		return C18descripcion;
	}
	public void setC18descripcion(String C18descripcion){
		this.C18descripcion = C18descripcion;
	}
	public int getC19fk_idgasto(){
		return C19fk_idgasto;
	}
	public void setC19fk_idgasto(int C19fk_idgasto){
		this.C19fk_idgasto = C19fk_idgasto;
	}
	public int getC20fk_idcompra(){
		return C20fk_idcompra;
	}
	public void setC20fk_idcompra(int C20fk_idcompra){
		this.C20fk_idcompra = C20fk_idcompra;
	}
	public int getC21fk_idventa(){
		return C21fk_idventa;
	}
	public void setC21fk_idventa(int C21fk_idventa){
		this.C21fk_idventa = C21fk_idventa;
	}
	public int getC22fk_idusuario(){
		return C22fk_idusuario;
	}
	public void setC22fk_idusuario(int C22fk_idusuario){
		this.C22fk_idusuario = C22fk_idusuario;
	}
	public int getC23fk_idrh_vale(){
		return C23fk_idrh_vale;
	}
	public void setC23fk_idrh_vale(int C23fk_idrh_vale){
		this.C23fk_idrh_vale = C23fk_idrh_vale;
	}
	public int getC24fk_idrh_liquidacion(){
		return C24fk_idrh_liquidacion;
	}
	public void setC24fk_idrh_liquidacion(int C24fk_idrh_liquidacion){
		this.C24fk_idrh_liquidacion = C24fk_idrh_liquidacion;
	}

    public double getC25monto_solo_adelanto() {
        return C25monto_solo_adelanto;
    }

    public void setC25monto_solo_adelanto(double C25monto_solo_adelanto) {
        this.C25monto_solo_adelanto = C25monto_solo_adelanto;
    }
        
	public String toString() {
		return "caja_cierre_detalle(" + ",idcaja_cierre_detalle=" + C1idcaja_cierre_detalle + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,cerrado_por=" + C4cerrado_por + " ,es_cerrado=" + C5es_cerrado + " ,monto_apertura_caja=" + C6monto_apertura_caja + " ,monto_cierre_caja=" + C7monto_cierre_caja + " ,monto_ocupa_minimo=" + C8monto_ocupa_minimo + " ,monto_ocupa_adicional=" + C9monto_ocupa_adicional + " ,monto_ocupa_consumo=" + C10monto_ocupa_consumo + " ,monto_ocupa_descuento=" + C11monto_ocupa_descuento + " ,monto_ocupa_adelanto=" + C12monto_ocupa_adelanto + " ,monto_gasto=" + C13monto_gasto + " ,monto_compra=" + C14monto_compra + " ,monto_vale=" + C15monto_vale + " ,monto_liquidacion=" + C16monto_liquidacion + " ,estado=" + C17estado + " ,descripcion=" + C18descripcion + " ,fk_idgasto=" + C19fk_idgasto + " ,fk_idcompra=" + C20fk_idcompra + " ,fk_idventa=" + C21fk_idventa + " ,fk_idusuario=" + C22fk_idusuario + " ,fk_idrh_vale=" + C23fk_idrh_vale + " ,fk_idrh_liquidacion=" + C24fk_idrh_liquidacion + " )";
	}
}
