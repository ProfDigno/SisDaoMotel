	package FORMULARIO.ENTIDAD;
public class habitacion_recepcion {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_recepcion;
private String C2fecha_creado;
private String C3creado_por;
private String C4estado;
private int C5nro_habitacion;
private String C6fec_libre_inicio;
private String C7fec_libre_fin;
private String C8fec_ocupado_inicio;
private String C9fec_ocupado_fin;
private String C10fec_sucio_inicio;
private String C11fec_sucio_fin;
private String C12fec_limpieza_inicio;
private String C13fec_limpieza_fin;
private String C14fec_mante_inicio;
private String C15fec_mante_fin;
private boolean C16es_libre;
private boolean C17es_ocupado;
private boolean C18es_sucio;
private boolean C19es_limpieza;
private boolean C20es_mante;
private boolean C21es_cancelado;
private boolean C22es_por_hora;
private boolean C23es_por_dormir;
private boolean C24es_boton_actual;
private boolean C25es_pagado;
private boolean C26es_terminado;
private double C27monto_por_hora_minimo;
private double C28monto_por_hora_adicional;
private double C29monto_por_dormir_minimo;
private double C30monto_por_dormir_adicional;
private double C31monto_consumo;
private double C32monto_descuento;
private double C33monto_adelanto;
private double C34cant_adicional;
private int C35fk_idhabitacion_dato;
private double  C36monto_por_hospedaje_minimo;
private String C37fec_hospedaje_inicio;
private String C38fec_hospedaje_fin;
private boolean C39es_hospedaje;
private static String nom_tabla;
private static String nom_idtabla;
//---------------TABLA-ID---------------
	public habitacion_recepcion() {
		setTb_habitacion_recepcion("habitacion_recepcion");
		setId_idhabitacion_recepcion("idhabitacion_recepcion");
	}
	public static String getTb_habitacion_recepcion(){
		return nom_tabla;
	}
	public static void setTb_habitacion_recepcion(String nom_tabla){
		habitacion_recepcion.nom_tabla = nom_tabla;
	}
	public static String getId_idhabitacion_recepcion(){
		return nom_idtabla;
	}
	public static void setId_idhabitacion_recepcion(String nom_idtabla){
		habitacion_recepcion.nom_idtabla = nom_idtabla;
	}
//---------------GET-SET-CAMPOS---------------

    public boolean getC39es_hospedaje() {
        return C39es_hospedaje;
    }

    public void setC39es_hospedaje(boolean C39es_hospedaje) {
        this.C39es_hospedaje = C39es_hospedaje;
    }

    public double getC36monto_por_hospedaje_minimo() {
        return C36monto_por_hospedaje_minimo;
    }

    public void setC36monto_por_hospedaje_minimo(double C36monto_por_hospedaje_minimo) {
        this.C36monto_por_hospedaje_minimo = C36monto_por_hospedaje_minimo;
    }

    public String getC37fec_hospedaje_inicio() {
        return C37fec_hospedaje_inicio;
    }

    public void setC37fec_hospedaje_inicio(String C37fec_hospedaje_inicio) {
        this.C37fec_hospedaje_inicio = C37fec_hospedaje_inicio;
    }

    public String getC38fec_hospedaje_fin() {
        return C38fec_hospedaje_fin;
    }

    public void setC38fec_hospedaje_fin(String C38fec_hospedaje_fin) {
        this.C38fec_hospedaje_fin = C38fec_hospedaje_fin;
    }
        
