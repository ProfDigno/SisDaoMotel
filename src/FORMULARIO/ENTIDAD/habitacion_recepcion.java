	package FORMULARIO.ENTIDAD;
public class habitacion_recepcion {

//---------------DECLARAR VARIABLES---------------
private int C1idhabitacion_recepcion;
private String C2fecha_creado;
private String C3creado_por;
private String C4estado;
private String C5fec_libre_inicio;
private String C6fec_libre_fin;
private String C7fec_ocupado_inicio;
private String C8fec_ocupado_fin;
private String C9fec_sucio_inicio;
private String C10fec_sucio_fin;
private String C11fec_limpieza_inicio;
private String C12fec_limpieza_fin;
private String C13fec_mante_inicio;
private String C14fec_mante_fin;
private boolean C15es_libre;
private boolean C16es_ocupado;
private boolean C17es_sucio;
private boolean C18es_limpieza;
private boolean C19es_mante;
private boolean C20es_cancelado;
private boolean C21es_por_hora;
private boolean C22es_por_dormir;
private boolean C23es_boton_actual;
private boolean C24es_pagado;
private boolean C25es_terminado;
private double C26monto_por_hora_minimo;
private double C27monto_por_hora_adicional;
private double C28monto_por_dormir_minimo;
private double C29monto_por_dormir_adicional;
private double C30monto_consumision;
private double C31monto_descuento;
private int C32fk_idhabitacion_dato;
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
	public String getC5fec_libre_inicio(){
		return C5fec_libre_inicio;
	}
	public void setC5fec_libre_inicio(String C5fec_libre_inicio){
		this.C5fec_libre_inicio = C5fec_libre_inicio;
	}
	public String getC6fec_libre_fin(){
		return C6fec_libre_fin;
	}
	public void setC6fec_libre_fin(String C6fec_libre_fin){
		this.C6fec_libre_fin = C6fec_libre_fin;
	}
	public String getC7fec_ocupado_inicio(){
		return C7fec_ocupado_inicio;
	}
	public void setC7fec_ocupado_inicio(String C7fec_ocupado_inicio){
		this.C7fec_ocupado_inicio = C7fec_ocupado_inicio;
	}
	public String getC8fec_ocupado_fin(){
		return C8fec_ocupado_fin;
	}
	public void setC8fec_ocupado_fin(String C8fec_ocupado_fin){
		this.C8fec_ocupado_fin = C8fec_ocupado_fin;
	}
	public String getC9fec_sucio_inicio(){
		return C9fec_sucio_inicio;
	}
	public void setC9fec_sucio_inicio(String C9fec_sucio_inicio){
		this.C9fec_sucio_inicio = C9fec_sucio_inicio;
	}
	public String getC10fec_sucio_fin(){
		return C10fec_sucio_fin;
	}
	public void setC10fec_sucio_fin(String C10fec_sucio_fin){
		this.C10fec_sucio_fin = C10fec_sucio_fin;
	}
	public String getC11fec_limpieza_inicio(){
		return C11fec_limpieza_inicio;
	}
	public void setC11fec_limpieza_inicio(String C11fec_limpieza_inicio){
		this.C11fec_limpieza_inicio = C11fec_limpieza_inicio;
	}
	public String getC12fec_limpieza_fin(){
		return C12fec_limpieza_fin;
	}
	public void setC12fec_limpieza_fin(String C12fec_limpieza_fin){
		this.C12fec_limpieza_fin = C12fec_limpieza_fin;
	}
	public String getC13fec_mante_inicio(){
		return C13fec_mante_inicio;
	}
	public void setC13fec_mante_inicio(String C13fec_mante_inicio){
		this.C13fec_mante_inicio = C13fec_mante_inicio;
	}
	public String getC14fec_mante_fin(){
		return C14fec_mante_fin;
	}
	public void setC14fec_mante_fin(String C14fec_mante_fin){
		this.C14fec_mante_fin = C14fec_mante_fin;
	}
	public boolean getC15es_libre(){
		return C15es_libre;
	}
	public void setC15es_libre(boolean C15es_libre){
		this.C15es_libre = C15es_libre;
	}
	public boolean getC16es_ocupado(){
		return C16es_ocupado;
	}
	public void setC16es_ocupado(boolean C16es_ocupado){
		this.C16es_ocupado = C16es_ocupado;
	}
	public boolean getC17es_sucio(){
		return C17es_sucio;
	}
	public void setC17es_sucio(boolean C17es_sucio){
		this.C17es_sucio = C17es_sucio;
	}
	public boolean getC18es_limpieza(){
		return C18es_limpieza;
	}
	public void setC18es_limpieza(boolean C18es_limpieza){
		this.C18es_limpieza = C18es_limpieza;
	}
	public boolean getC19es_mante(){
		return C19es_mante;
	}
	public void setC19es_mante(boolean C19es_mante){
		this.C19es_mante = C19es_mante;
	}
	public boolean getC20es_cancelado(){
		return C20es_cancelado;
	}
	public void setC20es_cancelado(boolean C20es_cancelado){
		this.C20es_cancelado = C20es_cancelado;
	}
	public boolean getC21es_por_hora(){
		return C21es_por_hora;
	}
	public void setC21es_por_hora(boolean C21es_por_hora){
		this.C21es_por_hora = C21es_por_hora;
	}
	public boolean getC22es_por_dormir(){
		return C22es_por_dormir;
	}
	public void setC22es_por_dormir(boolean C22es_por_dormir){
		this.C22es_por_dormir = C22es_por_dormir;
	}
	public boolean getC23es_boton_actual(){
		return C23es_boton_actual;
	}
	public void setC23es_boton_actual(boolean C23es_boton_actual){
		this.C23es_boton_actual = C23es_boton_actual;
	}
	public boolean getC24es_pagado(){
		return C24es_pagado;
	}
	public void setC24es_pagado(boolean C24es_pagado){
		this.C24es_pagado = C24es_pagado;
	}
	public boolean getC25es_terminado(){
		return C25es_terminado;
	}
	public void setC25es_terminado(boolean C25es_terminado){
		this.C25es_terminado = C25es_terminado;
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
	public int getC32fk_idhabitacion_dato(){
		return C32fk_idhabitacion_dato;
	}
	public void setC32fk_idhabitacion_dato(int C32fk_idhabitacion_dato){
		this.C32fk_idhabitacion_dato = C32fk_idhabitacion_dato;
	}
	public String toString() {
		return "habitacion_recepcion(" + ",idhabitacion_recepcion=" + C1idhabitacion_recepcion + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,estado=" + C4estado + " ,fec_libre_inicio=" + C5fec_libre_inicio + " ,fec_libre_fin=" + C6fec_libre_fin + " ,fec_ocupado_inicio=" + C7fec_ocupado_inicio + " ,fec_ocupado_fin=" + C8fec_ocupado_fin + " ,fec_sucio_inicio=" + C9fec_sucio_inicio + " ,fec_sucio_fin=" + C10fec_sucio_fin + " ,fec_limpieza_inicio=" + C11fec_limpieza_inicio + " ,fec_limpieza_fin=" + C12fec_limpieza_fin + " ,fec_mante_inicio=" + C13fec_mante_inicio + " ,fec_mante_fin=" + C14fec_mante_fin + " ,es_libre=" + C15es_libre + " ,es_ocupado=" + C16es_ocupado + " ,es_sucio=" + C17es_sucio + " ,es_limpieza=" + C18es_limpieza + " ,es_mante=" + C19es_mante + " ,es_cancelado=" + C20es_cancelado + " ,es_por_hora=" + C21es_por_hora + " ,es_por_dormir=" + C22es_por_dormir + " ,es_boton_actual=" + C23es_boton_actual + " ,es_pagado=" + C24es_pagado + " ,es_terminado=" + C25es_terminado + " ,monto_por_hora_minimo=" + C26monto_por_hora_minimo + " ,monto_por_hora_adicional=" + C27monto_por_hora_adicional + " ,monto_por_dormir_minimo=" + C28monto_por_dormir_minimo + " ,monto_por_dormir_adicional=" + C29monto_por_dormir_adicional + " ,monto_consumision=" + C30monto_consumision + " ,monto_descuento=" + C31monto_descuento + " ,fk_idhabitacion_dato=" + C32fk_idhabitacion_dato + " )";
	}
}
