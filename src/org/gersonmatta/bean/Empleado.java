package org.gersonmatta.bean;

public class Empleado {
    
    /* FORMA MYSQL
    
    codigoEmpleado int not null auto_increment,
    DPIEmpleado varchar(15) not null,
    nombresEmpleado varchar(200) not null,
    telefonoEmpleado varchar(8) not null,
    estado varchar(1) not null,
    usuario varchar(15) not null,
    
    */
    
    private int codigoEmpleado;
    private String DPIEmpleado;
    private String nombresEmpleado;
    private String telefonoEmpleado;
    private String estado;
    private String usuario;

    public Empleado() {
    }

    public Empleado(int codigoEmpleado, String DPIEmpleado, String nombresEmpleado, String telefonoEmpleado, String estado, String usuario) {
        this.codigoEmpleado = codigoEmpleado;
        this.DPIEmpleado = DPIEmpleado;
        this.nombresEmpleado = nombresEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.estado = estado;
        this.usuario = usuario;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getDPIEmpleado() {
        return DPIEmpleado;
    }

    public void setDPIEmpleado(String DPIEmpleado) {
        this.DPIEmpleado = DPIEmpleado;
    }

    public String getNombresEmpleado() {
        return nombresEmpleado;
    }

    public void setNombresEmpleado(String nombresEmpleado) {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