	public int getC1idhabitacion_recepcion(){
		return C1idhabitacion_recepcion;
	}
	public void setC1idhabitacion_recepcion(int C1idhabitacion_recepcion){
		this.C1idhabitacion_recepcion = C1idhabitacion_recepcion;
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
	public String getC4estado(){
		return C4estado;
	}
	public void setC4estado(String C4estado){
		this.C4estado = C4estado;
	}
	public int getC5nro_habitacion(){
		return C5nro_habitacion;
	}
	public void setC5nro_habitacion(int C5nro_habitacion){
		this.C5nro_habitacion = C5nro_habitacion;
	}
	public String getC6fec_libre_inicio(){
		return C6fec_libre_inicio;
	}
	public void setC6fec_libre_inicio(String C6fec_libre_inicio){
		this.C6fec_libre_inicio = C6fec_libre_inicio;
	}
	public String getC7fec_libre_fin(){
		return C7fec_libre_fin;
	}
	public void setC7fec_libre_fin(String C7fec_libre_fin){
		this.C7fec_libre_fin = C7fec_libre_fin;
	}
	public String getC8fec_ocupado_inicio(){
		return C8fec_ocupado_inicio;
	}
	public void setC8fec_ocupado_inicio(String C8fec_ocupado_inicio){
		this.C8fec_ocupado_inicio = C8fec_ocupado_inicio;
	}
	public String getC9fec_ocupado_fin(){
		return C9fec_ocupado_fin;
	}
	public void setC9fec_ocupado_fin(String C9fec_ocupado_fin){
		this.C9fec_ocupado_fin = C9fec_ocupado_fin;
	}
	public String getC10fec_sucio_inicio(){
		return C10fec_sucio_inicio;
	}
	public void setC10fec_sucio_inicio(String C10fec_sucio_inicio){
		this.C10fec_sucio_inicio = C10fec_sucio_inicio;
	}
	public String getC11fec_sucio_fin(){
		return C11fec_sucio_fin;
	}
	public void setC11fec_sucio_fin(String C11fec_sucio_fin){
		this.C11fec_sucio_fin = C11fec_sucio_fin;
	}
	public String getC12fec_limpieza_inicio(){
		return C12fec_limpieza_inicio;
	}
	public void setC12fec_limpieza_inicio(String C12fec_limpieza_inicio){
		this.C12fec_limpieza_inicio = C12fec_limpieza_inicio;
	}
	public String getC13fec_limpieza_fin(){
		return C13fec_limpieza_fin;
	}
	public void setC13fec_limpieza_fin(String C13fec_limpieza_fin){
		this.C13fec_limpieza_fin = C13fec_limpieza_fin;
	}
	public String getC14fec_mante_inicio(){
		return C14fec_mante_inicio;
	}
	public void setC14fec_mante_inicio(String C14fec_mante_inicio){
		this.C14fec_mante_inicio = C14fec_mante_inicio;
	}
	public String getC15fec_mante_fin(){
		return C15fec_mante_fin;
	}
	public void setC15fec_mante_fin(String C15fec_mante_fin){
		this.C15fec_mante_fin = C15fec_mante_fin;
	}
	public boolean getC16es_libre(){
		return C16es_libre;
	}
	public void setC16es_libre(boolean C16es_libre){
		this.C16es_libre = C16es_libre;
	}
	public boolean getC17es_ocupado(){
		return C17es_ocupado;
	}
	public void setC17es_ocupado(boolean C17es_ocupado){
		this.C17es_ocupado = C17es_ocupado;
	}
	public boolean getC18es_sucio(){
		return C18es_sucio;
	}
	public void setC18es_sucio(boolean C18es_sucio){
		this.C18es_sucio = C18es_sucio;
	}
	public boolean getC19es_limpieza(){
		return C19es_limpieza;
	}
	public void setC19es_limpieza(boolean C19es_limpieza){
		this.C19es_limpieza = C19es_limpieza;
	}
	public boolean getC20es_mante(){
		return C20es_mante;
	}
	public void setC20es_mante(boolean C20es_mante){
		this.C20es_mante = C20es_mante;
	}
	public boolean getC21es_cancelado(){
		return C21es_cancelado;
	}
	public void setC21es_cancelado(boolean C21es_cancelado){
		this.C21es_cancelado = C21es_cancelado;
	}
	public boolean getC22es_por_hora(){
		return C22es_por_hora;
	}
	public void setC22es_por_hora(boolean C22es_por_hora){
		this.C22es_por_hora = C22es_por_hora;
	}
	public boolean getC23es_por_dormir(){
		return C23es_por_dormir;
	}
	public void setC23es_por_dormir(boolean C23es_por_dormir){
		this.C23es_por_dormir = C23es_por_dormir;
	}
	public boolean getC24es_boton_actual(){
		return C24es_boton_actual;
	}
	public void setC24es_boton_actual(boolean C24es_boton_actual){
		this.C24es_boton_actual = C24es_boton_actual;
	}
	public boolean getC25es_pagado(){
		return C25es_pagado;
	}
	public void setC25es_pagado(boolean C25es_pagado){
		this.C25es_pagado = C25es_pagado;
	}
	public boolean getC26es_terminado(){
		return C26es_terminado;
	}
	public void setC26es_terminado(boolean C26es_terminado){
		this.C26es_terminado = C26es_terminado;
	}
	public double getC27monto_por_hora_minimo(){
		return C27monto_por_hora_minimo;
	}
	public void setC27monto_por_hora_minimo(double C27monto_por_hora_minimo){
		this.C27monto_por_hora_minimo = C27monto_por_hora_minimo;
	}
	public double getC28monto_por_hora_adicional(){
		return C28monto_por_hora_adicional;
	}
	public void setC28monto_por_hora_adicional(double C28monto_por_hora_adicional){
		this.C28monto_por_hora_adicional = C28monto_por_hora_adicional;
	}
	public double getC29monto_por_dormir_minimo(){
		return C29monto_por_dormir_minimo;
	}
	public void setC29monto_por_dormir_minimo(double C29monto_por_dormir_minimo){
		this.C29monto_por_dormir_minimo = C29monto_por_dormir_minimo;
	}
	public double getC30monto_por_dormir_adicional(){
		return C30monto_por_dormir_adicional;
	}
	public void setC30monto_por_dormir_adicional(double C30monto_por_dormir_adicional){
		this.C30monto_por_dormir_adicional = C30monto_por_dormir_adicional;
	}
	public double getC31monto_consumo(){
		return C31monto_consumo;
	}
	public void setC31monto_consumo(double C31monto_consumo){
		this.C31monto_consumo = C31monto_consumo;
	}
	public double getC32monto_descuento(){
		return C32monto_descuento;
	}
	public void setC32monto_descuento(double C32monto_descuento){
		this.C32monto_descuento = C32monto_descuento;
	}
	public double getC33monto_adelanto(){
		return C33monto_adelanto;
	}
	public void setC33monto_adelanto(double C33monto_adelanto){
		this.C33monto_adelanto = C33monto_adelanto;
	}
	public double getC34cant_adicional(){
		return C34cant_adicional;
	}
	public void setC34cant_adicional(double C34cant_adicional){
		this.C34cant_adicional = C34cant_adicional;
	}
	public int getC35fk_idhabitacion_dato(){
		return C35fk_idhabitacion_dato;
	}
	public void setC35fk_idhabitacion_dato(int C35fk_idhabitacion_dato){
		this.C35fk_idhabitacion_dato = C35fk_idhabitacion_dato;
	}
	public String toString() {
		return "habitacion_recepcion(" + ",idhabitacion_recepcion=" + C1idhabitacion_recepcion + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,estado=" + C4estado + " ,nro_habitacion=" + C5nro_habitacion + " ,fec_libre_inicio=" + C6fec_libre_inicio + " ,fec_libre_fin=" + C7fec_libre_fin + " ,fec_ocupado_inicio=" + C8fec_ocupado_inicio + " ,fec_ocupado_fin=" + C9fec_ocupado_fin + " ,fec_sucio_inicio=" + C10fec_sucio_inicio + " ,fec_sucio_fin=" + C11fec_sucio_fin + " ,fec_limpieza_inicio=" + C12fec_limpieza_inicio + " ,fec_limpieza_fin=" + C13fec_limpieza_fin + " ,fec_mante_inicio=" + C14fec_mante_inicio + " ,fec_mante_fin=" + C15fec_mante_fin + " ,es_libre=" + C16es_libre + " ,es_ocupado=" + C17es_ocupado + " ,es_sucio=" + C18es_sucio + " ,es_limpieza=" + C19es_limpieza + " ,es_mante=" + C20es_mante + " ,es_cancelado=" + C21es_cancelado + " ,es_por_hora=" + C22es_por_hora + " ,es_por_dormir=" + C23es_por_dormir + " ,es_boton_actual=" + C24es_boton_actual + " ,es_pagado=" + C25es_pagado + " ,es_terminado=" + C26es_terminado + " ,monto_por_hora_minimo=" + C27monto_por_hora_minimo + " ,monto_por_hora_adicional=" + C28monto_por_hora_adicional + " ,monto_por_dormir_minimo=" + C29monto_por_dormir_minimo + " ,monto_por_dormir_adicional=" + C30monto_por_dormir_adicional + " ,monto_consumo=" + C31monto_consumo + " ,monto_descuento=" + C32monto_descuento + " ,monto_adelanto=" + C33monto_adelanto + " ,cant_adicional=" + C34cant_adicional + " ,fk_idhabitacion_dato=" + C35fk_idhabitacion_dato + " )";
	}
}
