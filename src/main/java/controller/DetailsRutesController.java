package controller;

import controller.dao.RuteDAO;
import controller.db.ConnectionBBDD;
import controller.rute.RuteController;
import model.Routes;
import view.rute.DetailsRuteView;
import view.rute.RutePanel;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DetailsRutesController {
    
        private final DetailsRuteView detailsRuteView;
        private final RuteController ruteController;
        private final RutePanel rutePanel;
        private final RuteDAO ruteDAO;
        private List<Routes> routes;

        private boolean edicionActiva = false;

        public DetailsRutesController(DetailsRuteView detailsRuteView,
                                    RuteController ruteController,
                                    RutePanel rutePanel,
                                    List<Routes> routes,
                                    RuteDAO ruteDAO) {

            this.detailsRuteView = detailsRuteView;
            this.ruteController = ruteController;
            this.rutePanel = rutePanel;
            this.routes = routes;
            this.ruteDAO = ruteDAO;

            cargarRuta();

            detailsRuteView.getBtnEditar().addActionListener(e -> habilitarEdicion());
            detailsRuteView.getBtnSiguiente().addActionListener(e -> siguienteConductor());
            detailsRuteView.getBtnAnterior().addActionListener(e -> anteriorConductor());
            detailsRuteView.getBtnCargar().addActionListener(e -> seleccionarNuevaImagen());
        }

        // ==================== MÉTODOS ====================

        /*
         * Selecciona el conductor previo en la lista basándose en el índice de la tabla.
         * Actualiza la selección de la fila y recarga los datos en la vista de detalle.
         */
        private void anteriorConductor() {
            int index = rutePanel.getTablaVista().getSelectedRow();
            if (index > 0) {
                rutePanel.getTablaVista().setRowSelectionInterval(index - 1, index - 1);
                cargarRuta();
            } else {
                JOptionPane.showMessageDialog(detailsRuteView, "Ya estás en el primer bus.");
            }
        }

        /*
         * Selecciona el siguiente conductor en la lista basándose en el índice de la tabla.
         * Actualiza la selección de la fila y recarga los datos en la vista de detalle.
         */
        private void siguienteConductor() {
            int index = rutePanel.getTablaVista().getSelectedRow();
            if (index < routes.size() - 1) {
                rutePanel.getTablaVista().setRowSelectionInterval(index + 1, index + 1);
                cargarRuta();
            } else {
                JOptionPane.showMessageDialog(detailsRuteView, "Ya estás en el último bus.");
            }
        }

        /*
         * Alterna el estado de edición de la vista.
         * Si la edición está activa, gestiona la confirmación del guardado de los cambios realizados.
         */
        private void habilitarEdicion() {
            if (!edicionActiva) {
                edicionActiva = true;
                detailsRuteView.getBtnEditar().setText("Guardar");
                detailsRuteView.habilitarTxt();
            } else {
                int opcion = JOptionPane.showConfirmDialog(
                        detailsRuteView,
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
                    cargarRuta();
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
            int idPlace = Integer.parseInt(detailsRuteView.getIdPlace().getText().trim());
            String registro = detailsRuteView.getRegister().getText().trim();
            String idConductor = detailsRuteView.getNumDriver().getText().trim();
            int numConductor = Integer.parseInt(detailsRuteView.getNumDriver().getText().trim());

            if (registro.isEmpty() || numConductor == 0 || idPlace == 0) {
                JOptionPane.showMessageDialog(detailsRuteView,
                        "El idPlace, el registro  y el numero del conductor no pueden estar vacíos.");
                return;
            }

            int index = rutePanel.getTablaVista().getSelectedRow();
            if (index == -1) return;
            String diaSemana = routes.get(index).getDiaSemana();
            String imagenActual = routes.get(index).getImagen();

            Routes rutaEditada = new Routes(registro, idPlace, numConductor, diaSemana, imagenActual);

            try (Connection con = ConnectionBBDD.getConexion()) {
                if (ruteDAO.modificarRuta(con, rutaEditada)) {
                    JOptionPane.showMessageDialog(detailsRuteView, "Ruta actualizada correctamente.");
                    ruteController.cargarRutas();
                    cargarRuta();
                    desactivarEdicion();
                } else {
                    JOptionPane.showMessageDialog(detailsRuteView,
                            "Error al actualizar. Comprueba la base de datos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(detailsRuteView,
                        "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        /*
         * Restablece el estado de la interfaz de detalle al modo de solo lectura.
         */
        private void desactivarEdicion() {
            edicionActiva = false;
            detailsRuteView.getBtnEditar().setText("Editar");
            detailsRuteView.deshabilitarTxt();
        }

        /*
         * Muestra los datos (texto + imagen) del conductor seleccionado actualmente en la tabla.
         * Actualiza el indicador de paginación de la vista.
         */
        public void cargarRuta() {
            int index = rutePanel.getTablaVista().getSelectedRow();
            if (index == -1 || index >= routes.size()) return;

            Routes c = routes.get(index);
            detailsRuteView.setDatos(c.getRegistro(), c.getIdLugar(), c.getRegistro());
            detailsRuteView.getNumPagina().setText((index + 1) + " / " + routes.size());

            cargarImagen(c);
        }

        /*
         * Gestiona la carga y visualización del archivo de imagen asociado al conductor.
         * @param c Objeto conductor del cual se extraerá el nombre de la imagen.
         */
        private void cargarImagen(Routes c) {
            String nombreImagen = c.getImagen();

            if (nombreImagen != null && !nombreImagen.isBlank()) {
                detailsRuteView.mostrarImagen("Imagenes/" + nombreImagen);
            } else {
                detailsRuteView.mostrarImagen(null);
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

            int result = fileChooser.showOpenDialog(detailsRuteView);
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
                JOptionPane.showMessageDialog(detailsRuteView,
                        "Error al copiar la imagen: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int index = rutePanel.getTablaVista().getSelectedRow();
            if (index == -1) return;

            Routes c = routes.get(index);
            c.setImagen(nombreArchivo);

            try (Connection con = ConnectionBBDD.getConexion()) {
                if (ruteDAO.modificarRuta(con, c)) {
                    cargarImagen(c);
                    JOptionPane.showMessageDialog(detailsRuteView,
                            "Imagen actualizada y guardada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(detailsRuteView,
                            "Imagen copiada localmente pero no se pudo guardar en la BD.",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(detailsRuteView,
                        "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

