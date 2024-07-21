/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;

/**
 *
 * @author HP
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import models.Client;
import services.ClientService;

public class ListeClientsUI extends JDialog {

    private JTable clientsTable;
    private DefaultTableModel tableModel;
    private JButton modifierBtn;
    private JButton supprimerBtn;

    public ListeClientsUI(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        populateTable();
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            new ClientUI().setVisible(true);
            dispose();
        }
    });
    }

    private void initComponents() {
        setTitle("Liste des Clients");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Création du modèle de table avec les colonnes appropriées
        String[] columnNames = {"ID", "Nom", "Email", "Téléphone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        clientsTable = new JTable(tableModel);

        // Ajouter la table dans un JScrollPane pour le défilement
        JScrollPane scrollPane = new JScrollPane(clientsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Boutons pour modifier et supprimer un client
        modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique pour récupérer et modifier le client sélectionné
                int selectedRowIndex = clientsTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    // Récupérer l'ID du client sélectionné depuis la table
                    int clientId = (int) tableModel.getValueAt(selectedRowIndex, 0);

                    // Appeler la méthode de modification du client
                    modifierClient(clientId);
                } else {
                    JOptionPane.showMessageDialog(ListeClientsUI.this,
                            "Veuillez sélectionner un client à modifier.",
                            "Sélection Requise",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        supprimerBtn = new JButton("Supprimer");
        supprimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique pour supprimer le client sélectionné
                int selectedRowIndex = clientsTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    // Récupérer l'ID du client sélectionné depuis la table
                    int clientId = (int) tableModel.getValueAt(selectedRowIndex, 0);

                    // Appeler la méthode de suppression du client
                    supprimerClient(clientId);
                } else {
                    JOptionPane.showMessageDialog(ListeClientsUI.this,
                            "Veuillez sélectionner un client à supprimer.",
                            "Sélection Requise",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(modifierBtn);
        buttonsPanel.add(supprimerBtn);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void populateTable() {
        // Récupérer la liste des clients depuis le service
        ClientService clientService = new ClientService();
        List<Client> clients = clientService.getAllClients();

        // Remplir le modèle de la table avec les données des clients
        for (Client client : clients) {
            Object[] rowData = {client.getId(), client.getNom(), client.getEmail(), client.getTelephone()};
            tableModel.addRow(rowData);
        }
    }

    private void modifierClient(int clientId) {
        // Implémenter la logique de modification du client ici
        // Vous pouvez ouvrir un formulaire de modification similaire à celui d'ajout
        // ouvrir une boîte de dialogue, etc.
        // Exemple:
        Client client = getClientById(clientId);
        if (client != null) {
            FormulaireModificationClient dialog = new FormulaireModificationClient(client);
            dialog.setVisible(true);

            // Rafraîchir la table après la modification
            refreshTable();
        }
    }

    private void supprimerClient(int clientId) {
        // Implémenter la logique de suppression du client ici
        int confirmation = JOptionPane.showConfirmDialog(ListeClientsUI.this,
                "Êtes-vous sûr de vouloir supprimer ce client ?",
                "Confirmation de Suppression",
                JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            ClientService clientService = new ClientService();
            clientService.deleteClient(clientId);

            // Rafraîchir la table après la suppression
            refreshTable();
        }
    }

    private Client getClientById(int clientId) {
        // Implémenter la logique pour récupérer un client par son ID depuis le service
        ClientService clientService = new ClientService();
        return clientService.getClientById(clientId);
    }

    private void refreshTable() {
        // Effacer toutes les lignes existantes dans le modèle de la table
        tableModel.setRowCount(0);

        // Remplir à nouveau le modèle avec les données mises à jour
        populateTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Liste des Clients");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ListeClientsUI dialog = new ListeClientsUI(frame, true);
                dialog.setVisible(true);
            }
        });
    }
}
