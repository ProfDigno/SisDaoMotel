/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FILTRO.ClaAuxFiltroVenta;
//import FORMULARIO.DAO.DAO_cliente;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_producto_categoria;
import FORMULARIO.DAO.DAO_producto_marca;
import FORMULARIO.DAO.DAO_venta;
//import FORMULARIO.ENTIDAD.cliente;
import FORMULARIO.ENTIDAD.producto_categoria;
import FORMULARIO.ENTIDAD.producto_marca;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Digno
 */
public class FrmRepInventarioValorizado extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmRepVenta
     */
    EvenJFRAME evetbl = new EvenJFRAME();
    Connection conn = ConnPostgres.getConnPosgres();
    private EvenFecha evefec = new EvenFecha();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenConexion eveconn = new EvenConexion();
    private DAO_producto_categoria DAOpc = new DAO_producto_categoria();
    private DAO_producto_marca DAOpm = new DAO_producto_marca();
    private DAO_producto DAOp = new DAO_producto();
    private producto_categoria ENTpc = new producto_categoria();
    private producto_marca ENTpm = new producto_marca();
    private cla_color_pelete clacolor = new cla_color_pelete();
    private int fk_idproducto_categoria_local;
    private int fk_idproducto_marca_local;

    private void abrir_formulario() {
        this.setTitle("REPORTE INVENTARIO VALORIZADO POR VENTA");
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
    }

    private String suma_filtro() {
        String suma_filtro = "";
        String filtro_categoria = "";
        String filtro_stock = "";
        if (fk_idproducto_categoria_local > 0) {
            filtro_categoria = " and pc.idproducto_categoria=" + fk_idproducto_categoria_local + "\n";
        }
        String filtro_marca = "";
        if (fk_idproducto_marca_local > 0) {
            filtro_marca = " and pm.idproducto_marca=" + fk_idproducto_marca_local + "\n";
        }
        if (jCstock_positivo.isSelected() && !jCstock_cero_nega.isSelected()) {
            filtro_stock = " and p.stock_actual>0 ";
        }
        if (!jCstock_positivo.isSelected() && jCstock_cero_nega.isSelected()) {
            filtro_stock = " and p.stock_actual<=0 ";
        }
        suma_filtro = filtro_categoria + filtro_marca + filtro_stock;
        return suma_filtro;
    }

    private void boton_imprimir() {
        boton_calcular();
        DAOp.imprimir_rep_inventario_volorizado(conn, suma_filtro());
    }

    private void boton_calcular() {
        suma_inventario_valor_precio(conn, suma_filtro());
    }

    private void reestableser() {
        jCstock_positivo.setSelected(true);
        jCstock_cero_nega.setSelected(false);
        txtbucar_categoria.setText(null);
        txtbucar_marca.setText(null);
        fk_idproducto_categoria_local = 0;
        fk_idproducto_marca_local = 0;
        suma_inventario_valor_precio(conn, suma_filtro());
    }

    private void seleccionar_cargar_categoria() {
        fk_idproducto_categoria_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_categoria, "producto_categoria", "nombre", "idproducto_categoria");
        DAOpc.cargar_producto_categoria(conn, ENTpc, fk_idproducto_categoria_local);
        txtbucar_categoria.setText(ENTpc.getC4nombre());
        txtbucar_marca.grabFocus();
        suma_inventario_valor_precio(conn, suma_filtro());
    }

    private void seleccionar_cargar_marca() {
        fk_idproducto_marca_local = eveconn.getInt_Solo_seleccionar_JLista(conn, jList_marca, "producto_marca", "nombre", "idproducto_marca");
        DAOpm.cargar_producto_marca(conn, ENTpm, fk_idproducto_marca_local);
        txtbucar_marca.setText(ENTpm.getC4nombre());
        btnimprimir.grabFocus();
        suma_inventario_valor_precio(conn, suma_filtro());
    }

    private void suma_inventario_valor_precio(Connection conn, String filtro) {
        if (txtbucar_marca.getText().trim().length() > 0 || txtbucar_categoria.getText().trim().length() > 0) {
            String titulo = "suma_inventario_valor_precio";
            String sql = "SELECT "
                    + "sum(p.precio_venta*p.stock_actual) as tt_venta,\n"
                    + "sum(p.precio_compra*p.stock_actual) as tt_compra\n"
                    + " FROM producto p,producto_unidad pu,producto_categoria pc, producto_marca pm\n"
                    + "where p.fk_idproducto_unidad=pu.idproducto_unidad\n"
                    + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                    + "and p.fk_idproducto_marca=pm.idproducto_marca\n" + filtro;
            try {
                ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
                if (rs.next()) {
                    double tt_venta = rs.getDouble("tt_venta");
                    double tt_compra = rs.getDouble("tt_compra");
                    jFtotal_inventario_venta.setValue(tt_venta);
                    jFtotal_inventario_compra.setValue(tt_compra);
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
            }
        }else{
            jFtotal_inventario_venta.setValue(0);
            jFtotal_inventario_compra.setValue(0);
        }
    }

    public FrmRepInventarioValorizado() {
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

        panel_cate_mar = new javax.swing.JPanel();
        jList_categoria = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        txtbucar_categoria = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtbucar_marca = new javax.swing.JTextField();
        jList_marca = new javax.swing.JList<>();
        btnimprimir = new javax.swing.JButton();
        jFtotal_inventario_venta = new javax.swing.JFormattedTextField();
        btnreset = new javax.swing.JButton();
        btncalcular = new javax.swing.JButton();
        jCstock_positivo = new javax.swing.JCheckBox();
        jCstock_cero_nega = new javax.swing.JCheckBox();
        jFtotal_inventario_compra = new javax.swing.JFormattedTextField();

        setClosable(true);

        panel_cate_mar.setBorder(javax.swing.BorderFactory.createTitledBorder("INVENTARIO VALORIZADO"));

        jList_categoria.setBackground(new java.awt.Color(204, 204, 255));
        jList_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_categoria.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_categoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_categoriaMouseReleased(evt);
            }
        });
        jList_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_categoriaKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("CATEGORIA:");

        txtbucar_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucar_categoriaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucar_categoriaKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("MARCA:");

        txtbucar_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbucar_marcaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbucar_marcaKeyReleased(evt);
            }
        });

        jList_marca.setBackground(new java.awt.Color(204, 204, 255));
        jList_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jList_marca.setSelectionBackground(new java.awt.Color(255, 51, 51));
        jList_marca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList_marcaMouseReleased(evt);
            }
        });
        jList_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_marcaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel_cate_marLayout = new javax.swing.GroupLayout(panel_cate_mar);
        panel_cate_mar.setLayout(panel_cate_marLayout);
        panel_cate_marLayout.setHorizontalGroup(
            panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_cate_marLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jList_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbucar_marca, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                    .addComponent(jList_marca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_cate_marLayout.setVerticalGroup(
            panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cate_marLayout.createSequentialGroup()
                .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtbucar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jList_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_cate_marLayout.createSequentialGroup()
                        .addGroup(panel_cate_marLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtbucar_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jList_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        btnimprimir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnimprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir.setText("IMPRIMIR");
        btnimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimirActionPerformed(evt);
            }
        });

        jFtotal_inventario_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA STOCK X VENTA"));
        jFtotal_inventario_venta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_inventario_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_inventario_venta.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        btnreset.setText("RESET");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        btncalcular.setText("CALCULAR");
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });

        jCstock_positivo.setText("STOCK POSITIVO");

        jCstock_cero_nega.setText("STOCK CERO Y NEGATIVO");
        jCstock_cero_nega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCstock_cero_negaActionPerformed(evt);
            }
        });

        jFtotal_inventario_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA STOCK X COMPRA"));
        jFtotal_inventario_compra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        jFtotal_inventario_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_inventario_compra.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_cate_mar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFtotal_inventario_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(jFtotal_inventario_compra, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(btnimprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCstock_cero_nega)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCstock_positivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnreset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel_cate_mar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jFtotal_inventario_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_inventario_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCstock_cero_nega)
                    .addComponent(jCstock_positivo)
                    .addComponent(btnreset)
                    .addComponent(btncalcular))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimirActionPerformed
        // TODO add your handling code here:
        boton_imprimir();
    }//GEN-LAST:event_btnimprimirActionPerformed

    private void jList_categoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_categoriaMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_categoria();
    }//GEN-LAST:event_jList_categoriaMouseReleased

    private void jList_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_categoria();
        }
    }//GEN-LAST:event_jList_categoriaKeyPressed

    private void txtbucar_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_categoriaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_categoria);
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            txtbucarCliente_telefono.grabFocus();
//        }
    }//GEN-LAST:event_txtbucar_categoriaKeyPressed

    private void txtbucar_categoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_categoriaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucar_categoria, jList_categoria, "producto_categoria", "nombre", "nombre", 4);
        if (txtbucar_categoria.getText().trim().length() == 0) {
            fk_idproducto_categoria_local = 0;
            suma_inventario_valor_precio(conn, suma_filtro());
        }
    }//GEN-LAST:event_txtbucar_categoriaKeyReleased

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
        // TODO add your handling code here:
        reestableser();
    }//GEN-LAST:event_btnresetActionPerformed

    private void jList_marcaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_marcaMouseReleased
        // TODO add your handling code here:
        seleccionar_cargar_marca();
    }//GEN-LAST:event_jList_marcaMouseReleased

    private void jList_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_marcaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            seleccionar_cargar_marca();
        }
    }//GEN-LAST:event_jList_marcaKeyPressed

    private void txtbucar_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_marcaKeyPressed
        // TODO add your handling code here:
        evejtf.seleccionar_lista(evt, jList_marca);
    }//GEN-LAST:event_txtbucar_marcaKeyPressed

    private void txtbucar_marcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbucar_marcaKeyReleased
        // TODO add your handling code here:
        eveconn.buscar_cargar_Jlista(conn, txtbucar_marca, jList_marca, "producto_marca", "nombre", "nombre", 4);
        if (txtbucar_marca.getText().trim().length() == 0) {
            fk_idproducto_marca_local = 0;
            suma_inventario_valor_precio(conn, suma_filtro());
        }
    }//GEN-LAST:event_txtbucar_marcaKeyReleased

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        // TODO add your handling code here:
        boton_calcular();
    }//GEN-LAST:event_btncalcularActionPerformed

    private void jCstock_cero_negaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCstock_cero_negaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCstock_cero_negaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncalcular;
    private javax.swing.JButton btnimprimir;
    private javax.swing.JButton btnreset;
    private javax.swing.JCheckBox jCstock_cero_nega;
    private javax.swing.JCheckBox jCstock_positivo;
    private javax.swing.JFormattedTextField jFtotal_inventario_compra;
    private javax.swing.JFormattedTextField jFtotal_inventario_venta;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList_categoria;
    private javax.swing.JList<String> jList_marca;
    private javax.swing.JPanel panel_cate_mar;
    private javax.swing.JTextField txtbucar_categoria;
    private javax.swing.JTextField txtbucar_marca;
    // End of variables declaration//GEN-END:variables
}
