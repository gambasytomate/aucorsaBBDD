package model;

/**
 * Clase Routes
 * Representa la relación entre Bus, Conductor y Lugar,
 * indicando qué conductor conduce qué bus, a qué lugar y en qué día.
 * @author Marta
 */
public class Routes {

    // ==================== ATRIBUTOS ====================

    private final String matricula;
    private final int numConductor;
    private final int idLugar;
    private String diaSemana;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase Routes.
     * @param matricula Matrícula del bus
     * @param numConductor Número identificativo del conductor
     * @param idLugar Identificador del lugar
     * @param diaSemana Día de la semana del recorrido
     */
    public Routes(String matricula, int numConductor, int idLugar, String diaSemana){
        this.matricula = matricula;
        this.numConductor = numConductor;
        this.idLugar = idLugar;
        this.diaSemana = diaSemana;
    }

    // ==================== GETTERS Y SETTERS ====================

    /*
     * Obtiene la matrícula del bus.
     * @return matrícula del bus
     */
    public String getMatricula() { return matricula; }

    /*
     * Obtiene el número del conductor.
     * @return número del conductor
     */
    public int getNumConductor() { return numConductor; }

    /*
     * Obtiene el identificador del lugar.
     * @return identificador del lugar
     */
    public int getIdLugar() { return idLugar; }

    /*
     * Obtiene el día de la semana.
     * @return día de la semana
     */
    public String getDiaSemana() { return diaSemana; }

    /*
     * Modifica el día de la semana.
     * @param diaSemana nuevo día de la semana
     */
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    // ==================== OTROS MÉTODOS ====================

    /*
     * Devuelve información en texto del objeto Routes.
     * @return información de la relación Routes en formato String
     */
    @Override
    public String toString(){
        return "[Matrícula: " + matricula +
                ", NumConductor: " + numConductor +
                ", IDLugar: " + idLugar +
                ", Día: " + diaSemana + "]";
    }
}
