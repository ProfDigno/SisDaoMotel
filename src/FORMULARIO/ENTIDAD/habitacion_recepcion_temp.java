	package FORMULARIO.ENTIDAD;
public class habitacion_recepcion_temp {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_recepcion_temp;
private int C2idhabitacion_recepcion_actual;
private String C3fecha_creado;
private String C4creado_por;
private int C5nro_habitacion;
private String C6descripcion_habitacion;
private String C7estado;
private String C8fec_libre_inicio;
private String C9fec_libre_fin;
private String C10fec_ocupado_inicio;
private String C11fec_ocupado_fin;
private String C12fec_sucio_inicio;
private String C13fec_sucio_fin;
private String C14fec_limpieza_inicio;
private String C15fec_limpieza_fin;
private String C16fec_mante_inicio;
private String C17fec_mante_fin;
private boolean C18es_libre;
private boolean C19es_ocupado;
private boolean C20es_sucio;
private boolean C21es_limpieza;
private boolean C22es_mante;
private boolean C23es_cancelado;
private boolean C24es_por_hora;
private boolean C25es_por_dormir;
private double C26monto_por_hora_minimo;
private double C27monto_por_hora_adicional;
private double C28monto_por_dormir_minimo;
private double C29monto_por_dormir_adicional;
private double C30monto_consumision;
private double C31monto_descuento;
private int C32minuto_minimo;
private int C33minuto_adicional;
private int C34minuto_cancelar;
private String C35hs_dormir_ingreso_inicio;
private String C36hs_dormir_ingreso_final;
private String C37hs_dormir_salida_final;
private boolean C38puerta_cliente;
private boolean C39puerta_limpieza;
private String C40tipo_habitacion;
private double C41monto_adelanto;
private int C42idhabitacion_dato;
private boolean C43es_manual;
private int C44orden;
private boolean C45activo;
private double  C46monto_por_hospedaje_minimo;
private String C47fec_hospedaje_inicio;
private String C48fec_hospedaje_fin;
private boolean C49es_hospedaje;
private int C50minuto_hospedaje;
private String descrip_caja_desocupa;
private static String nom_tabla;
private static String nom_idtabla;
private static boolean hab_tiempo_menu; 
//---------------TABLA-ID---------------
	public habitacion_recepcion_temp() {
		setTb_habitacion_recepcion_temp("habitacion_recepcion_temp");
		setId_idhabitacion_recepcion_temp("idhabitacion_recepcion_temp");
	}

    public double getC46monto_por_hospedaje_minimo() {
        return C46monto_por_hospedaje_minimo;
    }

    public void setC46monto_por_hospedaje_minimo(double C46monto_por_hospedaje_minimo) {
        this.C46monto_por_hospedaje_minimo = C46monto_por_hospedaje_minimo;
    }

    public String getC47fec_hospedaje_inicio() {
        return C47fec_hospedaje_inicio;
    }

    public void setC47fec_hospedaje_inicio(String C47fec_hospedaje_inicio) {
        this.C47fec_hospedaje_inicio = C47fec_hospedaje_inicio;
    }

    public String getC48fec_hospedaje_fin() {
        return C48fec_hospedaje_fin;
    }

    public void setC48fec_hospedaje_fin(String C48fec_hospedaje_fin) {
        this.C48fec_hospedaje_fin = C48fec_hospedaje_fin;
    }

    public boolean getC49es_hospedaje() {
        return C49es_hospedaje;
    }

    public void setC49es_hospedaje(boolean C49es_hospedaje) {
        this.C49es_hospedaje = C49es_hospedaje;
    }

    public int getC50minuto_hospedaje() {
        return C50minuto_hospedaje;
    }

    public void setC50minuto_hospedaje(int C50minuto_hospedaje) {
        this.C50minuto_hospedaje = C50minuto_hospedaje;
    }

    public static boolean isHab_tiempo_menu() {
        return hab_tiempo_menu;
    }

    public static void setHab_tiempo_menu(boolean hab_tiempo_menu) {
        habitacion_recepcion_temp.hab_tiempo_menu = hab_tiempo_menu;
    }

    public String getDescrip_caja_desocupa() {
        return descrip_caja_desocupa;
    }

    public void setDescrip_caja_desocupa(String descrip_caja_desocupa) {
        this.descrip_caja_desocupa = descrip_caja_desocupa;
    }

