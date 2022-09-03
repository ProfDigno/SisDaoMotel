/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Fecha.EvenFecha;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import java.awt.Color;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmHab_costo extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private habitacion_costo ENThc=new habitacion_costo();
    private DAO_habitacion_costo DAOhc=new DAO_habitacion_costo();
    private BO_habitacion_costo BOhc=new BO_habitacion_costo();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
    private EvenFecha evefec=new EvenFecha();
    Connection conn = ConnPostgres.getConnPosgres();
    private String nombreTabla_pri="COSTO"; 
    private String nombreTabla_sec="HABITACION"; 
    private String creado_por="digno";
    private String nivel_lujo;
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);        
        reestableser();
        DAOhc.actualizar_tabla_habitacion_costo(conn, tbltabla_pri);
    }
    private void titulo_formulario(String fecha_creado,String creado_por){
        this.setTitle(nombreTabla_pri+" / fecha creado: "+fecha_creado+" / Creado Por: "+creado_por);
    }
    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if(evejtf.getBoo_JFormatted_vacio(jFmonto_hora_minimo, "DEBE CARGAR UN MONTO HORA MINIMO")){
             return false;
        }
        if(evejtf.getBoo_JFormatted_vacio(jFmonto_hora_adicional, "DEBE CARGAR UN MONTO HORA ADICIONAL")){
             return false;
        }
        if(evejtf.getBoo_JFormatted_vacio(jFmonto_dormir_minimo, "DEBE CARGAR UN MONTO DORMIR MINIMO")){
             return false;
        }
        if(evejtf.getBoo_JFormatted_vacio(jFmonto_dormir_adicional, "DEBE CARGAR UN MONTO DORMIR ADICIONAL")){
             return false;
        }
        if(evejtf.getBoo_JFormatted_vacio(jFdormir_ingreso_inicio, "DEBE CARGAR UN HORA INGRESO DORMIR MINIMO AUTORIZADO")){
             return false;
        }
        if(evejtf.getBoo_JFormatted_vacio(jFdormir_ingreso_final, "DEBE CARGAR UN HORA INGRESO DORMIR MAXIMO AUTORIZADO")){
             return false;
        }
        if(evejtf.getBoo_JFormatted_vacio(jFdormir_salida_final, "DEBE CARGAR UN HORA SALIDA DORMIR MINIMO AUTORIZADO")){
             return false;
        }
        if(evefec.getboolean_time_correcto(jFdormir_ingreso_inicio)){
            return false;
        }
        if(evefec.getboolean_time_correcto(jFdormir_ingreso_final)){
            return false;
        }
        if(evefec.getboolean_time_correcto(jFdormir_salida_final)){
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtminuto_minimo, "DEBE CARGAR UN MINUTO MINIMO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtminuto_adicional, "DEBE CARGAR UN MINUTO ADICIONAL")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtminuto_cancelar, "DEBE CARGAR UN MINUTO PARA CANCELAR")) {
            return false;
        }
        return true;
    }
    private void cargar_dato(){
        ENThc.setC3creado_por(creado_por);
        ENThc.setC4activo(jCactivo.isSelected());
        ENThc.setC5nombre(txtnombre.getText());
        ENThc.setC6nivel_lujo(nivel_lujo);
        ENThc.setC7monto_por_hora_minimo(evejtf.getDouble_format_nro_entero(jFmonto_hora_minimo));
        ENThc.setC8monto_por_hora_adicional(evejtf.getDouble_format_nro_entero(jFmonto_hora_adicional));
        ENThc.setC9monto_por_dormir_minimo(evejtf.getDouble_format_nro_entero(jFmonto_dormir_minimo));
        ENThc.setC10monto_por_dormir_adicional(evejtf.getDouble_format_nro_entero(jFmonto_dormir_adicional));
        ENThc.setC11minuto_minimo(Integer.parseInt(txtminuto_minimo.getText()));
        ENThc.setC12minuto_adicional(Integer.parseInt(txtminuto_adicional.getText()));
        ENThc.setC13minuto_cancelar(Integer.parseInt(txtminuto_cancelar.getText()));
        ENThc.setC14hs_dormir_ingreso_inicio(jFdormir_ingreso_inicio.getText());
        ENThc.setC15hs_dormir_ingreso_final(jFdormir_ingreso_final.getText());
        ENThc.setC16hs_dormir_salida_final(jFdormir_salida_final.getText());
    }
    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOhc.insertar_habitacion_costo(ENThc, tbltabla_pri);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENThc.setC1idhabitacion_costo(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOhc.update_habitacion_costo(ENThc, tbltabla_pri);
        }
    }

    private void seleccionar_tabla() {
        int idhabitacion_costo = eveJtab.getInt_select_id(tbltabla_pri);
        DAOhc.cargar_habitacion_costo(conn, ENThc, idhabitacion_costo);
        txtid.setText(String.valueOf(ENThc.getC1idhabitacion_costo()));
        titulo_formulario(ENThc.getC2fecha_creado(), ENThc.getC3creado_por());
        jCactivo.setSelected(ENThc.getC4activo());
        txtnombre.setText(ENThc.getC5nombre());
        txttipo_habitacion.setText(ENThc.getC6nivel_lujo());
        jFmonto_hora_minimo.setValue(ENThc.getC7monto_por_hora_minimo());
        jFmonto_hora_adicional.setValue(ENThc.getC8monto_por_hora_adicional());
        jFmonto_dormir_minimo.setValue(ENThc.getC9monto_por_dormir_minimo());
        jFmonto_dormir_adicional.setValue(ENThc.getC10monto_por_dormir_adicional());
        txtminuto_minimo.setText(String.valueOf(ENThc.getC11minuto_minimo()));
        txtminuto_adicional.setText(String.valueOf(ENThc.getC12minuto_adicional()));
        txtminuto_cancelar.setText(String.valueOf(ENThc.getC13minuto_cancelar()));
        jFdormir_ingreso_inicio.setText(ENThc.getC14hs_dormir_ingreso_inicio());
        jFdormir_ingreso_final.setText(ENThc.getC15hs_dormir_ingreso_final());
        jFdormir_salida_final.setText(ENThc.getC16hs_dormir_salida_final());
        DAOhc.actualizar_tabla_habitacion_costo_por_hab(conn, tbltabla_sec, idhabitacion_costo);
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void reestableser(){
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        color_tipo_boton(1);
        txtid.setText(null);
        txtnombre.setText(null);
//        txttipo_habitacion.setText(ENTgt.getC6nivel_lujo());
        jFmonto_hora_minimo.setValue(null);
        jFmonto_hora_adicional.setValue(null);
        jFmonto_dormir_minimo.setValue(null);
        jFmonto_dormir_adicional.setValue(null);
        txtminuto_minimo.setText(null);
        txtminuto_adicional.setText(null);
        txtminuto_cancelar.setText(null);
        jFdormir_ingreso_inicio.setText(null);
        jFdormir_ingreso_final.setText(null);
        jFdormir_salida_final.setText(null);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtnombre.grabFocus();
    }
    private void color_tipo_boton(int tipo){
        btntipo_estandar.setBackground(Color.white);
        btntipo_vip.setBackground(Color.white);
        btntipo_lujo.setBackground(Color.white);
        btntipo_penthause.setBackground(Color.white);
        nivel_lujo="sin-tipo";
        if(tipo==1){
            btntipo_estandar.setBackground(Color.yellow);
            nivel_lujo=eveest.getTipo_hab_estandar();
        }
        if(tipo==2){
            btntipo_vip.setBackground(Color.yellow);
            nivel_lujo=eveest.getTipo_hab_vip();
        }
        if(tipo==3){
            btntipo_lujo.setBackground(Color.yellow);
            nivel_lujo=eveest.getTipo_hab_luxury();
        }
        if(tipo==4){
            btntipo_penthause.setBackground(Color.yellow);
            nivel_lujo=eveest.getTipo_hab_penthouse();
        }
        txttipo_habitacion.setText(nivel_lujo);
    }
    private void boton_nuevo(){
        reestableser();
    }
    public FrmHab_costo() {
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

        jTab_principal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insertar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jCactivo = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        txtminuto_minimo = new javax.swing.JTextField();
        txtminuto_adicional = new javax.swing.JTextField();
        txtminuto_cancelar = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jFdormir_ingreso_inicio = new javax.swing.JFormattedTextField();
        jFdormir_ingreso_final = new javax.swing.JFormattedTextField();
        jFdormir_salida_final = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        jFmonto_hora_minimo = new javax.swing.JFormattedTextField();
        jFmonto_hora_adicional = new javax.swing.JFormattedTextField();
        jFmonto_dormir_minimo = new javax.swing.JFormattedTextField();
        jFmonto_dormir_adicional = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        btntipo_estandar = new javax.swing.JButton();
        btntipo_vip = new javax.swing.JButton();
        btntipo_lujo = new javax.swing.JButton();
        btntipo_penthause = new javax.swing.JButton();
        txttipo_habitacion = new javax.swing.JTextField();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla_pri = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbltabla_sec = new javax.swing.JTable();

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
        jLabel2.setText("NOMBRE:");

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnombreKeyReleased(evt);
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

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("TIEMPO TOLERANCIA"));

        txtminuto_minimo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminuto_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminuto_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN MINIMO"));
        txtminuto_minimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtminuto_minimoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtminuto_minimoKeyTyped(evt);
            }
        });

        txtminuto_adicional.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminuto_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminuto_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN ADICIONAL"));
        txtminuto_adicional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtminuto_adicionalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtminuto_adicionalKeyTyped(evt);
            }
        });

        txtminuto_cancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtminuto_cancelar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtminuto_cancelar.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN CANCELAR"));
        txtminuto_cancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtminuto_cancelarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtminuto_cancelarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtminuto_adicional)
                    .addComponent(txtminuto_minimo)
                    .addComponent(txtminuto_cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(txtminuto_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtminuto_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtminuto_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        jFdormir_ingreso_inicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFdormir_ingreso_inicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFdormir_ingreso_inicioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFdormir_ingreso_inicioKeyTyped(evt);
            }
        });

        jFdormir_ingreso_final.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO FINAL"));
        try {
            jFdormir_ingreso_final.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFdormir_ingreso_final.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdormir_ingreso_final.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFdormir_ingreso_final.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFdormir_ingreso_finalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFdormir_ingreso_finalKeyTyped(evt);
            }
        });

        jFdormir_salida_final.setBorder(javax.swing.BorderFactory.createTitledBorder("SALIDA FINAL"));
        try {
            jFdormir_salida_final.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFdormir_salida_final.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFdormir_salida_final.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jFdormir_salida_final.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFdormir_salida_finalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFdormir_salida_finalKeyTyped(evt);
            }
        });

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
                .addComponent(jFdormir_salida_final, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO"));

        jFmonto_hora_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA MINIMO"));
        jFmonto_hora_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFmonto_hora_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_hora_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jFmonto_hora_minimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFmonto_hora_minimoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFmonto_hora_minimoKeyTyped(evt);
            }
        });

        jFmonto_hora_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA ADICIONAL"));
        jFmonto_hora_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFmonto_hora_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_hora_adicional.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jFmonto_hora_adicional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFmonto_hora_adicionalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFmonto_hora_adicionalKeyTyped(evt);
            }
        });

        jFmonto_dormir_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("DORMIR MINIMO"));
        jFmonto_dormir_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFmonto_dormir_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_dormir_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jFmonto_dormir_minimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFmonto_dormir_minimoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFmonto_dormir_minimoKeyTyped(evt);
            }
        });

        jFmonto_dormir_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("DORMIR ADICIONAL"));
        jFmonto_dormir_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        jFmonto_dormir_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_dormir_adicional.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jFmonto_dormir_adicional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFmonto_dormir_adicionalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFmonto_dormir_adicionalKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jFmonto_hora_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFmonto_dormir_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jFmonto_hora_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFmonto_dormir_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmonto_hora_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmonto_dormir_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFmonto_hora_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFmonto_dormir_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 38, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO"));

        btntipo_estandar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_camaamor (2).png"))); // NOI18N
        btntipo_estandar.setText("ESTANDAR");
        btntipo_estandar.setToolTipText("");
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
        btntipo_vip.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btntipo_vip.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btntipo_vip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntipo_vipActionPerformed(evt);
            }
        });

        btntipo_lujo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_diamante.png"))); // NOI18N
        btntipo_lujo.setText("LUXURY");
        btntipo_lujo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btntipo_lujo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btntipo_lujo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntipo_lujoActionPerformed(evt);
            }
        });

        btntipo_penthause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_motel.png"))); // NOI18N
        btntipo_penthause.setText("PENTHOUSE");
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
                        .addComponent(btntipo_penthause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btntipo_estandar, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btntipo_vip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btntipo_lujo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btntipo_penthause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txttipo_habitacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCactivo))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCactivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_tabla, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_insertar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTab_principal.addTab("PRINCIPAL", jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbltabla_sec.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbltabla_sec);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1096, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOhc.ancho_tabla_habitacion_costo(tbltabla_pri);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltabla_priMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_priMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltabla_priMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jFmonto_hora_minimo.grabFocus();
        }
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
//        DAOgt.actualizar_tabla_habitacion_costo_buscar(conn, tbltabla_pri, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        // TODO add your handling code here:
        txtnombre.setText(txtnombre.getText().toUpperCase());
    }//GEN-LAST:event_txtnombreKeyReleased

    private void btntipo_estandarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntipo_estandarActionPerformed
        // TODO add your handling code here:
        color_tipo_boton(1);
    }//GEN-LAST:event_btntipo_estandarActionPerformed

    private void btntipo_vipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntipo_vipActionPerformed
        // TODO add your handling code here:
        color_tipo_boton(2);
    }//GEN-LAST:event_btntipo_vipActionPerformed

    private void btntipo_lujoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntipo_lujoActionPerformed
        // TODO add your handling code here:
        color_tipo_boton(3);
    }//GEN-LAST:event_btntipo_lujoActionPerformed

    private void btntipo_penthauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntipo_penthauseActionPerformed
        // TODO add your handling code here:
        color_tipo_boton(4);
    }//GEN-LAST:event_btntipo_penthauseActionPerformed

    private void jFmonto_hora_minimoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmonto_hora_minimoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter_JFormatted(evt, jFmonto_hora_minimo, jFmonto_hora_adicional);
    }//GEN-LAST:event_jFmonto_hora_minimoKeyPressed

    private void jFmonto_hora_adicionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmonto_hora_adicionalKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter_JFormatted(evt, jFmonto_hora_adicional, jFmonto_dormir_minimo);
    }//GEN-LAST:event_jFmonto_hora_adicionalKeyPressed

    private void jFmonto_dormir_minimoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmonto_dormir_minimoKeyPressed
        // TODO add your handling code here:
         evejtf.saltar_campo_enter_JFormatted(evt, jFmonto_dormir_minimo, jFmonto_dormir_adicional);
    }//GEN-LAST:event_jFmonto_dormir_minimoKeyPressed

    private void jFmonto_dormir_adicionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmonto_dormir_adicionalKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter_JFormatted(evt, jFmonto_dormir_adicional, jFdormir_ingreso_inicio);
    }//GEN-LAST:event_jFmonto_dormir_adicionalKeyPressed

    private void jFdormir_ingreso_inicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFdormir_ingreso_inicioKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter_JFormatted(evt, jFdormir_ingreso_inicio, jFdormir_ingreso_final);
    }//GEN-LAST:event_jFdormir_ingreso_inicioKeyPressed

    private void jFdormir_ingreso_finalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFdormir_ingreso_finalKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter_JFormatted(evt, jFdormir_ingreso_final, jFdormir_salida_final);
    }//GEN-LAST:event_jFdormir_ingreso_finalKeyPressed

    private void jFdormir_salida_finalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFdormir_salida_finalKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtminuto_minimo.grabFocus();
        }
    }//GEN-LAST:event_jFdormir_salida_finalKeyPressed

    private void txtminuto_minimoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_minimoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtminuto_minimo, txtminuto_adicional);
    }//GEN-LAST:event_txtminuto_minimoKeyPressed

    private void txtminuto_adicionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_adicionalKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtminuto_adicional, txtminuto_cancelar);
    }//GEN-LAST:event_txtminuto_adicionalKeyPressed

    private void txtminuto_cancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_cancelarKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtminuto_cancelar, txtnombre);
    }//GEN-LAST:event_txtminuto_cancelarKeyPressed

    private void jFmonto_hora_minimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmonto_hora_minimoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_jFmonto_hora_minimoKeyTyped

    private void jFmonto_hora_adicionalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmonto_hora_adicionalKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_jFmonto_hora_adicionalKeyTyped

    private void jFmonto_dormir_minimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmonto_dormir_minimoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_jFmonto_dormir_minimoKeyTyped

    private void jFmonto_dormir_adicionalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFmonto_dormir_adicionalKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_jFmonto_dormir_adicionalKeyTyped

    private void jFdormir_ingreso_inicioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFdormir_ingreso_inicioKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_jFdormir_ingreso_inicioKeyTyped

    private void jFdormir_ingreso_finalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFdormir_ingreso_finalKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_jFdormir_ingreso_finalKeyTyped

    private void jFdormir_salida_finalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFdormir_salida_finalKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_jFdormir_salida_finalKeyTyped

    private void txtminuto_minimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_minimoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtminuto_minimoKeyTyped

    private void txtminuto_adicionalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_adicionalKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtminuto_adicionalKeyTyped

    private void txtminuto_cancelarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtminuto_cancelarKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtminuto_cancelarKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btntipo_estandar;
    private javax.swing.JButton btntipo_lujo;
    private javax.swing.JButton btntipo_penthause;
    private javax.swing.JButton btntipo_vip;
    private javax.swing.JCheckBox jCactivo;
    private javax.swing.JFormattedTextField jFdormir_ingreso_final;
    private javax.swing.JFormattedTextField jFdormir_ingreso_inicio;
    private javax.swing.JFormattedTextField jFdormir_salida_final;
    private javax.swing.JFormattedTextField jFmonto_dormir_adicional;
    private javax.swing.JFormattedTextField jFmonto_dormir_minimo;
    private javax.swing.JFormattedTextField jFmonto_hora_adicional;
    private javax.swing.JFormattedTextField jFmonto_hora_minimo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTable tbltabla_sec;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtminuto_adicional;
    private javax.swing.JTextField txtminuto_cancelar;
    private javax.swing.JTextField txtminuto_minimo;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txttipo_habitacion;
    // End of variables declaration//GEN-END:variables
}
