package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.gasto;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_gasto {

    EvenConexion eveconn = new EvenConexion();
    private EvenJtable evejt = new EvenJtable();
    private EvenJasperReport rep = new EvenJasperReport();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenFecha evefec = new EvenFecha();
    private EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "GASTO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "GASTO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO gasto(idgasto,fecha_creado,creado_por,monto_gasto,monto_letra,descripcion,estado,fk_idgasto_tipo,fk_idusuario) VALUES (?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE gasto SET fecha_creado=?,creado_por=?,monto_gasto=?,monto_letra=?,descripcion=?,estado=?,fk_idgasto_tipo=?,fk_idusuario=? WHERE idgasto=?;";
    
    private String sql_cargar = "SELECT idgasto,fecha_creado,creado_por,monto_gasto,monto_letra,descripcion,estado,fk_idgasto_tipo,fk_idusuario FROM gasto WHERE idgasto=";

    public void insertar_gasto(Connection conn, gasto ga) {
        ga.setC1idgasto(eveconn.getInt_ultimoID_mas_uno(conn, ga.getTb_gasto(), ga.getId_idgasto()));
        String titulo = "insertar_gasto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ga.getC1idgasto());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ga.getC3creado_por());
            pst.setDouble(4, ga.getC4monto_gasto());
            pst.setString(5, ga.getC5monto_letra());
            pst.setString(6, ga.getC6descripcion());
            pst.setString(7, ga.getC7estado());
            pst.setInt(8, ga.getC8fk_idgasto_tipo());
            pst.setInt(9, ga.getC9fk_idusuario());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ga.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ga.toString(), titulo);
        }
    }

    public void update_gasto(Connection conn, gasto ga) {
        String titulo = "update_gasto";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ga.getC3creado_por());
            pst.setDouble(3, ga.getC4monto_gasto());
            pst.setString(4, ga.getC5monto_letra());
            pst.setString(5, ga.getC6descripcion());
            pst.setString(6, ga.getC7estado());
            pst.setInt(7, ga.getC8fk_idgasto_tipo());
            pst.setInt(8, ga.getC9fk_idusuario());
            pst.setInt(9, ga.getC1idgasto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ga.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ga.toString(), titulo);
        }
    }

    public void cargar_gasto(Connection conn, gasto ga, int idgasto) {
        String titulo = "Cargar_gasto";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idgasto, titulo);
            if (rs.next()) {
                ga.setC1idgasto(rs.getInt(1));
                ga.setC2fecha_creado(rs.getString(2));
                ga.setC3creado_por(rs.getString(3));
                ga.setC4monto_gasto(rs.getDouble(4));
                ga.setC5monto_letra(rs.getString(5));
                ga.setC6descripcion(rs.getString(6));
                ga.setC7estado(rs.getString(7));
                ga.setC8fk_idgasto_tipo(rs.getInt(8));
                ga.setC9fk_idusuario(rs.getInt(9));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ga.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ga.toString(), titulo);
        }
    }

    public void actualizar_tabla_gasto(Connection conn, JTable tbltabla,String filtro) {
        String sql_select = "select g.idgasto as idg,to_char(g.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
            + "gt.nombre as tipo,g.descripcion,\n"
            + "to_char(g.monto_gasto,'999G999G999') as monto,g.estado,g.creado_por,g.monto_gasto as i_monto_gasto  \n"
            + "from gasto g,gasto_tipo gt\n"
            + "where g.fk_idgasto_tipo=gt.idgasto_tipo \n"+filtro
            + " order by g.idgasto desc;";
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_gasto(tbltabla);
    }

    public void ancho_tabla_gasto(JTable tbltabla) {
        int Ancho[] = {5, 15, 23, 22, 8, 10, 18,1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.ocultar_columna(tbltabla, 7);
    }

    public void terminar_gasto_en_caja(Connection conn, int fk_idcaja_cierre) {
        String sql = "update gasto set estado='" + eveest.getEst_Terminar() + "' from caja_cierre_item ,caja_cierre_detalle \n"
                + "where caja_cierre_item.fk_idcaja_cierre_detalle=caja_cierre_detalle.idcaja_cierre_detalle \n"
                + "and caja_cierre_detalle.fk_idgasto=gasto.idgasto \n"
                + "and gasto.estado='" + eveest.getEst_Emitido() + "'\n"
                + "and caja_cierre_item.fk_idcaja_cierre=" + fk_idcaja_cierre;
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void actualizar_tabla_gasto_caja_cerrado(Connection conn, JTable tbltabla, int fk_idcaja_cierre,String filtro_gasto) {
        String sql = "select g.idgasto,\n"
                + "to_char(g.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,\n"
                + "gt.nombre as tipo,\n"
                + "g.descripcion,\n"
                + "TRIM(to_char(g.monto_gasto,'999G999G999')) as monto,\n"
                + "g.estado, \n"
                + "g.creado_por,g.monto_gasto as i_monto_gasto  \n"
                + " from gasto g,caja_cierre_item cci,caja_cierre_detalle ccd,gasto_tipo gt  \n"
                + " where  ccd.idcaja_cierre_detalle=cci.fk_idcaja_cierre_detalle \n"
                + " and ccd.fk_idgasto=g.idgasto\n"
                + " and g.fk_idgasto_tipo=gt.idgasto_tipo\n"
                + " and cci.fk_idcaja_cierre=" + fk_idcaja_cierre+filtro_gasto+"\n"
                + " order by g.idgasto desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_gasto_caja_cerrado(tbltabla);
    }

    public void ancho_tabla_gasto_caja_cerrado(JTable tbltabla) {
        int Ancho[] = {5, 15, 23, 22, 8, 10, 18,1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.ocultar_columna(tbltabla, 7);
    }
}
