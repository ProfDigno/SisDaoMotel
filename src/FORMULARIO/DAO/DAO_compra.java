package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.compra;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_compra {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "COMPRA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "COMPRA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO compra(idcompra,fecha_creado,creado_por,fecha_compra,nro_factura,es_factura,monto_total,monto_iva5,monto_iva10,monto_letra,observacion,estado,fk_idpersona,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE compra SET fecha_creado=?,creado_por=?,fecha_compra=?,nro_factura=?,es_factura=?,monto_total=?,monto_iva5=?,monto_iva10=?,monto_letra=?,observacion=?,estado=?,fk_idpersona=?,fk_idusuario=? WHERE idcompra=?;";
    private String sql_cargar = "SELECT idcompra,fecha_creado,creado_por,fecha_compra,nro_factura,es_factura,monto_total,monto_iva5,monto_iva10,monto_letra,observacion,estado,fk_idpersona,fk_idusuario FROM compra WHERE idcompra=";

    public void insertar_compra(Connection conn, compra co) {
        co.setC1idcompra(eveconn.getInt_ultimoID_mas_uno(conn, co.getTb_compra(), co.getId_idcompra()));
        String titulo = "insertar_compra";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, co.getC1idcompra());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, co.getC3creado_por());
            pst.setDate(4, evefec.getDateSQL_sistema());
            pst.setString(5, co.getC5nro_factura());
            pst.setBoolean(6, co.getC6es_factura());
            pst.setDouble(7, co.getC7monto_total());
            pst.setDouble(8, co.getC8monto_iva5());
            pst.setDouble(9, co.getC9monto_iva10());
            pst.setString(10, co.getC10monto_letra());
            pst.setString(11, co.getC11observacion());
            pst.setString(12, co.getC12estado());
            pst.setInt(13, co.getC13fk_idpersona());
            pst.setInt(14, co.getC14fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + co.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + co.toString(), titulo);
        }
    }

    public void update_compra(Connection conn, compra co) {
        String titulo = "update_compra";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, co.getC3creado_por());
            pst.setDate(3, evefec.getDateSQL_sistema());
            pst.setString(4, co.getC5nro_factura());
            pst.setBoolean(5, co.getC6es_factura());
            pst.setDouble(6, co.getC7monto_total());
            pst.setDouble(7, co.getC8monto_iva5());
            pst.setDouble(8, co.getC9monto_iva10());
            pst.setString(9, co.getC10monto_letra());
            pst.setString(10, co.getC11observacion());
            pst.setString(11, co.getC12estado());
            pst.setInt(12, co.getC13fk_idpersona());
            pst.setInt(13, co.getC14fk_idusuario());
            pst.setInt(14, co.getC1idcompra());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + co.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + co.toString(), titulo);
        }
    }

    public void cargar_compra(Connection conn, compra co, int idcompra) {
        String titulo = "Cargar_compra";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idcompra, titulo);
            if (rs.next()) {
                co.setC1idcompra(rs.getInt(1));
                co.setC2fecha_creado(rs.getString(2));
                co.setC3creado_por(rs.getString(3));
                co.setC4fecha_compra(rs.getString(4));
                co.setC5nro_factura(rs.getString(5));
                co.setC6es_factura(rs.getBoolean(6));
                co.setC7monto_total(rs.getDouble(7));
                co.setC8monto_iva5(rs.getDouble(8));
                co.setC9monto_iva10(rs.getDouble(9));
                co.setC10monto_letra(rs.getString(10));
                co.setC11observacion(rs.getString(11));
                co.setC12estado(rs.getString(12));
                co.setC13fk_idpersona(rs.getInt(13));
                co.setC14fk_idusuario(rs.getInt(14));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + co.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + co.toString(), titulo);
        }
    }

    public void actualizar_tabla_compra(Connection conn, JTable tbltabla, String filtro) {
        String sql_select = "select c.idcompra as idc,"
                + "to_char(c.fecha_creado,'yyyy-MM-dd HH24:MI') as creado,  \n"
                + "c.fecha_compra as fec_nota,c.nro_factura as nro_fac ,"
                + "p.nombre as proveedor,p.ruc as ruc,p.telefono as telefono,\n"
                + "TRIM(to_char(c.monto_iva5,'999G999G999')) as m_iva5,\n"
                + "TRIM(to_char(c.monto_iva10,'999G999G999')) as m_iva10, \n"
                + "TRIM(to_char(c.monto_total,'999G999G999')) as total,\n"
                + "c.estado,c.creado_por as usuario  \n"
                + "from compra c ,persona p \n"
                + "where c.fk_idpersona=p.idpersona \n" + filtro
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_compra(tbltabla);
    }

    public void ancho_tabla_compra(JTable tbltabla) {
        int Ancho[] = {5, 8, 6, 8, 20, 7, 6, 6, 6, 6, 8, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_compra_caja_cerrado(Connection conn, JTable tbltabla, int fk_idcaja_cierre,String filtro) {
        String sql = "select c.idcompra as idc,\n"
                + "to_char(c.fecha_creado,'yyyy-MM-dd HH24:MI') as creado,  \n"
                + "c.nro_factura as nro_fac ,\n"
                + "(p.nombre||'-('||p.ruc||')')  as proveedor,\n"
                + "TRIM(to_char(c.monto_total,'999G999G999')) as monto,\n"
                + "c.estado,c.creado_por as usuario,c.monto_total  \n"
                + "from compra c ,persona p,caja_cierre_item cci,caja_cierre_detalle ccd \n"
                + "where c.fk_idpersona=p.idpersona \n"
                + "and ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle\n"
                + "and ccd.fk_idcompra=c.idcompra\n"
                + "and cci.fk_idcaja_cierre="+fk_idcaja_cierre+filtro
                + " order by 1 desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_compra_caja_cerrado(tbltabla);
    }

    public void ancho_tabla_compra_caja_cerrado(JTable tbltabla) {
        int Ancho[] = {5, 15, 10, 35, 8, 10, 18,1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.ocultar_columna(tbltabla, 7);
    }
    public void terminar_compra_en_caja(Connection conn, int fk_idcaja_cierre) {
        String sql = "update compra set estado='" + eveest.getEst_Terminar() + "' from caja_cierre_item ,caja_cierre_detalle \n"
                + "where caja_cierre_item.fk_idcaja_cierre_detalle=caja_cierre_detalle.idcaja_cierre_detalle \n"
                + "and caja_cierre_detalle.fk_idcompra=compra.idcompra \n"
                + "and compra.estado='" + eveest.getEst_Pagado() + "'\n"
                + "and caja_cierre_item.fk_idcaja_cierre=" + fk_idcaja_cierre;
        eveconn.SQL_execute_libre(conn, sql);
    }
    public boolean getBoo_varificar_compra_pendiente(Connection conn, int fk_idusuario) {
            String titulo = "getBoo_varificar_compra_pendiente";
            String sql = "select count(*) as cant from compra "
                    + "where estado='PENDIENTE' and fk_idusuario=" + fk_idusuario;
            try {
                ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
                if (rs.next()) {
                    int cant = rs.getInt("cant");
                    if (cant==0) {
                        return true;
                    } else {
                        return false;
                    }
                }else{
                    return false;
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
                return false;
            }
        
    }
}
