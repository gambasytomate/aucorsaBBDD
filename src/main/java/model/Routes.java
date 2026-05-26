package model;

/**
 * Clase Routes
 * Representa la relación entre Bus, Conductor y Lugar,
 * indicando qué conductor conduce qué bus, a qué lugar y en qué día.
 * @author Marta
 */
public class Routes {

    // ==================== ATRIBUTOS ====================

    private final String registro;
    private final int numConductor;
    private final int idLugar;
    private String diaSemana;
    private String imagen;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase Routes.
     * @param matricula Matrícula del bus
     * @param numConductor Número identificativo del conductor
     * @param idLugar Identificador del lugar
     * @param diaSemana Día de la semana del recorrido
     */
    public Routes(String registro, int numConductor, int idLugar, String diaSemana, String imagen) {
        this.registro = registro;
        this.numConductor = numConductor;
        this.idLugar = idLugar;
        this.diaSemana = diaSemana;
        this.imagen = imagen;
    }

    // ==================== GETTERS Y SETTERS ====================


    public String getRegistro() {
        return registro;
    }

    public int getNumConductor() {
        return numConductor;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
}