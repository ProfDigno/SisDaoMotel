/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import java.awt.Color;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import static FORMULARIO.VISTA.FrmProd_dato.txtprod_categoria;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmHab_crear extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec=new EvenFecha();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
    private EvenCombobox evecmb=new EvenCombobox();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario();
    private habitacion_dato ENThd=new habitacion_dato();
    private DAO_habitacion_dato DAOhd=new DAO_habitacion_dato();
    private BO_habitacion_dato BOhd=new BO_habitacion_dato();
    private habitacion_recepcion_temp ENThrt=new habitacion_recepcion_temp();
    private habitacion_costo ENThc=new habitacion_costo();
    private DAO_habitacion_costo DAOhc=new DAO_habitacion_costo();
    private habitacion_item_sensor_gpio ENTigpio=new habitacion_item_sensor_gpio();
    private DAO_habitacion_item_sensor_gpio DAOigpio=new DAO_habitacion_item_sensor_gpio();
    private BO_habitacion_item_sensor_gpio BOigpio=new BO_habitacion_item_sensor_gpio();
    private habitacion_item_sensor_pino ENTpino=new habitacion_item_sensor_pino();
    private DAO_habitacion_item_sensor_pino DAOpino=new DAO_habitacion_item_sensor_pino();
    private BO_habitacion_item_sensor_pino BOipino=new BO_habitacion_item_sensor_pino();
    private producto_habitacion_insumo ENTphi=new producto_habitacion_insumo();
    private DAO_producto_habitacion_insumo DAOphi=new DAO_producto_habitacion_insumo();
    private BO_producto_habitacion_insumo BOphi=new BO_producto_habitacion_insumo();
    private producto_habitacion_frigobar ENTphf=new producto_habitacion_frigobar();
    private DAO_producto_habitacion_frigobar DAOphf=new DAO_producto_habitacion_frigobar();
    private BO_producto_habitacion_frigobar BOphf=new BO_producto_habitacion_frigobar();
    private producto_habitacion_patrimonio ENTphp=new producto_habitacion_patrimonio();
    private DAO_producto_habitacion_patrimonio DAOphp=new DAO_producto_habitacion_patrimonio();
    private BO_producto_habitacion_patrimonio BOphp=new BO_producto_habitacion_patrimonio();
    private habitacion_estado_gpio_temp ENThegt=new habitacion_estado_gpio_temp();
    private DAO_habitacion_estado_gpio_temp DAOhegt=new DAO_habitacion_estado_gpio_temp();
    private BO_habitacion_estado_gpio_temp BOhegt=new BO_habitacion_estado_gpio_temp();
    private String nombreTabla_pri="CREAR HABITACION"; 
    private String nombreTabla_sec="FILTRO"; 
    private String creado_por="digno";
    private String tipo_habitacion;
    String costo_id="idhabitacion_costo";
    String costo_nombre="nivel_lujo";
    String costo_tabla="habitacion_costo";
    String costo_where="where activo=true";
    String mini_pc_id="idhabitacion_mini_pc";
    String mini_pc_nombre="placa_nombre";
    String mini_pc_tabla="habitacion_mini_pc";
    String mini_pc_where="";
    String sensor_id="idhabitacion_sensor";
    String sensor_nombre="nombre";
    String sensor_tabla="habitacion_sensor";
    String sensor_where="where activo=true";
    String ardu_id="idhabitacion_arduino";
    String ardu_nombre="nombre";
    String ardu_tabla="habitacion_arduino";
    String ardu_where="";
    private String estado_actual="LIBRE";
    private int fk_idhabitacion_costo=0;
    private int fk_idhabitacion_mini_pc=0;
    private int fk_idhabitacion_sensor_gpio;
    private int fk_idhabitacion_dato;
    private int fk_idhabitacion_arduino;
    private int fk_idhabitacion_sensor_pino;
    private String estado_libre="LIBRE";
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this); 
        creado_por = ENTusu.getGlobal_nombre();
        carcar_combobox();
        reestableser_hab_dato();
        DAOhd.actualizar_tabla_habitacion_dato(conn, tbltabla_pri);
    }
    private void carcar_combobox(){
        evecmb.cargarCombobox(conn, cmbcosto, costo_id, costo_nombre, costo_tabla, costo_where);
        evecmb.cargarCombobox(conn, cmbmini_pc, mini_pc_id,mini_pc_nombre, mini_pc_tabla,mini_pc_where);
        evecmb.cargarCombobox(conn, cmbsensor_gpio, sensor_id,sensor_nombre, sensor_tabla,sensor_where);
        evecmb.cargarCombobox(conn, cmbsensor_pino, sensor_id,sensor_nombre, sensor_tabla,sensor_where);
        evecmb.cargarCombobox(conn, cmbarduino, ardu_id,ardu_nombre, ardu_tabla,ardu_where);
    }
    private void cargar_costos(int idhabitacion_costo){
        if(idhabitacion_costo>0){
        evecmb.setSeleccionarCombobox(conn, cmbcosto, costo_id, costo_nombre, costo_tabla,idhabitacion_costo);
        DAOhc.cargar_habitacion_costo(conn, ENThc, idhabitacion_costo);
        jFmonto_hora_minimo.setValue(ENThc.getC7monto_por_hora_minimo());
        jFmonto_hora_adicional.setValue(ENThc.getC8monto_por_hora_adicional());
        jFmonto_dormir_minimo.setValue(ENThc.getC9monto_por_dormir_minimo());
        jFmonto_dormir_adicional.setValue(ENThc.getC10monto_por_dormir_adicional());
        txtminumo_minimo.setText(String.valueOf(ENThc.getC11minuto_minimo()));
        txtminuto_adicional.setText(String.valueOf(ENThc.getC12minuto_adicional()));
        txtminuto_cancelar.setText(String.valueOf(ENThc.getC13minuto_cancelar()));
        jFdormir_ingreso_inicio.setText(ENThc.getC14hs_dormir_ingreso_inicio());
        jFdormir_ingreso_final.setText(ENThc.getC15hs_dormir_ingreso_final());
        jFdormir_salida_final.setText(ENThc.getC16hs_dormir_salida_final());
        }
    }
    private void color_tipo_boton(int tipo,String tipohab){
        btntipo_estandar.setBackground(Color.white);
        btntipo_vip.setBackground(Color.white);
        btntipo_lujo.setBackground(Color.white);
        btntipo_penthause.setBackground(Color.white);
        tipo_habitacion="sin-tipo";
        if(tipo==1 || tipohab.equals(eveest.getTipo_hab_estandar())){
            btntipo_estandar.setBackground(Color.yellow);
            tipo_habitacion=eveest.getTipo_hab_estandar();
        }
        if(tipo==2|| tipohab.equals(eveest.getTipo_hab_vip())){
            btntipo_vip.setBackground(Color.yellow);
            tipo_habitacion=eveest.getTipo_hab_vip();
        }
        if(tipo==3|| tipohab.equals(eveest.getTipo_hab_luxury())){
            btntipo_lujo.setBackground(Color.yellow);
            tipo_habitacion=eveest.getTipo_hab_luxury();
        }
        if(tipo==4|| tipohab.equals(eveest.getTipo_hab_penthouse())){
            btntipo_penthause.setBackground(Color.yellow);
            tipo_habitacion=eveest.getTipo_hab_penthouse();
        }
        txttipo_habitacion.setText(tipo_habitacion);
    }
    private void titulo_formulario(String fecha_creado,String creado_por){
        this.setTitle(nombreTabla_pri+" / fecha creado: "+fecha_creado+" / Creado Por: "+creado_por);
    }
    private boolean validar_guardar_hab_dato() {
        if (evejtf.getBoo_JTextField_vacio(txtubicacion, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtadescripcion, "DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbcosto, "DEBE SELECCIONAR UN COSTO")){
            return false;
        }
        return true;
    }
    private void cargar_dato_hab_dato(){
        ENThd.setC3creado_por(creado_por);
        ENThd.setC4nro_habitacion((int)(jSnro_habitacion.getValue()));
        ENThd.setC5tipo_habitacion(tipo_habitacion);
        ENThd.setC6estado_actual(estado_actual);
        ENThd.setC7descripcion(txtadescripcion.getText());
        ENThd.setC8ubicacion(txtubicacion.getText());
        ENThd.setC9activo(jCactivo.isSelected());
        ENThd.setC10con_frigobar(jCconfrigobar.isSelected());
        ENThd.setC11fk_idhabitacion_costo(fk_idhabitacion_costo);
    }
    private void cargar_dato_hab_recep_temp(){
        ENThrt.setC2idhabitacion_recepcion_actual(0);
        ENThrt.setC4creado_por(creado_por);
        ENThrt.setC5nro_habitacion((int)(jSnro_habitacion.getValue()));
        ENThrt.setC6descripcion_habitacion(txtadescripcion.getText());
        ENThrt.setC7estado(estado_libre);
        ENThrt.setC18es_libre(false);
        ENThrt.setC19es_ocupado(false);
        ENThrt.setC20es_sucio(false);
        ENThrt.setC21es_limpieza(false);
        ENThrt.setC22es_mante(false);
        ENThrt.setC23es_cancelado(false);
        ENThrt.setC24es_por_hora(false);
        ENThrt.setC25es_por_dormir(false);
        DAOhc.cargar_habitacion_costo(conn, ENThc, fk_idhabitacion_costo);
        ENThrt.setC26monto_por_hora_minimo(ENThc.getC7monto_por_hora_minimo());
        ENThrt.setC27monto_por_hora_adicional(ENThc.getC8monto_por_hora_adicional());
        ENThrt.setC28monto_por_dormir_minimo(ENThc.getC9monto_por_dormir_minimo());
        ENThrt.setC29monto_por_dormir_adicional(ENThc.getC10monto_por_dormir_adicional());
        ENThrt.setC30monto_consumision(0);
        ENThrt.setC31monto_descuento(0);
        ENThrt.setC32minuto_minimo(ENThc.getC11minuto_minimo());
        ENThrt.setC33minuto_adicional(ENThc.getC12minuto_adicional());
        ENThrt.setC34minuto_cancelar(ENThc.getC13minuto_cancelar());
        ENThrt.setC35hs_dormir_ingreso_inicio(ENThc.getC14hs_dormir_ingreso_inicio());
        ENThrt.setC36hs_dormir_ingreso_final(ENThc.getC15hs_dormir_ingreso_final());
        ENThrt.setC37hs_dormir_salida_final(ENThc.getC16hs_dormir_salida_final());
        ENThrt.setC38puerta_cliente(true);
        ENThrt.setC39puerta_limpieza(true);
        ENThrt.setC40tipo_habitacion(tipo_habitacion);
        ENThrt.setC41monto_adelanto(0);
        ENThrt.setC42idhabitacion_dato(fk_idhabitacion_dato);
    }
    private void boton_guardar_hab_dato() {
        if (validar_guardar_hab_dato()) {
            cargar_dato_hab_dato();
            cargar_dato_hab_recep_temp();
            BOhd.insertar_habitacion_dato(ENThd, ENThrt);
            reestableser_hab_dato();
        }
    }

    private void boton_editar_hab_dato() {
        if (validar_guardar_hab_dato()) {
            ENThd.setC1idhabitacion_dato(Integer.parseInt(txtid.getText()));
            cargar_dato_hab_dato();
            cargar_dato_hab_recep_temp();
            BOhd.update_habitacion_dato(ENThd, ENThrt);
            DAOhd.actualizar_tabla_habitacion_dato(conn, tbltabla_pri);
        }
    }

    private void seleccionar_tabla_hab_dato() {
        int id = eveJtab.getInt_select_id(tbltabla_pri);
        DAOhd.cargar_habitacion_dato(conn, ENThd, id);
        fk_idhabitacion_dato=ENThd.getC1idhabitacion_dato();
        txtid.setText(String.valueOf(ENThd.getC1idhabitacion_dato()));
        jSnro_habitacion.setValue(ENThd.getC4nro_habitacion());
        txttipo_habitacion.setText(ENThd.getC5tipo_habitacion());
        estado_actual=ENThd.getC6estado_actual();
        txtadescripcion.setText(ENThd.getC7descripcion());
        txtubicacion.setText(ENThd.getC8ubicacion());
        jCactivo.setSelected(ENThd.getC9activo());
        jCconfrigobar.setSelected(ENThd.getC10con_frigobar());
        fk_idhabitacion_costo=ENThd.getC11fk_idhabitacion_costo();
        cargar_costos(fk_idhabitacion_costo);
        titulo_formulario(ENThd.getC2fecha_creado(), ENThd.getC3creado_por());
        color_tipo_boton(0,ENThd.getC5tipo_habitacion());
        DAOigpio.actualizar_tabla_habitacion_item_sensor_gpio(conn, tblhabitacion_item_sensor_gpio, fk_idhabitacion_dato);
        DAOpino.actualizar_tabla_habitacion_item_sensor_pino(conn, tblhabitacion_item_sensor_pino, fk_idhabitacion_dato);
        DAOphi.actualizar_tabla_producto_habitacion_insumo(conn, tblprod_hab_insumo,fk_idhabitacion_dato);
        suma_prod_hab_insumo();
        DAOphf.actualizar_tabla_producto_habitacion_frigobar(conn, tblprod_hab_frigobar,fk_idhabitacion_dato);
        suma_prod_hab_frigobar();
        DAOphp.actualizar_tabla_producto_habitacion_patrimonio(conn, tblprod_hab_patrimonio,fk_idhabitacion_dato);
        suma_prod_hab_patrimonio();
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void reestableser_hab_dato(){
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        DAOhd.actualizar_tabla_habitacion_dato(conn, tbltabla_pri);
        color_tipo_boton(1,"");
        txtid.setText(null);
        txtubicacion.setText(null);
        txtadescripcion.setText(null);
        jCactivo.setSelected(true);
        jCconfrigobar.setSelected(false);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtubicacion.grabFocus();
    }
        private void boton_nuevo_hab_dato(){
        reestableser_hab_dato();
    }
    ////////////////GPIO/////////////////////
    private boolean validar_guardar_gpio(){
        if(eveJtab.getBoolean_validar_select(tbltabla_pri)){
            eveJtab.mostrar_JTabbedPane(jTab_principal, 1);
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtgpio, "DEBE CARGAR UN GPIO")){
            return false;
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbmini_pc, "SELECCIONE UNA MINI PC")){
            return false;
        }else{
            fk_idhabitacion_mini_pc=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbmini_pc,  mini_pc_id,mini_pc_nombre, mini_pc_tabla);
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbsensor_gpio, "SELECCIONE UN SENSOR")){
            return false;
        }else{
            fk_idhabitacion_sensor_gpio=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbsensor_gpio,  sensor_id,sensor_nombre, sensor_tabla);
        }
        return true;
    }
    private void cargar_dato_gpio(){
        ENTigpio.setC3alto_bajo(true);
        ENTigpio.setC4gpio(Integer.parseInt(txtgpio.getText()));
        ENTigpio.setC5activar(true);
        ENTigpio.setC6fk_idhabitacion_dato(fk_idhabitacion_dato);
        ENTigpio.setC7fk_idhabitacion_sensor(fk_idhabitacion_sensor_gpio);
        ENTigpio.setC8fk_idhabitacion_mini_pc(fk_idhabitacion_mini_pc);
    }
    private void cargar_dato_recepcion_temp(){
        
    }
    private void boton_guardar_gpio(){
        if(validar_guardar_gpio()){
            cargar_dato_gpio();
            BOigpio.insertar_habitacion_item_sensor_gpio(ENTigpio);
            DAOigpio.actualizar_tabla_habitacion_item_sensor_gpio(conn, tblhabitacion_item_sensor_gpio, fk_idhabitacion_dato);
        }
    }
    private void seleccionar_gpio(){
        int idhabitacion_item_sensor_gpio=eveJtab.getInt_select_id(tblhabitacion_item_sensor_gpio);
        DAOigpio.cargar_habitacion_item_sensor_gpio(conn, ENTigpio, idhabitacion_item_sensor_gpio);
    }
    private void boton_eliminar_gpio(){
        if(!eveJtab.getBoolean_validar_select(tblhabitacion_item_sensor_gpio)){
            seleccionar_gpio();
            ENTigpio.setC5activar(false);
            BOigpio.update_habitacion_item_sensor_gpio(ENTigpio);
            DAOigpio.actualizar_tabla_habitacion_item_sensor_gpio(conn, tblhabitacion_item_sensor_gpio, fk_idhabitacion_dato);
        }
    }
    //*****************PINO********************
    private boolean validar_guardar_pino(){
        if(eveJtab.getBoolean_validar_select(tbltabla_pri)){
            eveJtab.mostrar_JTabbedPane(jTab_principal, 1);
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtpino, "DEBE CARGAR UN PINO")){
            return false;
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbarduino, "SELECCIONE UN ARDUINO")){
            return false;
        }else{
            fk_idhabitacion_arduino=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbarduino,  ardu_id,ardu_nombre, ardu_tabla);
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbsensor_pino, "SELECCIONE UN SENSOR")){
            return false;
        }else{
            fk_idhabitacion_sensor_pino=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbsensor_pino,  sensor_id,sensor_nombre, sensor_tabla);
        }
        return true;
    }
    private void cargar_dato_pino(){
        ENTpino.setC3alto_bajo(true);
        ENTpino.setC4pino(Integer.parseInt(txtpino.getText()));
        ENTpino.setC5activar(true);
        ENTpino.setC6fk_idhabitacion_sensor(fk_idhabitacion_sensor_pino);
        ENTpino.setC7fk_idhabitacion_dato(fk_idhabitacion_dato);
        ENTpino.setC8fk_idhabitacion_arduino(fk_idhabitacion_arduino);
    }
    private void boton_guardar_pino(){
        if(validar_guardar_pino()){
            cargar_dato_pino();
            BOipino.insertar_habitacion_item_sensor_pino(ENTpino);
            DAOpino.actualizar_tabla_habitacion_item_sensor_pino(conn, tblhabitacion_item_sensor_pino, fk_idhabitacion_dato);
        }
    }
    private void seleccionar_pino(){
        int idhabitacion_item_sensor_pino=eveJtab.getInt_select_id(tblhabitacion_item_sensor_pino);
        DAOpino.cargar_habitacion_item_sensor_pino(conn, ENTpino, idhabitacion_item_sensor_pino);
    }
    private void boton_eliminar_pino(){
        if(!eveJtab.getBoolean_validar_select(tblhabitacion_item_sensor_pino)){
            seleccionar_pino();
            ENTpino.setC5activar(false);
            BOipino.update_habitacion_item_sensor_pino(ENTpino);
            DAOpino.actualizar_tabla_habitacion_item_sensor_pino(conn, tblhabitacion_item_sensor_pino, fk_idhabitacion_dato);
        }
    }
    //***********producto habitacion insumo*************
    private void cargar_dato_prod_hab_insumo(){
        ENTphi.setC3creado_por(creado_por);
        ENTphi.setC4cantidad(Integer.parseInt(txtprod_cant_insumo.getText()));
        ENTphi.setC5descripcion(txtprod_descrip_insumo.getText());
        ENTphi.setC6precio_venta(Integer.parseInt(txtprod_precio_insumo.getText()));
        ENTphi.setC7precio_compra(Integer.parseInt(txtprod_precio_insumo.getText()));
        ENTphi.setC8activo(true);
        ENTphi.setC9fk_idproducto(Integer.parseInt(txtprod_id_insumo.getText()));
        ENTphi.setC10fk_idhabitacion_dato(fk_idhabitacion_dato);
    }
    private boolean validar_guardar_prod_hab_insumo(){
        if(eveJtab.getBoolean_validar_select(tbltabla_pri)){
            eveJtab.mostrar_JTabbedPane(jTab_principal, 1);
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_id_insumo, "NO SE CARGO NINGUN PRODUCTO")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_descrip_insumo, "DEBE CARGAR UNA DESCRIPCION")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_cant_insumo, "DEBE CARGAR UNA CANTIDAD")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_precio_insumo, "DEBE CARGAR UN PRECIO")){
            return false;
        }
        return true;
    }
    private void restableser_prod_hab_insumo(){
        txtprod_id_insumo.setText(null);
        txtprod_descrip_insumo.setText(null);
        txtprod_cant_insumo.setText(null);
        txtprod_precio_insumo.setText(null);
        txtprod_subtt_insumo.setText(null);
    }
    private void boton_guardar_prod_hab_insumo(){
        if(validar_guardar_prod_hab_insumo()){
            cargar_dato_prod_hab_insumo();
            BOphi.insertar_producto_habitacion_insumo(ENTphi);
            restableser_prod_hab_insumo();
            DAOphi.actualizar_tabla_producto_habitacion_insumo(conn, tblprod_hab_insumo,fk_idhabitacion_dato);
            suma_prod_hab_insumo();
        }
    }
    private void suma_prod_hab_insumo(){
        double total=eveJtab.getDouble_sumar_tabla(tblprod_hab_insumo, 4);
        jFtotal_prod_hab_insumo.setValue(total);
    }
    private void seleccionar_prod_hab_insumo(){
        int idproducto_habitacion_insumo=eveJtab.getInt_select_id(tblprod_hab_insumo);
        DAOphi.cargar_producto_habitacion_insumo(conn, ENTphi, idproducto_habitacion_insumo);
    }
    private void boton_eliminar_prod_hab_insumo(){
        if(!eveJtab.getBoolean_validar_select(tblprod_hab_insumo)){
            seleccionar_prod_hab_insumo();
            ENTphi.setC8activo(false);
            BOphi.update_producto_habitacion_insumo(ENTphi);
            DAOphi.actualizar_tabla_producto_habitacion_insumo(conn, tblprod_hab_insumo,fk_idhabitacion_dato);
            suma_prod_hab_insumo();
        }
    }
    //*************PRODUCTO HABITACION FRIGOBAR******************
    private void cargar_dato_prod_hab_frigobar(){
        ENTphf.setC3creado_por(creado_por);
        ENTphf.setC4cantidad(Integer.parseInt(txtprod_cant_frigobar.getText()));
        ENTphf.setC5descripcion(txtprod_descrip_frigobar.getText());
        ENTphf.setC6precio_venta(Integer.parseInt(txtprod_precio_frigobar.getText()));
        ENTphf.setC7precio_compra(Integer.parseInt(txtprod_precio_frigobar.getText()));
        ENTphf.setC8activo(true);
        ENTphf.setC10fk_idproducto(Integer.parseInt(txtprod_id_frigobar.getText()));
        ENTphf.setC9fk_idhabitacion_dato(fk_idhabitacion_dato);
    }
    private boolean validar_guardar_prod_hab_frigobar(){
        if(eveJtab.getBoolean_validar_select(tbltabla_pri)){
            eveJtab.mostrar_JTabbedPane(jTab_principal, 1);
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_id_frigobar, "NO SE CARGO NINGUN PRODUCTO")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_descrip_frigobar, "DEBE CARGAR UNA DESCRIPCION")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_cant_frigobar, "DEBE CARGAR UNA CANTIDAD")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_precio_frigobar, "DEBE CARGAR UN PRECIO")){
            return false;
        }
        return true;
    }
    private void restableser_prod_hab_frigobar(){
        txtprod_id_frigobar.setText(null);
        txtprod_descrip_frigobar.setText(null);
        txtprod_cant_frigobar.setText(null);
        txtprod_precio_frigobar.setText(null);
        txtprod_subtotal_frigobar.setText(null);
    }
    private void boton_guardar_prod_hab_frigobar(){
        if(validar_guardar_prod_hab_frigobar()){
            cargar_dato_prod_hab_frigobar();
            BOphf.insertar_producto_habitacion_frigobar(ENTphf);
            restableser_prod_hab_frigobar();
            DAOphf.actualizar_tabla_producto_habitacion_frigobar(conn, tblprod_hab_frigobar,fk_idhabitacion_dato);
            suma_prod_hab_frigobar();
        }
    }
    private void suma_prod_hab_frigobar(){
        double total=eveJtab.getDouble_sumar_tabla(tblprod_hab_frigobar, 4);
        jFtotal_prod_hab_frigobar.setValue(total);
    }
    private void seleccionar_prod_hab_frigobar(){
        int idproducto_habitacion_frigobar=eveJtab.getInt_select_id(tblprod_hab_frigobar);
        DAOphf.cargar_producto_habitacion_frigobar(conn, ENTphf, idproducto_habitacion_frigobar);
    }
    private void boton_eliminar_prod_hab_frigobar(){
        if(!eveJtab.getBoolean_validar_select(tblprod_hab_frigobar)){
            seleccionar_prod_hab_frigobar();
            ENTphf.setC8activo(false);
            BOphf.update_producto_habitacion_frigobar(ENTphf);
            DAOphf.actualizar_tabla_producto_habitacion_frigobar(conn, tblprod_hab_frigobar,fk_idhabitacion_dato);
            suma_prod_hab_frigobar();
        }
    }
    //*************PRODUCTO HABITACION PATRIMONIO******************
    private void cargar_dato_prod_hab_patrimonio(){
        ENTphp.setC3creado_por(creado_por);
        ENTphp.setC4cantidad(Integer.parseInt(txtprod_cant_patrimonio.getText()));
        ENTphp.setC5descripcion(txtprod_descrip_patrimonio.getText());
        ENTphp.setC6precio_compra(Integer.parseInt(txtprod_precio_patrimonio.getText()));
        ENTphp.setC7activo(true);
        ENTphp.setC8fecha_ingreso(evefec.getString_formato_fecha());
        ENTphp.setC9fecha_salida(evefec.getString_formato_fecha());
        ENTphp.setC10motivo_salida("m");
        ENTphp.setC11fk_idproducto(Integer.parseInt(txtprod_id_patrimonio.getText()));
        ENTphp.setC12fk_idhabitacion_dato(fk_idhabitacion_dato);
    }
    private boolean validar_guardar_prod_hab_patrimonio(){
        if(eveJtab.getBoolean_validar_select(tbltabla_pri)){
            eveJtab.mostrar_JTabbedPane(jTab_principal, 1);
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_id_patrimonio, "NO SE CARGO NINGUN PRODUCTO")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_descrip_patrimonio, "DEBE CARGAR UNA DESCRIPCION")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_cant_patrimonio, "DEBE CARGAR UNA CANTIDAD")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtprod_precio_patrimonio, "DEBE CARGAR UN PRECIO")){
            return false;
        }
        return true;
    }
    private void restableser_prod_hab_patrimonio(){
        txtprod_id_patrimonio.setText(null);
        txtprod_descrip_patrimonio.setText(null);
        txtprod_cant_patrimonio.setText(null);
        txtprod_precio_patrimonio.setText(null);
        txtprod_subtotal_patrimonio.setText(null);
    }
    private void boton_guardar_prod_hab_patrimonio(){
        if(validar_guardar_prod_hab_patrimonio()){
            cargar_dato_prod_hab_patrimonio();
            BOphp.insertar_producto_habitacion_patrimonio(ENTphp);
            restableser_prod_hab_patrimonio();
            DAOphp.actualizar_tabla_producto_habitacion_patrimonio(conn, tblprod_hab_patrimonio,fk_idhabitacion_dato);
            suma_prod_hab_patrimonio();
        }
    }
    private void suma_prod_hab_patrimonio(){
        double total=eveJtab.getDouble_sumar_tabla(tblprod_hab_patrimonio, 4);
        jFtotal_prod_hab_patrimonio.setValue(total);
    }
    private void seleccionar_prod_hab_patrimonio(){
        int idproducto_habitacion_patrimonio=eveJtab.getInt_select_id(tblprod_hab_patrimonio);
        DAOphp.cargar_producto_habitacion_patrimonio(conn, ENTphp, idproducto_habitacion_patrimonio);
    }
    private void boton_eliminar_prod_hab_patrimonio(){
        if(!eveJtab.getBoolean_validar_select(tblprod_hab_patrimonio)){
            seleccionar_prod_hab_patrimonio();
            ENTphp.setC7activo(false);
            BOphp.update_producto_habitacion_patrimonio(ENTphp);
            DAOphp.actualizar_tabla_producto_habitacion_patrimonio(conn, tblprod_hab_patrimonio,fk_idhabitacion_dato);
            suma_prod_hab_patrimonio();
        }
    }
    public FrmHab_crear() {
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gru_tipo = new javax.swing.ButtonGroup();
        jTab_principal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insertar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtubicacion = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jCactivo = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jSnro_habitacion = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        btntipo_estandar = new javax.swing.JButton();
        btntipo_vip = new javax.swing.JButton();
        btntipo_lujo = new javax.swing.JButton();
        btntipo_penthause = new javax.swing.JButton();
        txttipo_habitacion = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtadescripcion = new javax.swing.JTextArea();
        jCconfrigobar = new javax.swing.JCheckBox();
        jTab_hijos = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbcosto = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jFmonto_hora_minimo = new javax.swing.JFormattedTextField();
        jFmonto_hora_adicional = new javax.swing.JFormattedTextField();
        jFmonto_dormir_minimo = new javax.swing.JFormattedTextField();
        jFmonto_dormir_adicional = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jFdormir_ingreso_inicio = new javax.swing.JFormattedTextField();
        jFdormir_ingreso_final = new javax.swing.JFormattedTextField();
        jFdormir_salida_final = new javax.swing.JFormattedTextField();
        jPanel9 = new javax.swing.JPanel();
        txtminumo_minimo = new javax.swing.JTextField();
        txtminuto_adicional = new javax.swing.JTextField();
        txtminuto_cancelar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnnuevo_habitacion_costo = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        cmbmini_pc = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cmbsensor_gpio = new javax.swing.JComboBox<>();
        btnnuevo_habitacion_minipc = new javax.swing.JButton();
        btnnuevo_habitacion_sensor_gpio = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtgpio = new javax.swing.JTextField();
        btnguardar_gpio = new javax.swing.JButton();
        btneliminar_gpio = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblhabitacion_item_sensor_gpio = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cmbarduino = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cmbsensor_pino = new javax.swing.JComboBox<>();
        btnnuevo_habitacion_arduino = new javax.swing.JButton();
        btnnuevo_habitacion_sensor_pino = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtpino = new javax.swing.JTextField();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblhabitacion_item_sensor_pino = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        txtprod_id_insumo = new javax.swing.JTextField();
        txtprod_descrip_insumo = new javax.swing.JTextField();
        txtprod_cant_insumo = new javax.swing.JTextField();
        txtprod_subtt_insumo = new javax.swing.JTextField();
        txtprod_precio_insumo = new javax.swing.JTextField();
        btnbuscar_insumo = new javax.swing.JButton();
        btnnuevo_insumo = new javax.swing.JButton();
        btnguardar_prod_hab_insumo = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblprod_hab_insumo = new javax.swing.JTable();
        jFtotal_prod_hab_insumo = new javax.swing.JFormattedTextField();
        btneliminar_insumo = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        txtprod_id_frigobar = new javax.swing.JTextField();
        txtprod_descrip_frigobar = new javax.swing.JTextField();
        txtprod_cant_frigobar = new javax.swing.JTextField();
        txtprod_subtotal_frigobar = new javax.swing.JTextField();
        txtprod_precio_frigobar = new javax.swing.JTextField();
        btnbuscar_frigobar = new javax.swing.JButton();
        btnnuevo_frigobar = new javax.swing.JButton();
        btnguardar_prod_hab_frigobar = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblprod_hab_frigobar = new javax.swing.JTable();
        jFtotal_prod_hab_frigobar = new javax.swing.JFormattedTextField();
        btneliminar_frigobar = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblprod_hab_patrimonio = new javax.swing.JTable();
        jFtotal_prod_hab_patrimonio = new javax.swing.JFormattedTextField();
        btneliminar_patrimonio = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        txtprod_id_patrimonio = new javax.swing.JTextField();
        txtprod_descrip_patrimonio = new javax.swing.JTextField();
        txtprod_cant_patrimonio = new javax.swing.JTextField();
        txtprod_subtotal_patrimonio = new javax.swing.JTextField();
        txtprod_precio_patrimonio = new javax.swing.JTextField();
        btnbuscar_patrimonio = new javax.swing.JButton();
        btnnuevo_patrimonio = new javax.swing.JButton();
        btnguardar_prod_hab_patrimonio = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla_pri = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        panel_insertar.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("UBICACION:");

        txtubicacion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtubicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtubicacionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtubicacionKeyReleased(evt);
            }
        });

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        jCactivo.setSelected(true);
        jCactivo.setText("ACTIVO");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("NUMERO HABITACION:");

        jSnro_habitacion.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jSnro_habitacion.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO"));

        btntipo_estandar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_camaamor (2).png"))); // NOI18N
        btntipo_estandar.setText("ESTANDAR");
        btntipo_estandar.setToolTipText("");
        gru_tipo.add(btntipo_estandar);
        btntipo_estandar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_cama.png"))); // NOI18N
        btntipo_estandar.setDisabledSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btntipo_estandar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btntipo_estandar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btntipo_estandar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntipo_estandarActionPerformed(evt);
            }
        });

        btntipo_vip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_corona.png"))); // NOI18N
        btntipo_vip.setText("VIP");
        gru_tipo.add(btntipo_vip);
        btntipo_vip.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btntipo_vip.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btntipo_vip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntipo_vipActionPerformed(evt);
            }
        });

        btntipo_lujo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_diamante.png"))); // NOI18N
        btntipo_lujo.setText("LUXURY");
        gru_tipo.add(btntipo_lujo);
        btntipo_lujo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btntipo_lujo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btntipo_lujo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntipo_lujoActionPerformed(evt);
            }
        });

        btntipo_penthause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_motel.png"))); // NOI18N
        btntipo_penthause.setText("PENTHOUSE");
        gru_tipo.add(btntipo_penthause);
        btntipo_penthause.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btntipo_penthause.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btntipo_penthause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntipo_penthauseActionPerformed(evt);
            }
        });

        txttipo_habitacion.setBackground(new java.awt.Color(0, 51, 255));
        txttipo_habitacion.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txttipo_habitacion.setForeground(new java.awt.Color(255, 255, 0));
        txttipo_habitacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txttipo_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btntipo_estandar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btntipo_vip, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btntipo_lujo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btntipo_penthause, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btntipo_estandar, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btntipo_vip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btntipo_lujo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btntipo_penthause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttipo_habitacion, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
        );

        txtadescripcion.setColumns(20);
        txtadescripcion.setRows(5);
        txtadescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        jScrollPane3.setViewportView(txtadescripcion);

        jCconfrigobar.setText("CON FRIGOBAR");

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSnro_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtubicacion))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCactivo)
                            .addComponent(jCconfrigobar)))
                    .addComponent(jScrollPane3))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addComponent(jSnro_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jCactivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCconfrigobar)))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("COSTO:");

        cmbcosto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbcosto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbcostoItemStateChanged(evt);
            }
        });
        cmbcosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbcostoActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO"));

        jFmonto_hora_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA MINIMO"));
        jFmonto_hora_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_hora_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_hora_minimo.setText("1");
        jFmonto_hora_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_hora_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA ADICIONAL"));
        jFmonto_hora_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_hora_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_hora_adicional.setText("1");
        jFmonto_hora_adicional.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_dormir_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("DORMIR MINIMO"));
        jFmonto_dormir_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_dormir_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_dormir_minimo.setText("1");
        jFmonto_dormir_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_dormir_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("DORMIR ADICIONAL"));
        jFmonto_dormir_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFmonto_dormir_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_dormir_adicional.setText("1");
        jFmonto_dormir_adicional.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jFmonto_hora_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFmonto_dormir_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jFmonto_hora_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFmonto_dormir_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmonto_hora_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmonto_dormir_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmonto_hora_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmonto_dormir_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("TIEMPO DORMIR"));

        jFdormir_ingreso_inicio.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO INICIO"));
        try {
            jFdormir_ingreso_inicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFdormir_ingreso_inicio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdormir_ingreso_inicio.setText("00:00:00");
        jFdormir_ingreso_inicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFdormir_ingreso_final.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO FINAL"));
        try {
            jFdormir_ingreso_final.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFdormir_ingreso_final.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdormir_ingreso_final.setText("00:00:00");
        jFdormir_ingreso_final.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jFdormir_salida_final.setBorder(javax.swing.BorderFactory.createTitledBorder("SALIDA FINAL"));
        try {
            jFdormir_salida_final.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFdormir_salida_final.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdormir_salida_final.setText("00:00:00");
        jFdormir_salida_final.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFdormir_ingreso_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFdormir_ingreso_final, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFdormir_salida_final, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jFdormir_ingreso_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFdormir_ingreso_final, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFdormir_salida_final, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("TIEMPO TOLERANCIA"));

        txtminumo_minimo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminumo_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminumo_minimo.setText("10");
        txtminumo_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN MINIMO"));

        txtminuto_adicional.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminuto_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminuto_adicional.setText("10");
        txtminuto_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN ADICIONAL"));

        txtminuto_cancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminuto_cancelar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminuto_cancelar.setText("10");
        txtminuto_cancelar.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN CANCELAR"));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtminuto_adicional, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(txtminumo_minimo)
                    .addComponent(txtminuto_cancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(txtminumo_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtminuto_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtminuto_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/96_tiempo.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/96_dinero_efectivo.png"))); // NOI18N

        btnnuevo_habitacion_costo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_habitacion_costo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_habitacion_costoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_habitacion_costo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(cmbcosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_habitacion_costo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 35, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(92, 92, 92))
        );

        jTab_hijos.addTab("COSTOS", jPanel6);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS GPIO"));

        jLabel8.setText("MINI PC:");

        cmbmini_pc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbmini_pc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbmini_pcActionPerformed(evt);
            }
        });

        jLabel9.setText("SENSOR:");

        cmbsensor_gpio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbsensor_gpio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbsensor_gpioActionPerformed(evt);
            }
        });

        btnnuevo_habitacion_minipc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_habitacion_minipc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_habitacion_minipcActionPerformed(evt);
            }
        });

        btnnuevo_habitacion_sensor_gpio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_habitacion_sensor_gpio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_habitacion_sensor_gpioActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("GPIO:");

        txtgpio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtgpio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtgpio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtgpioKeyTyped(evt);
            }
        });

        btnguardar_gpio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnguardar_gpio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btnguardar_gpio.setText("GUARDAR");
        btnguardar_gpio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar_gpioActionPerformed(evt);
            }
        });

        btneliminar_gpio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_eliminar_item.png"))); // NOI18N
        btneliminar_gpio.setText("ELIMINAR ITEM");
        btneliminar_gpio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_gpioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbmini_pc, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnnuevo_habitacion_minipc, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbsensor_gpio, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_habitacion_sensor_gpio, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtgpio, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnguardar_gpio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneliminar_gpio, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(cmbmini_pc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_habitacion_minipc, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cmbsensor_gpio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_habitacion_sensor_gpio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btneliminar_gpio, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnguardar_gpio, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel12Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtgpio, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.CENTER))
                .addGap(5, 5, 5))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA SENSOR GPIO"));

        tblhabitacion_item_sensor_gpio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblhabitacion_item_sensor_gpio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblhabitacion_item_sensor_gpioMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblhabitacion_item_sensor_gpio);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_hijos.addTab("MINI PC (GPIO)", jPanel10);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS PINO"));

        jLabel11.setText("ARDUINO:");

        cmbarduino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbarduino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbarduinoActionPerformed(evt);
            }
        });

        jLabel12.setText("SENSOR:");

        cmbsensor_pino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbsensor_pino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbsensor_pinoActionPerformed(evt);
            }
        });

        btnnuevo_habitacion_arduino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_habitacion_arduino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_habitacion_arduinoActionPerformed(evt);
            }
        });

        btnnuevo_habitacion_sensor_pino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_habitacion_sensor_pino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_habitacion_sensor_pinoActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("PINO:");

        txtpino.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtpino.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jButton19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        jButton19.setText("GUARDAR");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_eliminar_item.png"))); // NOI18N
        jButton20.setText("ELIMINAR ITEM");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbarduino, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnnuevo_habitacion_arduino, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbsensor_pino, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_habitacion_sensor_pino, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpino, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(cmbarduino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_habitacion_arduino, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cmbsensor_pino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_habitacion_sensor_pino, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel13))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(txtpino, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA SENSOR PINO"));

        tblhabitacion_item_sensor_pino.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblhabitacion_item_sensor_pino);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 407, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addGap(110, 110, 110)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTab_hijos.addTab("ARDUINO (PIN)", jPanel11);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("CARGAR INSUMO"));

        txtprod_id_insumo.setEditable(false);
        txtprod_id_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "IDPro"));

        txtprod_descrip_insumo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtprod_descrip_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Descripcion"));
        txtprod_descrip_insumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_descrip_insumoKeyPressed(evt);
            }
        });

        txtprod_cant_insumo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtprod_cant_insumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_cant_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Cant"));
        txtprod_cant_insumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_cant_insumoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprod_cant_insumoKeyTyped(evt);
            }
        });

        txtprod_subtt_insumo.setEditable(false);
        txtprod_subtt_insumo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtprod_subtt_insumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_subtt_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Subtotal"));

        txtprod_precio_insumo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtprod_precio_insumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_precio_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Precio"));
        txtprod_precio_insumo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_precio_insumoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprod_precio_insumoKeyTyped(evt);
            }
        });

        btnbuscar_insumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_insumoActionPerformed(evt);
            }
        });

        btnnuevo_insumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_insumoActionPerformed(evt);
            }
        });

        btnguardar_prod_hab_insumo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnguardar_prod_hab_insumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btnguardar_prod_hab_insumo.setText("GUARDAR");
        btnguardar_prod_hab_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar_prod_hab_insumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(txtprod_id_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(btnguardar_prod_hab_insumo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnbuscar_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(txtprod_descrip_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_cant_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_precio_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_subtt_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprod_id_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_descrip_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_cant_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_precio_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_subtt_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnbuscar_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_insumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnguardar_prod_hab_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM INSUMO"));

        tblprod_hab_insumo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblprod_hab_insumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblprod_hab_insumoMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblprod_hab_insumo);

        jFtotal_prod_hab_insumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL"));
        jFtotal_prod_hab_insumo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_prod_hab_insumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_prod_hab_insumo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btneliminar_insumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_eliminar_item.png"))); // NOI18N
        btneliminar_insumo.setText("ELIMINAR ITEM");
        btneliminar_insumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_insumoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(btneliminar_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFtotal_prod_hab_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_prod_hab_insumo)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(btneliminar_insumo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_hijos.addTab("INSUMO", jPanel16);

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("CARGAR FRIGOBAR"));

        txtprod_id_frigobar.setEditable(false);
        txtprod_id_frigobar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "IDPro"));

        txtprod_descrip_frigobar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Descripcion"));

        txtprod_cant_frigobar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_cant_frigobar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Cant"));
        txtprod_cant_frigobar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprod_cant_frigobarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprod_cant_frigobarKeyTyped(evt);
            }
        });

        txtprod_subtotal_frigobar.setEditable(false);
        txtprod_subtotal_frigobar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_subtotal_frigobar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Subtotal"));

        txtprod_precio_frigobar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_precio_frigobar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Precio"));
        txtprod_precio_frigobar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprod_precio_frigobarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprod_precio_frigobarKeyTyped(evt);
            }
        });

        btnbuscar_frigobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_frigobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_frigobarActionPerformed(evt);
            }
        });

        btnnuevo_frigobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_frigobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_frigobarActionPerformed(evt);
            }
        });

        btnguardar_prod_hab_frigobar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnguardar_prod_hab_frigobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btnguardar_prod_hab_frigobar.setText("GUARDAR");
        btnguardar_prod_hab_frigobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar_prod_hab_frigobarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(txtprod_id_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(btnguardar_prod_hab_frigobar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnbuscar_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(txtprod_descrip_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_cant_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_precio_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_subtotal_frigobar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprod_id_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_descrip_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_cant_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_precio_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_subtotal_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnbuscar_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_frigobar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnguardar_prod_hab_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM FRIGOBAR"));

        tblprod_hab_frigobar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblprod_hab_frigobar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblprod_hab_frigobarMouseReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tblprod_hab_frigobar);

        jFtotal_prod_hab_frigobar.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL"));
        jFtotal_prod_hab_frigobar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_prod_hab_frigobar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_prod_hab_frigobar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btneliminar_frigobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_eliminar_item.png"))); // NOI18N
        btneliminar_frigobar.setText("ELIMINAR ITEM");
        btneliminar_frigobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_frigobarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(btneliminar_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFtotal_prod_hab_frigobar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_prod_hab_frigobar)
                    .addComponent(btneliminar_frigobar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_hijos.addTab("FRIGOBAR", jPanel17);

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM PATRIMONIO"));

        tblprod_hab_patrimonio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tblprod_hab_patrimonio);

        jFtotal_prod_hab_patrimonio.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL"));
        jFtotal_prod_hab_patrimonio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_prod_hab_patrimonio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_prod_hab_patrimonio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btneliminar_patrimonio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_eliminar_item.png"))); // NOI18N
        btneliminar_patrimonio.setText("ELIMINAR ITEM");
        btneliminar_patrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_patrimonioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(btneliminar_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFtotal_prod_hab_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_prod_hab_patrimonio)
                    .addComponent(btneliminar_patrimonio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("CARGAR PATRIMONIO"));

        txtprod_id_patrimonio.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "IDPro"));

        txtprod_descrip_patrimonio.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Descripcion"));

        txtprod_cant_patrimonio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_cant_patrimonio.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Cant"));
        txtprod_cant_patrimonio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprod_cant_patrimonioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprod_cant_patrimonioKeyTyped(evt);
            }
        });

        txtprod_subtotal_patrimonio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_subtotal_patrimonio.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Subtotal"));

        txtprod_precio_patrimonio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprod_precio_patrimonio.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Precio"));
        txtprod_precio_patrimonio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprod_precio_patrimonioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprod_precio_patrimonioKeyTyped(evt);
            }
        });

        btnbuscar_patrimonio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_patrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_patrimonioActionPerformed(evt);
            }
        });

        btnnuevo_patrimonio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_patrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_patrimonioActionPerformed(evt);
            }
        });

        btnguardar_prod_hab_patrimonio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnguardar_prod_hab_patrimonio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btnguardar_prod_hab_patrimonio.setText("GUARDAR");
        btnguardar_prod_hab_patrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar_prod_hab_patrimonioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(txtprod_id_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(btnguardar_prod_hab_patrimonio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnbuscar_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(txtprod_descrip_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_cant_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_precio_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprod_subtotal_patrimonio, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprod_id_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_descrip_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_cant_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_precio_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprod_subtotal_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnbuscar_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_patrimonio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnguardar_prod_hab_patrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_hijos.addTab("PATRIMONIO", jPanel20);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTab_hijos))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTab_hijos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jTab_principal.addTab("PRINCIPAL", jPanel1);

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbltabla_pri.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbltabla_pri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbltabla_priMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbltabla_pri);

        jLabel3.setText("BUSCAR NOMBRE:");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1119, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1131, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_principal.addTab("SECUNDARIO", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_hab_dato();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOhd.ancho_tabla_habitacion_dato(tbltabla_pri);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltabla_priMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_priMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_hab_dato();
    }//GEN-LAST:event_tbltabla_priMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar_hab_dato();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo_hab_dato();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtubicacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtubicacionKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txtubicacionKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
