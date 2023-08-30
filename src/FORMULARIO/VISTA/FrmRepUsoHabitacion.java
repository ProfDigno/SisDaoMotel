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
import FORMULARIO.DAO.DAO_habitacion_recepcion;
import FORMULARIO.DAO.DAO_rh_vale;
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
public class FrmRepUsoHabitacion extends javax.swing.JInternalFrame {

    private String nombre_formulario = "REPORTE USO HABITACION";
    private EvenJFRAME evetbl = new EvenJFRAME();
    private Connection conn = ConnPostgres.getConnPosgres();
    private EvenJtable evejt = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private ClaAuxFiltroVenta auxvent = new ClaAuxFiltroVenta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private EvenRender everende = new EvenRender();
    private DAO_habitacion_recepcion DAOv = new DAO_habitacion_recepcion();
    private EvenCombobox evecmb = new EvenCombobox();
    private DAO_usuario DAOusu = new DAO_usuario();
    private EvenRender everend = new EvenRender();
    private int tipo_seleccionar_grupo;
    private int idhabitacion_dato;
    private int imes;
    private int idia;
    private String filtro_tb = "";

    private void abrir_formulario() {
        this.setTitle(nombre_formulario);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        tabla_grupo();
        tabla_vale();
    }

    private String filtro_fecha() {
        String filtro = "";
        filtro = evefec.getFiltroSql_desde_hasta_campo(dcfecDesde, dcfecHasta, "v.fecha_creado");
        return filtro;
    }

    private void actualizar_tabla() {

        double monto_consumo = evejt.getDouble_sumar_tabla(tblhabitacion, 15);
        double monto_total = evejt.getDouble_sumar_tabla(tblhabitacion, 16);
        jFmonto_gral.setValue(monto_consumo);
        int cant = tblhabitacion.getRowCount();
        jFcantidad.setValue(cant);
    }

    private void tabla_grupo() {
        String sql = "";
        tipo_seleccionar_grupo++;
        if (tipo_seleccionar_grupo > 4) {
            tipo_seleccionar_grupo = 4;
        }
        if (tipo_seleccionar_grupo == 1) {
            sql = "SELECT \n"
                    + "d.idhabitacion_dato,"
                    + "('('||d.tipo_habitacion||')-'||h.nro_habitacion) as habi,count(*) as cant,\n"
                    + "TRIM(to_char(sum(v.monto_minimo+v.monto_adicional+v.monto_consumo),'9G999G999G999')) as total\n"
                    + "FROM\n"
                    + "  habitacion_recepcion h,venta v,habitacion_dato d\n"
                    + "  where h.idhabitacion_recepcion=v.fk_idhabitacion_recepcion\n"
                    + "  and v.estado='TERMINADO'\n" + filtro_fecha()
                    + "  and h.fk_idhabitacion_dato=d.idhabitacion_dato\n"
                    + "  group by 1,2\n"
                    + "  order by 3 desc;";
            actualizar_tabla_vale_grupo(conn, tblgrupo, sql);
            ancho_tabla_hab_1(tblgrupo);
        } else if (tipo_seleccionar_grupo == 2) {
            sql = "SELECT TRIM(to_char(date_part('month',h.fecha_creado),'99')) as imes,"
                    + "('('||d.tipo_habitacion||')-'||h.nro_habitacion) as habi,\n"
                    + "case \n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=1 then '1-ENERO'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=2 then '2-FEBRERO'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=3 then '3-MARZO'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=4 then '4-ABRIL'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=5 then '5-MAYO'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=6 then '6-JUNIO'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=7 then '7-JULIO'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=8 then '8-AGOSTO'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=9 then '9-SEPTIEMBRE'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=10 then '10-OCTUBRE'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=11 then '11-NOVIEMBRE'\n"
                    + "when EXTRACT(MONTH FROM h.fecha_creado)=12 then '12-DICIEMBRE'\n"
                    + "else 'error' end as mes,\n"
                    + "count(*) as cant,\n"
                    + "TRIM(to_char(sum(v.monto_minimo+v.monto_adicional+v.monto_consumo),'9G999G999G999')) as total\n"
                    + "FROM\n"
                    + "  habitacion_recepcion h,venta v,habitacion_dato d\n"
                    + "  where h.idhabitacion_recepcion=v.fk_idhabitacion_recepcion\n"
                    + "  and h.fk_idhabitacion_dato=d.idhabitacion_dato\n"
                    + "  and v.estado='TERMINADO'\n" + filtro_fecha() + filtro_tb
                    + "  group by 1,2,3\n"
                    + "  order by 1 desc";
            actualizar_tabla_vale_grupo(conn, tblgrupo, sql);
            ancho_tabla_hab_2(tblgrupo);
        } else if (tipo_seleccionar_grupo == 3) {
            sql = "SELECT TRIM(to_char(date_part('day',h.fecha_creado),'99')) as idia,\n"
                    + "('('||d.tipo_habitacion||')-'||h.nro_habitacion) as habi,\n"
                    + "date(h.fecha_creado) as fecha,\n"
                    + "count(*) as cant,\n"
                    + "TRIM(to_char(sum(v.monto_minimo+v.monto_adicional+v.monto_consumo),'9G999G999G999')) as total\n"
                    + "FROM\n"
                    + "  habitacion_recepcion h,venta v,habitacion_dato d\n"
                    + "  where h.idhabitacion_recepcion=v.fk_idhabitacion_recepcion\n"
                    + "  and h.fk_idhabitacion_dato=d.idhabitacion_dato\n"
                    + "  and v.estado='TERMINADO'\n" + filtro_fecha() + filtro_tb
                    + "  group by 1,2,3\n"
                    + "  order by 3 desc";
            actualizar_tabla_vale_grupo(conn, tblgrupo, sql);
            ancho_tabla_hab_2(tblgrupo);
        }
    }

