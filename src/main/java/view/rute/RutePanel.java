package view.rute;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

    public class RutePanel extends JPanel {

        // ==================== ATRIBUTOS ====================

        // Tabla visual donde se mostrarán las rutas
        JTable tablaVista = new JTable();

        // Modelo de datos que alimenta la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel();

        // ==================== CONSTRUCTOR ====================

        /*
         * Constrctor de la clase ConductorPanel.
         * Configura el diseño del panel, inicializa el modelo de la tabla con las
         * columnas pertinentes (ID, Nombre y Apellido) y define las propiedades
         * de selección y edición para la visualización de datos.
         */
        public RutePanel() {

            setLayout(new BorderLayout());

            // Inicializa el modelo con las columnas de Conductor, sin filas iniciales
            modeloTabla = new DefaultTableModel(
                    new String[]{"register", "numdriver", "idplace"}, 0) {

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

        // ==================== GETTERS ====================

        /*
         * Obtiene el modelo de datos asociado a la tabla de conductores.
         * * @return El DefaultTableModel que gestiona los registros de la tabla.
         */
        public DefaultTableModel getModeloTabla() { return modeloTabla; }

        /*
         * Obtiene el componente JTable que muestra la información de los conductores.
         * * @return El componente visual de la tabla.
         */
        public JTable getTablaVista() { return tablaVista; }
    }