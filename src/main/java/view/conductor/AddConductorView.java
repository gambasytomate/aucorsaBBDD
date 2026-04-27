package view.conductor;

import javax.swing.*;
import java.awt.*;
/**
 * Clase AddConductorView
 * Vista del formulario para dar de alta un nuevo Conductor.
 * Contiene los campos necesarios para introducir los datos del conductor
 * y los botones para confirmar o cancelar la operación.
 * @author Marta
 */
public class AddConductorView extends JFrame {

    // ==================== ATRIBUTOS ====================

    private final JButton btnAdd = new JButton("Añadir");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final JTextField nombre = new JTextField(10);
    private final JTextField apellidos = new JTextField(10);
    private final JTextField numConductor = new JTextField(10);

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase AddConductorView.
     * Configura el marco principal de la ventana, inicializa los componentes de entrada
     * de texto y botones, y organiza la disposición visual mediante gestores de diseño.
     */
    public AddConductorView() {

        this.setTitle("Aucorsa - Añadir Conductor");

        // Establece el tamaño y centra la ventana en pantalla
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel de campos con GridLayout: 3 filas, 2 columnas (etiqueta + campo)
        JPanel camposPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        camposPanel.add(new JLabel("Nombre:"));
        camposPanel.add(nombre);
        camposPanel.add(new JLabel("Apellido:"));
        camposPanel.add(apellidos);
        camposPanel.add(new JLabel("Núm. conductor:"));
        camposPanel.add(numConductor);

        // Panel de botones con FlowLayout centrado
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.add(btnAdd);
        botonesPanel.add(btnCancelar);

        // Añade los paneles a la ventana y la hace visible
        add(camposPanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // ==================== GETTERS ====================

    /*
     * Obtiene el componente de texto para el número identificativo del conductor.
     * @return JTextField correspondiente al número de conductor.
     */
    public JTextField getNumConductor() { return numConductor; }

    /*
     * Obtiene el componente de texto para los apellidos del conductor.
     * @return JTextField correspondiente a los apellidos.
     */
    public JTextField getApellidos() { return apellidos; }

    /*
     * Obtiene el componente de texto para el nombre del conductor.
     * @return JTextField correspondiente al nombre.
     */
    public JTextField getNombre() { return nombre; }

    /*
     * Obtiene el botón destinado a cancelar la acción y cerrar el formulario.
     * @return JButton de cancelación.
     */
    public JButton getBtnCancelar() { return btnCancelar; }

    /*
     * Obtiene el botón destinado a confirmar y añadir el nuevo conductor.
     * @return JButton de confirmación de alta.
     */
    public JButton getBtnAdd() { return btnAdd; }
}