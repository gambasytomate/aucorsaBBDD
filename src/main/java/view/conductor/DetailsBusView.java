package view.conductor;

import javax.swing.*;
import java.awt.*;

/**
 * Clase DetailsDriverView
 * Vista detallada para la visualización y edición de la ficha de un conductor.
 * Incluye gestión de imagen de perfil, datos personales y navegación entre registros.
 * @author Marta
 */
public class DetailsBusView extends JFrame {

    // ==================== ATRIBUTOS ====================

    JTextField nombre     = new JTextField(10);
    JTextField apellidos  = new JTextField(10);
    JTextField nunDriver  = new JTextField(10);

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
     * Constructor de la clase DetailsDriverView.
     * Configura el marco principal de la ventana, inicializa los componentes de entrada
     * de texto y botones, y organiza la disposición visual mediante gestores de diseño.
     */
    public DetailsBusView() {

        setTitle("Ficha conductor");
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

        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        apellidos.setAlignmentX(Component.CENTER_ALIGNMENT);
        nunDriver.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentroEste.add(new JLabel("Nombre:"));
        panelCentroEste.add(nombre);
        panelCentroEste.add(Box.createVerticalStrut(15));
        panelCentroEste.add(new JLabel("Apellidos:"));
        panelCentroEste.add(apellidos);
        panelCentroEste.add(Box.createVerticalStrut(15));
        panelCentroEste.add(new JLabel("Nº conductor:"));
        panelCentroEste.add(nunDriver);
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

        nunDriver.setEnabled(false);
        nombre.setEnabled(false);
        apellidos.setEnabled(false);

        add(panelPrincipal);

        setVisible(true);
    }

    // ==================== MÉTODOS ====================

    /*
     * Habilita los campos de texto editables (nombre y apellidos) para permitir la modificación de datos.
     */
    public void habilitarTxt() {
        nombre.setEnabled(true);
        apellidos.setEnabled(true);
    }

    /*
     * Deshabilita todos los campos de texto de la vista para impedir la edición accidental de la información.
     */
    public void deshabilitarTxt() {
        nunDriver.setEnabled(false);
        nombre.setEnabled(false);
        apellidos.setEnabled(false);
    }

    /*
     * Actualiza el contenido de los campos de texto con la información proporcionada.
     * @param numDriver Número identificativo del conductor.
     * @param nombreDAO Nombre recuperado de la persistencia.
     * @param apellidosDAO Apellidos recuperados de la persistencia.
     */
    public void setDatos(int numDriver, String nombreDAO, String apellidosDAO) {
        nunDriver.setText(String.valueOf(numDriver));
        nombre.setText(nombreDAO);
        apellidos.setText(apellidosDAO);
    }

    /*
     * Muestra la imagen escalada al tamaño fijo reservado para la etiqueta.
     * Asegura que el escalado sea proporcional al contenedor.
     * @param rutaCompleta Ruta absoluta o relativa del archivo de imagen a cargar.
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

    /*
     * Obtiene el campo de texto del nombre.
     * @return JTextField del nombre.
     */
    public JTextField getNombre()    { return nombre; }

    /*
     * Obtiene el campo de texto de los apellidos.
     * @return JTextField de los apellidos.
     */
    public JTextField getApellidos() { return apellidos; }

    /*
     * Obtiene el campo de texto del número de conductor.
     * @return JTextField del número identificativo.
     */
    public JTextField getNunDriver() { return nunDriver; }

    /*
     * Obtiene el botón de edición/guardado.
     * @return JButton de editar.
     */
    public JButton getBtnEditar()    { return btnEditar; }

    /*
     * Obtiene el botón para cargar nuevas imágenes.
     * @return JButton de cargar imagen.
     */
    public JButton getBtnCargar()    { return btnCargar; }

    /*
     * Obtiene el botón de navegación hacia el registro anterior.
     * @return JButton anterior.
     */
    public JButton getBtnAnterior()  { return btnAnterior; }

    /*
     * Obtiene el botón de navegación hacia el siguiente registro.
     * @return JButton siguiente.
     */
    public JButton getBtnSiguiente() { return btnSiguiente; }

    /*
     * Obtiene la etiqueta que muestra el índice de paginación.
     * @return JLabel de número de página.
     */
    public JLabel getNumPagina()      { return numPagina; }
}