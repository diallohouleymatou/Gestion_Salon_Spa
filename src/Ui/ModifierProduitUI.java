package Ui;

import models.Produit;
import services.ProduitService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ModifierProduitUI extends JDialog {

    private ListeProduitsUI parentFrame;
    private Produit produit;
    private ProduitService produitService;

    private JTextField nomField;
    private JTextArea descriptionArea;
    private JTextField prixField;
    private JTextField quantiteField;

    public ModifierProduitUI(ListeProduitsUI parentFrame, boolean modal, Produit produit) {
        super(parentFrame, modal);
        this.parentFrame = parentFrame;
        this.produit = produit;
        this.produitService = new ProduitService();

        initComponents();
        fillForm();

        setTitle("Modifier Produit");
        setSize(400, 300);
        setLocationRelativeTo(parentFrame);
        setModal(true);
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField();
        formPanel.add(nomLabel);
        formPanel.add(nomField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionScrollPane);

        JLabel prixLabel = new JLabel("Prix:");
        prixField = new JTextField();
        formPanel.add(prixLabel);
        formPanel.add(prixField);

        JLabel quantiteLabel = new JLabel("Quantité en Stock:");
        quantiteField = new JTextField();
        formPanel.add(quantiteLabel);
        formPanel.add(quantiteField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void fillForm() {
        if (produit != null) {
            nomField.setText(produit.getNom());
            descriptionArea.setText(produit.getDescription());
            prixField.setText(String.valueOf(produit.getPrix()));
            quantiteField.setText(String.valueOf(produit.getQuantiteStock()));
        }
    }

    private void saveChanges() {
        String nom = nomField.getText().trim();
        String description = descriptionArea.getText().trim();
        BigDecimal prix = new BigDecimal(prixField.getText().trim());
        int quantiteStock = Integer.parseInt(quantiteField.getText().trim());

        produit.setNom(nom);
        produit.setDescription(description);
        produit.setPrix(prix);
        produit.setQuantiteStock(quantiteStock);

        produitService.update(produit);

        parentFrame.loadProduits(); // Recharger la liste des produits dans la fenêtre parente

        dispose();
        JOptionPane.showMessageDialog(parentFrame,
            "Produit modifié avec succès.",
            "Succès",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Exemple d'utilisation dans un contexte différent (pour les tests, par exemple)
                Produit produit = new Produit();
                produit.setId(1); // Remplacez par l'ID d'un produit existant à modifier
                produit.setNom("Produit Test");
                produit.setDescription("Description du produit test");
                produit.setPrix(new BigDecimal("19.99"));
                produit.setQuantiteStock(50);

                ListeProduitsUI parentFrame = new ListeProduitsUI();
                ModifierProduitUI modifierProduitUI = new ModifierProduitUI(parentFrame, true, produit);
                modifierProduitUI.setVisible(true);
            }
        });
    }
}
