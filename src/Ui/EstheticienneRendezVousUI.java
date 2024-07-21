/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;

/**
 *
 * @author HP
 */


import models.Estheticienne;
import models.Rendezvous;
import services.RendezVousService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class EstheticienneRendezVousUI extends JFrame {
    private JTable rendezvousTable;
    private DefaultTableModel tableModel;
    private JButton confirmerBtn;
    private RendezVousService rendezVousService;
    private Estheticienne estheticienne;

    public EstheticienneRendezVousUI(Estheticienne estheticienne) {
        this.estheticienne = estheticienne;
        rendezVousService = new RendezVousService();
        initComponents();
        populateTable();
    }

    private void initComponents() {
        setTitle("Mes Rendez-vous");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Client", "Date", "Heure", "Statut"};
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
                    boolean updated = rendezVousService.confirmRendezVous(rendezvousId);
                    if (updated) {
                        JOptionPane.showMessageDialog(EstheticienneRendezVousUI.this,
                                "Rendez-vous confirmé avec succès.",
                                "Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);
                        populateTable(); // Actualiser la table après la confirmation
                    } else {
                        JOptionPane.showMessageDialog(EstheticienneRendezVousUI.this,
                                "Erreur lors de la confirmation du rendez-vous.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(EstheticienneRendezVousUI.this,
                            "Veuillez sélectionner un rendez-vous à confirmer.",
                            "Sélection Requise",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(confirmerBtn);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        List<Rendezvous> rendezvousList = rendezVousService.findByEstheticienne(estheticienne);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        for (Rendezvous rendezvous : rendezvousList) {
            Object[] rowData = {
                rendezvous.getId(),
                rendezvous.getIdClient().getNom(),
                dateFormat.format(rendezvous.getDate()),
                timeFormat.format(rendezvous.getHeure()),
                rendezvous.getConfirme() ? "Confirmé" : "Non Confirmé"
            };
            tableModel.addRow(rowData);
        }
    }
}
