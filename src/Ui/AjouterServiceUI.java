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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

public class AjouterServiceUI extends JDialog {

    private JTextField nomField;
    private JTextField descriptionField;
    private JTextField prixField; // Champ pour le prix
    private JButton ajouterBtn;

    public AjouterServiceUI(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        setTitle("Ajouter Service");
        setSize(300, 250); // Ajusté pour inclure le champ prix
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2)); // Ajout d'une ligne pour le champ prix

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        JLabel prixLabel = new JLabel("Prix:"); // Label pour le prix
        prixField = new JTextField(); // Champ pour le prix
        ajouterBtn = new JButton("Ajouter");

        ajouterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les valeurs des champs
                String nom = nomField.getText();
        String description = descriptionField.getText();
        BigDecimal prix = new BigDecimal(prixField.getText()); // Conversion du texte en BigDecimal

        // Créer un nouvel objet Service
        Service service = new Service();
        service.setNom(nom);
        service.setDescription(description);
        service.setPrix(prix); // Définir le prix

        // Sauvegarder le service en utilisant le ServiceService
        ServiceService serviceService = new ServiceService();
        serviceService.save(service);

                // Fermer la fenêtre après l'ajout
                dispose();
                JOptionPane.showMessageDialog(null, "Service ajouté avec succès.");
            }
        });

        add(nomLabel);
        add(nomField);
        add(descriptionLabel);
        add(descriptionField);
        add(prixLabel); // Ajout du label pour le prix
        add(prixField); // Ajout du champ pour le prix
        add(new JLabel()); // Cellule vide
        add(ajouterBtn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Ajouter Service");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                AjouterServiceUI dialog = new AjouterServiceUI(frame, true);
                dialog.setVisible(true);
            }
        });
    }
}


