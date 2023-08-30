package FORMULARIO.ENTIDAD;

public class patrimonio_ubicacion {

//---------------DECLARAR VARIABLES---------------
    private int C1idpatrimonio_ubicacion;
    private String C2fecha_creado;
    private String C3creado_por;
    private String C4nombre;
    private boolean C5activo;
    private static String nom_tabla;
    private static String nom_idtabla;
//---------------TABLA-ID---------------

    public patrimonio_ubicacion() {
        setTb_patrimonio_ubicacion("patrimonio_ubicacion");
        setId_idpatrimonio_ubicacion("idpatrimonio_ubicacion");
    }

    public static String getTb_patrimonio_ubicacion() {
        return nom_tabla;
    }

    public static void setTb_patrimonio_ubicacion(String nom_tabla) {
        patrimonio_ubicacion.nom_tabla = nom_tabla;
    }

    public static String getId_idpatrimonio_ubicacion() {
        return nom_idtabla;
    }

    public static void setId_idpatrimonio_ubicacion(String nom_idtabla) {
        patrimonio_ubicacion.nom_idtabla = nom_idtabla;
    }
//---------------GET-SET-CAMPOS---------------

    public int getC1idpatrimonio_ubicacion() {
        return C1idpatrimonio_ubicacion;
    }

    public void setC1idpatrimonio_ubicacion(int C1idpatrimonio_ubicacion) {
        this.C1idpatrimonio_ubicacion = C1idpatrimonio_ubicacion;
    }

    public String getC2fecha_creado() {
        return C2fecha_creado;
    }

    public void setC2fecha_creado(String C2fecha_creado) {
        this.C2fecha_creado = C2fecha_creado;
    }

    public String getC3creado_por() {
        return C3creado_por;
    }

    public void setC3creado_por(String C3creado_por) {
        this.C3creado_por = C3creado_por;
    }

    public String getC4nombre() {
        return C4nombre;
    }

    public void setC4nombre(String C4nombre) {
        this.C4nombre = C4nombre;
    }

    public boolean getC5activo() {
        return C5activo;
    }

    public void setC5activo(boolean C5activo) {
        this.C5activo = C5activo;
    }

    public String toString() {
        return "patrimonio_ubicacion(" + ",idpatrimonio_ubicacion=" + C1idpatrimonio_ubicacion + " ,fecha_creado=" + C2fecha_creado + " ,creado_por=" + C3creado_por + " ,nombre=" + C4nombre + " ,activo=" + C5activo + " )";
    }
}
