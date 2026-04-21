package model;

public class Rute {
    int id_L;
    int cod_Post;
    String ciudad;
    String ubicacion;

    public Rute(int id_L, int cod_Post, String ciudad, String ubicacion) {
        this.id_L = id_L;
        this.cod_Post = cod_Post;
        this.ciudad = ciudad;
        this.ubicacion = ubicacion;
    }

    public int getId_L() {
        return id_L;
    }

    public void setId_L(int id_L) {
        this.id_L = id_L;
    }

    public int getCod_Post() {
        return cod_Post;
    }

    public void setCod_Post(int cod_Post) {
        this.cod_Post = cod_Post;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Rute{" +
                "id_L=" + id_L +
                ", cod_Post=" + cod_Post +
                ", ciudad='" + ciudad + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
