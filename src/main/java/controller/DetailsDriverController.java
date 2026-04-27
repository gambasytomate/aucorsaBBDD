package controller;

import controller.conductor.ConductorController;
import controller.dao.ConductorDAO;
import controller.db.ConnectionBBDD;
import model.Conductor;
import view.conductor.ConductorPanel;
import view.conductor.DetailsDriverView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Controlador para la gestión detallada de conductores.
 * Permite la visualización, navegación secuencial, edición de datos
 * y gestión de imágenes para los conductores seleccionados.
 * * @author TuNombre
 */
public class DetailsDriverController {

    // ==================== ATRIBUTOS ====================

    private final DetailsDriverView detailsDriverView;
    private final ConductorController conductorController;
    private final ConductorPanel conductorPanel;
    private final ConductorDAO conductorDAO;
    private List<Conductor> conductores;

    private boolean edicionActiva = false;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de DetailsDriverController.
     * Inicializa las referencias, carga los datos del conductor seleccionado
     * y configura los escuchadores de eventos para la navegación y edición.
     * @param detailsDriverView Vista de detalle del conductor.
     * @param conductorController Controlador principal de conductores.
     * @param conductorPanel Panel que contiene la tabla de conductores.
     * @param conductores Lista de conductores cargados en memoria.
     * @param conductorDAO Objeto de acceso a datos para la entidad Conductor.
     */
    public DetailsDriverController(DetailsDriverView detailsDriverView,
                                   ConductorController conductorController,
                                   ConductorPanel conductorPanel,
                                   List<Conductor> conductores,
                                   ConductorDAO conductorDAO) {

        this.detailsDriverView = detailsDriverView;
        this.conductorController = conductorController;
        this.conductorPanel = conductorPanel;
        this.conductores = conductores;
        this.conductorDAO = conductorDAO;

        cargarConductor();

        detailsDriverView.getBtnEditar().addActionListener(e -> habilitarEdicion());
        detailsDriverView.getBtnSiguiente().addActionListener(e -> siguienteConductor());
        detailsDriverView.getBtnAnterior().addActionListener(e -> anteriorConductor());
        detailsDriverView.getBtnCargar().addActionListener(e -> seleccionarNuevaImagen());
    }

    // ==================== MÉTODOS ====================

