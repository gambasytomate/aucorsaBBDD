package controller.rute;

import controller.dao.RuteDAO;
import controller.db.ConnectionBBDD;
import model.Routes;
import view.rute.ModifyRuteView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

    public class ModifyRuteController {

        // ==================== ATRIBUTOS ====================
        private final ModifyRuteView view;
        private final RuteController rutaController;
        private final RuteDAO ruteDAO;

        // ==================== CONSTRUCTOR ====================
        /**
         * Inicializa el controlador de modificación de rutas vinculando la vista con el DAO.
         * @param view Instancia de la interfaz gráfica ModifyRutaView.
         * @param rutaController Controlador principal de rutas para refrescar la tabla tras los cambios.
         * @param rutaDAO Objeto de acceso a datos para la persistencia.
         */
        public ModifyRuteController(ModifyRuteView view, RuteController rutaController, RuteDAO rutaDAO) {
            this.view = view;
            this.rutaController = rutaController;
            this.ruteDAO = rutaDAO;

            // Configura los escuchadores de los botones
            view.getBtnCancelar().addActionListener(e -> view.dispose());
            view.getBtnGuardar().addActionListener(e -> confirmarModificacion());
        }

        // ==================== LÓGICA DE NEGOCIO ====================

        /**
         * Procesa y valida los datos del formulario visual para aplicar la actualización en el sistema.
         */
        private void confirmarModificacion() {
            // 1. Recuperamos los identificadores fijos de la clave compuesta
            String matricula = view.getMatricula();
            int idLugar = view.getIdLugar();

            // 2. Recuperamos el campo de texto descriptivo modificado
            String diaSemana = view.getDiaSemana().getText().trim();

            // 3. EXTRACCIÓN CRUCIAL: Decodificamos el JComboBox para obtener la ID limpia del Conductor
            int numConductor = obtenerIdConductorSeleccionado();

            // Validaciones previas antes de enviar a la base de datos
            if (numConductor == -1) {
                JOptionPane.showMessageDialog(view, "Debes seleccionar un conductor válido del desplegable.",
                        "Error de selección", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (diaSemana.isEmpty()) {
                JOptionPane.showMessageDialog(view, "El campo de día de la semana no puede estar vacío.",
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Solicita confirmación expresa al usuario
            int opcion = JOptionPane.showConfirmDialog(view,
                    "¿Estás seguro de que deseas guardar los cambios en esta ruta?",
                    "Confirmar modificación", JOptionPane.YES_NO_OPTION);

            if (opcion != JOptionPane.YES_OPTION) return;

            // Construimos el objeto del modelo con los nuevos datos (manteniendo la imagen en null o su valor previo)
            Routes rutaModificada = new Routes(matricula, numConductor, idLugar, diaSemana, null);

            boolean updateSuccess = false;

            // Ejecución de la persistencia usando bloques try-with-resources seguros
            try (Connection con = ConnectionBBDD.getConexion()) {
                updateSuccess = ruteDAO.modificarRuta(con, rutaModificada);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Respuesta visual según el resultado de la base de datos
            if (updateSuccess) {
                JOptionPane.showMessageDialog(view, "Ruta modificada correctamente.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                rutaController.cargarRutas(); // Refresca la tabla principal instantáneamente
                view.dispose(); // Cierra la ventana emergente de edición
            } else {
                JOptionPane.showMessageDialog(view, "Error al modificar la ruta en la base de datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Método auxiliar que procesa el String seleccionado en el JComboBox
         * (por ejemplo: "ID: 3 - Juan Pérez") y extrae únicamente el entero numérico (3).
         * @return El identificador numérico del conductor o -1 si ocurre un error.
         */
        private int obtenerIdConductorSeleccionado() {
            String itemSeleccionado = (String) view.getComboConductores().getSelectedItem();

            if (itemSeleccionado == null) {
                return -1;
            }

            try {
                // El String se rompe en trozos usando los espacios en blanco como separador
                // Ejemplo: "ID:", "3", "-", "Juan", "Pérez"
                String[] partes = itemSeleccionado.split(" ");

                // La posición [1] contiene de forma estricta la cadena con el número identificador
                return Integer.parseInt(partes[1]);
            } catch (Exception e) {
                // Devuelve -1 si la estructura del String no coincide o está corrupta
                return -1;
            }
        }
    }