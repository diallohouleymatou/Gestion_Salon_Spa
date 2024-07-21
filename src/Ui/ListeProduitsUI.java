package Ui;

import models.Produit;
import services.ProduitService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ListeProduitsUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private ProduitService produitService;

    public ListeProduitsUI() {
        initComponents();
        produitService = new ProduitService();
        loadProduits();
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            new Produits().setVisible(true);
            dispose();
        }
    });
    }

    

    private void initComponents() {
        setTitle("Liste des Produits");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Nom", "Description", "Prix", "Quantité en stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton modifierBtn = new JButton("Modifier");
        JButton supprimerBtn = new JButton("Supprimer");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modifierBtn);
        buttonPanel.add(supprimerBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int produitId = (int) tableModel.getValueAt(selectedRow, 0);
                    Produit produit = produitService.findById(produitId);
                    ModifierProduitUI modifierProduitUI = new ModifierProduitUI(ListeProduitsUI.this, true, produit);
                    modifierProduitUI.setVisible(true); // Afficher la boîte de dialogue de modification
                } else {
                    JOptionPane.showMessageDialog(ListeProduitsUI.this,
                            "Veuillez sélectionner un produit à modifier.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        supprimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int produitId = (int) tableModel.getValueAt(selectedRow, 0);
                    int confirmation = JOptionPane.showConfirmDialog(ListeProduitsUI.this,
                            "Êtes-vous sûr de vouloir supprimer ce produit?",
                            "Confirmation de suppression",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        produitService.delete(produitId);
                        loadProduits();
                        JOptionPane.showMessageDialog(ListeProduitsUI.this,
                                "Produit supprimé avec succès.",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ListeProduitsUI.this,
                            "Veuillez sélectionner un produit à supprimer.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void loadProduits() {
        List<Produit> produits = produitService.findAll();
        tableModel.setRowCount(0); // Clear existing rows
        for (Produit produit : produits) {
            tableModel.addRow(new Object[]{produit.getId(), produit.getNom(), produit.getDescription(), produit.getPrix(), produit.getQuantiteStock()});
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ListeProduitsUI listeProduitsUI = new ListeProduitsUI();
                listeProduitsUI.setVisible(true);
            }
        });
    }
}