    /*
     * Selecciona el conductor previo en la lista basándose en el índice de la tabla.
     * Actualiza la selección de la fila y recarga los datos en la vista de detalle.
     */
    private void anteriorConductor() {
        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index > 0) {
            conductorPanel.getTablaVista().setRowSelectionInterval(index - 1, index - 1);
            cargarConductor();
        } else {
            JOptionPane.showMessageDialog(detailsDriverView, "Ya estás en el primer conductor.");
        }
    }

    /*
     * Selecciona el siguiente conductor en la lista basándose en el índice de la tabla.
     * Actualiza la selección de la fila y recarga los datos en la vista de detalle.
     */
    private void siguienteConductor() {
        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index < conductores.size() - 1) {
            conductorPanel.getTablaVista().setRowSelectionInterval(index + 1, index + 1);
            cargarConductor();
        } else {
            JOptionPane.showMessageDialog(detailsDriverView, "Ya estás en el último conductor.");
        }
    }

    /*
     * Alterna el estado de edición de la vista.
     * Si la edición está activa, gestiona la confirmación del guardado de los cambios realizados.
     */
    private void habilitarEdicion() {
        if (!edicionActiva) {
            edicionActiva = true;
            detailsDriverView.getBtnEditar().setText("Guardar");
            detailsDriverView.habilitarTxt();
        } else {
            int opcion = JOptionPane.showConfirmDialog(
                    detailsDriverView,
                    "¿Guardar cambios?",
                    "Confirmar",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );

            if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                return;
            }

            if (opcion == JOptionPane.YES_OPTION) {
                guardarCambios();
            } else {
                cargarConductor();
                desactivarEdicion();
            }
        }
    }

    /*
     * Procesa y persiste los cambios textuales realizados en el perfil del conductor.
     * Valida que los campos obligatorios no estén vacíos antes de actualizar la base de datos.
     * @throws SQLException Si ocurre un error de comunicación con la base de datos durante la actualización.
     */
    private void guardarCambios() {
        String nombre = detailsDriverView.getNombre().getText().trim();
        String apellidos = detailsDriverView.getApellidos().getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(detailsDriverView,
                    "El nombre y los apellidos no pueden estar vacíos.");
            return;
        }

        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index == -1) return;

        String imagenActual = conductores.get(index).getImagen();
        int numDriver = Integer.parseInt(detailsDriverView.getNunDriver().getText().trim());

        Conductor conductorEditado = new Conductor(numDriver, nombre, apellidos, imagenActual);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (conductorDAO.modificarConductor(con, conductorEditado)) {
                JOptionPane.showMessageDialog(detailsDriverView, "Conductor actualizado correctamente.");
                conductores = conductorController.cargarConductores();
                cargarConductor();
                desactivarEdicion();
            } else {
                JOptionPane.showMessageDialog(detailsDriverView,
                        "Error al actualizar. Comprueba la base de datos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailsDriverView,
                    "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Restablece el estado de la interfaz de detalle al modo de solo lectura.
     */
    private void desactivarEdicion() {
        edicionActiva = false;
        detailsDriverView.getBtnEditar().setText("Editar");
        detailsDriverView.deshabilitarTxt();
    }

    /*
     * Muestra los datos (texto + imagen) del conductor seleccionado actualmente en la tabla.
     * Actualiza el indicador de paginación de la vista.
     */
    public void cargarConductor() {
        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index == -1 || index >= conductores.size()) return;

        Conductor c = conductores.get(index);
        detailsDriverView.setDatos(c.getNumConductor(), c.getNombre(), c.getApellido());
        detailsDriverView.getNumPagina().setText((index + 1) + " / " + conductores.size());

        cargarImagen(c);
    }

    /*
     * Gestiona la carga y visualización del archivo de imagen asociado al conductor.
     * @param c Objeto conductor del cual se extraerá el nombre de la imagen.
     */
    private void cargarImagen(Conductor c) {
        String nombreImagen = c.getImagen();

        if (nombreImagen != null && !nombreImagen.isBlank()) {
            detailsDriverView.mostrarImagen("Imagenes/" + nombreImagen);
        } else {
            detailsDriverView.mostrarImagen(null);
        }
    }

    /*
     * Abre un selector de archivos para elegir una nueva imagen de perfil.
     * Copia el archivo seleccionado al directorio local y actualiza la ruta en la base de datos.
     * @throws IOException Si ocurre un error al copiar el archivo físico al directorio de imágenes.
     * @throws SQLException Si ocurre un error al actualizar la ruta de la imagen en la base de datos.
     */
    private void seleccionarNuevaImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Imágenes (JPG, PNG)", "jpg", "jpeg", "png"));

        int result = fileChooser.showOpenDialog(detailsDriverView);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File archivoSeleccionado = fileChooser.getSelectedFile();
        String nombreArchivo = archivoSeleccionado.getName();

        File carpetaDestino = new File("Imagenes");
        if (!carpetaDestino.exists()) carpetaDestino.mkdirs();

        File destino = new File(carpetaDestino, nombreArchivo);

        try {
            java.nio.file.Files.copy(
                    archivoSeleccionado.toPath(),
                    destino.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailsDriverView,
                    "Error al copiar la imagen: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int index = conductorPanel.getTablaVista().getSelectedRow();
        if (index == -1) return;

        Conductor c = conductores.get(index);
        c.setImagen(nombreArchivo);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (conductorDAO.modificarConductor(con, c)) {
                cargarImagen(c);
                JOptionPane.showMessageDialog(detailsDriverView,
                        "Imagen actualizada y guardada correctamente.");
            } else {
                JOptionPane.showMessageDialog(detailsDriverView,
                        "Imagen copiada localmente pero no se pudo guardar en la BD.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailsDriverView,
                    "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}