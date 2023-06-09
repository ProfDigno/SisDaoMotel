/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import FILTRO.ClaAuxFiltroVenta;
import FORMULARIO.DAO.DAO_transaccion_banco;
import FORMULARIO.DAO.DAO_usuario;
import FORMULARIO.DAO.DAO_venta;
//import FORMULARIO.DAO.DAO_cliente;
//import FORMULARIO.DAO.DAO_venta_alquiler;
//import FORMULARIO.ENTIDAD.cliente;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Digno
 */
public class FrmRepTransaccionBanco extends javax.swing.JInternalFrame {

    private String nombre_formulario = "REPORTE BANCO";
    private EvenJFRAME evetbl = new EvenJFRAME();
    private Connection conn = ConnPostgres.getConnPosgres();
    private EvenJtable evejt = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private EvenRender everende = new EvenRender();
    private DAO_transaccion_banco DAOtb = new DAO_transaccion_banco();
    private EvenCombobox evecmb = new EvenCombobox();
    private DAO_usuario DAOusu = new DAO_usuario();
    private EvenRender everend=new EvenRender();
    private int tipo_seleccionar_grupo;
    private int iddato_banco;
    private int imes;
    private String filtro_tb="";
    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        tabla_grupo();
        tabla_transaccion_banco();
    }

    private String filtro_fecha() {
        String filtro = "";
        String fecDesde = evefec.getfechaDCStringFormat(dcfecDesde, "yyyy-MM-dd");
        String fecHasta = evefec.getfechaDCStringFormat(dcfecHasta, "yyyy-MM-dd");
        filtro = "and date(tb.fecha_transaccion)>='" + fecDesde + "' and date(tb.fecha_transaccion)<='" + fecHasta + "' ";

        return filtro;
    }

    private void actualizar_tabla() {

        double monto_consumo = evejt.getDouble_sumar_tabla(tbltransaccion_banco, 15);
        double monto_total = evejt.getDouble_sumar_tabla(tbltransaccion_banco, 16);
        jFmonto_guarani.setValue(monto_consumo);
        jFmonto_dolar.setValue(monto_total);
        int cant = tbltransaccion_banco.getRowCount();
        jFcantidad.setValue(cant);
    }

    private void tabla_grupo() {
        String sql = "";
        tipo_seleccionar_grupo++;
        if(tipo_seleccionar_grupo>3){
            tipo_seleccionar_grupo=3;
        }
        if (tipo_seleccionar_grupo == 1) {
            sql = "select \n"
                    + "db.iddato_banco,(b.nombre||'-'||db.nro_cuenta) as banco,\n"
                    + "TRIM(to_char(sum(tb.monto_guarani),'999G999G999')) as guarani,\n"
                    + "TRIM(to_char(sum(tb.monto_dolar),'999G999G999')) as dolar\n"
                    + "from transaccion_banco tb,dato_banco db,banco b\n"
                    + "where tb.fk_iddato_banco=db.iddato_banco\n"
                    + "and db.fk_idbanco=b.idbanco\n"
                    + "and tb.estado='EMITIDO' "+filtro_fecha()
                    + " group by 1,2\n"
                    + "order by 1 asc;";
            actualizar_tabla_transaccion_banco_grupo(conn, tblgrupo, sql);
        } else if (tipo_seleccionar_grupo == 2) {
            sql = "select TRIM(to_char(date_part('month',tb.fecha_transaccion),'99')) as imes,\n"
                    + "case \n"
                    + "when date_part('month',tb.fecha_transaccion)=1 then 'ENERO'\n"
                    + "when date_part('month',tb.fecha_transaccion)=2 then 'FEBRERO'\n"
                    + "when date_part('month',tb.fecha_transaccion)=3 then 'MARZO'\n"
                    + "when date_part('month',tb.fecha_transaccion)=4 then 'ABRIL'\n"
                    + "when date_part('month',tb.fecha_transaccion)=5 then 'MAYO'\n"
                    + "when date_part('month',tb.fecha_transaccion)=6 then 'JUNIO'\n"
                    + "when date_part('month',tb.fecha_transaccion)=7 then 'JULIO'\n"
                    + "when date_part('month',tb.fecha_transaccion)=8 then 'AGOSTO'\n"
                    + "when date_part('month',tb.fecha_transaccion)=9 then 'SEPTIEMBRE'\n"
                    + "when date_part('month',tb.fecha_transaccion)=10 then 'OCTUBRE'\n"
                    + "when date_part('month',tb.fecha_transaccion)=11 then 'NOVIEMBRE'\n"
                    + "when date_part('month',tb.fecha_transaccion)=12 then 'DICIEMBRE'\n"
                    + "else 'error' end as mes,\n"
                    + "TRIM(to_char(sum(tb.monto_guarani),'999G999G999')) as guarani,\n"
                    + "TRIM(to_char(sum(tb.monto_dolar),'999G999G999')) as dolar\n"
                    + "from transaccion_banco tb,dato_banco db,banco b\n"
                    + "where tb.fk_iddato_banco=db.iddato_banco\n"
                    + "and db.fk_idbanco=b.idbanco\n"
                    + "and tb.estado='EMITIDO' "+filtro_fecha()
                    + " and db.iddato_banco=" + iddato_banco
                    + " group by 1,2\n"
                    + "order by 1 asc;";
            actualizar_tabla_transaccion_banco_grupo(conn, tblgrupo, sql);
        } else if (tipo_seleccionar_grupo == 3) {
            sql = "select (0) as id,tb.fecha_transaccion as fecha,\n"
                    + "TRIM(to_char(sum(tb.monto_guarani),'999G999G999')) as guarani,\n"
                    + "TRIM(to_char(sum(tb.monto_dolar),'999G999G999')) as dolar\n"
                    + "from transaccion_banco tb,dato_banco db,banco b\n"
                    + "where tb.fk_iddato_banco=db.iddato_banco\n"
                    + "and db.fk_idbanco=b.idbanco\n"
                    + "and tb.estado='EMITIDO' "+filtro_fecha()
                    + " and db.iddato_banco=" + iddato_banco
                    + " and date_part('month',tb.fecha_transaccion)=" + imes
                    + " group by 1,2\n"
                    + "order by tb.fecha_transaccion desc;";
            actualizar_tabla_transaccion_banco_grupo(conn, tblgrupo, sql);
        }
    }

    private void tabla_transaccion_banco() {
        String sql = "select tb.idtransaccion_banco as idtb,\n"
                + "tb.fecha_transaccion as fecha,\n"
                + "(b.nombre||'-'||db.nro_cuenta) as banco,\n"
                + "tb.nro_transaccion as referencia,tb.concepto,\n"
                + "TRIM(to_char(tb.monto_guarani,'999G999G999')) as guarani,\n"
                + "TRIM(to_char(tb.monto_dolar,'999G999G999')) as dolar,\n"
                + "tb.estado,tb.monto_guarani,tb.monto_dolar \n"
                + "from transaccion_banco tb,dato_banco db,banco b\n"
                + "where tb.fk_iddato_banco=db.iddato_banco "+filtro_fecha()
                + " and db.fk_idbanco=b.idbanco\n"+filtro_tb
                + " order by tb.fecha_transaccion desc;";
        actualizar_tabla_transaccion_banco(conn, tbltransaccion_banco, sql);
        double sum_guarani=evejt.getDouble_sumar_tabla(tbltransaccion_banco, 8);
        jFmonto_guarani.setValue(sum_guarani);
        double sum_dolar=evejt.getDouble_sumar_tabla(tbltransaccion_banco, 9);
        jFmonto_dolar.setValue(sum_dolar);
        
    }

    private void seleccionar_grupo() {

        if (tipo_seleccionar_grupo == 1) {
            iddato_banco = evejt.getInt_select_id(tblgrupo);
            filtro_tb=filtro_tb+" and db.iddato_banco="+iddato_banco;
            tabla_grupo();
            tabla_transaccion_banco();
        } else if (tipo_seleccionar_grupo == 2) {
            imes = evejt.getInt_select_id(tblgrupo);
            filtro_tb=filtro_tb+" and date_part('month',tb.fecha_transaccion)="+imes;
            tabla_grupo();
            tabla_transaccion_banco();
        }

    }

    public void actualizar_tabla_transaccion_banco_grupo(Connection conn, JTable tbltabla, String sql) {
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_transaccion_banco_grupo(tbltabla);
    }

    public void ancho_tabla_transaccion_banco_grupo(JTable tbltabla) {
        int Ancho[] = {1, 60, 20, 20};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 0);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 3);
    }