//        DAOhd.actualizar_tabla_habitacion_dato_buscar(conn, tbltabla_pri, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtubicacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtubicacionKeyReleased
        // TODO add your handling code here:
        txtubicacion.setText(txtubicacion.getText().toUpperCase());
    }//GEN-LAST:event_txtubicacionKeyReleased

    private void btntipo_estandarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntipo_estandarActionPerformed
        // TODO add your handling code here:
        color_tipo_boton(1,"");
    }//GEN-LAST:event_btntipo_estandarActionPerformed

    private void btntipo_vipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntipo_vipActionPerformed
        // TODO add your handling code here:
        color_tipo_boton(2,"");
    }//GEN-LAST:event_btntipo_vipActionPerformed

    private void btntipo_lujoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntipo_lujoActionPerformed
        // TODO add your handling code here:
        color_tipo_boton(3,"");
    }//GEN-LAST:event_btntipo_lujoActionPerformed

    private void btntipo_penthauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntipo_penthauseActionPerformed
        // TODO add your handling code here:
        color_tipo_boton(4,"");
    }//GEN-LAST:event_btntipo_penthauseActionPerformed

    private void cmbcostoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbcostoItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbcostoItemStateChanged

    private void cmbcostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbcostoActionPerformed
        // TODO add your handling code here:
        fk_idhabitacion_costo= evecmb.getInt_seleccionar_COMBOBOX(conn, cmbcosto, costo_id, costo_nombre, costo_tabla);
        cargar_costos(fk_idhabitacion_costo);
    }//GEN-LAST:event_cmbcostoActionPerformed

    private void cmbmini_pcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbmini_pcActionPerformed
        // TODO add your handling code here:
        fk_idhabitacion_mini_pc=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbmini_pc,  mini_pc_id,mini_pc_nombre, mini_pc_tabla);
    }//GEN-LAST:event_cmbmini_pcActionPerformed

    private void cmbsensor_gpioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbsensor_gpioActionPerformed
        // TODO add your handling code here:
        fk_idhabitacion_sensor_gpio=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbsensor_gpio,  sensor_id,sensor_nombre, sensor_tabla);
    }//GEN-LAST:event_cmbsensor_gpioActionPerformed

    private void btnguardar_gpioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar_gpioActionPerformed
        // TODO add your handling code here:
        boton_guardar_gpio();
    }//GEN-LAST:event_btnguardar_gpioActionPerformed

    private void txtgpioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtgpioKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtgpioKeyTyped

    private void tblhabitacion_item_sensor_gpioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblhabitacion_item_sensor_gpioMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblhabitacion_item_sensor_gpioMouseReleased

    private void btneliminar_gpioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_gpioActionPerformed
        // TODO add your handling code here:
        boton_eliminar_gpio();
    }//GEN-LAST:event_btneliminar_gpioActionPerformed

    private void cmbarduinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbarduinoActionPerformed
        // TODO add your handling code here:
        fk_idhabitacion_arduino=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbarduino,  ardu_id,ardu_nombre, ardu_tabla);
    }//GEN-LAST:event_cmbarduinoActionPerformed

    private void cmbsensor_pinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbsensor_pinoActionPerformed
        // TODO add your handling code here:
        fk_idhabitacion_sensor_pino=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbsensor_pino,  sensor_id,sensor_nombre, sensor_tabla);
    }//GEN-LAST:event_cmbsensor_pinoActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        boton_guardar_pino();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        boton_eliminar_pino();
    }//GEN-LAST:event_jButton20ActionPerformed

    private void btnnuevo_habitacion_costoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_habitacion_costoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_costo());
    }//GEN-LAST:event_btnnuevo_habitacion_costoActionPerformed

    private void btnnuevo_habitacion_minipcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_habitacion_minipcActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_miniPc());
    }//GEN-LAST:event_btnnuevo_habitacion_minipcActionPerformed

    private void btnnuevo_habitacion_sensor_gpioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_habitacion_sensor_gpioActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_sensor());
    }//GEN-LAST:event_btnnuevo_habitacion_sensor_gpioActionPerformed

    private void btnnuevo_habitacion_arduinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_habitacion_arduinoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_arduino());
    }//GEN-LAST:event_btnnuevo_habitacion_arduinoActionPerformed

    private void btnnuevo_habitacion_sensor_pinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_habitacion_sensor_pinoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmHab_sensor());
    }//GEN-LAST:event_btnnuevo_habitacion_sensor_pinoActionPerformed

    private void btnnuevo_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_insumoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_dato());
    }//GEN-LAST:event_btnnuevo_insumoActionPerformed

    private void btnnuevo_frigobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_frigobarActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_dato());
    }//GEN-LAST:event_btnnuevo_frigobarActionPerformed

    private void btnnuevo_patrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_patrimonioActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_dato());
    }//GEN-LAST:event_btnnuevo_patrimonioActionPerformed

    private void txtprod_descrip_insumoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_descrip_insumoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            vbus.abrir_buscar(4, "PRODUCTO INSUMO", txtprod_descrip_insumo);
        }
    }//GEN-LAST:event_txtprod_descrip_insumoKeyPressed

    private void btnbuscar_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_insumoActionPerformed
        // TODO add your handling code here:
        vbus.abrir_buscar(4, "PRODUCTO INSUMO", txtprod_descrip_insumo);
    }//GEN-LAST:event_btnbuscar_insumoActionPerformed

    private void txtprod_cant_insumoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cant_insumoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprod_cant_insumoKeyTyped

    private void txtprod_cant_insumoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cant_insumoKeyPressed
        // TODO add your handling code here:
        evejtf.setSubtotal_suma_cantidad_flecha(evt, txtprod_cant_insumo, txtprod_precio_insumo, txtprod_subtt_insumo);
    }//GEN-LAST:event_txtprod_cant_insumoKeyPressed

    private void btnguardar_prod_hab_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar_prod_hab_insumoActionPerformed
        // TODO add your handling code here:
        boton_guardar_prod_hab_insumo();
    }//GEN-LAST:event_btnguardar_prod_hab_insumoActionPerformed

    private void btneliminar_insumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_insumoActionPerformed
        // TODO add your handling code here:
        boton_eliminar_prod_hab_insumo();
    }//GEN-LAST:event_btneliminar_insumoActionPerformed

    private void tblprod_hab_frigobarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblprod_hab_frigobarMouseReleased
        // TODO add your handling code here:
        seleccionar_prod_hab_frigobar();
    }//GEN-LAST:event_tblprod_hab_frigobarMouseReleased

    private void tblprod_hab_insumoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblprod_hab_insumoMouseReleased
        // TODO add your handling code here:
        seleccionar_prod_hab_insumo();
    }//GEN-LAST:event_tblprod_hab_insumoMouseReleased

    private void btnguardar_prod_hab_frigobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar_prod_hab_frigobarActionPerformed
        // TODO add your handling code here:
        boton_guardar_prod_hab_frigobar();
    }//GEN-LAST:event_btnguardar_prod_hab_frigobarActionPerformed

    private void btnbuscar_frigobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_frigobarActionPerformed
        // TODO add your handling code here:
        vbus.abrir_buscar(5, "PRODUCTO FRIGOBAR", txtprod_descrip_frigobar);
    }//GEN-LAST:event_btnbuscar_frigobarActionPerformed

    private void txtprod_precio_insumoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_precio_insumoKeyPressed
        // TODO add your handling code here:
         evejtf.setSubtotal_suma_cantidad_flecha(evt, txtprod_cant_insumo, txtprod_precio_insumo, txtprod_subtt_insumo);
    }//GEN-LAST:event_txtprod_precio_insumoKeyPressed

    private void txtprod_cant_frigobarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cant_frigobarKeyReleased
        // TODO add your handling code here:
         evejtf.setSubtotal_suma_cantidad_flecha(evt, txtprod_cant_frigobar, txtprod_precio_frigobar, txtprod_subtotal_frigobar);
    }//GEN-LAST:event_txtprod_cant_frigobarKeyReleased

    private void txtprod_precio_frigobarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_precio_frigobarKeyReleased
        // TODO add your handling code here:
        evejtf.setSubtotal_suma_cantidad_flecha(evt, txtprod_cant_frigobar, txtprod_precio_frigobar, txtprod_subtotal_frigobar);
    }//GEN-LAST:event_txtprod_precio_frigobarKeyReleased

    private void txtprod_precio_insumoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_precio_insumoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprod_precio_insumoKeyTyped

    private void txtprod_cant_frigobarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cant_frigobarKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprod_cant_frigobarKeyTyped

    private void txtprod_precio_frigobarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_precio_frigobarKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprod_precio_frigobarKeyTyped

    private void btneliminar_frigobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_frigobarActionPerformed
        // TODO add your handling code here:
        boton_eliminar_prod_hab_frigobar();
    }//GEN-LAST:event_btneliminar_frigobarActionPerformed

    private void btnguardar_prod_hab_patrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar_prod_hab_patrimonioActionPerformed
        // TODO add your handling code here:
        boton_guardar_prod_hab_patrimonio();
    }//GEN-LAST:event_btnguardar_prod_hab_patrimonioActionPerformed

    private void btneliminar_patrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_patrimonioActionPerformed
        // TODO add your handling code here:
        boton_eliminar_prod_hab_patrimonio();
    }//GEN-LAST:event_btneliminar_patrimonioActionPerformed

    private void btnbuscar_patrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_patrimonioActionPerformed
        // TODO add your handling code here:
        vbus.abrir_buscar(6, "PRODUCTO PATRIMONIO", txtprod_descrip_patrimonio);
    }//GEN-LAST:event_btnbuscar_patrimonioActionPerformed

    private void txtprod_cant_patrimonioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cant_patrimonioKeyReleased
        // TODO add your handling code here:
        evejtf.setSubtotal_suma_cantidad_flecha(evt, txtprod_cant_patrimonio, txtprod_precio_patrimonio, txtprod_subtotal_patrimonio);
    }//GEN-LAST:event_txtprod_cant_patrimonioKeyReleased

    private void txtprod_precio_patrimonioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_precio_patrimonioKeyReleased
        // TODO add your handling code here:
        evejtf.setSubtotal_suma_cantidad_flecha(evt, txtprod_cant_patrimonio, txtprod_precio_patrimonio, txtprod_subtotal_patrimonio);
    }//GEN-LAST:event_txtprod_precio_patrimonioKeyReleased

    private void txtprod_cant_patrimonioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_cant_patrimonioKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprod_cant_patrimonioKeyTyped

    private void txtprod_precio_patrimonioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_precio_patrimonioKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprod_precio_patrimonioKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_frigobar;
    private javax.swing.JButton btnbuscar_insumo;
    private javax.swing.JButton btnbuscar_patrimonio;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneliminar_frigobar;
    private javax.swing.JButton btneliminar_gpio;
    private javax.swing.JButton btneliminar_insumo;
    private javax.swing.JButton btneliminar_patrimonio;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnguardar_gpio;
    private javax.swing.JButton btnguardar_prod_hab_frigobar;
    private javax.swing.JButton btnguardar_prod_hab_insumo;
    private javax.swing.JButton btnguardar_prod_hab_patrimonio;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_frigobar;
    private javax.swing.JButton btnnuevo_habitacion_arduino;
    private javax.swing.JButton btnnuevo_habitacion_costo;
    private javax.swing.JButton btnnuevo_habitacion_minipc;
    private javax.swing.JButton btnnuevo_habitacion_sensor_gpio;
    private javax.swing.JButton btnnuevo_habitacion_sensor_pino;
    private javax.swing.JButton btnnuevo_insumo;
    private javax.swing.JButton btnnuevo_patrimonio;
    private javax.swing.JButton btntipo_estandar;
    private javax.swing.JButton btntipo_lujo;
    private javax.swing.JButton btntipo_penthause;
    private javax.swing.JButton btntipo_vip;
    private javax.swing.JComboBox<String> cmbarduino;
    private javax.swing.JComboBox<String> cmbcosto;
    private javax.swing.JComboBox<String> cmbmini_pc;
    private javax.swing.JComboBox<String> cmbsensor_gpio;
    private javax.swing.JComboBox<String> cmbsensor_pino;
    private javax.swing.ButtonGroup gru_tipo;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JCheckBox jCactivo;
    private javax.swing.JCheckBox jCconfrigobar;
    private javax.swing.JFormattedTextField jFdormir_ingreso_final;
    private javax.swing.JFormattedTextField jFdormir_ingreso_inicio;
    private javax.swing.JFormattedTextField jFdormir_salida_final;
    private javax.swing.JFormattedTextField jFmonto_dormir_adicional;
    private javax.swing.JFormattedTextField jFmonto_dormir_minimo;
    private javax.swing.JFormattedTextField jFmonto_hora_adicional;
    private javax.swing.JFormattedTextField jFmonto_hora_minimo;
    private javax.swing.JFormattedTextField jFtotal_prod_hab_frigobar;
    private javax.swing.JFormattedTextField jFtotal_prod_hab_insumo;
    private javax.swing.JFormattedTextField jFtotal_prod_hab_patrimonio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSpinner jSnro_habitacion;
    private javax.swing.JTabbedPane jTab_hijos;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tblhabitacion_item_sensor_gpio;
    private javax.swing.JTable tblhabitacion_item_sensor_pino;
    private javax.swing.JTable tblprod_hab_frigobar;
    private javax.swing.JTable tblprod_hab_insumo;
    private javax.swing.JTable tblprod_hab_patrimonio;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTextArea txtadescripcion;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtgpio;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtminumo_minimo;
    private javax.swing.JTextField txtminuto_adicional;
    private javax.swing.JTextField txtminuto_cancelar;
    private javax.swing.JTextField txtpino;
    public static javax.swing.JTextField txtprod_cant_frigobar;
    public static javax.swing.JTextField txtprod_cant_insumo;
    public static javax.swing.JTextField txtprod_cant_patrimonio;
    public static javax.swing.JTextField txtprod_descrip_frigobar;
    public static javax.swing.JTextField txtprod_descrip_insumo;
    public static javax.swing.JTextField txtprod_descrip_patrimonio;
    public static javax.swing.JTextField txtprod_id_frigobar;
    public static javax.swing.JTextField txtprod_id_insumo;
    public static javax.swing.JTextField txtprod_id_patrimonio;
    public static javax.swing.JTextField txtprod_precio_frigobar;
    public static javax.swing.JTextField txtprod_precio_insumo;
    public static javax.swing.JTextField txtprod_precio_patrimonio;
    public static javax.swing.JTextField txtprod_subtotal_frigobar;
    public static javax.swing.JTextField txtprod_subtotal_patrimonio;
    public static javax.swing.JTextField txtprod_subtt_insumo;
    private javax.swing.JTextField txttipo_habitacion;
    private javax.swing.JTextField txtubicacion;
    // End of variables declaration//GEN-END:variables
}
