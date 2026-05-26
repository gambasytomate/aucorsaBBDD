package view.rute;

import javax.swing.*;
import java.awt.*;

/**
 * Clase DetailsRutaView
 * Vista detallada para la visualización y edición de la ficha de una ruta.
 * Incluye gestión de imagen de mapa/lugar, datos de ruta y navegación entre registros.
 * @author Marta
 */
public class DetailsRuteView extends JFrame {

    // ==================== ATRIBUTOS ====================

    JTextField register   = new JTextField(10);
    JTextField numDriver  = new JTextField(10);
    JTextField idPlace    = new JTextField(10);

    JButton btnEditar    = new JButton("Editar");
    JButton btnCargar    = new JButton("Cargar imagen");
    JButton btnAnterior  = new JButton("<<");
    JButton btnSiguiente = new JButton(">>");

    JLabel numPagina = new JLabel("página");

    // Tamaño fijo para que la imagen siempre tenga espacio reservado
    private static final int IMG_W = 150;
    private static final int IMG_H = 150;

    JLabel etiquetaImagen = new JLabel("Sin imagen", SwingConstants.CENTER);

    JPanel panelPrincipal    = new JPanel();
    JPanel panelCentro       = new JPanel();
    JPanel panelCentroEste   = new JPanel();
    JPanel panelCentroOeste  = new JPanel();
    JPanel panelSur          = new JPanel();
    JPanel panelCamposSur    = new JPanel();

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase DetailsRutaView.
     * Configura el marco principal de la ventana, inicializa los componentes de entrada
     * de texto y botones, y organiza la disposición visual mediante gestores de diseño.
     */
    public DetailsRuteView() {

        setTitle("Ficha ruta");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        etiquetaImagen.setPreferredSize(new Dimension(IMG_W, IMG_H));
        etiquetaImagen.setMinimumSize(new Dimension(IMG_W, IMG_H));
        etiquetaImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        etiquetaImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelCentro.setLayout(new BorderLayout());
        panelCentroEste.setLayout(new BoxLayout(panelCentroEste, BoxLayout.Y_AXIS));
        panelCentroOeste.setLayout(new BoxLayout(panelCentroOeste, BoxLayout.Y_AXIS));
        panelCamposSur.setLayout(new FlowLayout());
        panelSur.setLayout(new BorderLayout(10, 10));

        panelCamposSur.add(btnAnterior);
        panelCamposSur.add(numPagina);
        panelCamposSur.add(btnSiguiente);
        panelSur.add(btnEditar, BorderLayout.EAST);
        panelSur.add(panelCamposSur, BorderLayout.CENTER);

        register.setAlignmentX(Component.CENTER_ALIGNMENT);
        numDriver.setAlignmentX(Component.CENTER_ALIGNMENT);
        idPlace.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentroEste.add(new JLabel("Registro / ID:"));
        panelCentroEste.add(register);
        panelCentroEste.add(Box.createVerticalStrut(15));
        panelCentroEste.add(new JLabel("Núm. conductor:"));
        panelCentroEste.add(numDriver);
        panelCentroEste.add(Box.createVerticalStrut(15));
        panelCentroEste.add(new JLabel("ID Lugar:"));
        panelCentroEste.add(idPlace);
        panelCentroEste.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 30));

        btnCargar.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentroOeste.add(etiquetaImagen);
        panelCentroOeste.add(Box.createVerticalStrut(10));
        panelCentroOeste.add(btnCargar);
        panelCentroOeste.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 10));

        panelCentro.add(panelCentroOeste, BorderLayout.WEST);
        panelCentro.add(panelCentroEste,  BorderLayout.EAST);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelSur,    BorderLayout.SOUTH);

        register.setEnabled(false);
        numDriver.setEnabled(false);
        idPlace.setEnabled(false);

        add(panelPrincipal);

        setVisible(true);
    }

    // ==================== MÉTODOS ====================

    /*
     * Habilita los campos de texto editables para permitir la modificación de datos.
     */
    public void habilitarTxt() {
        numDriver.setEnabled(true);
        idPlace.setEnabled(true);
    }

    /*
     * Deshabilita todos los campos de texto de la vista para impedir la edición accidental de la información.
     */
    public void deshabilitarTxt() {
        register.setEnabled(false);
        numDriver.setEnabled(false);
        idPlace.setEnabled(false);
    }

    /*
     * Actualiza el contenido de los campos de texto con la información proporcionada.
     */
    public void setDatos(String registerDAO, int numDriverDAO, String idPlaceDAO) {
        register.setText(registerDAO);
        numDriver.setText(String.valueOf(numDriverDAO));
        idPlace.setText(idPlaceDAO);
    }

    /*
     * Muestra la imagen escalada al tamaño fijo reservado para la etiqueta.
     */
    public void mostrarImagen(String rutaCompleta) {
        if (rutaCompleta == null || rutaCompleta.isBlank()) {
            etiquetaImagen.setIcon(null);
            etiquetaImagen.setText("Sin imagen");
            return;
        }

        ImageIcon iconoOriginal = new ImageIcon(rutaCompleta);

        if (iconoOriginal.getIconWidth() == -1) {
            etiquetaImagen.setIcon(null);
            etiquetaImagen.setText("No encontrada");
        } else {
            Image imgEscalada = iconoOriginal.getImage()
                    .getScaledInstance(IMG_W, IMG_H, Image.SCALE_SMOOTH);
            etiquetaImagen.setIcon(new ImageIcon(imgEscalada));
            etiquetaImagen.setText("");
        }
    }

    // ==================== GETTERS ====================

    public JTextField getRegister()  { return register; }

    public JTextField getNumDriver() { return numDriver; }

    public JTextField getIdPlace()   { return idPlace; }

    public JButton getBtnEditar()    { return btnEditar; }

    public JButton getBtnCargar()    { return btnCargar; }

    public JButton getBtnAnterior()  { return btnAnterior; }

    public JButton getBtnSiguiente() { return btnSiguiente; }

    public JLabel getNumPagina()      { return numPagina; }
}
