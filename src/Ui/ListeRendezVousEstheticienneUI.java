/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;

/**
 *
 * @author HP
 */

import models.Rendezvous;
import services.RendezVousService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListeRendezVousEstheticienneUI extends JDialog {
    private JTable rendezvousTable;
    private DefaultTableModel tableModel;
    private JTextField identifiantTextField;
    private JButton rechercherBtn;
    private JButton confirmerBtn;
    private RendezVousService rendezVousService;
    private String estheticienneIdentifiant;

    public ListeRendezVousEstheticienneUI(Frame parent, boolean modal) {
        super(parent, modal);
        rendezVousService = new RendezVousService();
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new RendezVous().setVisible(true);
                dispose();
            }
        });
    }

    private void initComponents() {
        setTitle("Liste des Rendez-vous");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel identifiantLabel = new JLabel("Identifiant Esthéticienne:");
        identifiantTextField = new JTextField(15);
        rechercherBtn = new JButton("Rechercher");

        rechercherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String identifiant = identifiantTextField.getText().trim();
                if (!identifiant.isEmpty()) {
                    estheticienneIdentifiant = identifiant;
                    populateTable(estheticienneIdentifiant);
                } else {
                    JOptionPane.showMessageDialog(ListeRendezVousEstheticienneUI.this,
                            "Veuillez entrer un identifiant.",
                            "Champ requis",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        topPanel.add(identifiantLabel);
        topPanel.add(identifiantTextField);
        topPanel.add(rechercherBtn);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Client", "Esthéticienne", "Date", "Heure", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0);
        rendezvousTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(rendezvousTable);
        add(scrollPane, BorderLayout.CENTER);

        confirmerBtn = new JButton("Confirmer");
        confirmerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = rendezvousTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    int rendezvousId = (int) tableModel.getValueAt(selectedRowIndex, 0);
                    Rendezvous rendezvous = rendezVousService.findById(rendezvousId);
                    if (rendezvous != null) {
                        if (rendezvous.getIdEstheticienne().getIdentifiant().equals(estheticienneIdentifiant)) {
                            rendezvous.setConfirme(true);
                            rendezVousService.update(rendezvousId);
                            populateTable(estheticienneIdentifiant);
                        } else {
                            JOptionPane.showMessageDialog(ListeRendezVousEstheticienneUI.this,
                                    "Vous ne pouvez confirmer que vos propres rendez-vous.",
                                    "Accès non autorisé",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(ListeRendezVousEstheticienneUI.this,
                            "Veuillez sélectionner un rendez-vous à confirmer.",
                            "Sélection Requise",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(confirmerBtn);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Initial population of the table
        populateTable(estheticienneIdentifiant);
    }

    private void populateTable(String identifiantEstheticienne) {
        tableModel.setRowCount(0); // Clear previous table data

        // Only populate if estheticienneIdentifiant is not null or empty
        if (identifiantEstheticienne != null && !identifiantEstheticienne.isEmpty()) {
            List<Rendezvous> rendezvousList = rendezVousService.findByEstheticienneIdentifiant(identifiantEstheticienne);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            for (Rendezvous rendezvous : rendezvousList) {
                Object[] rowData = {
                        rendezvous.getId(),
                        rendezvous.getIdClient().getNom(),
                        rendezvous.getIdEstheticienne().getNom(),
                        dateFormat.format(rendezvous.getDate()),
                        timeFormat.format(rendezvous.getHeure()),
                        rendezvous.getConfirme() ? "Confirmé" : "Non Confirmé"
                };
                tableModel.addRow(rowData);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Liste des Rendez-vous");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ListeRendezVousEstheticienneUI dialog = new ListeRendezVousEstheticienneUI(frame, true);
                dialog.setVisible(true);
            }
        });
    }
}
