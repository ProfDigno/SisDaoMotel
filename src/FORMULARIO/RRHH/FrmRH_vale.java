/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.RRHH;

import BASEDATO.EvenConexion;
import FORMULARIO.VISTA.*;
import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.BO_gasto_tipo;
import FORMULARIO.BO.BO_rh_entrada;
import FORMULARIO.BO.BO_rh_vale;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_gasto_tipo;
import FORMULARIO.DAO.DAO_persona;
import FORMULARIO.DAO.DAO_rh_entrada;
import FORMULARIO.DAO.DAO_rh_liquidacion;
import FORMULARIO.DAO.DAO_rh_liquidacion_entrada;
import FORMULARIO.DAO.DAO_rh_liquidacion_vale;
import FORMULARIO.DAO.DAO_rh_vale;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.gasto_tipo;
import FORMULARIO.ENTIDAD.persona;
import FORMULARIO.ENTIDAD.rh_entrada;
import FORMULARIO.ENTIDAD.rh_liquidacion;
import FORMULARIO.ENTIDAD.rh_liquidacion_detalle;
import FORMULARIO.ENTIDAD.rh_liquidacion_entrada;
import FORMULARIO.ENTIDAD.rh_liquidacion_vale;
import FORMULARIO.ENTIDAD.rh_vale;
import FORMULARIO.ENTIDAD.usuario;
import IMPRESORA_POS.PosImprimir_vale;
import java.awt.Color;
import java.awt.event.KeyEvent;
//import FORMULARIO.BO.*;
//import FORMULARIO.DAO.*;
//import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmRH_vale extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private rh_vale ENTrhv = new rh_vale();
    private DAO_rh_vale DAOrhv = new DAO_rh_vale();
    private BO_rh_vale BOrhv = new BO_rh_vale();
    private persona ENTper = new persona();
    private DAO_persona DAOper = new DAO_persona();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenEstado eveest = new EvenEstado();
    private DAO_rh_liquidacion DAOrhli = new DAO_rh_liquidacion();
    private DAO_rh_liquidacion_vale DAOrhlv = new DAO_rh_liquidacion_vale();
    private rh_liquidacion_vale ENTrhlv = new rh_liquidacion_vale();
    private EvenNumero_a_Letra evenrolt=new EvenNumero_a_Letra();
    private rh_liquidacion ENTrhli=new rh_liquidacion();
    private rh_liquidacion_detalle ENTrhlde = new rh_liquidacion_detalle();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private PosImprimir_vale posvale=new PosImprimir_vale();
    private EvenMensajeJoptionpane evemsj=new EvenMensajeJoptionpane();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    Connection conn = ConnPostgres.getConnPosgres();
    EvenConexion eveconn = new EvenConexion();
    private String nombreTabla = "RH VALE";
    private usuario ENTusu = new usuario();
    private String creado_por = "digno";
    private int fk_idusuario;
    private int fk_idpersona;
    private int fk_idrh_liquidacion;
    private int fk_idrh_entrada_abierto;
    private int idrh_vale;
    private void abrir_formulario() {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        fk_idpersona = ENTper.getIdpersona_global();
        this.setTitle(nombreTabla);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        cargar_personal();
    }

   

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtdescripcion, "DEBE CARGAR UNA DESCRIPCION ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto, "DEBE CARGAR UN MONTO ")) {
            return false;
        }
        double monto_vale=evejtf.getDouble_format_nro_entero(txtmonto);
        if(!getBoo_validar_monto_vale(monto_vale)){
            JOptionPane.showMessageDialog(null,"MONTO LIMITE DE VALE SUPERADO");
            return false;
        }
        return true;
    }

    private void cargar_personal() {
        DAOper.cargar_persona(conn, ENTper, fk_idpersona);
        fk_idrh_liquidacion = DAOrhli.getInt_idrh_liquidacion_rh_liquidacion_abierto(conn, fk_idpersona);
        txtpersona.setText(ENTper.getC4nombre());
        txtlimite_vale.setText(evejtf.getString_format_nro_decimal(ENTper.getC13limite_vale()));
        DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, fk_idrh_liquidacion);
        txtsuma_vale.setText(evejtf.getString_format_nro_decimal(ENTrhli.getSum_vale()));
    }
    private boolean getBoo_validar_monto_vale(double monto_vale){
//        double monto_vale=evejtf.getDouble_convertir_JTextField(txtmonto);
        if((ENTper.getC13limite_vale()-ENTrhli.getSum_vale())>=monto_vale){
            txtmonto.setBackground(Color.white);
            return true;
        }else{
            txtmonto.setBackground(Color.yellow);
            return false;
        }
    }
