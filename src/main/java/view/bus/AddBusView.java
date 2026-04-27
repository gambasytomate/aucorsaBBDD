package view.bus;

import javax.swing.*;
import java.awt.*;

/**
 * Clase AddBusView
 * Vista del formulario para dar de alta un nuevo Bus.
 * Contiene los campos necesarios para introducir los datos del bus
 * y los botones para confirmar o cancelar la operación.
 * @author Marta
 */
public class AddBusView extends JFrame {

    // ==================== ATRIBUTOS ====================

    private final JButton btnAdd = new JButton("Añadir");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final JTextField matricula = new JTextField(10);
    private final JTextField tipo = new JTextField(10);
    private final JTextField licencia = new JTextField(10);

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase AddBusView.
     * Configura la ventana principal del formulario, inicializa los componentes
     * visuales, establece los gestores de diseño (Layouts) y hace visible la interfaz.
     */
    public AddBusView() {

        this.setTitle("Aucorsa - Añadir Bus");

        // Establece el tamaño y centra la ventana en pantalla
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel de campos con GridLayout: 3 filas, 2 columnas (etiqueta + campo)
        JPanel camposPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        camposPanel.add(new JLabel("Matrícula:"));
        camposPanel.add(matricula);
        camposPanel.add(new JLabel("Tipo:"));
        camposPanel.add(tipo);
        camposPanel.add(new JLabel("Licencia:"));
        camposPanel.add(licencia);

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
     * Obtiene el componente de texto correspondiente a la matrícula del bus.
     * @return JTextField para la entrada de la matrícula.
     */
    public JTextField getMatricula() { return matricula; }

    /*
     * Obtiene el componente de texto correspondiente al tipo de bus.
     * @return JTextField para la entrada del tipo de vehículo.
     */
    public JTextField getTipo() { return tipo; }

    /*
     * Obtiene el componente de texto correspondiente a la licencia del bus.
     * @return JTextField para la entrada de la licencia.
     */
    public JTextField getLicencia() { return licencia; }

    /*
     * Obtiene el botón destinado a cancelar la operación y cerrar la vista.
     * @return JButton de cancelación.
     */
    public JButton getBtnCancelar() { return btnCancelar; }

    /*
     * Obtiene el botón destinado a confirmar la adición del nuevo bus.
     * @return JButton de confirmación de alta.
     */
    public JButton getBtnAdd() { return btnAdd; }
}