	package FORMULARIO.ENTIDAD;
public class venta_item {

//---------------DECLARAR VARIABLES---------------
private int C1idventa_item;
private String C2fecha_creado;
private String C3creado_por;
private String C4tipo_item;
private String C5descripcion;
private double C6cantidad;
private double C7precio_venta;
private double C8precio_compra;
private int C9fk_idventa;
private int C10fk_idproducto;
private static String nom_tabla;
private static String nom_idtabla;
private static String tipo_temporal;
//private static String tipo_cargado;
private static String tipo_terminado;
//---------------TABLA-ID---------------
	public venta_item() {
		setTb_venta_item("venta_item");
		setId_idventa_item("idventa_item");
                setTipo_temporal("TEMP");
//                setTipo_cargado("CARGADO");
                setTipo_terminado("TERMINADO");
	}
	public static String getTb_venta_item(){
		return nom_tabla;
	}
	public static void setTb_venta_item(String nom_tabla){
		venta_item.nom_tabla = nom_tabla;
	}
	public static String getId_idventa_item(){
		return nom_idtabla;
	}
	public static void setId_idventa_item(String nom_idtabla){
		venta_item.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idventa_item(){
		return C1idventa_item;
	}
	public void setC1idventa_item(int C1idventa_item){
		this.C1idventa_item = C1idventa_item;
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
	public String getC4tipo_item(){
		return C4tipo_item;
	}
	public void setC4tipo_item(String C4tipo_item){
		this.C4tipo_item = C4tipo_item;
	}
	public String getC5descripcion(){
		return C5descripcion;
	}
	public void setC5descripcion(String C5descripcion){
		this.C5descripcion = C5descripcion;
	}
	public double getC6cantidad(){
		return C6cantidad;
	}
	public void setC6cantidad(double C6cantidad){
		this.C6cantidad = C6cantidad;
	}
	public double getC7precio_venta(){
		return C7precio_venta;
	}
	public void setC7precio_venta(double C7precio_venta){
		this.C7precio_venta = C7precio_venta;
	}
	public double getC8precio_compra(){
		return C8precio_compra;
	}
	public void setC8precio_compra(double C8precio_compra){
		this.C8precio_compra = C8precio_compra;
	}
	public int getC9fk_idventa(){
		return C9fk_idventa;
	}
	public void setC9fk_idventa(int C9fk_idventa){
		this.C9fk_idventa = C9fk_idventa;
	}
	public int getC10fk_idproducto(){
		return C10fk_idproducto;
	}
	public void setC10fk_idproducto(int C10fk_idproducto){
		this.C10fk_idproducto = C10fk_idproducto;
	}

    public static String getTipo_temporal() {
        return tipo_temporal;
    }

    public static void setTipo_temporal(String tipo_tempotal) {
        venta_item.tipo_temporal = tipo_tempotal;
    }

//    public static String getTipo_cargado() {
//        return tipo_cargado;
//    }
//
//    public static void setTipo_cargado(String tipo_cargado) {
//        venta_item.tipo_cargado = tipo_cargado;
//    }

    public static String getTipo_terminado() {
        return tipo_terminado;
    }

    public static void setTipo_terminado(String tipo_terminado) {
        venta_item.tipo_terminado = tipo_terminado;
    }
        
	public String toString() {
		return "venta_item(" + ",idventa_item=" + C1idventa_item + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,tipo_item=" + C4tipo_item + " ,descripcion=" + C5descripcion + " ,cantidad=" + C6cantidad + " ,precio_venta=" + C7precio_venta + " ,precio_compra=" + C8precio_compra + " ,fk_idventa=" + C9fk_idventa + " ,fk_idproducto=" + C10fk_idproducto + " )";
	}
}
