/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import FORMULARIO.VISTA.BUSCAR.JDiaBuscar;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmGasto extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private gasto ENTgt=new gasto();
    private DAO_gasto DAOgt=new DAO_gasto();
    private BO_gasto BOgt=new BO_gasto();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenNumero_a_Letra nroLetra=new EvenNumero_a_Letra();
    Connection conn = ConnPostgres.getConnPosgres();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private usuario ENTusu = new usuario();
    private EvenEstado eveest = new EvenEstado();
    private String nombreTabla_pri="GASTO"; 
    private String nombreTabla_sec="CAJA GASTO"; 
    private String creado_por="digno";
    private int fk_idusuario=0;
    private static int fk_idgasto_tipo;
    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        creado_por=ENTusu.getGlobal_nombre();
        fk_idusuario=ENTusu.getGlobal_idusuario();
        evetbl.centrar_formulario_internalframa(this);        
        reestableser();
        DAOgt.actualizar_tabla_gasto(conn, tbltabla_pri);
    }

    public static int getFk_idgasto_tipo() {
        return fk_idgasto_tipo;
    }

    public static void setFk_idgasto_tipo(int fk_idgasto_tipo) {
        FrmGasto.fk_idgasto_tipo = fk_idgasto_tipo;
    }
    
    private void titulo_formulario(String fecha_creado,String creado_por){
        this.setTitle(nombreTabla_pri+" / fecha creado: "+fecha_creado+" / Creado Por: "+creado_por);
    }
    private boolean validar_guardar() {
        if(getFk_idgasto_tipo()==0){
            txtgasto_tipo.setText(null);
            if (evejtf.getBoo_JTextField_vacio(txtgasto_tipo, "DEBE CARGO CORRECTAMENTE UN TIPO")) {
               return false;
            }
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_gasto, "DEBE CARGAR UN MONTO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtadescripcion,"DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        return true;
    }
    private void cargar_dato(){
        String monto_gasto=evejtf.getString_format_nro_entero1(txtmonto_gasto);
        String monto_letra=nroLetra.Convertir(monto_gasto,true);
        txtmonto_letra.setText(monto_letra);
        ENTgt.setC3creado_por(creado_por);
        ENTgt.setC4monto_gasto(Double.parseDouble(monto_gasto));
        ENTgt.setC5monto_letra(monto_letra);
        ENTgt.setC6descripcion(txtadescripcion.getText());
        ENTgt.setC7estado(eveest.getEst_Emitido());
        ENTgt.setC8fk_idgasto_tipo(fk_idgasto_tipo);
        ENTgt.setC9fk_idusuario(fk_idusuario);
    }
    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOgt.insertar_gasto(ENTgt, tbltabla_pri);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTgt.setC1idgasto(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOgt.update_gasto(ENTgt, tbltabla_pri);
        }
    }

    private void seleccionar_tabla() {
        int id = eveJtab.getInt_select_id(tbltabla_pri);
        DAOgt.cargar_gasto(conn, ENTgt, id);
        txtid.setText(String.valueOf(ENTgt.getC1idgasto()));
//        txtgasto_tipo.setText(ENTgt.getC4nombre());
//        jCactivo.setSelected(ENTgt.getC5activo());
        titulo_formulario(ENTgt.getC2fecha_creado(), ENTgt.getC3creado_por());
        btnguardar.setEnabled(false);
        btnanular.setEnabled(true);
    }
    private void reestableser(){
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        setFk_idgasto_tipo(0);
        txtid.setText(null);
        txtgasto_tipo.setText(null);
        txtmonto_gasto.setText(null);
        txtmonto_letra.setText(null);
        txtadescripcion.setText(null);
        btnguardar.setEnabled(true);
        btnanular.setEnabled(false);
        txtgasto_tipo.grabFocus();
    }

    private void boton_nuevo(){
        reestableser();
    }
    public FrmGasto() {
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
        txtgasto_tipo = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        btnnuevo_gtipo = new javax.swing.JButton();
        btnbuscar_gtipo = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtadescripcion = new javax.swing.JTextArea();
        txtmonto_gasto = new javax.swing.JTextField();
        txtmonto_letra = new javax.swing.JTextField();
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

        txtgasto_tipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtgasto_tipo.setBorder(javax.swing.BorderFactory.createTitledBorder("GASTO TIPO:"));
        txtgasto_tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtgasto_tipoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtgasto_tipoKeyReleased(evt);
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

        btnanular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btnanular.setText("ANULAR");
        btnanular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnanular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });

        btnnuevo_gtipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N

        btnbuscar_gtipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_gtipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_gtipoActionPerformed(evt);
            }
        });

        txtadescripcion.setColumns(20);
        txtadescripcion.setRows(5);
        txtadescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        jScrollPane3.setViewportView(txtadescripcion);

        txtmonto_gasto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtmonto_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO GASTO"));
        txtmonto_gasto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyTyped(evt);
            }
        });

        txtmonto_letra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtmonto_letra.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO LETRA:"));

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(txtgasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_gtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscar_gtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE))
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
                                .addComponent(btnanular))
                            .addComponent(txtmonto_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtmonto_letra))
                .addContainerGap())
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo_gtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtgasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_gtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btnanular))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
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
        DAOgt.ancho_tabla_gasto(tbltabla_pri);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltabla_priMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_priMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltabla_priMouseReleased

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btnanularActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtgasto_tipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtgasto_tipoKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txtgasto_tipoKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
//        DAOgt.actualizar_tabla_gasto_buscar(conn, tbltabla_pri, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtgasto_tipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtgasto_tipoKeyReleased
        // TODO add your handling code here:
        txtgasto_tipo.setText(txtgasto_tipo.getText().toUpperCase());
    }//GEN-LAST:event_txtgasto_tipoKeyReleased

    private void txtmonto_gastoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyReleased
        // TODO add your handling code here:
        String monto=evejtf.getString_format_nro_entero1(txtmonto_gasto);
        String monto_letra=nroLetra.Convertir(monto,true);
        txtmonto_letra.setText(monto_letra);
    }//GEN-LAST:event_txtmonto_gastoKeyReleased

    private void txtmonto_gastoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_gastoKeyTyped

    private void btnbuscar_gtipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_gtipoActionPerformed
        // TODO add your handling code here:
        vbus.setNombre_tabla("GATO TIPO");
        vbus.setTipo_tabla(7);
        JDiaBuscar frm=new JDiaBuscar(null,true);
        frm.setVisible(true);
    }//GEN-LAST:event_btnbuscar_gtipoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar_gtipo;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_gtipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTable tbltabla_sec;
    private javax.swing.JTextArea txtadescripcion;
    private javax.swing.JTextField txtbuscar;
    public static javax.swing.JTextField txtgasto_tipo;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtmonto_gasto;
    private javax.swing.JTextField txtmonto_letra;
    // End of variables declaration//GEN-END:variables
}
