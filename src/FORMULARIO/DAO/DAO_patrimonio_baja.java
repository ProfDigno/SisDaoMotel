package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.patrimonio_baja;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_patrimonio_baja {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenRender everen = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PATRIMONIO_BAJA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PATRIMONIO_BAJA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO patrimonio_baja(idpatrimonio_baja,fecha_creado,creado_por,estado,observacion,monto) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE patrimonio_baja SET fecha_creado=?,creado_por=?,estado=?,observacion=?,monto=? WHERE idpatrimonio_baja=?;";
    private String sql_select = "SELECT idpatrimonio_baja,fecha_creado,creado_por,estado,observacion,monto FROM patrimonio_baja order by 1 desc;";
    private String sql_cargar = "SELECT idpatrimonio_baja,fecha_creado,creado_por,estado,observacion,monto FROM patrimonio_baja WHERE idpatrimonio_baja=";

    public void insertar_patrimonio_baja(Connection conn, patrimonio_baja ENTpb) {
        ENTpb.setC1idpatrimonio_baja(eveconn.getInt_ultimoID_mas_uno(conn, ENTpb.getTb_patrimonio_baja(), ENTpb.getId_idpatrimonio_baja()));
        String titulo = "insertar_patrimonio_baja";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTpb.getC1idpatrimonio_baja());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTpb.getC3creado_por());
            pst.setString(4, ENTpb.getC4estado());
            pst.setString(5, ENTpb.getC5observacion());
            pst.setDouble(6, ENTpb.getC6monto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTpb.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTpb.toString(), titulo);
        }
    }

    public void update_patrimonio_baja(Connection conn, patrimonio_baja ENTpb) {
        String titulo = "update_patrimonio_baja";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ENTpb.getC3creado_por());
            pst.setString(3, ENTpb.getC4estado());
            pst.setString(4, ENTpb.getC5observacion());
            pst.setDouble(5, ENTpb.getC6monto());
            pst.setInt(6, ENTpb.getC1idpatrimonio_baja());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpb.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpb.toString(), titulo);
        }
    }

    public void update_patrimonio_baja_anular(Connection conn, patrimonio_baja ENTpb) {
        String titulo = "update_patrimonio_baja_anular";
        PreparedStatement pst = null;
        String sql_update = "UPDATE patrimonio_baja SET estado=? WHERE idpatrimonio_baja=?;";
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, ENTpb.getC4estado());
            pst.setInt(2, ENTpb.getC1idpatrimonio_baja());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpb.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpb.toString(), titulo);
        }
    }

    public void cargar_patrimonio_baja(Connection conn, patrimonio_baja ENTpb, int idpatrimonio_baja) {
        String titulo = "Cargar_patrimonio_baja";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpatrimonio_baja, titulo);
            if (rs.next()) {
                ENTpb.setC1idpatrimonio_baja(rs.getInt(1));
                ENTpb.setC2fecha_creado(rs.getString(2));
                ENTpb.setC3creado_por(rs.getString(3));
                ENTpb.setC4estado(rs.getString(4));
                ENTpb.setC5observacion(rs.getString(5));
                ENTpb.setC6monto(rs.getDouble(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTpb.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTpb.toString(), titulo);
        }
    }

    public void actualizar_tabla_patrimonio_baja(Connection conn, JTable tbltabla) {
        String sql = "select idpatrimonio_baja as idpc,\n"
                + "to_char(fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,creado_por as usuario,\n"
                + "estado,observacion,to_char(monto,'999G999G999') as monto \n"
                + "from patrimonio_baja\n"
                + "order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_patrimonio_baja(tbltabla);
    }

    public void ancho_tabla_patrimonio_baja(JTable tbltabla) {
        int Ancho[] = {5, 15, 15, 10, 45, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        everen.rendertabla_estados(tbltabla, 3);
    }

    public void reporte_actualizar_tabla_patrimonio_baja(Connection conn, JTable tbltabla, String filtro_fecha) {
        String sql = "SELECT pp.idpatrimonio_producto as idpp,\n"
                + "to_char(pci.fecha_creado,'yyyy-MM-dd HH24:MI:00') as fecha,\n"
                + "pci.descripcion,pbm.nombre as motivo,\n"
                + "pu.nombre as ubicacion,\n"
                + "pci.referencia,pci.cantidad as cant,\n"
                + "to_char(pci.precio_compra,'999G999G999') as precio,\n"
                + "to_char((pci.cantidad*pci.precio_compra),'999G999G999') as subtotal\n"
                + "FROM patrimonio_baja_item pci,patrimonio_producto pp,patrimonio_ubicacion pu,patrimonio_baja pc,patrimonio_baja_motivo pbm\n"
                + "where  pci.fk_idpatrimonio_producto=pp.idpatrimonio_producto\n"
                + "and pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                + "and pci.fk_idpatrimonio_baja=pc.idpatrimonio_baja\n"
                + "and pci.fk_idpatrimonio_baja_motivo=pbm.idpatrimonio_baja_motivo\n"
                + "and pc.estado='DE_BAJA'  \n" + filtro_fecha
                + " order by pci.idpatrimonio_baja_item desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        reporte_ancho_tabla_patrimonio_baja(tbltabla);
    }

    public void reporte_ancho_tabla_patrimonio_baja(JTable tbltabla) {
        int Ancho[] = {5, 12, 30, 18, 10, 9, 5, 8, 8};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.alinear_derecha_columna(tbltabla, 8);
    }

    public void imprimir_patrimonio_baja(Connection conn, String filtro_fecha) {
        String sql = "SELECT pp.idpatrimonio_producto as idpp,\n"
                + "to_char(pci.fecha_creado,'yyyy-MM-dd') as fecha,\n"
                + "pci.descripcion as descripcion,pbm.nombre as motivo,\n"
                + "pu.nombre as ubicacion,\n"
                + "pci.referencia as referencia,pci.cantidad as cant,\n"
                + "pci.precio_compra as precio,\n"
                + "(pci.cantidad*pci.precio_compra) as subtotal\n"
                + "FROM patrimonio_baja_item pci,patrimonio_producto pp,patrimonio_ubicacion pu,patrimonio_baja pc,patrimonio_baja_motivo pbm\n"
                + "where  pci.fk_idpatrimonio_producto=pp.idpatrimonio_producto\n"
                + "and pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                + "and pci.fk_idpatrimonio_baja=pc.idpatrimonio_baja\n"
                + "and pci.fk_idpatrimonio_baja_motivo=pbm.idpatrimonio_baja_motivo\n"
                + "and pc.estado='DE_BAJA'  \n"+filtro_fecha
                + " order by pci.idpatrimonio_baja_item desc;";
        String titulonota = "PATRIMINIO BAJA";
        String direccion = "src/REPORTE/PATRIMONIO/repPatrimonio_baja.jrxml";
        String rutatemp = "repPatrimonio_baja";
        rep.imprimir_jasper_pdf(conn, sql, titulonota, direccion, rutatemp);
    }
}
