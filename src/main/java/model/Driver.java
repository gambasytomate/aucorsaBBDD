package model;

public class Driver {
    int id_C;
    String nombre;
    String apellido;

    public Driver(int id_C, String nombre, String apellido) {
        this.id_C = id_C;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId_C() {
        return id_C;
    }

    public void setId_C(int id_C) {
        this.id_C = id_C;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id_C=" + id_C +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }

}
