package model;

/**
 * Clase Conductor
 * Representa a un conductor con su número identificativo, nombre y apellido.
 * @author Marta
 */
public class Conductor {

    // ==================== ATRIBUTOS ====================

    private final int numConductor;
    private String nombre;
    private String apellido;
    private String imagen;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase Conductor.
     * @param numConductor Número identificativo del conductor
     * @param nombre Nombre del conductor
     * @param apellido Apellido del conductor
     */
    public Conductor(int numConductor, String nombre, String apellido, String imagen){
        this.numConductor = numConductor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.imagen = imagen;

    }

    // ==================== GETTERS ====================

    /*
     * Obtiene el número del conductor.
     * @return número del conductor
     */
    public int getNumConductor() { return numConductor; }

    /*
     * Obtiene el nombre del conductor.
     * @return nombre del conductor
     */
    public String getNombre() { return nombre; }

    /*
     * Modifica el nombre del conductor.
     * @param nombre nombre del conductor
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /*
     * Obtiene el apellido del conductor.
     * @return apellido del conductor
     */
    public String getApellido() { return apellido; }

    /*
     * Modifica el apellido del conductor.
     * @param apellido apellido del conductor
     */
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    // ==================== OTROS MÉTODOS ====================

    /*
     * Devuelve información en forma de texto del objeto Conductor.
     * @return información del conductor en formato String
     */
    @Override
    public String toString(){
        return "[NumConductor: " + numConductor +
                ", Nombre: " + nombre +
                ", Apellido: " + apellido + "]";
    }
}