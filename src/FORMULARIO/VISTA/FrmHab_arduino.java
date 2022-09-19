/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import Evento.Combobox.EvenCombobox;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmHab_arduino extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private habitacion_arduino ENTgt=new habitacion_arduino();
    private DAO_habitacion_arduino DAOgt=new DAO_habitacion_arduino();
    private BO_habitacion_arduino BOgt=new BO_habitacion_arduino();
     private habitacion_item_sensor_pino ENTpino=new habitacion_item_sensor_pino();
    private DAO_habitacion_item_sensor_pino DAOpino=new DAO_habitacion_item_sensor_pino();
    private BO_habitacion_item_sensor_pino BOipino=new BO_habitacion_item_sensor_pino();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenCombobox evecmb=new EvenCombobox();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario();
    private String nombreTabla_pri="ARDUINO"; 
    private String nombreTabla_sec="ITEM PINO"; 
    private String creado_por="digno";
    int fk_idhabitacion_mini_pc=0;
    String mini_pc_id="idhabitacion_mini_pc";
    String mini_pc_nombre="placa_nombre";
    String mini_pc_tabla="habitacion_mini_pc";
    String mini_pc_where="";
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);  
        creado_por=ENTusu.getGlobal_nombre();
        evecmb.cargarCombobox(conn, cmbmini_pc, mini_pc_id,mini_pc_nombre, mini_pc_tabla,mini_pc_where);
        reestableser();
        DAOgt.actualizar_tabla_habitacion_arduino(conn, tbltabla_pri);
    }
    private void titulo_formulario(String fecha_creado,String creado_por){
        this.setTitle(nombreTabla_pri+" / fecha creado: "+fecha_creado+" / Creado Por: "+creado_por);
    }
    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtusb_puerto, "DEBE CARGAR UN PUERTO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtusb_bps, "DEBE CARGAR UN BPS")) {
            return false;
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbmini_pc,"SE DEBE SELECCIONAR UNA PLACA MINI PC")){
            return false;
        }else{
            fk_idhabitacion_mini_pc=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbmini_pc,  mini_pc_id,mini_pc_nombre, mini_pc_tabla);
        }
        return true;
    }
    private void cargar_dato(){
        ENTgt.setC3creado_por(creado_por);
        ENTgt.setC4nombre(txtnombre.getText());
        ENTgt.setC5usb_puerto(txtusb_puerto.getText());
        ENTgt.setC6usb_bps(Integer.parseInt(txtusb_bps.getText()));
        ENTgt.setC7fk_idhabitacion_mini_pc(fk_idhabitacion_mini_pc);
    }
    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOgt.insertar_habitacion_arduino(ENTgt, tbltabla_pri);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTgt.setC1idhabitacion_arduino(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOgt.update_habitacion_arduino(ENTgt, tbltabla_pri);
        }
    }

    private void seleccionar_tabla() {
        int idhabitacion_arduino = eveJtab.getInt_select_id(tbltabla_pri);
        DAOgt.cargar_habitacion_arduino(conn, ENTgt, idhabitacion_arduino);
        txtid.setText(String.valueOf(ENTgt.getC1idhabitacion_arduino()));
        txtnombre.setText(ENTgt.getC4nombre());
        txtusb_puerto.setText(ENTgt.getC5usb_puerto());
        txtusb_bps.setText(String.valueOf(ENTgt.getC6usb_bps()));
        evecmb.setSeleccionarCombobox(conn, cmbmini_pc,  mini_pc_id,mini_pc_nombre, mini_pc_tabla, ENTgt.getC7fk_idhabitacion_mini_pc());
        titulo_formulario(ENTgt.getC2fecha_creado(), ENTgt.getC3creado_por());
        DAOpino.actualizar_tabla_habitacion_item_sensor_pino_arduino(conn, tbltabla_sec, idhabitacion_arduino);
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void reestableser(){
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        txtid.setText(null);
        txtnombre.setText(null);
        txtusb_puerto.setText(null);
        txtusb_bps.setText(null);
        cmbmini_pc.setSelectedIndex(0);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void boton_nuevo(){
        reestableser();
    }
    public FrmHab_arduino() {
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
        jLabel4 = new javax.swing.JLabel();
        txtusb_puerto = new javax.swing.JTextField();
        txtusb_bps = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbmini_pc = new javax.swing.JComboBox<>();
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

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("USB PUERTO:");

        txtusb_puerto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtusb_puerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtusb_puertoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtusb_puertoKeyReleased(evt);
            }
        });

        txtusb_bps.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtusb_bps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtusb_bpsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtusb_bpsKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("USB BPS:");

        jLabel6.setText("MINI PC:");

        cmbmini_pc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnombre)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(btnnuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnguardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btneditar))
                            .addComponent(jLabel4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtusb_puerto, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtusb_bps, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbmini_pc, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 41, Short.MAX_VALUE))))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtusb_puerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtusb_bps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbmini_pc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar))
                .addContainerGap())
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 148, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 17, Short.MAX_VALUE))
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
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
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
            .addComponent(jTab_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        DAOgt.ancho_tabla_habitacion_arduino(tbltabla_pri);
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
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
//        DAOgt.actualizar_tabla_habitacion_arduino_buscar(conn, tbltabla_pri, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        // TODO add your handling code here:
        txtnombre.setText(txtnombre.getText().toUpperCase());
    }//GEN-LAST:event_txtnombreKeyReleased

    private void txtusb_puertoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusb_puertoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusb_puertoKeyPressed

    private void txtusb_puertoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusb_puertoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusb_puertoKeyReleased

    private void txtusb_bpsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusb_bpsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusb_bpsKeyPressed

    private void txtusb_bpsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusb_bpsKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusb_bpsKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JComboBox<String> cmbmini_pc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTable tbltabla_sec;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtusb_bps;
    private javax.swing.JTextField txtusb_puerto;
    // End of variables declaration//GEN-END:variables
}