    private void tabla_vale() {
        String sql = "SELECT \n"
                + "('('||d.tipo_habitacion||')-'||h.nro_habitacion) as habitacion,\n"
                + "to_char(h.fec_ocupado_inicio,'HH24:MI:ss') as inicio,\n"
                + "to_char(h.fec_ocupado_fin,'HH24:MI:ss') as fin,\n"
                + "to_char((h.fec_ocupado_fin-h.fec_ocupado_inicio),'dd  HH24:MI:ss') as tiempo,\n"
                + "TRIM(to_char(v.monto_minimo,'999G999G999')) as minimo,\n"
                + "TRIM(to_char(v.monto_adicional,'999G999G999')) as adicional,\n"
                + "TRIM(to_char(v.monto_consumo,'999G999G999')) as consumo,\n"
                + "TRIM(to_char((v.monto_minimo+v.monto_adicional+v.monto_consumo),'999G999G999')) as total,\n"
                + "v.monto_minimo,v.monto_adicional,v.monto_consumo,\n"
                + "(v.monto_minimo+v.monto_adicional+v.monto_consumo) as monto_total \n"
                + "FROM\n"
                + "  habitacion_recepcion h,venta v,habitacion_dato d\n"
                + "  where h.idhabitacion_recepcion=v.fk_idhabitacion_recepcion\n"
                + "  and h.fk_idhabitacion_dato=d.idhabitacion_dato\n"
                + "  and v.estado='TERMINADO'\n" + filtro_fecha() + filtro_tb
                + "  order by 2 asc";
        actualizar_tabla_vale(conn, tblhabitacion, sql);
//        double sum_guarani = evejt.getDouble_sumar_tabla(tblhabitacion, 6);
//        jFmonto_guarani.setValue(sum_guarani);
    }

    private void seleccionar_grupo() {

        if (tipo_seleccionar_grupo == 1) {
            idhabitacion_dato = evejt.getInt_select_id(tblgrupo);
            filtro_tb = filtro_tb + " and d.idhabitacion_dato=" + idhabitacion_dato;
            tabla_grupo();
            tabla_vale();
        } else if (tipo_seleccionar_grupo == 2) {
            imes = evejt.getInt_select_id(tblgrupo);
            filtro_tb = filtro_tb + " and date_part('month',h.fecha_creado)=" + imes;
            tabla_grupo();
            tabla_vale();
        } else if (tipo_seleccionar_grupo == 3) {
            idia = evejt.getInt_select_id(tblgrupo);
            filtro_tb = "";
            filtro_tb = filtro_tb + " and d.idhabitacion_dato=" + idhabitacion_dato;
            filtro_tb = filtro_tb + " and date_part('month',h.fecha_creado)=" + imes;
            filtro_tb = filtro_tb + " and date_part('day',h.fecha_creado)=" + idia;
            tabla_vale();
        }

    }

    private void actualizar_tabla_vale_grupo(Connection conn, JTable tbltabla, String sql) {
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
    }

