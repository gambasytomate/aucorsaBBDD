package view.bus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 * Clase BusPanel
 * Panel de la sección de buses.
 * Muestra una tabla con todos los buses cargados desde la base de datos.
 * * @author Marta
 */
public class BusPanel extends JPanel {

    // ==================== ATRIBUTOS ====================

    // Tabla visual donde se muestran los buses
    private JTable tablaVista = new JTable();

    // Modelo de datos que alimenta la tabla
    private DefaultTableModel modeloTabla;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase BusPanel.
     * Configura el diseño del panel, inicializa el modelo de la tabla con las
     * columnas correspondientes y define las propiedades de selección y edición
     * de la interfaz gráfica.
     */
    public BusPanel() {

        setLayout(new BorderLayout());

        // Inicializa el modelo con las columnas de Bus, sin filas iniciales
        modeloTabla = new DefaultTableModel(
                new String[]{"Matricula", "Tipo", "Licencia"}, 0) {

            // Hace no editable ninguna celda de la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Asocia el modelo a la tabla
        tablaVista = new JTable(modeloTabla);

        // Configura el comportamiento de selección de la tabla
        tablaVista.setFillsViewportHeight(true);
        tablaVista.setRowSelectionAllowed(true);
        tablaVista.setColumnSelectionAllowed(false);
        tablaVista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Añade la tabla dentro de un JScrollPane para permitir desplazamiento
        add(new JScrollPane(tablaVista), BorderLayout.CENTER);
    }

    // ==================== GETTERS Y SETTERS ====================

    /*
     * Obtiene el componente JTable que muestra la información de los buses.
     * @return El componente visual de la tabla.
     */
    public JTable getTablaVista() { return tablaVista; }

    /*
     * Establece el componente JTable del panel.
     * @param tablaVista El nuevo componente JTable a asignar.
     */
    public void setTablaVista(JTable tablaVista) { this.tablaVista = tablaVista; }

    /*
     * Obtiene el modelo de datos asociado a la tabla.
     * @return El DefaultTableModel que gestiona los datos de la tabla.
     */
    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    /*
     * Establece el modelo de datos de la tabla.
     * @param modeloTabla El nuevo modelo de tabla a asignar.
     */
    public void setModeloTabla(DefaultTableModel modeloTabla) { this.modeloTabla = modeloTabla; }
}