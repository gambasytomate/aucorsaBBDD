package controller;

import controller.bus.BusController;
import controller.conductor.ConductorController;
import controller.dao.BusDAO;
import controller.dao.ConductorDAO;
import controller.dao.RuteDAO;
import controller.rute.RuteController;
import view.AucorsaView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
/**
 * Controlador principal de la aplicación Aucorsa.
 * Coordina BusController y ConductorController y gestiona la ventana principal.
 * @author Marta
 */
public class MainController {

    // ==================== ATRIBUTOS ====================

    private final BusController busController;
    private final ConductorController conductorController;
    private final RuteController ruteController;
    private final AucorsaView aucorsaView;

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de MainController.
     * Inicializa los controladores secundarios, vincula la vista principal y configura
     * los escuchadores de eventos para la navegación global y el cierre de la aplicación.
     * * @param view Instancia de la vista principal AucorsaView.
     */
    public MainController(AucorsaView view) {
        this.aucorsaView = view;

        BusDAO busDAO = new BusDAO();
        busController = new BusController(aucorsaView.getBusPanel(), busDAO);

        ConductorDAO conductorDAO = new ConductorDAO();
        conductorController = new ConductorController(aucorsaView.getConductorPanel(), conductorDAO);

        RuteDAO ruteDAO = new RuteDAO();
        ruteController = new RuteController(aucorsaView.getRoutePanel(),ruteDAO);

        aucorsaView.getBtnRefresh().addActionListener(e -> refrescarTabla());

        aucorsaView.getBtnAdd().addActionListener(e -> añadir());

        aucorsaView.getBtnDelete().addActionListener(e -> eliminar());

        aucorsaView.getBtnModify().addActionListener(e -> modificar());

        aucorsaView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int resultado = JOptionPane.showConfirmDialog(
                        aucorsaView,
                        "¿Estás seguro de que quieres salir?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );

                if (resultado == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // ==================== MÉTODOS ====================

    /*
     * Identifica cuál es la pestaña (Tab) que el usuario tiene seleccionada actualmente.
     * * @return String con el título de la pestaña activa ("Bus", "Drivers" o "Route").
     */
    private String tabActiva() {
        int idx = aucorsaView.getTabs().getSelectedIndex();
        return aucorsaView.getTabs().getTitleAt(idx);
    }

    /*
     * Solicitala recarga de datos al controlador correspondiente según la pestaña activa.
     */
    private void refrescarTabla() {
        switch (tabActiva()) {
            case "Bus" -> busController.cargarBuses();
            case "Drivers" -> conductorController.cargarConductores();
            case "Route" -> ruteController.cargarRutas();
        }
    }

    /*
     * Ejecuta la lógica de creación de un nuevo registro delegando la acción
     * al controlador de la entidad activa.
     */
    private void añadir() {
        switch (tabActiva()) {
            case "Bus" -> busController.añadirBus();
            case "Drivers" -> conductorController.añadirConductor();
            case "Route" -> ruteController.añadirRuta();
        }
    }

    /*
     * Ejecuta la lógica de eliminación de un registro delegando la acción
     * al controlador de la entidad activa.
     */
    private void eliminar() {
        switch (tabActiva()) {
            case "Bus" -> busController.eliminarBus();
            case "Drivers" -> conductorController.eliminarConductor();
            case "Route" -> ruteController.eliminarRuta();
        }
    }

    /*
     * Ejecuta la lógica de modificación de un registro delegando la acción
     * al controlador de la entidad activa.
     */
    private void modificar() {
        switch (tabActiva()) {
            case "Bus" -> busController.modificarBus();
            case "Drivers" -> conductorController.modificarConductor();
            case "Route" -> ruteController.modificarRuta();
        }
    }
}