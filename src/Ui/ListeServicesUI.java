/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;

/**
 *
 * @author HP
 */


import models.Service;
import services.ServiceService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ListeServicesUI extends JDialog {

    private JTable servicesTable;
    private DefaultTableModel tableModel;
    private JButton modifierBtn;
    private JButton supprimerBtn;

    public ListeServicesUI(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        populateTable();
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            new services().setVisible(true);
            dispose();
        }
    });
    }

    private void initComponents() {
        setTitle("Liste des Services");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Nom", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0);
        servicesTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(servicesTable);
        add(scrollPane, BorderLayout.CENTER);

        modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierService();
            }
        });

        supprimerBtn = new JButton("Supprimer");
        supprimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerService();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(modifierBtn);
        buttonsPanel.add(supprimerBtn);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void populateTable() {
        ServiceService serviceService = new ServiceService();
        List<Service> services = serviceService.findAll();

        for (Service service : services) {
            Object[] rowData = {service.getId(), service.getNom(), service.getDescription()};
            tableModel.addRow(rowData);
        }
    }

    private void modifierService() {
        int selectedRowIndex = servicesTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            int serviceId = (int) tableModel.getValueAt(selectedRowIndex, 0); // Récupérer l'ID du service sélectionné
            ServiceService serviceService = new ServiceService();
            Service service = serviceService.findById(serviceId);
            if (service != null) {
                FormulaireModificationService dialog = new FormulaireModificationService(service);
                dialog.setVisible(true);
                refreshTable();
                JOptionPane.showMessageDialog(ListeServicesUI.this,
                        "Service modifié avec succès.",
                        "Modification Réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ListeServicesUI.this,
                        "Service non trouvé.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(ListeServicesUI.this,
                    "Veuillez sélectionner un service à modifier.",
                    "Sélection Requise",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void supprimerService() {
        int selectedRowIndex = servicesTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            int serviceId = (int) tableModel.getValueAt(selectedRowIndex, 0); // Récupérer l'ID du service sélectionné
            ServiceService serviceService = new ServiceService();
            int confirmation = JOptionPane.showConfirmDialog(ListeServicesUI.this,
                    "Êtes-vous sûr de vouloir supprimer ce service ?",
                    "Confirmation de Suppression",
                    JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                serviceService.delete(serviceId);
                refreshTable();
                JOptionPane.showMessageDialog(ListeServicesUI.this,
                        "Service supprimé avec succès.",
                        "Suppression Réussie",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(ListeServicesUI.this,
                    "Veuillez sélectionner un service à supprimer.",
                    "Sélection Requise",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        populateTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Liste des Services");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ListeServicesUI dialog = new ListeServicesUI(frame, true);
                dialog.setVisible(true);
            }
        });
    }
}

