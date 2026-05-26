package app;

import controller.MainController;
import view.AucorsaView;

/**
 * Clase Principal
 * Punto de entrada de la aplicación Aucorsa.
 */
public class Principal {

    /*
     *
     * @param args
     */
    public static void main(String[] args) {

        // Crea la vista principal de la aplicación
        AucorsaView mainView = new AucorsaView();
        // Crea el controlador principal y le pasa la vista
        new MainController(mainView);
        // Hace visible la ventana principal
        mainView.setVisible(true);
    }
}