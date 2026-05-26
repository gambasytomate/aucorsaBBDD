package view.rute;

import javax.swing.*;
import java.awt.*;

/**
 * Clase AddRutaView
 * Vista del formulario para dar de alta una nueva Ruta.
 * Contiene los campos necesarios para introducir los datos de la ruta
 * y los botones para confirmar o cancelar la operación.
 * @author Marta
 */
public class AddRuteView extends JFrame {
            // ==================== ATRIBUTOS ====================
            private final JButton btnAdd = new JButton("Añadir");
            private final JButton btnCancelar = new JButton("Cancelar");

            private final JTextField matricula = new JTextField(10);
            private final JTextField numConductor = new JTextField(10);
            private final JTextField idLugar = new JTextField(10);
            private final JTextField diaSemana = new JTextField(10);

            // ==================== CONSTRUCTOR ====================
            public AddRuteView() {
                this.setTitle("Aucorsa - Añadir Ruta");
                setSize(320, 220);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);

                JPanel camposPanel = new JPanel(new GridLayout(4, 2, 10, 10));
                camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                camposPanel.add(new JLabel("Matrícula Bus:"));
                camposPanel.add(matricula);
                camposPanel.add(new JLabel("Núm. Conductor:"));
                camposPanel.add(numConductor);
                camposPanel.add(new JLabel("ID Lugar:"));
                camposPanel.add(idLugar);
                camposPanel.add(new JLabel("Día de la semana:"));
                camposPanel.add(diaSemana);

                JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                botonesPanel.add(btnAdd);
                botonesPanel.add(btnCancelar);

                add(camposPanel, BorderLayout.CENTER);
                add(botonesPanel, BorderLayout.SOUTH);
                setVisible(true);
            }

            // ==================== GETTERS ====================
            public JTextField getMatricula() { return matricula; }
            public JTextField getNumConductor() { return numConductor; }
            public JTextField getIdLugar() { return idLugar; }
            public JTextField getDiaSemana() { return diaSemana; }
            public JButton getBtnCancelar() { return btnCancelar; }
            public JButton getBtnAdd() { return btnAdd; }
        }
