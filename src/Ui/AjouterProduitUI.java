package Ui;

import models.Produit;
import services.ProduitService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

public class AjouterProduitUI extends JDialog {

    private JTextField nomField;
    private JTextField descriptionField;
    private JTextField prixField;
    private JTextField quantiteStockField;
    private JButton ajouterBtn;

    public AjouterProduitUI(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        setTitle("Ajouter Produit");
        setSize(300, 250); // Adjust size to accommodate new field
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        JLabel prixLabel = new JLabel("Prix:");
        prixField = new JTextField();
        JLabel quantiteStockLabel = new JLabel("Quantité en stock:");
        quantiteStockField = new JTextField();
        ajouterBtn = new JButton("Ajouter");

        ajouterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    Produit produit = new Produit();
                    produit.setNom(nomField.getText());
                    produit.setDescription(descriptionField.getText());

                    try {
                        BigDecimal prix = new BigDecimal(prixField.getText());
                        produit.setPrix(prix);

                        int quantiteStock = Integer.parseInt(quantiteStockField.getText());
                        produit.setQuantiteStock(quantiteStock);

                        ProduitService produitService = new ProduitService();
                        produitService.save(produit);
                        JOptionPane.showMessageDialog(AjouterProduitUI.this,
                                "Produit ajouté avec succès.",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AjouterProduitUI.this,
                                "Le prix doit être un nombre valide et la quantité doit être un entier.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(AjouterProduitUI.this,
                            "Tous les champs doivent être remplis.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(nomLabel);
        add(nomField);
        add(descriptionLabel);
        add(descriptionField);
        add(prixLabel);
        add(prixField);
        add(quantiteStockLabel);
        add(quantiteStockField);
        add(new JLabel()); // empty cell
        add(ajouterBtn);
    }

    private boolean validateFields() {
        return !nomField.getText().trim().isEmpty() &&
               !descriptionField.getText().trim().isEmpty() &&
               !prixField.getText().trim().isEmpty() &&
               isNumeric(prixField.getText()) &&
               !quantiteStockField.getText().trim().isEmpty() &&
               isInteger(quantiteStockField.getText());
    }

    private boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
