package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.patrimonio_carga;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_patrimonio_carga {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenRender everen = new EvenRender();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "PATRIMONIO_CARGA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "PATRIMONIO_CARGA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO patrimonio_carga(idpatrimonio_carga,fecha_creado,creado_por,estado,observacion,monto) VALUES (?,?,?,?,?,?);";
    private String sql_update = "UPDATE patrimonio_carga SET fecha_creado=?,creado_por=?,estado=?,observacion=?,monto=? WHERE idpatrimonio_carga=?;";
//    private String sql_select = "SELECT idpatrimonio_carga,fecha_creado,creado_por,estado,observacion,monto FROM patrimonio_carga order by 1 desc;";
    private String sql_cargar = "SELECT idpatrimonio_carga,fecha_creado,creado_por,estado,observacion,monto FROM patrimonio_carga WHERE idpatrimonio_carga=";

    public void insertar_patrimonio_carga(Connection conn, patrimonio_carga ENTpc) {
        ENTpc.setC1idpatrimonio_carga(eveconn.getInt_ultimoID_mas_uno(conn, ENTpc.getTb_patrimonio_carga(), ENTpc.getId_idpatrimonio_carga()));
        String titulo = "insertar_patrimonio_carga";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTpc.getC1idpatrimonio_carga());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTpc.getC3creado_por());
            pst.setString(4, ENTpc.getC4estado());
            pst.setString(5, ENTpc.getC5observacion());
            pst.setDouble(6, ENTpc.getC6monto());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTpc.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTpc.toString(), titulo);
        }
    }

    public void update_patrimonio_carga(Connection conn, patrimonio_carga ENTpc) {
        String titulo = "update_patrimonio_carga";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, ENTpc.getC3creado_por());
            pst.setString(3, ENTpc.getC4estado());
            pst.setString(4, ENTpc.getC5observacion());
            pst.setDouble(5, ENTpc.getC6monto());
            pst.setInt(6, ENTpc.getC1idpatrimonio_carga());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpc.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpc.toString(), titulo);
        }
    }

    public void update_patrimonio_carga_anular(Connection conn, patrimonio_carga ENTpc) {
        String titulo = "update_patrimonio_carga_anular";
        PreparedStatement pst = null;
        String sql_update = "UPDATE patrimonio_carga SET estado=? WHERE idpatrimonio_carga=?;";
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setString(1, ENTpc.getC4estado());
            pst.setInt(2, ENTpc.getC1idpatrimonio_carga());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTpc.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTpc.toString(), titulo);
        }
    }

    public void cargar_patrimonio_carga(Connection conn, patrimonio_carga ENTpc, int idpatrimonio_carga) {
        String titulo = "Cargar_patrimonio_carga";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idpatrimonio_carga, titulo);
            if (rs.next()) {
                ENTpc.setC1idpatrimonio_carga(rs.getInt(1));
                ENTpc.setC2fecha_creado(rs.getString(2));
                ENTpc.setC3creado_por(rs.getString(3));
                ENTpc.setC4estado(rs.getString(4));
                ENTpc.setC5observacion(rs.getString(5));
                ENTpc.setC6monto(rs.getDouble(6));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTpc.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTpc.toString(), titulo);
        }
    }

    public void actualizar_tabla_patrimonio_carga(Connection conn, JTable tbltabla) {
        String sql = "select idpatrimonio_carga as idpc,\n"
                + "to_char(fecha_creado,'yyyy-MM-dd HH24:MI') as fecha,creado_por as usuario,\n"
                + "estado,observacion,to_char(monto,'999G999G999') as monto \n"
                + "from patrimonio_carga\n"
                + "order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_patrimonio_carga(tbltabla);
    }

    public void ancho_tabla_patrimonio_carga(JTable tbltabla) {
        int Ancho[] = {5, 15, 15, 10, 45, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 5);
        everen.rendertabla_estados(tbltabla, 3);
    }

    public void reporte_actualizar_tabla_patrimonio_carga(Connection conn, JTable tbltabla, String filtro_fecha) {
        String sql = "SELECT pp.idpatrimonio_producto as idpp,\n"
                + "to_char(pci.fecha_creado,'yyyy-MM-dd HH24:MI:00') as fecha,\n"
                + "pci.descripcion,\n"
                + "pu.nombre as ubicacion,\n"
                + "pci.referencia,pci.cantidad as cant,\n"
                + "to_char(pci.precio_compra,'999G999G999') as precio,\n"
                + "to_char((pci.cantidad*pci.precio_compra),'999G999G999') as subtotal\n"
                + "FROM patrimonio_carga_item pci,patrimonio_producto pp,patrimonio_ubicacion pu,patrimonio_carga pc\n"
                + "where  pci.fk_idpatrimonio_producto=pp.idpatrimonio_producto\n"
                + "and pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                + "and pci.fk_idpatrimonio_carga=pc.idpatrimonio_carga\n"
                + "and pc.estado='CARGADO' \n" + filtro_fecha
                + " order by pci.idpatrimonio_carga_item desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        reporte_ancho_tabla_patrimonio_carga(tbltabla);
    }

    public void reporte_ancho_tabla_patrimonio_carga(JTable tbltabla) {
        int Ancho[] = {5, 15, 35, 10, 10, 5, 10, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
    }

    public void imprimir_patrimonio_carga(Connection conn, String fec_caja) {
        String sql = "SELECT pp.idpatrimonio_producto as idpp,\n"
                + "to_char(pci.fecha_creado,'yyyy-MM-dd HH24:MI:00') as fecha,\n"
                + "pci.descripcion,\n"
                + "pu.nombre as ubicacion,\n"
                + "pci.referencia,pci.cantidad as cant,\n"
                + "pci.precio_compra as precio,\n"
                + "(pci.cantidad*pci.precio_compra) as subtotal\n"
                + "FROM patrimonio_carga_item pci,patrimonio_producto pp,patrimonio_ubicacion pu,patrimonio_carga pc\n"
                + "where  pci.fk_idpatrimonio_producto=pp.idpatrimonio_producto\n"
                + "and pp.fk_idpatrimonio_ubicacion=pu.idpatrimonio_ubicacion\n"
                + "and pci.fk_idpatrimonio_carga=pc.idpatrimonio_carga\n"+fec_caja
                + " and pc.estado='CARGADO'\n"
                + "order by pci.idpatrimonio_carga_item desc;";
        String titulonota = "PATRIMINIO CARGA";
        String direccion = "src/REPORTE/PATRIMONIO/repPatrimonio_carga.jrxml";
        String rutatemp = "repPatrimonio_carga";
        rep.imprimir_jasper_pdf(conn, sql, titulonota, direccion, rutatemp);
    }
}
