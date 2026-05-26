package controller.rute;

import controller.DetailsRutesController;
import controller.dao.RuteDAO;
import controller.db.ConnectionBBDD;
import model.Routes;
import view.rute.AddRuteView;
import view.rute.DetailsRuteView;
import view.rute.RutePanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuteController {

        private final RutePanel rutaPanel;
        private final RuteDAO ruteDAO;
        private List<Routes> rutas = new ArrayList<>();

        public RuteController(RutePanel panel, RuteDAO ruteDAO) {
            this.rutaPanel = panel;
            this.ruteDAO = ruteDAO;

            rutaPanel.getTablaVista().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && rutaPanel.getTablaVista().getSelectedRow() != -1) {
                        DetailsRuteView vista = new DetailsRuteView();
                        new DetailsRutesController(vista, RuteController.this, rutaPanel, rutas, ruteDAO);
                    }
                }
            });

            cargarRutas();
        }

    public List<Routes> cargarRutas() {
            try (Connection con = ConnectionBBDD.getConexion()) {
                List<Routes> lista = ruteDAO.mostrarTodasRutas(con);
                if (lista == null) lista = new ArrayList<>();
                rutas = lista;

                rutaPanel.getModeloTabla().setRowCount(0);
                for (Routes r : rutas) {
                    rutaPanel.getModeloTabla().addRow(new Object[]{
                            r.getRegistro(),
                            r.getNumConductor(),
                            r.getIdLugar(),
                            r.getDiaSemana()
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rutas;
        }

        public void añadirRuta() {
            new AddRuteController(new AddRuteView(), this, ruteDAO);
        }

        public void eliminarRuta() {
            int fila = rutaPanel.getTablaVista().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(rutaPanel, "Debes seleccionar una ruta de la tabla.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String register = (String) rutaPanel.getModeloTabla().getValueAt(fila, 0);

            int opcion = JOptionPane.showConfirmDialog(rutaPanel, "¿Eliminar la ruta seleccionada?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (opcion != JOptionPane.YES_OPTION) return;

            try (Connection con = ConnectionBBDD.getConexion()) {
                if (ruteDAO.eliminarRuta(con, register)) {
                    JOptionPane.showMessageDialog(rutaPanel, "Ruta eliminada correctamente.");
                    cargarRutas();
                } else {
                    JOptionPane.showMessageDialog(rutaPanel, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void modificarRuta() {
            int fila = rutaPanel.getTablaVista().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(rutaPanel, "Debes seleccionar una ruta para modificar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String matricula = (String) rutaPanel.getModeloTabla().getValueAt(fila, 0);
            int numConductor = (int) rutaPanel.getModeloTabla().getValueAt(fila, 1);
            int idLugar = (int) rutaPanel.getModeloTabla().getValueAt(fila, 2);
            String diaSemana = (String) rutaPanel.getModeloTabla().getValueAt(fila, 3);

         //   new ModifyRuteController(new ModifyRuteView(matricula, idLugar, numConductor, diaSemana), this, ruteDAO);
        }
    }
