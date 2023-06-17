package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.factura;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;

public class DAO_factura {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "FACTURA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "FACTURA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO factura(idfactura,fecha_creado,creado_por,"
            + "nro_factura,fecha_nota,estado,condicion"
            + ",monto_total,monto_iva5,monto_iva10,monto_letra,"
            + "fk_idtimbrado,fk_idpersona,fk_idventa,numero) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE factura SET fecha_creado=?,creado_por=?,"
            + "nro_factura=?,fecha_nota=?,estado=?,condicion=?,"
            + "monto_total=?,monto_iva5=?,monto_iva10=?,monto_letra=?,"
            + "fk_idtimbrado=?,fk_idpersona=?,fk_idventa=? WHERE idfactura=?;";
    private String sql_select = "SELECT idfactura,fecha_creado,creado_por,"
            + "nro_factura,fecha_nota,estado,condicion,monto_total,monto_iva5,monto_iva10,monto_letra,"
            + "fk_idtimbrado,fk_idpersona,fk_idventa FROM factura order by 1 desc;";
    private String sql_cargar = "SELECT idfactura,fecha_creado,creado_por,"
            + "nro_factura,fecha_nota,estado,condicion,"
            + "monto_total,monto_iva5,monto_iva10,monto_letra,"
            + "fk_idtimbrado,fk_idpersona,fk_idventa FROM factura WHERE idfactura=";

    public int getInt_ult_nro_factura(Connection conn){
        String titulo="getInt_ult_nro_factura";
        int getid = 1;
        String sql = "select (numero+1) nuevo_nro from factura where idfactura=(select max(idfactura) as maxid from factura ); ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                getid = rs.getInt("nuevo_nro");
                if(getid==0){
                    getid=1;
                }
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return getid;
    }
    public void insertar_factura(Connection conn, factura ENTf) {
        ENTf.setC1idfactura(eveconn.getInt_ultimoID_mas_uno(conn, ENTf.getTb_factura(), ENTf.getId_idfactura()));
        String titulo = "insertar_factura";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTf.getC1idfactura());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTf.getC3creado_por());
            pst.setString(4, ENTf.getC4nro_factura());
            pst.setDate(5, evefec.getDateSQL_sistema());
            pst.setString(6, ENTf.getC6estado());
            pst.setString(7, ENTf.getC7condicion());
            pst.setDouble(8, ENTf.getC8monto_total());
            pst.setDouble(9, ENTf.getC9monto_iva5());
            pst.setDouble(10, ENTf.getC10monto_iva10());
            pst.setString(11, ENTf.getC11monto_letra());
            pst.setInt(12, ENTf.getC12fk_idtimbrado());
            pst.setInt(13, ENTf.getC13fk_idpersona());
            pst.setInt(14, ENTf.getC14fk_idventa());
            pst.setInt(15, ENTf.getC15numero());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTf.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTf.toString(), titulo);
        }
    }

    public void update_factura(Connection conn, factura f) {
        String titulo = "update_factura";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, f.getC3creado_por());
            pst.setString(3, f.getC4nro_factura());
            pst.setDate(4, evefec.getDateSQL_sistema());
            pst.setString(5, f.getC6estado());
            pst.setString(6, f.getC7condicion());
            pst.setDouble(7, f.getC8monto_total());
            pst.setDouble(8, f.getC9monto_iva5());
            pst.setDouble(9, f.getC10monto_iva10());
            pst.setString(10, f.getC11monto_letra());
            pst.setInt(11, f.getC12fk_idtimbrado());
            pst.setInt(12, f.getC13fk_idpersona());
            pst.setInt(13, f.getC14fk_idventa());
            pst.setInt(14, f.getC1idfactura());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + f.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + f.toString(), titulo);
        }
    }

    public void cargar_factura(Connection conn, factura f, int idfactura) {
        String titulo = "Cargar_factura";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idfactura, titulo);
            if (rs.next()) {
                f.setC1idfactura(rs.getInt(1));
                f.setC2fecha_creado(rs.getString(2));
                f.setC3creado_por(rs.getString(3));
                f.setC4nro_factura(rs.getString(4));
                f.setC5fecha_nota(rs.getString(5));
                f.setC6estado(rs.getString(6));
                f.setC7condicion(rs.getString(7));
                f.setC8monto_total(rs.getDouble(8));
                f.setC9monto_iva5(rs.getDouble(9));
                f.setC10monto_iva10(rs.getDouble(10));
                f.setC11monto_letra(rs.getString(11));
                f.setC12fk_idtimbrado(rs.getInt(12));
                f.setC13fk_idpersona(rs.getInt(13));
                f.setC14fk_idventa(rs.getInt(14));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + f.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + f.toString(), titulo);
        }
    }

    public void actualizar_tabla_factura(Connection conn, JTable tbltabla,String filtro) {
        String sql = "select f.idfactura,to_char(f.fecha_nota,'yyyy-MM-dd') as fecha,f.nro_factura, \n"
                + "p.nombre,p.ruc,\n"
                + "TRIM(to_char(f.monto_iva5,'999G999G999')) as iva5,\n"
                + "TRIM(to_char(f.monto_iva10,'999G999G999')) as iva10,\n"
                + "TRIM(to_char(f.monto_total,'999G999G999')) as total,\n"
                + "f.estado,f.monto_iva5,f.monto_iva10,f.monto_total\n"
                + "from factura f,persona p\n"
                + "where f.fk_idpersona=p.idpersona\n"
                + "order by f.fecha_nota desc,f.nro_factura desc;";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_factura(tbltabla);
    }

    public void ancho_tabla_factura(JTable tbltabla) {
        int Ancho[] = {5,8,12,30,8,10, 10, 10, 10, 1, 1, 1};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
        evejt.ocultar_columna(tbltabla, 9);
        evejt.ocultar_columna(tbltabla, 10);
        evejt.ocultar_columna(tbltabla, 11);
    }

    public void imprimir_nota_factura(Connection conn, int idfactura) {
        boolean vista_previa = true;
        String sql = "select f.idfactura as id,to_char(f.fecha_nota,'yyyy-MM-dd') as fecha,\n"
                + "f.condicion as condicion,f.monto_total as mtotal,f.monto_iva5 as miva5,f.monto_iva10 as miva10,\n"
                + "f.monto_letra as mletra,\n"
                + "p.nombre as cli_nombre,p.ruc as cli_ruc,p.direccion as cli_direccion,p.telefono as cli_telefono,\n"
                + "fi.fk_idproducto as it_idp,fi.cantidad as it_cant,fi.descripcion as it_descrip,\n"
                + "(fi.precio_iva5+fi.precio_iva10+fi.precio_exenta) as it_punit,\n"
                + "(fi.precio_exenta*fi.cantidad) as it_subexe,\n"
                + "(fi.precio_iva5*fi.cantidad) as it_subiva5,\n"
                + "(fi.precio_iva10*fi.cantidad) as if_subiva10 \n"
                + "from factura f,persona p,factura_item fi\n"
                + "where f.fk_idpersona=p.idpersona\n"
                + "and f.idfactura=fi.fk_idfactura\n"
                + "and f.idfactura=" + idfactura;
        String titulo = "NOTA FACTURA";
        String direccion = "src/REPORTE/FACTURA/repNotaFactura.jrxml";
        rep.imprimirjasper_por_nombre_impresora(conn, sql, titulo, direccion, vista_previa);
    }
}