public void actualizar_tabla_transaccion_banco(Connection conn, JTable tbltabla, String sql) {
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_transaccion_banco(tbltabla);
    }

    public void ancho_tabla_transaccion_banco(JTable tbltabla) {
        int Ancho[] = {5, 10, 20, 10,20,8,8,8,1,1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 8);
        evejt.ocultar_columna(tbltabla, 9);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        everend.rendertabla_estados(tbltabla, 7);
    }
    private void reestableser() {
        evefec.setFechaDCSistema(dcfecDesde);
        evefec.setFechaDCSistema(dcfecHasta);
    }

    private void boton_imprimir_transaccion_banco() {
        String filtro=filtro_tb+filtro_fecha();
        DAOtb.imprimir_filtro_transaccion_banco(conn, filtro);
    }

    public FrmRepTransaccionBanco() {
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
        tbltransaccion_banco = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblgrupo = new javax.swing.JTable();
        btnatras = new javax.swing.JButton();
        jFmonto_guarani = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        btnbuscar_fecha = new javax.swing.JButton();
        dcfecDesde = new com.toedter.calendar.JDateChooser();
        dcfecHasta = new com.toedter.calendar.JDateChooser();
        btnimprimir_filtro_depo_banco = new javax.swing.JButton();
        jFmonto_dolar = new javax.swing.JFormattedTextField();
        jFcantidad = new javax.swing.JFormattedTextField();

        setClosable(true);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA OCUPACION"));

        tbltransaccion_banco.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbltransaccion_banco);

        tblgrupo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblgrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblgrupoMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblgrupo);

        btnatras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnatras.setText("ATRAS");
        btnatras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnatrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnatras, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnatras))
        );

        jFmonto_guarani.setEditable(false);
        jFmonto_guarani.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_guarani.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO GUARANI"));
        jFmonto_guarani.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_guarani.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO OCUPACION"));

        btnbuscar_fecha.setText("BUSCAR");
        btnbuscar_fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_fechaActionPerformed(evt);
            }
        });

        dcfecDesde.setBorder(javax.swing.BorderFactory.createTitledBorder("DESDE:"));
        dcfecDesde.setDateFormatString("yyyy-MM-dd");
        dcfecDesde.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        dcfecHasta.setBorder(javax.swing.BorderFactory.createTitledBorder("HASTA:"));
        dcfecHasta.setDateFormatString("yyyy-MM-dd");
        dcfecHasta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(dcfecDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcfecHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcfecDesde, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(dcfecHasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        btnimprimir_filtro_depo_banco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_filtro_depo_banco.setText("FILTRO DEPOSITO BANCO");
        btnimprimir_filtro_depo_banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_filtro_depo_bancoActionPerformed(evt);
            }
        });

        jFmonto_dolar.setEditable(false);
        jFmonto_dolar.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_dolar.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO DOLAR"));
        jFmonto_dolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_dolar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFcantidad.setEditable(false);
        jFcantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT:"));
        jFcantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFcantidad.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_filtro_depo_banco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_guarani, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFmonto_guarani)
                        .addComponent(jFcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jFmonto_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnimprimir_filtro_depo_banco))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOva.ancho_tabla_venta_alquiler_rep_1(tblfiltro_venta_alquiler);
        ancho_tabla_transaccion_banco_grupo(tblgrupo);
        ancho_tabla_transaccion_banco(tbltransaccion_banco);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        tipo_seleccionar_grupo=0;
        filtro_tb="";
        tabla_grupo();
        tabla_transaccion_banco();
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void btnimprimir_filtro_depo_bancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_filtro_depo_bancoActionPerformed
        // TODO add your handling code here:
        boton_imprimir_transaccion_banco();
    }//GEN-LAST:event_btnimprimir_filtro_depo_bancoActionPerformed

    private void tblgrupoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupoMouseReleased
        // TODO add your handling code here:
        seleccionar_grupo();
    }//GEN-LAST:event_tblgrupoMouseReleased

    private void btnatrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnatrasActionPerformed
        // TODO add your handling code here:
        tipo_seleccionar_grupo=0;
        filtro_tb="";
        tabla_grupo();
        tabla_transaccion_banco();
    }//GEN-LAST:event_btnatrasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnatras;
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btnimprimir_filtro_depo_banco;
    private com.toedter.calendar.JDateChooser dcfecDesde;
    private com.toedter.calendar.JDateChooser dcfecHasta;
    private javax.swing.JFormattedTextField jFcantidad;
    private javax.swing.JFormattedTextField jFmonto_dolar;
    private javax.swing.JFormattedTextField jFmonto_guarani;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblgrupo;
    private javax.swing.JTable tbltransaccion_banco;
    // End of variables declaration//GEN-END:variables
}
