package model;

/**
 * Clase Lugar
 * Representa un lugar con su identificador, código postal, ciudad y ubicación.
 * @author Marta
 */
public class Lugar {

    // ==================== ATRIBUTOS ====================

    private final int idLugar;
    private String cp;
    private String ciudad;
    private String ubicacion;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase Lugar.
     * @param idLugar Identificador del lugar
     * @param cp Código postal del lugar
     * @param ciudad Ciudad del lugar
     * @param ubicacion Ubicación del lugar
     */
    public Lugar(int idLugar, String cp, String ciudad, String ubicacion){
        this.idLugar = idLugar;
        this.cp = cp;
        this.ciudad = ciudad;
        this.ubicacion = ubicacion;
    }

    // ==================== GETTERS ====================

    /*
     * Obtiene la ubicación del lugar.
     * @return ubicación del lugar
     */
    public String getUbicacion() { return ubicacion; }

    /*
     * Modifica la ubicacion del lugar
     * @param ubicacion ubicación del lugar
     */
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    /*
     * Obtiene la ciudad del lugar.
     * @return ciudad del lugar
     */
    public String getCiudad() { return ciudad; }

    /*
     * Modifica la ciudad del lugar
     * @param ciudad ciudad del lugar
     */
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    /*
     * Obtiene el código postal del lugar.
     * @return código postal del lugar
     */
    public String getCP() { return cp; }

    /*
     * Modifica el cp del lugar
     * @param cp código postal del lugar
     */
    public void setCP(String cp) { this.cp = cp; }

    /*
     * Obtiene el identificador del lugar.
     * @return identificador del lugar
     */
    public int getIdLugar() { return idLugar; }

    // ==================== OTROS MÉTODOS ====================

    /*
     * Devuelve información en texto del objeto Lugar.
     * @return información del lugar en formato String
     */
    @Override
    public String toString(){
        return "[IDLugar: " + idLugar +
                ", CP: " + cp +
                ", Ciudad: " + ciudad +
                ", Ubicacion: " + ubicacion + "]";
    }
}
