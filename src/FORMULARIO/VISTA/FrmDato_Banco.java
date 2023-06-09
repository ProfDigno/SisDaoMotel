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
public class FrmDato_Banco extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private dato_banco ENTdb=new dato_banco();
    private DAO_dato_banco DAOdb=new DAO_dato_banco();
    private BO_dato_banco BOdb=new BO_dato_banco();
    private DAO_banco DAOb=new DAO_banco();
    private EvenJTextField evejtf = new EvenJTextField();
    EvenCombobox evecmb=new EvenCombobox();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario(); //creado_por = ENTusu.getGlobal_nombre();
    private String nombreTabla_pri="DATO-BANCO"; 
    private String nombreTabla_sec="TRANSFERENCIA"; 
    private String creado_por="digno";
    private int fk_idbanco;
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);   
        creado_por = ENTusu.getGlobal_nombre();
        reestableser();
        DAOdb.actualizar_tabla_dato_banco(conn, tbltabla_pri);
        evecmb.cargarCombobox(conn, cmbbanco,"idbanco","nombre","banco", "");
    }
    private void titulo_formulario(String fecha_creado,String creado_por){
        this.setTitle(nombreTabla_pri+" / fecha creado: "+fecha_creado+" / Creado Por: "+creado_por);
    }
    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txttitular, "DEBE CARGAR UN TITULAR")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtdocumento, "DEBE CARGAR UN DOCUMENTO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtnro_cuenta, "DEBE CARGAR UN NRO CUENTA")) {
            return false;
        }
        if(evecmb.getBoo_JCombobox_seleccionar(cmbbanco,"DEBE SELECCIONAR UN BANCO")){
            return false;
        }
        return true;
    }
    private void cargar_dato(){
        fk_idbanco=evecmb.getInt_seleccionar_COMBOBOX(conn, cmbbanco, "idbanco","nombre", "banco");
        ENTdb.setC3creado_por(creado_por);
        ENTdb.setC4titular(txttitular.getText());
        ENTdb.setC5documento(txtdocumento.getText());
        ENTdb.setC6nro_cuenta(txtnro_cuenta.getText());
        ENTdb.setC7activo(jCactivar.isSelected());
        ENTdb.setC8es_guarani(jRguarani.isSelected());
        ENTdb.setC9es_dolar(jRdolar.isSelected());
        ENTdb.setC10fk_idbanco(fk_idbanco);
    }
    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOdb.insertar_dato_banco(ENTdb, tbltabla_pri);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTdb.setC1iddato_banco(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOdb.update_dato_banco(ENTdb, tbltabla_pri);
        }
    }

    private void seleccionar_tabla() {
        int id = eveJtab.getInt_select_id(tbltabla_pri);
        DAOdb.cargar_dato_banco(conn, ENTdb, id);
        txtid.setText(String.valueOf(ENTdb.getC1iddato_banco()));
        txttitular.setText(ENTdb.getC4titular());
        txtdocumento.setText(ENTdb.getC5documento());
        txtnro_cuenta.setText(ENTdb.getC6nro_cuenta());
        jCactivar.setSelected(ENTdb.getC7activo());
        jRguarani.setSelected(ENTdb.getC8es_guarani());
        jRdolar.setSelected(ENTdb.getC9es_dolar());
        evecmb.setSeleccionarCombobox(conn, cmbbanco, "idbanco", "nombre", "banco", ENTdb.getC10fk_idbanco());
        titulo_formulario(ENTdb.getC2fecha_creado(), ENTdb.getC3creado_por());
        String filtro="and pm.iddato_banco="+ENTdb.getC1iddato_banco();
//        DAOp.actualizar_tabla_producto(conn, tbltabla_sec, filtro,"3","");
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void reestableser(){
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        txtid.setText(null);
        txttitular.setText(null);
        txtdocumento.setText(null);
        txtnro_cuenta.setText(null);
        jCactivar.setSelected(true);
        jRguarani.setSelected(true);
        jRdolar.setSelected(false);
        cmbbanco.setSelectedIndex(0);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txttitular.grabFocus();
    }

    private void boton_nuevo(){
        reestableser();
    }
    public FrmDato_Banco() {
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

        gru_money = new javax.swing.ButtonGroup();
        jTab_principal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insertar = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        txttitular = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        cmbbanco = new javax.swing.JComboBox<>();
        txtdocumento = new javax.swing.JTextField();
        txtnro_cuenta = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jRguarani = new javax.swing.JRadioButton();
        jRdolar = new javax.swing.JRadioButton();
        jCactivar = new javax.swing.JCheckBox();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla_pri = new javax.swing.JTable();
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

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txttitular.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txttitular.setBorder(javax.swing.BorderFactory.createTitledBorder("TITULAR:"));
        txttitular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttitularKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttitularKeyReleased(evt);
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

        cmbbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmbbanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbbanco.setBorder(javax.swing.BorderFactory.createTitledBorder("BANCO"));

        txtdocumento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtdocumento.setBorder(javax.swing.BorderFactory.createTitledBorder("DOCUMENTO:"));
        txtdocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtdocumentoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtdocumentoKeyReleased(evt);
            }
        });

        txtnro_cuenta.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtnro_cuenta.setBorder(javax.swing.BorderFactory.createTitledBorder("NRO CUENTA:"));
        txtnro_cuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnro_cuentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnro_cuentaKeyReleased(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("MONEDA:"));

        gru_money.add(jRguarani);
        jRguarani.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRguarani.setSelected(true);
        jRguarani.setText("GUARANI");

        gru_money.add(jRdolar);
        jRdolar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jRdolar.setText("DOLAR");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRguarani)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRdolar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRguarani)
                    .addComponent(jRdolar))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jCactivar.setSelected(true);
        jCactivar.setText("ACTIVAR");

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txttitular)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(45, Short.MAX_VALUE))
                    .addComponent(cmbbanco, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtdocumento)
                    .addComponent(txtnro_cuenta)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCactivar)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnro_cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCactivar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
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
        DAOdb.ancho_tabla_dato_banco(tbltabla_pri);
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

    private void txttitularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttitularKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txttitularKeyPressed

    private void txttitularKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttitularKeyReleased
        // TODO add your handling code here:
        txttitular.setText(txttitular.getText().toUpperCase());
    }//GEN-LAST:event_txttitularKeyReleased

    private void txtdocumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdocumentoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdocumentoKeyPressed

    private void txtdocumentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdocumentoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdocumentoKeyReleased

    private void txtnro_cuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnro_cuentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnro_cuentaKeyPressed

    private void txtnro_cuentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnro_cuentaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnro_cuentaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JComboBox<String> cmbbanco;
    private javax.swing.ButtonGroup gru_money;
    private javax.swing.JCheckBox jCactivar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRdolar;
    private javax.swing.JRadioButton jRguarani;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTable tbltabla_sec;
    private javax.swing.JTextField txtdocumento;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnro_cuenta;
    private javax.swing.JTextField txttitular;
    // End of variables declaration//GEN-END:variables
}
