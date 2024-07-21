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

public class FormulaireModificationService extends JDialog {

    private JTextField nomField;
    private JTextField descriptionField;
    private JTextField prixField; // Champ pour le prix
    private JButton modifierBtn;

    public FormulaireModificationService(Service service) {
        initComponents(service);
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

    private void initComponents(Service service) {
        setTitle("Modifier Service");
        setSize(300, 250); // Ajusté la taille pour accueillir le champ de prix
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2)); // Ajouté une rangée pour le prix

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField(service.getNom());
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(service.getDescription());
        JLabel prixLabel = new JLabel("Prix:"); // Label pour le prix
        prixField = new JTextField(service.getPrix().toString()); // Champ pour le prix
        modifierBtn = new JButton("Modifier");

        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les nouvelles valeurs et mettre à jour le service
                service.setNom(nomField.getText());
                service.setDescription(descriptionField.getText());
                BigDecimal prix = new BigDecimal(prixField.getText());
                service.setPrix(prix);

                ServiceService serviceService = new ServiceService();
                serviceService.update(service);

                JOptionPane.showMessageDialog(FormulaireModificationService.this,
                        "Service modifié avec succès.",
                        "Modification Réussie",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose(); // Fermer la fenêtre de modification après la mise à jour
            }
        });

        add(nomLabel);
        add(nomField);
        add(descriptionLabel);
        add(descriptionField);
        add(prixLabel); // Ajouté le label du prix
        add(prixField); // Ajouté le champ du prix
        add(new JLabel()); // empty cell
        add(modifierBtn);
    }
}