//    private void cargar_dato_caja_detalle() {
//        
//        
//    }
    private void cargar_dato_vale(boolean caja) {
        idrh_vale=(eveconn.getInt_ultimoID_mas_uno(conn, ENTrhv.getTb_rh_vale(), ENTrhv.getId_idrh_vale()));
        double monto_vale=evejtf.getDouble_format_nro_entero(txtmonto);
        int Imonto=(int)monto_vale;
        String Smonto=String.valueOf(Imonto);
        String monto_letra=evenrolt.Convertir(Smonto,true);
        ENTrhv.setC3creado_por(creado_por);
        ENTrhv.setC4descripcion(txtdescripcion.getText());
        ENTrhv.setC5monto_vale(monto_vale);
        ENTrhv.setC6estado(eveest.getEst_Emitido());
        ENTrhv.setC7es_cerrado(false);
        ENTrhv.setC8monto_letra(monto_letra);
        ENTrhv.setC9fk_idpersona(fk_idpersona);
        
        ENTrhlv.setC3creado_por(creado_por);
        ENTrhlv.setC4fk_idrh_liquidacion(fk_idrh_liquidacion);
        ENTrhlv.setC5fk_idrh_vale(idrh_vale);
        
        ENTrhlde.setC3creado_por(creado_por);
        ENTrhlde.setC4descripcion(txtdescripcion.getText());
        ENTrhlde.setC5monto_descuento(0);
        ENTrhlde.setC6monto_vale(monto_vale);
        ENTrhlde.setC7tabla("VALE");
        ENTrhlde.setC8estado(eveest.getEst_Emitido());
        ENTrhlde.setC9fk_idrh_liquidacion(fk_idrh_liquidacion);
        ENTrhlde.setC10fk_idrh_descuento(0);
        ENTrhlde.setC11fk_idrh_vale(idrh_vale);
        
        ENTccd.setC3creado_por(creado_por);
        ENTccd.setC4cerrado_por(eveest.getCaja_VALE());
        ENTccd.setC5es_cerrado(false);
        ENTccd.setC6monto_apertura_caja(0);
        ENTccd.setC7monto_cierre_caja(0);
        ENTccd.setC8monto_ocupa_minimo(0);
        ENTccd.setC9monto_ocupa_adicional(0);
        ENTccd.setC10monto_ocupa_consumo(0);
        ENTccd.setC11monto_ocupa_descuento(0);
        ENTccd.setC12monto_ocupa_adelanto(0);
        ENTccd.setC13monto_gasto(0);
        ENTccd.setC14monto_compra(0);
        ENTccd.setC15monto_vale(monto_vale);
        ENTccd.setC16monto_liquidacion(0);
        ENTccd.setC17estado(eveest.getEst_Emitido());
        ENTccd.setC18descripcion(txtdescripcion.getText());
        ENTccd.setC19fk_idgasto(0);
        ENTccd.setC20fk_idcompra(0);
        ENTccd.setC21fk_idventa(0);
        ENTccd.setC22fk_idusuario(fk_idusuario);
        ENTccd.setC23fk_idrh_vale(idrh_vale);
        ENTccd.setC24fk_idrh_liquidacion(0);
        ENTccd.setC25monto_solo_adelanto(0);
        ENTccd.setC26monto_interno(0);
        ENTccd.setC27fk_idventa_interno(0);
        ENTccd.setC28monto_garantia(0);
        ENTccd.setC29fk_idgarantia(0);
        
        if(caja){
            BOrhv.insertar_rh_vale_caja(ENTrhv, ENTrhlv, ENTrhlde, ENTccd);
        }else{
            BOrhv.insertar_rh_vale(ENTrhv, ENTrhlv,ENTrhlde);
        }
    }

    private void cargar_dato_liquidacion(){
        DAOrhli.actualizar_tabla_rh_liquidacion_vale(conn, FrmRH_liquidacion.tblvale, fk_idrh_liquidacion);
            DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, fk_idrh_liquidacion);
            FrmRH_liquidacion.jfmonto_vale.setValue(ENTrhli.getSum_vale());
            FrmRH_liquidacion.jfmonto_descuento.setValue(ENTrhli.getSum_descuento());
            double liquidacion=ENTrhli.getC11salario_base()-(ENTrhli.getSum_vale()+ENTrhli.getSum_descuento());
            FrmRH_liquidacion.jfmonto_liquidacion.setValue(liquidacion);
            int Imonto=(int)liquidacion;
            String Smonto=String.valueOf(Imonto);
            String monto_letra=evenrolt.Convertir(Smonto,true);
            FrmRH_liquidacion.txtmonto_letra.setText(monto_letra);
    }
