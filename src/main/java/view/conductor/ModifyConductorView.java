package view.conductor;

import javax.swing.*;
import java.awt.*;

/**
 * Clase ModifyConductorView
 * Vista del formulario para modificar un Conductor existente.
 * Los campos se pre-rellenan con los datos actuales del conductor seleccionado.
 * El número de conductor se muestra pero no es editable al ser el identificador del registro.
 */
public class ModifyConductorView extends JFrame {

    // ==================== ATRIBUTOS ====================

    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    // El número de conductor no es editable porque es el identificador del conductor
    private final JLabel numConductorValor;
    private final JTextField nombre = new JTextField(10);
    private final JTextField apellido = new JTextField(10);

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de ModifyConductorView.
     * Pre-rellena los campos con los datos actuales del conductor seleccionado.
     * @param numConductor Número del conductor a modificar
     * @param nombreActual Nombre actual del conductor
     * @param apellidoActual Apellido actual del conductor
     */
    public ModifyConductorView(int numConductor, String nombreActual, String apellidoActual) {

        this.setTitle("Aucorsa - Modificar Conductor");

        // Establece el tamaño y centra la ventana en pantalla
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Inicializa la etiqueta de número de conductor con el valor actual
        numConductorValor = new JLabel(String.valueOf(numConductor));

        // Pre-rellena los campos con los datos actuales del conductor
        nombre.setText(nombreActual);
        apellido.setText(apellidoActual);

        // Panel de campos con GridLayout: 3 filas, 2 columnas (etiqueta + campo)
        JPanel camposPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        camposPanel.add(new JLabel("Núm. conductor:"));
        camposPanel.add(numConductorValor);
        camposPanel.add(new JLabel("Nombre:"));
        camposPanel.add(nombre);
        camposPanel.add(new JLabel("Apellido:"));
        camposPanel.add(apellido);

        // Panel de botones con FlowLayout centrado
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnCancelar);

        // Añade los paneles a la ventana y la hace visible
        add(camposPanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // ==================== GETTERS ====================

    public int getNumConductor() { return Integer.parseInt(numConductorValor.getText()); }

    public JTextField getNombre() { return nombre; }

    public JTextField getApellido() { return apellido; }

    public JButton getBtnGuardar() { return btnGuardar; }

    public JButton getBtnCancelar() { return btnCancelar; }
}