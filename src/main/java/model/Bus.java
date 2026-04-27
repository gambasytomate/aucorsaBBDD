package model;

/**
 * Clase Bus
 * Representa un autobús con su matrícula, tipo y licencia.
 * @author Marta
 */
public class Bus {

    // ==================== ATRIBUTOS ====================

    private final String matricula;
    private String tipo;
    private String licencia;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase Bus.
     * @param matricula Matrícula o identificador del autobús
     * @param tipo Tipo de autobús
     * @param licencia Licencia del autobús
     */
    public Bus(String matricula, String tipo, String licencia){
        this.matricula = matricula;
        this.licencia = licencia;
        this.tipo = tipo;
    }

    // ==================== GETTERS ====================

    /*
     * Obtiene la matrícula del autobús.
     * @return matrícula del autobús
     */
    public String getMatricula(){ return matricula; }

    /*
     * Obtiene el tipo de autobús.
     * @return tipo del autobús
     */
    public String getTipo(){ return tipo; }

    /*
     * Modifica el tipo de autobús.
     * @param tipo tipo del autobús
     */
    public void setTipo(String tipo) { this.tipo = tipo; }

    /*
     * Obtiene la licencia del autobús.
     * @return licencia del autobús
     */
    public String getLicencia(){ return licencia; }

    /*
     * Modifica la licencia de autobús
     * @param licencia licencia del autobús
     */
    public void setLicencia(String licencia) { this.licencia = licencia; }

    // ==================== OTROS MÉTODOS ====================

    /*
     * Devuelve información en forma de texto del objeto Bus.
     * @return información del autobús en formato String
     */
    @Override
    public String toString(){
        return "[Matrícula: " + matricula +
                ", Tipo: " + tipo +
                ", Licencia: " + licencia + "]";
    }
}