//    private void cargar_dato_liquidacion_detalle(){
//        
//    }
    private void boton_guardar_vale(boolean caja) {
        if (validar_guardar()) {
            cargar_dato_vale(caja);
            reestableser();
            cargar_dato_liquidacion();
            int mensaje=(evemsj.getIntMensaje_informacion_3btn("DESEA IMPRIMIR VALE", "VALE", "TICKET", "A4","CANCELAR"));
            if(mensaje==0){
                posvale.boton_imprimir_pos_vale(conn, idrh_vale);
            }
            if(mensaje==1){
                DAOrhv.imprimir_nota_rh_vale(conn, idrh_vale);
            }
            DAOcc.exportar_excel_liquidacion_vale_N3(conn);
            this.dispose();
        }
    }


    private void reestableser() {
        this.setTitle(nombreTabla);
        txtpersona.setText(null);
        txtdescripcion.setText("VALE ADELANTO");
    }


    public FrmRH_vale() {
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
        txtpersona = new javax.swing.JTextField();
        txtcarga = new javax.swing.JTextField();
        txtdescripcion = new javax.swing.JTextField();
        txtmonto = new javax.swing.JTextField();
        txtmonto_letra = new javax.swing.JTextField();
        btnpago_admin = new javax.swing.JButton();
        txtlimite_vale = new javax.swing.JTextField();
        txtsuma_vale = new javax.swing.JTextField();
        btnpago_caja = new javax.swing.JButton();

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

        txtpersona.setEditable(false);
        txtpersona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtpersona.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONAL"));

        txtcarga.setEditable(false);
        txtcarga.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcarga.setBorder(javax.swing.BorderFactory.createTitledBorder("CARGO"));

        txtdescripcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtdescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));

        txtmonto.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtmonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO"));
        txtmonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmontoKeyReleased(evt);
            }
        });

        txtmonto_letra.setEditable(false);
        txtmonto_letra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtmonto_letra.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO LETRA"));

        btnpago_admin.setBackground(new java.awt.Color(204, 255, 204));
        btnpago_admin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpago_admin.setText("PAGO POR ADMIN");
        btnpago_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpago_adminActionPerformed(evt);
            }
        });

        txtlimite_vale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtlimite_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtlimite_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("LIMITE VALE"));

        txtsuma_vale.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtsuma_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtsuma_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA VALE"));

        btnpago_caja.setBackground(new java.awt.Color(255, 255, 153));
        btnpago_caja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnpago_caja.setText("PAGO POR CAJA");
        btnpago_caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpago_cajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(txtpersona, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcarga))
                    .addComponent(txtdescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addComponent(txtmonto_letra, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtmonto, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(txtlimite_vale)
                            .addComponent(btnpago_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(txtsuma_vale))
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnpago_caja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcarga, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtlimite_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsuma_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(txtmonto, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnpago_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnpago_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOtc.ancho_tabla_tercero_ciudad(tbltabla);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtmontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoKeyReleased
        // TODO add your handling code here:
//        evejtf.getDouble_format_nro_entero(txtmonto);
        double monto_vale=evejtf.getDouble_format_nro_entero(txtmonto);
        int Imonto=(int)monto_vale;
        String Smonto=String.valueOf(Imonto);
        String monto_letra=evenrolt.Convertir(Smonto,true);
        txtmonto_letra.setText(monto_letra);
        getBoo_validar_monto_vale(monto_vale);
    }//GEN-LAST:event_txtmontoKeyReleased

    private void btnpago_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpago_adminActionPerformed
        // TODO add your handling code here:
        boton_guardar_vale(false);
    }//GEN-LAST:event_btnpago_adminActionPerformed

    private void btnpago_cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpago_cajaActionPerformed
        // TODO add your handling code here:
        boton_guardar_vale(true);
    }//GEN-LAST:event_btnpago_cajaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnpago_admin;
    private javax.swing.JButton btnpago_caja;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JTextField txtcarga;
    private javax.swing.JTextField txtdescripcion;
    private javax.swing.JTextField txtlimite_vale;
    private javax.swing.JTextField txtmonto;
    private javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtpersona;
    private javax.swing.JTextField txtsuma_vale;
    // End of variables declaration//GEN-END:variables
}