    public int getC44orden() {
        return C44orden;
    }

    public void setC44orden(int C44orden) {
        this.C44orden = C44orden;
    }

    public boolean getC43es_manual() {
        return C43es_manual;
    }

    public void setC43es_manual(boolean C43es_manual) {
        this.C43es_manual = C43es_manual;
    }

    public boolean getC45activo() {
        return C45activo;
    }

    public void setC45activo(boolean C45activo) {
        this.C45activo = C45activo;
    }
        
	public static String getTb_habitacion_recepcion_temp(){
		return nom_tabla;
	}
	public static void setTb_habitacion_recepcion_temp(String nom_tabla){
		habitacion_recepcion_temp.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_recepcion_temp(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_recepcion_temp(String nom_idtabla){
		habitacion_recepcion_temp.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------
	public int getC1idhabitacion_recepcion_temp(){
		return C1idhabitacion_recepcion_temp;
	}
	public void setC1idhabitacion_recepcion_temp(int C1idhabitacion_recepcion_temp){
		this.C1idhabitacion_recepcion_temp = C1idhabitacion_recepcion_temp;
	}
	public int getC2idhabitacion_recepcion_actual(){
		return C2idhabitacion_recepcion_actual;
	}
	public void setC2idhabitacion_recepcion_actual(int C2idhabitacion_recepcion_actual){
		this.C2idhabitacion_recepcion_actual = C2idhabitacion_recepcion_actual;
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
	public int getC5nro_habitacion(){
		return C5nro_habitacion;
	}
	public void setC5nro_habitacion(int C5nro_habitacion){
		this.C5nro_habitacion = C5nro_habitacion;
	}
	public String getC6descripcion_habitacion(){
		return C6descripcion_habitacion;
	}
	public void setC6descripcion_habitacion(String C6descripcion_habitacion){
		this.C6descripcion_habitacion = C6descripcion_habitacion;
	}
	public String getC7estado(){
		return C7estado;
	}
	public void setC7estado(String C7estado){
		this.C7estado = C7estado;
	}
	public String getC8fec_libre_inicio(){
		return C8fec_libre_inicio;
	}
	public void setC8fec_libre_inicio(String C8fec_libre_inicio){
		this.C8fec_libre_inicio = C8fec_libre_inicio;
	}
	public String getC9fec_libre_fin(){
		return C9fec_libre_fin;
	}
	public void setC9fec_libre_fin(String C9fec_libre_fin){
		this.C9fec_libre_fin = C9fec_libre_fin;
	}
	public String getC10fec_ocupado_inicio(){
		return C10fec_ocupado_inicio;
	}
	public void setC10fec_ocupado_inicio(String C10fec_ocupado_inicio){
		this.C10fec_ocupado_inicio = C10fec_ocupado_inicio;
	}
	public String getC11fec_ocupado_fin(){
		return C11fec_ocupado_fin;
	}
	public void setC11fec_ocupado_fin(String C11fec_ocupado_fin){
		this.C11fec_ocupado_fin = C11fec_ocupado_fin;
	}
	public String getC12fec_sucio_inicio(){
		return C12fec_sucio_inicio;
	}
	public void setC12fec_sucio_inicio(String C12fec_sucio_inicio){
		this.C12fec_sucio_inicio = C12fec_sucio_inicio;
	}
	public String getC13fec_sucio_fin(){
		return C13fec_sucio_fin;
	}
	public void setC13fec_sucio_fin(String C13fec_sucio_fin){
		this.C13fec_sucio_fin = C13fec_sucio_fin;
	}
	public String getC14fec_limpieza_inicio(){
		return C14fec_limpieza_inicio;
	}
	public void setC14fec_limpieza_inicio(String C14fec_limpieza_inicio){
		this.C14fec_limpieza_inicio = C14fec_limpieza_inicio;
	}
	public String getC15fec_limpieza_fin(){
		return C15fec_limpieza_fin;
	}
	public void setC15fec_limpieza_fin(String C15fec_limpieza_fin){
		this.C15fec_limpieza_fin = C15fec_limpieza_fin;
	}
	public String getC16fec_mante_inicio(){
		return C16fec_mante_inicio;
	}
	public void setC16fec_mante_inicio(String C16fec_mante_inicio){
		this.C16fec_mante_inicio = C16fec_mante_inicio;
	}
	public String getC17fec_mante_fin(){
		return C17fec_mante_fin;
	}
	public void setC17fec_mante_fin(String C17fec_mante_fin){
		this.C17fec_mante_fin = C17fec_mante_fin;
	}
	public boolean getC18es_libre(){
		return C18es_libre;
	}
	public void setC18es_libre(boolean C18es_libre){
		this.C18es_libre = C18es_libre;
	}
	public boolean getC19es_ocupado(){
		return C19es_ocupado;
	}
	public void setC19es_ocupado(boolean C19es_ocupado){
		this.C19es_ocupado = C19es_ocupado;
	}
	public boolean getC20es_sucio(){
		return C20es_sucio;
	}
	public void setC20es_sucio(boolean C20es_sucio){
		this.C20es_sucio = C20es_sucio;
	}
	public boolean getC21es_limpieza(){
		return C21es_limpieza;
	}
	public void setC21es_limpieza(boolean C21es_limpieza){
		this.C21es_limpieza = C21es_limpieza;
	}
	public boolean getC22es_mante(){
		return C22es_mante;
	}
	public void setC22es_mante(boolean C22es_mante){
		this.C22es_mante = C22es_mante;
	}
	public boolean getC23es_cancelado(){
		return C23es_cancelado;
	}
	public void setC23es_cancelado(boolean C23es_cancelado){
		this.C23es_cancelado = C23es_cancelado;
	}
	public boolean getC24es_por_hora(){
		return C24es_por_hora;
	}
	public void setC24es_por_hora(boolean C24es_por_hora){
		this.C24es_por_hora = C24es_por_hora;
	}
	public boolean getC25es_por_dormir(){
		return C25es_por_dormir;
	}
	public void setC25es_por_dormir(boolean C25es_por_dormir){
		this.C25es_por_dormir = C25es_por_dormir;
	}
	public double getC26monto_por_hora_minimo(){
		return C26monto_por_hora_minimo;
	}
	public void setC26monto_por_hora_minimo(double C26monto_por_hora_minimo){
		this.C26monto_por_hora_minimo = C26monto_por_hora_minimo;
	}
	public double getC27monto_por_hora_adicional(){
		return C27monto_por_hora_adicional;
	}
	public void setC27monto_por_hora_adicional(double C27monto_por_hora_adicional){
		this.C27monto_por_hora_adicional = C27monto_por_hora_adicional;
	}
	public double getC28monto_por_dormir_minimo(){
		return C28monto_por_dormir_minimo;
	}
	public void setC28monto_por_dormir_minimo(double C28monto_por_dormir_minimo){
		this.C28monto_por_dormir_minimo = C28monto_por_dormir_minimo;
	}
	public double getC29monto_por_dormir_adicional(){
		return C29monto_por_dormir_adicional;
	}
	public void setC29monto_por_dormir_adicional(double C29monto_por_dormir_adicional){
		this.C29monto_por_dormir_adicional = C29monto_por_dormir_adicional;
	}
	public double getC30monto_consumision(){
		return C30monto_consumision;
	}
	public void setC30monto_consumision(double C30monto_consumision){
		this.C30monto_consumision = C30monto_consumision;
	}
	public double getC31monto_descuento(){
		return C31monto_descuento;
	}
	public void setC31monto_descuento(double C31monto_descuento){
		this.C31monto_descuento = C31monto_descuento;
	}
	public int getC32minuto_minimo(){
		return C32minuto_minimo;
	}
	public void setC32minuto_minimo(int C32minuto_minimo){
		this.C32minuto_minimo = C32minuto_minimo;
	}
	public int getC33minuto_adicional(){
		return C33minuto_adicional;
	}
	public void setC33minuto_adicional(int C33minuto_adicional){
		this.C33minuto_adicional = C33minuto_adicional;
	}
	public int getC34minuto_cancelar(){
		return C34minuto_cancelar;
	}
	public void setC34minuto_cancelar(int C34minuto_cancelar){
		this.C34minuto_cancelar = C34minuto_cancelar;
	}
	public String getC35hs_dormir_ingreso_inicio(){
		return C35hs_dormir_ingreso_inicio;
	}
	public void setC35hs_dormir_ingreso_inicio(String C35hs_dormir_ingreso_inicio){
		this.C35hs_dormir_ingreso_inicio = C35hs_dormir_ingreso_inicio;
	}
	public String getC36hs_dormir_ingreso_final(){
		return C36hs_dormir_ingreso_final;
	}
	public void setC36hs_dormir_ingreso_final(String C36hs_dormir_ingreso_final){
		this.C36hs_dormir_ingreso_final = C36hs_dormir_ingreso_final;
	}
	public String getC37hs_dormir_salida_final(){
		return C37hs_dormir_salida_final;
	}
	public void setC37hs_dormir_salida_final(String C37hs_dormir_salida_final){
		this.C37hs_dormir_salida_final = C37hs_dormir_salida_final;
	}

    public boolean getC38puerta_cliente() {
        return C38puerta_cliente;
    }

    public void setC38puerta_cliente(boolean C38puerta_cliente) {
        this.C38puerta_cliente = C38puerta_cliente;
    }

    public boolean getC39puerta_limpieza() {
        return C39puerta_limpieza;
    }

    public void setC39puerta_limpieza(boolean C39puerta_limpieza) {
        this.C39puerta_limpieza = C39puerta_limpieza;
    }

    public String getC40tipo_habitacion() {
        return C40tipo_habitacion;
    }

    public void setC40tipo_habitacion(String C40tipo_habitacion) {
        this.C40tipo_habitacion = C40tipo_habitacion;
    }

    public double getC41monto_adelanto() {
        return C41monto_adelanto;
    }

    public void setC41monto_adelanto(double C41monto_adelanto) {
        this.C41monto_adelanto = C41monto_adelanto;
    }

    public int getC42idhabitacion_dato() {
        return C42idhabitacion_dato;
    }

    public void setC42idhabitacion_dato(int C42idhabitacion_dato) {
        this.C42idhabitacion_dato = C42idhabitacion_dato;
    }
        
	public String toString() {
		return "habitacion_recepcion_temp(" + ",idhabitacion_recepcion_temp=" + C1idhabitacion_recepcion_temp + " ,idhabitacion_recepcion_actual=" + C2idhabitacion_recepcion_actual + " ,fecha_creado=" + C3fecha_creado + " ,creado_por=" + C4creado_por + " ,nro_habitacion=" + C5nro_habitacion + " ,descripcion_habitacion=" + C6descripcion_habitacion + " ,estado=" + C7estado + " ,fec_libre_inicio=" + C8fec_libre_inicio + " ,fec_libre_fin=" + C9fec_libre_fin + " ,fec_ocupado_inicio=" + C10fec_ocupado_inicio + " ,fec_ocupado_fin=" + C11fec_ocupado_fin + " ,fec_sucio_inicio=" + C12fec_sucio_inicio + " ,fec_sucio_fin=" + C13fec_sucio_fin + " ,fec_limpieza_inicio=" + C14fec_limpieza_inicio + " ,fec_limpieza_fin=" + C15fec_limpieza_fin + " ,fec_mante_inicio=" + C16fec_mante_inicio + " ,fec_mante_fin=" + C17fec_mante_fin + " ,es_libre=" + C18es_libre + " ,es_ocupado=" + C19es_ocupado + " ,es_sucio=" + C20es_sucio + " ,es_limpieza=" + C21es_limpieza + " ,es_mante=" + C22es_mante + " ,es_cancelado=" + C23es_cancelado + " ,es_por_hora=" + C24es_por_hora + " ,es_por_dormir=" + C25es_por_dormir + " ,monto_por_hora_minimo=" + C26monto_por_hora_minimo + " ,monto_por_hora_adicional=" + C27monto_por_hora_adicional + " ,monto_por_dormir_minimo=" + C28monto_por_dormir_minimo + " ,monto_por_dormir_adicional=" + C29monto_por_dormir_adicional + " ,monto_consumision=" + C30monto_consumision + " ,monto_descuento=" + C31monto_descuento + " ,minuto_minimo=" + C32minuto_minimo + " ,minuto_adicional=" + C33minuto_adicional + " ,minuto_cancelar=" + C34minuto_cancelar + " ,hs_dormir_ingreso_inicio=" + C35hs_dormir_ingreso_inicio + " ,hs_dormir_ingreso_final=" + C36hs_dormir_ingreso_final + " ,hs_dormir_salida_final=" + C37hs_dormir_salida_final + " )";
	}
}
