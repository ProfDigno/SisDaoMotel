package FORMULARIO.ENTIDAD;

public class producto {

//---------------DECLARAR VARIABLES---------------
    private int C1idproducto;
    private String C2fecha_creado;
    private String C3creado_por;
    private String C4codigo_barra;
    private String C5nombre;
    private boolean C6controlar_stock;
    private boolean C7es_venta;
    private boolean C8es_compra;
    private boolean C9es_insumo;
    private boolean C10es_patrimonio;
    private double C11precio_venta;
    private double C12precio_compra;
    private double C13stock_actual;
    private double C14stock_minimo;
    private double C15stock_maximo;
    private double C16iva;
    private int C17fk_idproducto_categoria;
    private int C18fk_idproducto_unidad;
    private int C19fk_idproducto_marca;
    private double C20precio_interno;
    private String precio_venta_mostrar;
    private String precio_compra_mostrar;
    private static String nom_tabla;
    private static String nom_idtabla;
//---------------TABLA-ID---------------

    public producto() {
        setTb_producto("producto");
        setId_idproducto("idproducto");
    }

    public double getC20precio_interno() {
        return C20precio_interno;
    }

    public void setC20precio_interno(double C20precio_interno) {
        this.C20precio_interno = C20precio_interno;
    }

    public static String getTb_producto() {
        return nom_tabla;
    }

    public static void setTb_producto(String nom_tabla) {
        producto.nom_tabla = nom_tabla;
    }

    public static String getId_idproducto() {
        return nom_idtabla;
    }

    public static void setId_idproducto(String nom_idtabla) {
        producto.nom_idtabla = nom_idtabla;
    }
//---------------GET-SET-CAMPOS---------------

    public int getC1idproducto() {
        return C1idproducto;
    }

    public void setC1idproducto(int C1idproducto) {
        this.C1idproducto = C1idproducto;
    }

    public String getC2fecha_creado() {
        return C2fecha_creado;
    }

    public void setC2fecha_creado(String C2fecha_creado) {
        this.C2fecha_creado = C2fecha_creado;
    }

    public String getC3creado_por() {
        return C3creado_por;
    }

    public void setC3creado_por(String C3creado_por) {
        this.C3creado_por = C3creado_por;
    }

    public String getC4codigo_barra() {
        return C4codigo_barra;
    }

    public void setC4codigo_barra(String C4codigo_barra) {
        this.C4codigo_barra = C4codigo_barra;
    }

    public String getC5nombre() {
        return C5nombre;
    }

    public void setC5nombre(String C5nombre) {
        this.C5nombre = C5nombre;
    }

    public boolean getC6controlar_stock() {
        return C6controlar_stock;
    }

    public void setC6controlar_stock(boolean C6controlar_stock) {
        this.C6controlar_stock = C6controlar_stock;
    }

    public boolean getC7es_venta() {
        return C7es_venta;
    }

    public void setC7es_venta(boolean C7es_venta) {
        this.C7es_venta = C7es_venta;
    }

    public boolean getC8es_compra() {
        return C8es_compra;
    }

    public void setC8es_compra(boolean C8es_compra) {
        this.C8es_compra = C8es_compra;
    }

    public boolean getC9es_insumo() {
        return C9es_insumo;
    }

    public void setC9es_insumo(boolean C9es_insumo) {
        this.C9es_insumo = C9es_insumo;
    }

    public boolean getC10es_patrimonio() {
        return C10es_patrimonio;
    }

    public void setC10es_patrimonio(boolean C10es_patrimonio) {
        this.C10es_patrimonio = C10es_patrimonio;
    }

    public double getC11precio_venta() {
        return C11precio_venta;
    }

    public void setC11precio_venta(double C11precio_venta) {
        this.C11precio_venta = C11precio_venta;
    }

    public double getC12precio_compra() {
        return C12precio_compra;
    }

    public void setC12precio_compra(double C12precio_compra) {
        this.C12precio_compra = C12precio_compra;
    }

    public double getC13stock_actual() {
        return C13stock_actual;
    }

    public void setC13stock_actual(double C13stock_actual) {
        this.C13stock_actual = C13stock_actual;
    }

    public double getC14stock_minimo() {
        return C14stock_minimo;
    }

    public void setC14stock_minimo(double C14stock_minimo) {
        this.C14stock_minimo = C14stock_minimo;
    }

    public double getC15stock_maximo() {
        return C15stock_maximo;
    }

    public void setC15stock_maximo(double C15stock_maximo) {
        this.C15stock_maximo = C15stock_maximo;
    }

    public double getC16iva() {
        return C16iva;
    }

    public void setC16iva(double C16iva) {
        this.C16iva = C16iva;
    }

    public int getC17fk_idproducto_categoria() {
        return C17fk_idproducto_categoria;
    }

    public void setC17fk_idproducto_categoria(int C17fk_idproducto_categoria) {
        this.C17fk_idproducto_categoria = C17fk_idproducto_categoria;
    }

    public int getC18fk_idproducto_unidad() {
        return C18fk_idproducto_unidad;
    }

    public void setC18fk_idproducto_unidad(int C18fk_idproducto_unidad) {
        this.C18fk_idproducto_unidad = C18fk_idproducto_unidad;
    }

    public int getC19fk_idproducto_marca() {
        return C19fk_idproducto_marca;
    }

    public void setC19fk_idproducto_marca(int C19fk_idproducto_marca) {
        this.C19fk_idproducto_marca = C19fk_idproducto_marca;
    }

    public  String getPrecio_venta_mostrar() {
        return precio_venta_mostrar;
    }

    public void setPrecio_venta_mostrar(String precio_venta_mostrar) {
        this.precio_venta_mostrar = precio_venta_mostrar;
    }

    public String getPrecio_compra_mostrar() {
        return precio_compra_mostrar;
    }

    public void setPrecio_compra_mostrar(String precio_compra_mostrar) {
        this.precio_compra_mostrar = precio_compra_mostrar;
    }

    public String toString() {
        return "producto(" + ",idproducto=" + C1idproducto + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,codigo_barra=" + C4codigo_barra + " ,nombre=" + C5nombre + " ,controlar_stock=" + C6controlar_stock + " ,es_venta=" + C7es_venta + " ,es_compra=" + C8es_compra + " ,es_insumo=" + C9es_insumo + " ,es_patrimonio=" + C10es_patrimonio + " ,precio_venta=" + C11precio_venta + " ,precio_compra=" + C12precio_compra + " ,stock_actual=" + C13stock_actual + " ,stock_minimo=" + C14stock_minimo + " ,stock_maximo=" + C15stock_maximo + " ,iva=" + C16iva + " ,fk_idproducto_categoria=" + C17fk_idproducto_categoria + " ,fk_idproducto_unidad=" + C18fk_idproducto_unidad + " ,fk_idproducto_marca=" + C19fk_idproducto_marca + " )";
    }
}
