package model;

public class Bus {
    String id_bus;
    String tipo;
    String licencia;

    public Bus(String id_bus, String tipo, String licencia) {
        this.id_bus = id_bus;
        this.tipo = tipo;
        this.licencia = licencia;
    }

    public String getId_bus() {
        return id_bus;
    }

    public void setId_bus(String id_bus) {
        this.id_bus = id_bus;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id_bus='" + id_bus + '\'' +
                ", tipo='" + tipo + '\'' +
                ", licencia='" + licencia + '\'' +
                '}';
    }
}
