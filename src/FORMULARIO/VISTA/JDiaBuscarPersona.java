/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.BO_persona;
import FORMULARIO.DAO.DAO_persona;
import FORMULARIO.ENTIDAD.persona;
import FORMULARIO.ENTIDAD.usuario;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class JDiaBuscarPersona extends javax.swing.JDialog {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private persona ENTper = new persona();
    private DAO_persona DAOper = new DAO_persona();
    private BO_persona BOper = new BO_persona();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenEstado eveest = new EvenEstado();
    private EvenConexion eveconn = new EvenConexion();
    Connection conn = ConnPostgres.getConnPosgres();
    usuario ENTusu = new usuario();
    private String nombreTabla_pri = "PERSONA";
    private String nombreTabla_sec = "FILTRO";
    private String creado_por = "digno";

    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
//        evetbl.centrar_formulario_internalframa(this);  
        creado_por = ENTusu.getGlobal_nombre();
        actualizar_tabla_persona();
    }

    private void actualizar_tabla_persona() {
        String sql = "select per.idpersona as idp,per.nombre as nombre,per.ruc as ruc,\n"
                + "per.telefono as telefono,per.tipo_persona as tipo \n"
                + "from persona per "
                + "where per.tipo_persona='"+ENTper.getBus_tipo_persona()+"' \n";
        eveconn.Select_cargar_jtable(conn, sql, tblbuscarpersona);
        ancho_tabla_persona(tblbuscarpersona);
    }
    private void seleccionar_tabla(){
        if(tblbuscarpersona.getSelectedRow()>=0){
            int idpersona=eveJtab.getInt_select_id(tblbuscarpersona);
            String nombre = eveJtab.getString_select(tblbuscarpersona,1);
            String ruc = eveJtab.getString_select(tblbuscarpersona,2);
            ENTper.setBus_idpersona(idpersona);
            if(ENTper.getBus_quien_llama()==1){
                FrmCompra.txtproveedor_nombre.setText(nombre);
                FrmCompra.txtproveedor_ruc.setText(ruc);
            }
            if(ENTper.getBus_quien_llama()==2){
                FrmCompra_reposicion.txtproveedor_nombre.setText(nombre);
                FrmCompra_reposicion.txtproveedor_ruc.setText(ruc);
            }
        }
    }
    public void ancho_tabla_persona(JTable tbltabla) {
        int Ancho[] = {5,50,15,15,15};
        eveJtab.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public JDiaBuscarPersona(java.awt.Frame parent, boolean modal) {
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbuscarpersona = new javax.swing.JTable();
        txtbusca_nombre = new javax.swing.JTextField();
        txtbusca_ruc = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR PERSONA"));

        tblbuscarpersona.setModel(new javax.swing.table.DefaultTableModel(
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
        tblbuscarpersona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblbuscarpersonaMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblbuscarpersona);

        txtbusca_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));

        txtbusca_ruc.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC"));

        jButton1.setText("SELECCIONAR Y SALIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtbusca_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbusca_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbusca_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbusca_ruc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblbuscarpersonaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscarpersonaMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblbuscarpersonaMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        seleccionar_tabla();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(JDiaBuscarPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscarPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDiaBuscarPersona dialog = new JDiaBuscarPersona(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblbuscarpersona;
    private javax.swing.JTextField txtbusca_nombre;
    private javax.swing.JTextField txtbusca_ruc;
    // End of variables declaration//GEN-END:variables
}
