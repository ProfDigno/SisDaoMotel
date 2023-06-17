	package FORMULARIO.ENTIDAD;
public class factura {

//---------------DECLARAR VARIABLES---------------
private int C1idfactura;
private String C2fecha_creado;
private String C3creado_por;
private String C4nro_factura;
private String C5fecha_nota;
private String C6estado;
private String C7condicion;
private double C8monto_total;
private double C9monto_iva5;
private double C10monto_iva10;
private String C11monto_letra;
private int C12fk_idtimbrado;
private int C13fk_idpersona;
private int C14fk_idventa;
private int C15numero;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public factura() {
		setTb_factura("factura");
		setId_idfactura("idfactura");
	}
	public static String getTb_factura(){
		return nom_tabla;
	}
	public static void setTb_factura(String nom_tabla){
		factura.nom_tabla = nom_tabla;
	}
	public static String getId_idfactura(){
		return nom_idtabla;
	}
	public static void setId_idfactura(String nom_idtabla){
		factura.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idfactura(){
		return C1idfactura;
	}
	public void setC1idfactura(int C1idfactura){
		this.C1idfactura = C1idfactura;
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
	public String getC4nro_factura(){
		return C4nro_factura;
	}
	public void setC4nro_factura(String C4nro_factura){
		this.C4nro_factura = C4nro_factura;
	}
	public String getC5fecha_nota(){
		return C5fecha_nota;
	}
	public void setC5fecha_nota(String C5fecha_nota){
		this.C5fecha_nota = C5fecha_nota;
	}
	public String getC6estado(){
		return C6estado;
	}
	public void setC6estado(String C6estado){
		this.C6estado = C6estado;
	}
	public String getC7condicion(){
		return C7condicion;
	}
	public void setC7condicion(String C7condicion){
		this.C7condicion = C7condicion;
	}
	public double getC8monto_total(){
		return C8monto_total;
	}
	public void setC8monto_total(double C8monto_total){
		this.C8monto_total = C8monto_total;
	}
	public double getC9monto_iva5(){
		return C9monto_iva5;
	}
	public void setC9monto_iva5(double C9monto_iva5){
		this.C9monto_iva5 = C9monto_iva5;
	}
	public double getC10monto_iva10(){
		return C10monto_iva10;
	}
	public void setC10monto_iva10(double C10monto_iva10){
		this.C10monto_iva10 = C10monto_iva10;
	}
	public String getC11monto_letra(){
		return C11monto_letra;
	}
	public void setC11monto_letra(String C11monto_letra){
		this.C11monto_letra = C11monto_letra;
	}
	public int getC12fk_idtimbrado(){
		return C12fk_idtimbrado;
	}
	public void setC12fk_idtimbrado(int C12fk_idtimbrado){
		this.C12fk_idtimbrado = C12fk_idtimbrado;
	}
	public int getC13fk_idpersona(){
		return C13fk_idpersona;
	}
	public void setC13fk_idpersona(int C13fk_idpersona){
		this.C13fk_idpersona = C13fk_idpersona;
	}
	public int getC14fk_idventa(){
		return C14fk_idventa;
	}
	public void setC14fk_idventa(int C14fk_idventa){
		this.C14fk_idventa = C14fk_idventa;
	}

    public int getC15numero() {
        return C15numero;
    }

    public void setC15numero(int C15numero) {
        this.C15numero = C15numero;
    }
        
	public String toString() {
		return "factura(" + ",idfactura=" + C1idfactura + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nro_factura=" + C4nro_factura + " ,fecha_nota=" + C5fecha_nota + " ,estado=" + C6estado + " ,condicion=" + C7condicion + " ,monto_total=" + C8monto_total + " ,monto_iva5=" + C9monto_iva5 + " ,monto_iva10=" + C10monto_iva10 + " ,monto_letra=" + C11monto_letra + " ,fk_idtimbrado=" + C12fk_idtimbrado + " ,fk_idpersona=" + C13fk_idpersona + " ,fk_idventa=" + C14fk_idventa + " )";
	}
}
