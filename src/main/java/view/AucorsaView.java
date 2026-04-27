package view;

import view.bus.BusPanel;
import view.conductor.ConductorPanel;

import javax.swing.*;
import java.awt.*;
/**
 * Clase AucorsaView
 * Vista principal de la aplicación Aucorsa.
 * Contiene el panel de pestañas con las secciones de la aplicación
 * y la barra de herramientas con los botones de acción.
 * @author Marta
 */
public class AucorsaView extends JFrame {

    // ==================== ATRIBUTOS ====================

    // Panel de pestañas principal que agrupa las secciones de la aplicación
    private JTabbedPane tabs = new JTabbedPane();

    // Paneles de cada sección
    private BusPanel busPanel = new BusPanel();
    private ConductorPanel conductorPanel = new ConductorPanel();
    private JPanel routePanel = new JPanel();

    // Etiqueta de estado en la parte superior de la ventana
    private JLabel etiquetaEstado = new JLabel("Estado...");

    // Botones de la barra de herramientas
    JButton btnAdd = new JButton("Añadir");
    JButton btnDelete = new JButton("Borrar");
    JButton btnRefresh = new JButton("Refrescar");
    JButton btnModify = new JButton("Modificar");

    // Paneles de la zona norte de la ventana
    JPanel panelNorte = new JPanel();
    JPanel botoneriaPanel = new JPanel();

    // ==================== CONSTRUCTOR ====================

    /*
     * Constructor de la clase AucorsaView.
     * Configura el marco principal (JFrame), inicializa el sistema de pestañas para las
     * diferentes secciones y organiza la barra de herramientas superior con sus
     * respectivos botones de acción.
     */
    public AucorsaView() {

        super("Aucorsa - Ventana principal");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(800, 600);

        setLayout(new BorderLayout());

        // Añade cada sección como una pestaña del panel principal
        tabs.add("Bus", busPanel);
        tabs.add("Drivers", conductorPanel);
        tabs.add("Route", routePanel);

        // Coloca el panel de pestañas en el centro de la ventana
        add(tabs, BorderLayout.CENTER);

        // Configura el panel norte con BorderLayout para separar botones y etiqueta
        panelNorte.setLayout(new BorderLayout());

        // Añade los botones de acción al panel de botones
        botoneriaPanel.setLayout(new FlowLayout());
        botoneriaPanel.add(btnAdd);
        botoneriaPanel.add(btnDelete);
        botoneriaPanel.add(btnModify);
        botoneriaPanel.add(btnRefresh);

        // Coloca la botonería arriba y la etiqueta de estado abajo en el norte
        panelNorte.add(botoneriaPanel, BorderLayout.NORTH);
        panelNorte.add(etiquetaEstado, BorderLayout.SOUTH);

        // Añade el panel norte a la ventana
        add(panelNorte, BorderLayout.NORTH);
    }

    // ==================== GETTERS Y SETTERS ====================

    /*
     * Obtiene el contenedor de pestañas de la aplicación.
     * @return El objeto JTabbedPane principal.
     */
    public JTabbedPane getTabs() { return tabs; }

    /*
     * Establece el contenedor de pestañas de la aplicación.
     * @param tabs El nuevo JTabbedPane a asignar.
     */
    public void setTabs(JTabbedPane tabs) { this.tabs = tabs; }

    /*
     * Obtiene el panel dedicado a la gestión de buses.
     * @return El panel BusPanel contenido en la vista.
     */
    public BusPanel getBusPanel() { return busPanel; }

    /*
     * Establece el panel de gestión de buses.
     * @param busPanel El nuevo BusPanel a asignar.
     */
    public void setBusPanel(BusPanel busPanel) { this.busPanel = busPanel; }

    /*
     * Obtiene el panel dedicado a la gestión de conductores.
     * @return El panel ConductorPanel contenido en la vista.
     */
    public ConductorPanel getConductorPanel() { return conductorPanel; }

    /*
     * Establece el panel de gestión de conductores.
     * @param conductorPanel El nuevo ConductorPanel a asignar.
     */
    public void setConductorPanel(ConductorPanel conductorPanel) { this.conductorPanel = conductorPanel; }

    /*
     * Obtiene el panel dedicado a la sección de rutas.
     * @return El JPanel de la sección Route.
     */
    public JPanel getRoutePanel() { return routePanel; }

    /*
     * Establece el panel de la sección de rutas.
     * @param routePanel El nuevo JPanel a asignar.
     */
    public void setRoutePanel(JPanel routePanel) { this.routePanel = routePanel; }

    /*
     * Obtiene la etiqueta de texto utilizada para mostrar mensajes de estado.
     * @return El componente JLabel de estado.
     */
    public JLabel getEtiquetaEstado() { return etiquetaEstado; }

    /*
     * Establece la etiqueta de texto de estado.
     * @param etiquetaEstado El nuevo JLabel a asignar.
     */
    public void setEtiquetaEstado(JLabel etiquetaEstado) { this.etiquetaEstado = etiquetaEstado; }

    /*
     * Obtiene el botón de añadir nuevo registro.
     * @return El JButton de inserción.
     */
    public JButton getBtnAdd() { return btnAdd; }

    /*
     * Establece el botón de añadir nuevo registro.
     * @param btnAdd El nuevo JButton a asignar.
     */
    public void setBtnAdd(JButton btnAdd) { this.btnAdd = btnAdd; }

    /*
     * Obtiene el botón de eliminar registro.
     * @return El JButton de borrado.
     */
    public JButton getBtnDelete() { return btnDelete; }

    /*
     * Establece el botón de eliminar registro.
     * @param btnDelete El nuevo JButton a asignar.
     */
    public void setBtnDelete(JButton btnDelete) { this.btnDelete = btnDelete; }

    /*
     * Obtiene el botón de refrescar o recargar datos de las tablas.
     * @return El JButton de actualización.
     */
    public JButton getBtnRefresh() { return btnRefresh; }

    /*
     * Establece el botón de refrescar datos.
     * @param btnRefresh El nuevo JButton a asignar.
     */
    public void setBtnRefresh(JButton btnRefresh) { this.btnRefresh = btnRefresh; }

    /*
     * Obtiene el botón de modificar registro existente.
     * @return El JButton de modificación.
     */
    public JButton getBtnModify() { return btnModify; }

    /*
     * Establece el botón de modificar registro.
     * @param btnModify El nuevo JButton a asignar.
     */
    public void setBtnModify(JButton btnModify) { this.btnModify = btnModify; }

    /*
     * Obtiene el panel contenedor de la zona norte de la ventana.
     * @return El JPanel de la zona norte.
     */
    public JPanel getPanelNorte() { return panelNorte; }

    /*
     * Establece el panel contenedor de la zona norte.
     * @param panelNorte El nuevo JPanel a asignar.
     */
    public void setPanelNorte(JPanel panelNorte) { this.panelNorte = panelNorte; }

    /*
     * Obtiene el panel que organiza la botonería de acción.
     * @return El JPanel con el conjunto de botones.
     */
    public JPanel getBotoneriaPanel() { return botoneriaPanel; }

    /*
     * Establece el panel de la botonería.
     * @param botoneriaPanel El nuevo JPanel a asignar.
     */
    public void setBotoneriaPanel(JPanel botoneriaPanel) { this.botoneriaPanel = botoneriaPanel; }
}