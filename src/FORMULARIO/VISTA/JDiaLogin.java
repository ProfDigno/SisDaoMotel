/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import BASEDATO.LOCAL.VariablesBD;
import Config_JSON.json_array_conexion;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class JDiaLogin extends javax.swing.JDialog {

    /**
     * Creates new form JDiaLogin
     */
    private EvenJFRAME evetbl = new EvenJFRAME();
    usuario ENTusu = new usuario();
    private BO_usuario bo_usu = new BO_usuario();
    private DAO_usuario dao_usu = new DAO_usuario();//dao_usuario
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenMensajeJoptionpane evemsj = new EvenMensajeJoptionpane();
    Connection conn = ConnPostgres.getConnPosgres();
    private EvenConexion eveconn = new EvenConexion();
    private VariablesBD var = new VariablesBD();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    json_array_conexion jscon = new json_array_conexion();

    private void abrir_formulario() {
        this.setTitle("INGRESAR USUARIO");
        habilitar_evento_menu_bloquear();
    }

    private boolean validar_ingreso() {
        if (evejtf.getBoo_JTextField_vacio(txtusuario, "DEBE CARGAR UN USUARIO")) {
            return false;
        }
        if (evejtf.getBoo_JPasswordField_vacio(jPassword, "DEBE CARGAR UNA SENHA")) {
            return false;
        }
        return true;
    }

    private void buscar_usuario() {
        if (dao_usu.getBoolean_buscar_usuario_existente(conn, ENTusu)) {
            JOptionPane.showMessageDialog(this, "BIENVENIDO\n" + ENTusu.getGlobal_nombre());
            FrmMenuMotel.lblusuario.setText(ENTusu.getGlobal_idusuario()+"-"+ENTusu.getGlobal_nombre());
            if (!ENTusu.getC7activo()) {
                JOptionPane.showMessageDialog(this, "ESTE USUARIO NO ESTA ACTIVO", "USUARIO", JOptionPane.WARNING_MESSAGE);
                txtusuario.setText(null);
                jPassword.setText(null);
                txtusuario.grabFocus();
            } else {
                if(DAOccd.getBoo_es_caja_cerrado_por_usu(conn, ENTusu.getGlobal_idusuario())){
                    JDiaCajaApertura frm=new JDiaCajaApertura(null,true);
                    frm.setVisible(true);
                }
                habilitar_evento_menu();
                this.dispose();
                if (jscon.getCrear_backup().equals("SI")) {
                    evetbl.abrir_TablaJinternal(new FrmCrearBackupJson());
                }
                
            }
        } else {
            txtusuario.setText(null);
            jPassword.setText(null);
            txtusuario.grabFocus();
        }
    }

    private void boton_entrar() {
        if (validar_ingreso()) {
            ENTusu.setC5usuario(txtusuario.getText());
            String senha = String.valueOf(jPassword.getPassword());
            ENTusu.setC6password(senha);
            buscar_usuario();
        }
    }

    private void habilitar_evento_menu_bloquear() {
        FrmMenuMotel.barra_menu_principal.setVisible(false);
        FrmMenuMotel.panel_acceso_rapido.setVisible(false);
    }

    private void habilitar_evento_menu() {
        FrmMenuMotel.barra_menu_principal.setVisible(true);
        FrmMenuMotel.panel_acceso_rapido.setVisible(true);
//        FrmMenuDespacho.btnliquidacion_proforma.setEnabled(dao_usu.getBoolean_hab_evento(conn, "1"));
    }

    public JDiaLogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        panel_insert = new javax.swing.JPanel();
        txtusuario = new javax.swing.JTextField();
        jPassword = new javax.swing.JPasswordField();
        btnentrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(102, 204, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panel_insert.setBackground(new java.awt.Color(153, 204, 255));
        panel_insert.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESO DE USUARIO"));

        txtusuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtusuario.setBorder(javax.swing.BorderFactory.createTitledBorder("USUARIO:"));
        txtusuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtusuarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtusuarioKeyReleased(evt);
            }
        });

        jPassword.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPassword.setBorder(javax.swing.BorderFactory.createTitledBorder("PASSWORD:"));
        jPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordKeyReleased(evt);
            }
        });

        btnentrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnentrar.setText("ENTRAR");
        btnentrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnentrarActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_user.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/48_key.png"))); // NOI18N

        javax.swing.GroupLayout panel_insertLayout = new javax.swing.GroupLayout(panel_insert);
        panel_insert.setLayout(panel_insertLayout);
        panel_insertLayout.setHorizontalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnentrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_insertLayout.createSequentialGroup()
                        .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertLayout.setVerticalGroup(
            panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel_insertLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(btnentrar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panel_insert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnentrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnentrarActionPerformed
        // TODO add your handling code here:
        boton_entrar();
    }//GEN-LAST:event_btnentrarActionPerformed

    private void txtusuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jPassword.grabFocus();
        }
    }//GEN-LAST:event_txtusuarioKeyPressed

    private void jPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_entrar();
        }
    }//GEN-LAST:event_jPasswordKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (evemsj.getBooMensaje_question("DESEAS SALIR DEL SISTEMA", "SALIR", "ACEPTAR", "CANCELAR")) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void txtusuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyReleased
        // TODO add your handling code here:
        txtusuario.setText(txtusuario.getText().toUpperCase());
    }//GEN-LAST:event_txtusuarioKeyReleased

    private void jPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDiaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDiaLogin dialog = new JDiaLogin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnentrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JPanel panel_insert;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