    private void ancho_tabla_hab_1(JTable tbltabla) {
        int Ancho[] = {1,30, 20, 50};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 0);
        evejt.alinear_derecha_columna(tbltabla, 1);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 3);
    }

    private void ancho_tabla_hab_2(JTable tbltabla) {
        int Ancho[] = {1, 30, 30, 15, 25};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 0);
        evejt.alinear_derecha_columna(tbltabla, 1);
        evejt.alinear_derecha_columna(tbltabla, 2);
        evejt.alinear_derecha_columna(tbltabla, 3);
        evejt.alinear_derecha_columna(tbltabla, 4);
    }

    public void actualizar_tabla_vale(Connection conn, JTable tbltabla, String sql) {
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_vale(tbltabla);
        double monto_minimo = evejt.getDouble_sumar_tabla(tbltabla, 8);
        jFmonto_minimo.setValue(monto_minimo);
        double monto_adicional = evejt.getDouble_sumar_tabla(tbltabla, 9);
        jFmonto_adicional.setValue(monto_adicional);
        double monto_consumo = evejt.getDouble_sumar_tabla(tbltabla, 10);
        jFmonto_consumo.setValue(monto_consumo);
        double monto_gral = evejt.getDouble_sumar_tabla(tbltabla, 11);
        jFmonto_gral.setValue(monto_gral);
        int cant = tbltabla.getRowCount();
        jFcantidad.setValue(cant);
    }

    public void ancho_tabla_vale(JTable tbltabla) {
        int Ancho[] = {10, 12, 12, 12, 12, 12, 12, 12, 1, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.ocultar_columna(tbltabla, 8);
        evejt.ocultar_columna(tbltabla, 9);
        evejt.ocultar_columna(tbltabla, 10);
        evejt.ocultar_columna(tbltabla, 11);
        evejt.alinear_derecha_columna(tbltabla, 0);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
//        everend.rendertabla_estados(tbltabla, 1);
    }

    private void reestableser() {
        evefec.setFechaDCcargado(dcfecDesde, evefec.getString_fecha_dia1());
        evefec.setFechaDCSistema(dcfecHasta);
    }

    private void boton_imprimir_vale() {
        String filtro = filtro_tb + filtro_fecha();
        DAOv.imprimir_filtro_habitacion_uso(conn, filtro);
    }

    public FrmRepUsoHabitacion() {
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
        tblhabitacion = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblgrupo = new javax.swing.JTable();
        btnatras = new javax.swing.JButton();
        jFmonto_gral = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        btnbuscar_fecha = new javax.swing.JButton();
        dcfecDesde = new com.toedter.calendar.JDateChooser();
        dcfecHasta = new com.toedter.calendar.JDateChooser();
        btnimprimir_uso_habitacion = new javax.swing.JButton();
        jFcantidad = new javax.swing.JFormattedTextField();
        jFmonto_consumo = new javax.swing.JFormattedTextField();
        jFmonto_adicional = new javax.swing.JFormattedTextField();
        jFmonto_minimo = new javax.swing.JFormattedTextField();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA USO HABITACION"));

        tblhabitacion.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblhabitacion);

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
                    .addComponent(btnatras, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnatras))
        );

        jFmonto_gral.setEditable(false);
        jFmonto_gral.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_gral.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO GRAL"));
        jFmonto_gral.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_gral.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO USO HABITACION"));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(387, Short.MAX_VALUE)
                .addComponent(dcfecDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcfecHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(368, 368, 368))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcfecDesde, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(dcfecHasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 9, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnbuscar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnimprimir_uso_habitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_uso_habitacion.setText("FILTRO USO HABITACION");
        btnimprimir_uso_habitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_uso_habitacionActionPerformed(evt);
            }
        });

        jFcantidad.setEditable(false);
        jFcantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT:"));
        jFcantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFcantidad.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFmonto_consumo.setEditable(false);
        jFmonto_consumo.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO CONSUMO"));
        jFmonto_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_consumo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_adicional.setEditable(false);
        jFmonto_adicional.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO ADICIONAL"));
        jFmonto_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_adicional.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jFmonto_minimo.setEditable(false);
        jFmonto_minimo.setBackground(new java.awt.Color(255, 255, 255));
        jFmonto_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO MINIMO"));
        jFmonto_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFmonto_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_uso_habitacion)
                .addGap(120, 120, 120)
                .addComponent(jFcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFmonto_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFmonto_gral, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFmonto_gral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFmonto_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFmonto_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFmonto_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnimprimir_uso_habitacion))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOva.ancho_tabla_venta_alquiler_rep_1(tblfiltro_venta_alquiler);
//        ancho_tabla_hab_1(tblgrupo);
        ancho_tabla_vale(tblhabitacion);
//        ancho_tabla_transaccion_banco(tbltransaccion_banco);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnbuscar_fechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_fechaActionPerformed
        // TODO add your handling code here:
        tipo_seleccionar_grupo = 0;
        filtro_tb = "";
        tabla_grupo();
        tabla_vale();
    }//GEN-LAST:event_btnbuscar_fechaActionPerformed

    private void btnimprimir_uso_habitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_uso_habitacionActionPerformed
        // TODO add your handling code here:
        boton_imprimir_vale();
    }//GEN-LAST:event_btnimprimir_uso_habitacionActionPerformed

    private void tblgrupoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgrupoMouseReleased
        // TODO add your handling code here:
        seleccionar_grupo();
    }//GEN-LAST:event_tblgrupoMouseReleased

    private void btnatrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnatrasActionPerformed
        // TODO add your handling code here:
        tipo_seleccionar_grupo = 0;
        filtro_tb = "";
        tabla_grupo();
        tabla_vale();
    }//GEN-LAST:event_btnatrasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnatras;
    private javax.swing.JButton btnbuscar_fecha;
    private javax.swing.JButton btnimprimir_uso_habitacion;
    private com.toedter.calendar.JDateChooser dcfecDesde;
    private com.toedter.calendar.JDateChooser dcfecHasta;
    private javax.swing.JFormattedTextField jFcantidad;
    private javax.swing.JFormattedTextField jFmonto_adicional;
    private javax.swing.JFormattedTextField jFmonto_consumo;
    private javax.swing.JFormattedTextField jFmonto_gral;
    private javax.swing.JFormattedTextField jFmonto_minimo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblgrupo;
    private javax.swing.JTable tblhabitacion;
    // End of variables declaration//GEN-END:variables
}
