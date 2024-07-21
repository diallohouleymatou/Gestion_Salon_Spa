/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;

/**
 *
 * @author HP
 */






import models.Client;
import models.Estheticienne;
import models.Rendezvous;
import services.ClientService;
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

public class ListeRendezVousUI extends JDialog {
    private JTable rendezvousTable;
    private DefaultTableModel tableModel;
    private JButton ajouterBtn;
    private JButton modifierBtn;
    private JButton supprimerBtn;
    private RendezVousService rendezVousService;

    public ListeRendezVousUI(Frame parent, boolean modal) {
        super(parent, modal);
        rendezVousService = new RendezVousService();
        initComponents();
        populateTable();
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

        String[] columnNames = {"ID", "Client", "Esthéticienne", "Date", "Heure", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0);
        rendezvousTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(rendezvousTable);
        add(scrollPane, BorderLayout.CENTER);

        ajouterBtn = new JButton("Ajouter");
        ajouterBtn.addActionListener(new ActionListener() {
            @Override
             public void actionPerformed(ActionEvent e) {
                AjouterRendezVousUI dialog = new AjouterRendezVousUI(ListeRendezVousUI.this, true);
                dialog.setVisible(true);
                populateTable();
             }
        });

        modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = rendezvousTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    int rendezvousId = (int) tableModel.getValueAt(selectedRowIndex, 0);
                    Rendezvous rendezvous = rendezVousService.findById(rendezvousId);
                    if (rendezvous != null) {
                        ModifierRendezVousUI dialog = new ModifierRendezVousUI(ListeRendezVousUI.this, true, rendezvous);
                        dialog.setVisible(true);
                        populateTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(ListeRendezVousUI.this,
                            "Veuillez sélectionner un rendez-vous à modifier.",
                            "Sélection Requise",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        supprimerBtn = new JButton("Supprimer");
        supprimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = rendezvousTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    int rendezvousId = (int) tableModel.getValueAt(selectedRowIndex, 0);
                    rendezVousService.delete(rendezvousId);
                    populateTable();
                } else {
                    JOptionPane.showMessageDialog(ListeRendezVousUI.this,
                            "Veuillez sélectionner un rendez-vous à supprimer.",
                            "Sélection Requise",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(ajouterBtn);
        buttonsPanel.add(modifierBtn);
        buttonsPanel.add(supprimerBtn);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        List<Rendezvous> rendezvousList = rendezVousService.findAll();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Liste des Rendez-vous");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ListeRendezVousUI dialog = new ListeRendezVousUI(frame, true);
                dialog.setVisible(true);
            }
        });
    }
}
