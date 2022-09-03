/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA.BUSCAR;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.VISTA.FrmGasto;
import FORMULARIO.VISTA.FrmHab_crear;
import FORMULARIO.VISTA.FrmProd_dato;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class JDiaBuscar extends javax.swing.JDialog {

    private EvenJTextField evejtf = new EvenJTextField();
    Connection conn = ConnPostgres.getConnPosgres();
    private EvenConexion eveconn = new EvenConexion();
    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private ClaVarBuscar vbus = new ClaVarBuscar();

    private void abrir_formulario() {
        this.setTitle("BUSCAR : " + vbus.getNombre_tabla());
        txtbuscar.setText(vbus.getPre_busqueda());
        actualizar_buscar();
    }

    private void actualizar_buscar() {
        int cant_columna = 0;
        String sql = "";
        String buscar = txtbuscar.getText();
        if (vbus.getTipo_tabla() == 1) {
            cant_columna = 2;
            sql = "select idproducto_categoria as idpc,"
                    + "nombre as categoria "
                    + "from producto_categoria "
                    + "where activo=true "
                    + "and nombre ilike'%" + buscar + "%' "
                    + "order by orden desc;";
        }
        if (vbus.getTipo_tabla() == 2) {
            cant_columna = 2;
            sql = "select idproducto_unidad as idpu,"
                    + "nombre as unidad "
                    + "from producto_unidad "
                    + "where activo=true "
                    + "and nombre ilike'%" + buscar + "%' "
                    + "order by orden desc;";
        }
        if (vbus.getTipo_tabla() == 3) {
            cant_columna = 2;
            sql = "select idproducto_marca as idpm,"
                    + "nombre as marca "
                    + "from producto_marca "
                    + "where activo=true "
                    + "and nombre ilike'%" + buscar + "%' "
                    + "order by orden desc;";
        }
        if (vbus.getTipo_tabla() == 4) {
            cant_columna = 6;
            sql = "SELECT p.idproducto as idp,p.codigo_barra as codbarra,p.nombre as producto,\n"
                    + "pc.nombre as categoria,\n"
                    + "p.precio_venta as pventa,\n"
                    + "p.stock_actual as stock\n"
                    + "FROM producto p,producto_categoria pc,producto_unidad pu,producto_marca pm\n"
                    + "where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                    + "and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                    + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                    + "and p.nombre ilike'%" + buscar + "%' "
                    + "and p.es_insumo=true\n"
                    + "order by 3 desc;";
        }
        if (vbus.getTipo_tabla() == 5) {
            cant_columna = 6;
            sql = "SELECT p.idproducto as idp,p.codigo_barra as codbarra,p.nombre as producto,\n"
                    + "pc.nombre as categoria,\n"
                    + "p.precio_venta as pventa,\n"
                    + "p.stock_actual as stock\n"
                    + "FROM producto p,producto_categoria pc,producto_unidad pu,producto_marca pm\n"
                    + "where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                    + "and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                    + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                    + "and p.nombre ilike'%" + buscar + "%' "
                    //                    + "and p.es_insumo=true\n"
                    + "order by 3 desc;";
        }
        if (vbus.getTipo_tabla() == 6) {
            cant_columna = 6;
            sql = "SELECT p.idproducto as idp,p.codigo_barra as codbarra,p.nombre as producto,\n"
                    + "pc.nombre as categoria,\n"
                    + "p.precio_venta as pventa,\n"
                    + "p.stock_actual as stock\n"
                    + "FROM producto p,producto_categoria pc,producto_unidad pu,producto_marca pm\n"
                    + "where p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                    + "and p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                    + "and p.fk_idproducto_marca=pm.idproducto_marca\n"
                    + "and p.nombre ilike'%" + buscar + "%' "
                    + "and p.es_patrimonio=true\n"
                    + "order by 3 desc;";
        }
        if (vbus.getTipo_tabla() == 7) {
            cant_columna = 2;
            sql = "select gt.idgasto_tipo as idgt,gt.nombre as tipo \n"
                    + "from gasto_tipo gt\n"
                    + "where gt.activo=true\n"
                    + "and gt.nombre ilike'%" + buscar + "%' \n"
                    + "order by gt.nombre desc;";
        }
        if (cant_columna == 2) {
            int Ancho[] = {20, 80};
            eveconn.Select_cargar_jtable(conn, sql, tblbuscar);
            eveJtab.setAnchoColumnaJtable(tblbuscar, Ancho);
        }
        if (cant_columna == 6) {
            int Ancho[] = {5, 10, 40, 20, 10, 5};
            eveconn.Select_cargar_jtable(conn, sql, tblbuscar);
            eveJtab.setAnchoColumnaJtable(tblbuscar, Ancho);
        }
    }

    private void seleccionar_buscar() {
        int id = 0;
        String nombre = "sin";
        String codbarra = "sin";
        String precio = "0";
        try {

            if (vbus.getTipo_tabla() == 4 || vbus.getTipo_tabla() == 5 || vbus.getTipo_tabla() == 6) {
                id = eveJtab.getInt_select_id(tblbuscar);
                codbarra = eveJtab.getString_select(tblbuscar, 1);
                nombre = eveJtab.getString_select(tblbuscar, 2);
                precio = eveJtab.getString_select(tblbuscar, 4);
            } else {
                id = eveJtab.getInt_select_id(tblbuscar);
                nombre = eveJtab.getString_select(tblbuscar, 1);
            }
        } catch (Exception e) {
            System.err.println("ERROR:" + e);

        }
        if (tblbuscar.getSelectedRow() >= 0) {
            if (vbus.getTipo_tabla() == 1) {
                FrmProd_dato.txtprod_categoria.setText(nombre);
                FrmProd_dato.setFk_idproducto_categoria(id);
            }
            if (vbus.getTipo_tabla() == 2) {
                FrmProd_dato.txtprod_unidad.setText(nombre);
                FrmProd_dato.setFk_idproducto_unidad(id);
            }
            if (vbus.getTipo_tabla() == 3) {
                FrmProd_dato.txtprod_marca.setText(nombre);
                FrmProd_dato.setFk_idproducto_marca(id);
            }
            if (vbus.getTipo_tabla() == 4) {
                FrmHab_crear.txtprod_id_insumo.setText(String.valueOf(id));
                FrmHab_crear.txtprod_descrip_insumo.setText(nombre);
                FrmHab_crear.txtprod_precio_insumo.setText(precio);
                FrmHab_crear.txtprod_cant_insumo.setText("1");
                FrmHab_crear.txtprod_subtt_insumo.setText(precio);
            }
            if (vbus.getTipo_tabla() == 5) {
                FrmHab_crear.txtprod_id_frigobar.setText(String.valueOf(id));
                FrmHab_crear.txtprod_descrip_frigobar.setText(nombre);
                FrmHab_crear.txtprod_precio_frigobar.setText(precio);
                FrmHab_crear.txtprod_cant_frigobar.setText("1");
                FrmHab_crear.txtprod_subtotal_frigobar.setText(precio);
            }
            if (vbus.getTipo_tabla() == 6) {
                FrmHab_crear.txtprod_id_patrimonio.setText(String.valueOf(id));
                FrmHab_crear.txtprod_descrip_patrimonio.setText(nombre);
                FrmHab_crear.txtprod_precio_patrimonio.setText(precio);
                FrmHab_crear.txtprod_cant_patrimonio.setText("1");
                FrmHab_crear.txtprod_subtotal_patrimonio.setText(precio);
            }
            if (vbus.getTipo_tabla() == 7) {
                FrmGasto.txtgasto_tipo.setText(nombre);
                FrmGasto.setFk_idgasto_tipo(id);
            }
        }
    }

    private void seleccionar_focus() {

        if (tblbuscar.getSelectedRow() >= 0) {
            if (vbus.getTipo_tabla() == 1) {
                FrmProd_dato.txtprod_unidad.grabFocus();
            }
            if (vbus.getTipo_tabla() == 2) {
                FrmProd_dato.txtprod_marca.grabFocus();
            }
            if (vbus.getTipo_tabla() == 3) {
                FrmProd_dato.txtnombre.grabFocus();
            }
            if (vbus.getTipo_tabla() == 4) {
                FrmHab_crear.txtprod_cant_insumo.grabFocus();
            }
            if (vbus.getTipo_tabla() == 5) {
                FrmHab_crear.txtprod_cant_frigobar.grabFocus();
            }
            if (vbus.getTipo_tabla() == 6) {
                FrmHab_crear.txtprod_cant_patrimonio.grabFocus();
            }
        }
    }

    public JDiaBuscar(java.awt.Frame parent, boolean modal) {
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
        jLabel1 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbuscar = new javax.swing.JTable();
        btnseleccionar_salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("BUSCAR:");

        txtbuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblbuscar.setModel(new javax.swing.table.DefaultTableModel(
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
        tblbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblbuscarMouseReleased(evt);
            }
        });
        tblbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblbuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblbuscarKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblbuscar);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        );

        btnseleccionar_salir.setText("SELECCIONAR Y SALIR");
        btnseleccionar_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnseleccionar_salirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnseleccionar_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnseleccionar_salir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtbuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyPressed
        // TODO add your handling code here:
        eveJtab.seleccionar_tabla_flecha_abajo(evt, tblbuscar);
    }//GEN-LAST:event_txtbuscarKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
        actualizar_buscar();
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void tblbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblbuscarKeyReleased
        // TODO add your handling code here:
        seleccionar_buscar();
    }//GEN-LAST:event_tblbuscarKeyReleased

    private void tblbuscarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbuscarMouseReleased
        // TODO add your handling code here:
        seleccionar_buscar();
    }//GEN-LAST:event_tblbuscarMouseReleased

    private void tblbuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblbuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_buscar();
            this.dispose();
            seleccionar_focus();
        }
    }//GEN-LAST:event_tblbuscarKeyPressed

    private void btnseleccionar_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnseleccionar_salirActionPerformed
        // TODO add your handling code here:
        seleccionar_buscar();
        this.dispose();
        seleccionar_focus();
    }//GEN-LAST:event_btnseleccionar_salirActionPerformed

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
            java.util.logging.Logger.getLogger(JDiaBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDiaBuscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDiaBuscar dialog = new JDiaBuscar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnseleccionar_salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblbuscar;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
