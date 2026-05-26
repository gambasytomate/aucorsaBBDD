package controller.rute;

import controller.dao.RuteDAO;
import controller.db.ConnectionBBDD;
import model.Routes;
import view.rute.AddRuteView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
     * Clase AddRutaController
     * @author Marta
     */
    public class AddRuteController {

        private final AddRuteView view;
        private final RuteController rutaController;
        private final RuteDAO ruteDAO;

    public AddRuteController(AddRuteView view, RuteController rutaController, RuteDAO ruteDAO) {
        this.view = view;
        this.rutaController = rutaController;
        this.ruteDAO = ruteDAO;
    }

    private void confirmarGuardado() {
            String matricula = view.getMatricula().getText().trim();
            String sNumConductor = view.getNumConductor().getText().trim();
            String sIdLugar = view.getIdLugar().getText().trim();
            String diaSemana = view.getDiaSemana().getText().trim();

            if (matricula.isEmpty() || sNumConductor.isEmpty() || sIdLugar.isEmpty() || diaSemana.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Deben estar todos los campos completos.",
                        "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numConductor;
            int idLugar;
            try {
                numConductor = Integer.parseInt(sNumConductor);
                idLugar = Integer.parseInt(sIdLugar);
                if (numConductor <= 0 || idLugar <= 0) {
                    JOptionPane.showMessageDialog(view, "Los valores numéricos deben ser mayores a 0.",
                            "Error de validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "El Conductor y el Lugar deben poseer formatos numéricos enteros.",
                        "Error de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(view, "¿Estás seguro de que deseas guardar la ruta?",
                    "Confirmar guardado", JOptionPane.YES_NO_OPTION);

            if (opcion != JOptionPane.YES_OPTION) return;

            Routes ruta = new Routes(matricula, numConductor, idLugar, diaSemana, null);

            boolean addSuccess = false;
            try (Connection con = ConnectionBBDD.getConexion()) {
                addSuccess = ruteDAO.insertarRuta(con, ruta);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (addSuccess) {
                JOptionPane.showMessageDialog(view, "Ruta guardada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                rutaController.cargarRutas();
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Error al guardar (Clave compuesta duplicada o error de integridad referencial).",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }