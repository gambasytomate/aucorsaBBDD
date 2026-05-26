package view.rute;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Clase ModifyRutaView modificada con JComboBox para conductores.
 * @author Marta
 */
public class ModifyRuteView extends JFrame {

    // ==================== ATRIBUTOS ====================
    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    private final JLabel matriculaValor;
    private final JLabel idLugarValor;
    private final JTextField diaSemana = new JTextField(10);

    // CAMBIO CLAVE: Ahora es un JComboBox en lugar de un JTextField
    private final JComboBox<String> comboConductores = new JComboBox<>();

    // ==================== CONSTRUCTOR ====================
    public ModifyRuteView(String mat, int lugarActual, String diaActual, List<String> listaConductores) {
        this.setTitle("Aucorsa - Modificar Ruta");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        matriculaValor = new JLabel(mat);
        idLugarValor = new JLabel(String.valueOf(lugarActual));
        diaSemana.setText(diaActual);

        // Rellenamos el ComboBox con las opciones que le pasa el Controlador
        if (listaConductores != null) {
            for (String cond : listaConductores) {
                comboConductores.addItem(cond);
            }
        }

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        camposPanel.add(new JLabel("Matrícula Bus:"));
        camposPanel.add(matriculaValor);

        // Colocamos el desplegable en el formulario
        camposPanel.add(new JLabel("Seleccionar Conductor:"));
        camposPanel.add(comboConductores);

        camposPanel.add(new JLabel("ID Lugar:"));
        camposPanel.add(idLugarValor);
        camposPanel.add(new JLabel("Día de la semana:"));
        camposPanel.add(diaSemana);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnCancelar);

        add(camposPanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // ==================== GETTERS ====================
    public String getMatricula() { return matriculaValor.getText(); }
    public int getIdLugar() { return Integer.parseInt(idLugarValor.getText()); }
    public JTextField getDiaSemana() { return diaSemana; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }

    // Getter para obtener el String seleccionado (ej: "ID: 3 - Juan")
    public JComboBox<String> getComboConductores() { return comboConductores; }
}