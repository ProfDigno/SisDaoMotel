/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
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
public class FrmUsuario_crear extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private usuario ENTgt=new usuario();
    private DAO_usuario DAOgt=new DAO_usuario();
    private BO_usuario BOgt=new BO_usuario();
    private EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    private String nombreTabla_pri="CREAR USUARIO"; 
    private String nombreTabla_sec="CAJA"; 
    private String creado_por="digno";
    int fk_idusuario_roll=0;
    int fk_idpersona=0;
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);        
        reestableser();
        DAOgt.actualizar_tabla_usuario(conn, tbltabla_pri);
    }
    private void titulo_formulario(String fecha_creado,String creado_por){
        this.setTitle(nombreTabla_pri+" / fecha creado: "+fecha_creado+" / Creado Por: "+creado_por);
    }
    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtusuario, "DEBE CARGAR UN USUARIO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtpassword, "DEBE CARGAR UN PASSWORD")) {
            return false;
        }
        return true;
    }
    private void cargar_dato(){
        ENTgt.setC3creado_por(creado_por);
        ENTgt.setC4nombre(txtnombre.getText());
        ENTgt.setC5usuario(txtusuario.getText());
        ENTgt.setC6password(txtpassword.getText());
        ENTgt.setC7activo(jCactivo.isSelected());
        ENTgt.setC8fk_idusuario_rol(fk_idusuario_roll);
        ENTgt.setC9fk_idpersona(fk_idpersona);
//        ENTgt.setC5activo(jCactivo.isSelected());
    }
    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOgt.insertar_usuario(ENTgt, tbltabla_pri);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTgt.setC1idusuario(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOgt.update_usuario(ENTgt, tbltabla_pri);
        }
    }

    private void seleccionar_tabla() {
        int id = eveJtab.getInt_select_id(tbltabla_pri);
        DAOgt.cargar_usuario(conn, ENTgt, id);
        txtid.setText(String.valueOf(ENTgt.getC1idusuario()));
        txtnombre.setText(ENTgt.getC4nombre());
        txtusuario.setText(ENTgt.getC5usuario());
//        txtpassword.setText("****************");
        jCactivo.setSelected(ENTgt.getC7activo());
        titulo_formulario(ENTgt.getC2fecha_creado(), ENTgt.getC3creado_por());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void reestableser(){
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        txtid.setText(null);
        txtnombre.setText(null);
        txtusuario.setText(null);
        txtpassword.setText(null);
        jCactivo.setSelected(true);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void boton_nuevo(){
        reestableser();
    }
    public FrmUsuario_crear() {
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
        txtnombre = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jCactivo = new javax.swing.JCheckBox();
        txtusuario = new javax.swing.JTextField();
        txtpassword = new javax.swing.JTextField();
        cmbusuario_roll = new javax.swing.JComboBox<>();
        cmbpersona = new javax.swing.JComboBox<>();
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

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));
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

        txtusuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtusuario.setBorder(javax.swing.BorderFactory.createTitledBorder("USUARIO"));
        txtusuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtusuarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtusuarioKeyReleased(evt);
            }
        });

        txtpassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtpassword.setBorder(javax.swing.BorderFactory.createTitledBorder("PASSWORD"));
        txtpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpasswordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpasswordKeyReleased(evt);
            }
        });

        cmbusuario_roll.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbusuario_roll.setBorder(javax.swing.BorderFactory.createTitledBorder("ROLL"));

        cmbpersona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbpersona.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONA"));

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnombre)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCactivo)
                            .addComponent(txtusuario)
                            .addComponent(txtpassword, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(cmbusuario_roll, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel_insertarLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel_insertarLayout.createSequentialGroup()
                                        .addComponent(btnnuevo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnguardar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btneditar)))
                                .addGap(0, 99, Short.MAX_VALUE))
                            .addComponent(cmbpersona, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbusuario_roll, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbpersona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCactivo)
                .addGap(24, 24, 24)
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
        DAOgt.ancho_tabla_usuario(tbltabla_pri);
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
//        DAOgt.actualizar_tabla_usuario_buscar(conn, tbltabla_pri, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        // TODO add your handling code here:
        txtnombre.setText(txtnombre.getText().toUpperCase());
    }//GEN-LAST:event_txtnombreKeyReleased

    private void txtusuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusuarioKeyPressed

    private void txtusuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusuarioKeyReleased

    private void txtpasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpasswordKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpasswordKeyPressed

    private void txtpasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpasswordKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpasswordKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JComboBox<String> cmbpersona;
    private javax.swing.JComboBox<String> cmbusuario_roll;
    private javax.swing.JCheckBox jCactivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JTextField txtpassword;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
