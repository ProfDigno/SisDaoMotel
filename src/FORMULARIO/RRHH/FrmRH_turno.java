/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.RRHH;

import FORMULARIO.VISTA.*;
import BASEDATO.LOCAL.ConnPostgres;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.BO_gasto_tipo;
import FORMULARIO.BO.BO_rh_turno;
import FORMULARIO.DAO.DAO_gasto_tipo;
import FORMULARIO.DAO.DAO_rh_turno;
import FORMULARIO.ENTIDAD.gasto_tipo;
import FORMULARIO.ENTIDAD.rh_turno;
import FORMULARIO.ENTIDAD.usuario;
//import FORMULARIO.BO.*;
//import FORMULARIO.DAO.*;
//import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRH_turno extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private rh_turno ENTrht=new rh_turno();
    private DAO_rh_turno DAOrht=new DAO_rh_turno();
    private BO_rh_turno BOrht=new BO_rh_turno();
    private EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    private usuario ENTusu = new usuario();
    private String nombreTabla="RH TURNO"; 
    private String creado_por = "digno";
    private void abrir_formulario() {
        this.setTitle(nombreTabla);
        creado_por = ENTusu.getGlobal_nombre();
        evetbl.centrar_formulario_internalframa(this);        
        reestableser();
        DAOrht.actualizar_tabla_rh_turno(conn, tbltabla);
    }
    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtturno, "DEBE CARGAR UN TURNO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txthora_inicio, "DEBE CARGAR UNA HORA DE INICIO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txthora_salida, "DEBE CARGAR UNA HORA SALIDA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttolera_llega_tarde, "DEBE CARGAR UNA TOLERANCIA LLEGA TARDE MIN")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txttolera_sale_antes, "DEBE CARGAR UNA TOLERANCIA SALE ANTES MIN")) {
            return false;
        }
        
        return true;
    }
    private void cargar_dato(){
        ENTrht.setC3creado_por(creado_por);
        ENTrht.setC4turno(txtturno.getText());
        ENTrht.setC5hora_inicio(txthora_inicio.getText());
        ENTrht.setC6hora_salida(txthora_salida.getText());
        ENTrht.setC7tolera_llega_tarde(Integer.parseInt(txttolera_llega_tarde.getText()));
        ENTrht.setC8tolera_sale_antes(Integer.parseInt(txttolera_sale_antes.getText()));
        ENTrht.setC9domindo(jCdomindo.isSelected());
        ENTrht.setC10lunes(jClunes.isSelected());
        ENTrht.setC11martes(jCmartes.isSelected());
        ENTrht.setC12miercoles(jCmiercoles.isSelected());
        ENTrht.setC13jueves(jCjueves.isSelected());
        ENTrht.setC14viernes(jCviernes.isSelected());
        ENTrht.setC15sabado(jCsabado.isSelected());
        ENTrht.setC16activo(jCactivo.isSelected());
        ENTrht.setC17incluye_dos_dia(jCincluye_dos_dia.isSelected());
    }
    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOrht.insertar_rh_turno(ENTrht, tbltabla);
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTrht.setC1idrh_turno(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOrht.update_rh_turno(ENTrht, tbltabla);
        }
    }

    private void seleccionar_tabla() {
        int id = eveJtab.getInt_select_id(tbltabla);
        DAOrht.cargar_rh_turno(conn, ENTrht, id);
        txtid.setText(String.valueOf(ENTrht.getC1idrh_turno()));
        txtturno.setText(ENTrht.getC4turno());
        txthora_inicio.setText(ENTrht.getC5hora_inicio());
        txthora_salida.setText(ENTrht.getC6hora_salida());
        txttolera_llega_tarde.setText(String.valueOf(ENTrht.getC7tolera_llega_tarde()));
        txttolera_sale_antes.setText(String.valueOf(ENTrht.getC8tolera_sale_antes()));
        jCdomindo.setSelected(ENTrht.getC9domindo());
        jClunes.setSelected(ENTrht.getC10lunes());
        jCmartes.setSelected(ENTrht.getC11martes());
        jCmiercoles.setSelected(ENTrht.getC12miercoles());
        jCjueves.setSelected(ENTrht.getC13jueves());
        jCviernes.setSelected(ENTrht.getC14viernes());
        jCsabado.setSelected(ENTrht.getC15sabado());
        jCactivo.setSelected(ENTrht.getC16activo());
        jCincluye_dos_dia.setSelected(ENTrht.getC17incluye_dos_dia());
        this.setTitle(nombreTabla+"/fecha creado:"+ENTrht.getC2fecha_creado()+"/ Creado Por:"+ENTrht.getC3creado_por());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }
    private void reestableser(){
        this.setTitle(nombreTabla);
        txtid.setText(null);
        txtturno.setText(null);
        txthora_inicio.setText("00:00:00");
        txthora_salida.setText("00:00:00");
        txttolera_llega_tarde.setText(String.valueOf(0));
        txttolera_sale_antes.setText(String.valueOf(0));
        jCdomindo.setSelected(true);
        jClunes.setSelected(true);
        jCmartes.setSelected(true);
        jCmiercoles.setSelected(true);
        jCjueves.setSelected(true);
        jCviernes.setSelected(true);
        jCsabado.setSelected(true);
        jCactivo.setSelected(true);
        jCincluye_dos_dia.setSelected(false);
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtturno.grabFocus();
    }

    private void boton_nuevo(){
        reestableser();
    }
    public FrmRH_turno() {
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

        panel_insertar = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        txtturno = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jCactivo = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        txthora_inicio = new javax.swing.JTextField();
        txthora_salida = new javax.swing.JTextField();
        txttolera_llega_tarde = new javax.swing.JTextField();
        txttolera_sale_antes = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jCdomindo = new javax.swing.JCheckBox();
        jClunes = new javax.swing.JCheckBox();
        jCmartes = new javax.swing.JCheckBox();
        jCmiercoles = new javax.swing.JCheckBox();
        jCjueves = new javax.swing.JCheckBox();
        jCviernes = new javax.swing.JCheckBox();
        jCsabado = new javax.swing.JCheckBox();
        jCincluye_dos_dia = new javax.swing.JCheckBox();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla = new javax.swing.JTable();

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
        txtid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createTitledBorder("ID:"));

        txtturno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtturno.setBorder(javax.swing.BorderFactory.createTitledBorder("TURNO:"));
        txtturno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtturnoKeyPressed(evt);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("HORAS:"));

        txthora_inicio.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA INICIO:"));

        txthora_salida.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA SALIDA:"));

        txttolera_llega_tarde.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN LLEGA TARDE"));

        txttolera_sale_antes.setBorder(javax.swing.BorderFactory.createTitledBorder("MIN SALE ANTES:"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txthora_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txthora_salida, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txttolera_llega_tarde, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttolera_sale_antes, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txthora_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txthora_salida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttolera_llega_tarde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttolera_sale_antes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("DIAS:"));

        jCdomindo.setText("DOMINGO");

        jClunes.setText("LUNES");

        jCmartes.setText("MARTES");

        jCmiercoles.setText("MIERCOLES");

        jCjueves.setText("JUEVES");

        jCviernes.setText("VIERNES");

        jCsabado.setText("SABADO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCdomindo)
                    .addComponent(jClunes)
                    .addComponent(jCmartes)
                    .addComponent(jCmiercoles)
                    .addComponent(jCjueves)
                    .addComponent(jCviernes)
                    .addComponent(jCsabado))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jCdomindo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jClunes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCmartes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCmiercoles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCjueves)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCviernes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCsabado))
        );

        jCincluye_dos_dia.setSelected(true);
        jCincluye_dos_dia.setText("INCLUYE DOS DIA");

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar))
                    .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtturno, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCactivo)
                            .addComponent(jCincluye_dos_dia)))
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addComponent(txtturno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jCincluye_dos_dia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCactivo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar))
                .addContainerGap())
        );

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbltabla.setModel(new javax.swing.table.DefaultTableModel(
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
        tbltabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbltablaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbltabla);

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOtc.ancho_tabla_tercero_ciudad(tbltabla);
        DAOrht.ancho_tabla_rh_turno(tbltabla);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltablaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltablaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltablaMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtturnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtturnoKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txtturnoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JCheckBox jCactivo;
    private javax.swing.JCheckBox jCdomindo;
    private javax.swing.JCheckBox jCincluye_dos_dia;
    private javax.swing.JCheckBox jCjueves;
    private javax.swing.JCheckBox jClunes;
    private javax.swing.JCheckBox jCmartes;
    private javax.swing.JCheckBox jCmiercoles;
    private javax.swing.JCheckBox jCsabado;
    private javax.swing.JCheckBox jCviernes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla;
    private javax.swing.JTextField txthora_inicio;
    private javax.swing.JTextField txthora_salida;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txttolera_llega_tarde;
    private javax.swing.JTextField txttolera_sale_antes;
    private javax.swing.JTextField txtturno;
    // End of variables declaration//GEN-END:variables
}
