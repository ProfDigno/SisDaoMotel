	package FORMULARIO.ENTIDAD;
public class compra {

//---------------DECLARAR VARIABLES---------------
private int C1idcompra;
private String C2fecha_creado;
private String C3creado_por;
private String C4fecha_compra;
private String C5nro_factura;
private boolean C6es_factura;
private double C7monto_total;
private double C8monto_iva5;
private double C9monto_iva10;
private String C10monto_letra;
private String C11observacion;
private String C12estado;
private int C13fk_idpersona;
private int C14fk_idusuario;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public compra() {
		setTb_compra("compra");
		setId_idcompra("idcompra");
	}
	public static String getTb_compra(){
		return nom_tabla;
	}
	public static void setTb_compra(String nom_tabla){
		compra.nom_tabla = nom_tabla;
	}
	public static String getId_idcompra(){
		return nom_idtabla;
	}
	public static void setId_idcompra(String nom_idtabla){
		compra.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idcompra(){
		return C1idcompra;
	}
	public void setC1idcompra(int C1idcompra){
		this.C1idcompra = C1idcompra;
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
	public String getC4fecha_compra(){
		return C4fecha_compra;
	}
	public void setC4fecha_compra(String C4fecha_compra){
		this.C4fecha_compra = C4fecha_compra;
	}
	public String getC5nro_factura(){
		return C5nro_factura;
	}
	public void setC5nro_factura(String C5nro_factura){
		this.C5nro_factura = C5nro_factura;
	}
	public boolean getC6es_factura(){
		return C6es_factura;
	}
	public void setC6es_factura(boolean C6es_factura){
		this.C6es_factura = C6es_factura;
	}
	public double getC7monto_total(){
		return C7monto_total;
	}
	public void setC7monto_total(double C7monto_total){
		this.C7monto_total = C7monto_total;
	}
	public double getC8monto_iva5(){
		return C8monto_iva5;
	}
	public void setC8monto_iva5(double C8monto_iva5){
		this.C8monto_iva5 = C8monto_iva5;
	}
	public double getC9monto_iva10(){
		return C9monto_iva10;
	}
	public void setC9monto_iva10(double C9monto_iva10){
		this.C9monto_iva10 = C9monto_iva10;
	}
	public String getC10monto_letra(){
		return C10monto_letra;
	}
	public void setC10monto_letra(String C10monto_letra){
		this.C10monto_letra = C10monto_letra;
	}
	public String getC11observacion(){
		return C11observacion;
	}
	public void setC11observacion(String C11observacion){
		this.C11observacion = C11observacion;
	}
	public String getC12estado(){
		return C12estado;
	}
	public void setC12estado(String C12estado){
		this.C12estado = C12estado;
	}
	public int getC13fk_idpersona(){
		return C13fk_idpersona;
	}
	public void setC13fk_idpersona(int C13fk_idpersona){
		this.C13fk_idpersona = C13fk_idpersona;
	}
	public int getC14fk_idusuario(){
		return C14fk_idusuario;
	}
	public void setC14fk_idusuario(int C14fk_idusuario){
		this.C14fk_idusuario = C14fk_idusuario;
	}
	public String toString() {
		return "compra(" + ",idcompra=" + C1idcompra + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,fecha_compra=" + C4fecha_compra + " ,nro_factura=" + C5nro_factura + " ,es_factura=" + C6es_factura + " ,monto_total=" + C7monto_total + " ,monto_iva5=" + C8monto_iva5 + " ,monto_iva10=" + C9monto_iva10 + " ,monto_letra=" + C10monto_letra + " ,observacion=" + C11observacion + " ,estado=" + C12estado + " ,fk_idpersona=" + C13fk_idpersona + " ,fk_idusuario=" + C14fk_idusuario + " )";
	}
}
