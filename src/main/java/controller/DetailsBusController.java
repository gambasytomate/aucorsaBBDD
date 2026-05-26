package controller;

import controller.bus.BusController;
import controller.dao.BusDAO;
import controller.db.ConnectionBBDD;
import model.Bus;
import view.bus.BusPanel;
import view.bus.DetailsBusView;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DetailsBusController {
    private final DetailsBusView detailsBusView;
    private final BusController busController;
    private final BusPanel busPanel;
    private final BusDAO busDAO;
    private List<Bus> buses;

    private boolean edicionActiva = false;

    public DetailsBusController(DetailsBusView detailsBusView,
                                BusController busController,
                                BusPanel busPanel,
                                List<Bus> buses,
                                BusDAO busDAO) {

        this.detailsBusView = detailsBusView;
        this.busController = busController;
        this.busPanel = busPanel;
        this.buses = buses;
        this.busDAO = busDAO;

        cargarBus();

        detailsBusView.getBtnEditar().addActionListener(e -> habilitarEdicion());
        detailsBusView.getBtnSiguiente().addActionListener(e -> siguienteConductor());
        detailsBusView.getBtnAnterior().addActionListener(e -> anteriorConductor());
        detailsBusView.getBtnCargar().addActionListener(e -> seleccionarNuevaImagen());
    }

    // ==================== MÉTODOS ====================

    /*
     * Selecciona el conductor previo en la lista basándose en el índice de la tabla.
     * Actualiza la selección de la fila y recarga los datos en la vista de detalle.
     */
    private void anteriorConductor() {
        int index = busPanel.getTablaVista().getSelectedRow();
        if (index > 0) {
            busPanel.getTablaVista().setRowSelectionInterval(index - 1, index - 1);
            cargarBus();
        } else {
            JOptionPane.showMessageDialog(detailsBusView, "Ya estás en el primer bus.");
        }
    }

    /*
     * Selecciona el siguiente conductor en la lista basándose en el índice de la tabla.
     * Actualiza la selección de la fila y recarga los datos en la vista de detalle.
     */
    private void siguienteConductor() {
        int index = busPanel.getTablaVista().getSelectedRow();
        if (index < buses.size() - 1) {
            busPanel.getTablaVista().setRowSelectionInterval(index + 1, index + 1);
            cargarBus();
        } else {
            JOptionPane.showMessageDialog(detailsBusView, "Ya estás en el último bus.");
        }
    }

    /*
     * Alterna el estado de edición de la vista.
     * Si la edición está activa, gestiona la confirmación del guardado de los cambios realizados.
     */
    private void habilitarEdicion() {
        if (!edicionActiva) {
            edicionActiva = true;
            detailsBusView.getBtnEditar().setText("Guardar");
            detailsBusView.habilitarTxt();
        } else {
            int opcion = JOptionPane.showConfirmDialog(
                    detailsBusView,
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
                cargarBus();
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
        String tipo = detailsBusView.getTipo().getText().trim();
        String licencia = detailsBusView.getLicencia().getText().trim();

        if (tipo.isEmpty() || licencia.isEmpty()) {
            JOptionPane.showMessageDialog(detailsBusView,
                    "El tipo y la licencia no pueden estar vacíos.");
            return;
        }

        int index = busPanel.getTablaVista().getSelectedRow();
        if (index == -1) return;

        String imagenActual = buses.get(index).getImagen();
        String matricula = detailsBusView.getMatricula().getText().trim();

        Bus busEditado = new Bus(matricula, tipo, licencia, imagenActual);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (busDAO.modificarBus(con, busEditado)) {
                JOptionPane.showMessageDialog(detailsBusView, "Bus actualizado correctamente.");
                busController.cargarBuses();
                cargarBus();
                desactivarEdicion();
            } else {
                JOptionPane.showMessageDialog(detailsBusView,
                        "Error al actualizar. Comprueba la base de datos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailsBusView,
                    "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Restablece el estado de la interfaz de detalle al modo de solo lectura.
     */
    private void desactivarEdicion() {
        edicionActiva = false;
        detailsBusView.getBtnEditar().setText("Editar");
        detailsBusView.deshabilitarTxt();
    }

    /*
     * Muestra los datos (texto + imagen) del conductor seleccionado actualmente en la tabla.
     * Actualiza el indicador de paginación de la vista.
     */
    public void cargarBus() {
        int index = busPanel.getTablaVista().getSelectedRow();
        if (index == -1 || index >= buses.size()) return;

        Bus c = buses.get(index);
        detailsBusView.setDatos(c.getMatricula(), c.getTipo(), c.getLicencia());
        detailsBusView.getNumPagina().setText((index + 1) + " / " + buses.size());

        cargarImagen(c);
    }

    /*
     * Gestiona la carga y visualización del archivo de imagen asociado al conductor.
     * @param c Objeto conductor del cual se extraerá el nombre de la imagen.
     */
    private void cargarImagen(Bus c) {
        String nombreImagen = c.getImagen();

        if (nombreImagen != null && !nombreImagen.isBlank()) {
            detailsBusView.mostrarImagen("Imagenes/" + nombreImagen);
        } else {
            detailsBusView.mostrarImagen(null);
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

        int result = fileChooser.showOpenDialog(detailsBusView);
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
            JOptionPane.showMessageDialog(detailsBusView,
                    "Error al copiar la imagen: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int index = busPanel.getTablaVista().getSelectedRow();
        if (index == -1) return;

        Bus c = buses.get(index);
        c.setImagen(nombreArchivo);

        try (Connection con = ConnectionBBDD.getConexion()) {
            if (busDAO.modificarBus(con, c)) {
                cargarImagen(c);
                JOptionPane.showMessageDialog(detailsBusView,
                        "Imagen actualizada y guardada correctamente.");
            } else {
                JOptionPane.showMessageDialog(detailsBusView,
                        "Imagen copiada localmente pero no se pudo guardar en la BD.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailsBusView,
                    "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

