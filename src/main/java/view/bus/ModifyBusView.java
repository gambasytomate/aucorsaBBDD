package view.bus;

import javax.swing.*;
import java.awt.*;

/**
 * Clase ModifyBusView
 * Vista del formulario para modificar un Bus existente.
 * Los campos se pre-rellenan con los datos actuales del bus seleccionado.
 * La matrícula se muestra pero no es editable al ser el identificador del registro.
 */
public class ModifyBusView extends JFrame {

    // ==================== ATRIBUTOS ====================

    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    // La matrícula no es editable porque es el identificador del bus
    private final JLabel matriculaValor;
    private final JTextField tipo = new JTextField(10);
    private final JTextField licencia = new JTextField(10);

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de ModifyBusView.
     * Pre-rellena los campos con los datos actuales del bus seleccionado.
     * @param matricula Matrícula del bus a modificar
     * @param tipoActual Tipo actual del bus
     * @param licenciaActual Licencia actual del bus
     */
    public ModifyBusView(String matricula, String tipoActual, String licenciaActual) {

        this.setTitle("Aucorsa - Modificar Bus");

        // Establece el tamaño y centra la ventana en pantalla
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Inicializa la etiqueta de matrícula con el valor actual
        matriculaValor = new JLabel(matricula);

        // Pre-rellena los campos con los datos actuales del bus
        tipo.setText(tipoActual);
        licencia.setText(licenciaActual);

        // Panel de campos con GridLayout: 3 filas, 2 columnas (etiqueta + campo)
        JPanel camposPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        camposPanel.add(new JLabel("Matrícula:"));
        camposPanel.add(matriculaValor);
        camposPanel.add(new JLabel("Tipo:"));
        camposPanel.add(tipo);
        camposPanel.add(new JLabel("Licencia:"));
        camposPanel.add(licencia);

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

    public String getMatricula() { return matriculaValor.getText(); }

    public JTextField getTipo() { return tipo; }

    public JTextField getLicencia() { return licencia; }

    public JButton getBtnGuardar() { return btnGuardar; }

    public JButton getBtnCancelar() { return btnCancelar; }
}