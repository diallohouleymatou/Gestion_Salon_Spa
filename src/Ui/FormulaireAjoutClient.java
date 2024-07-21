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
import java.awt.*;
import java.awt.event.*;
import models.Client;
import services.ClientService;
import models.Client;




public class FormulaireAjoutClient extends JDialog {

    private JLabel nomLabel;
    private JTextField nomField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel telephoneLabel;
    private JTextField telephoneField;
    private JButton ajouterBtn;
    private JButton annulerBtn;

    public FormulaireAjoutClient(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        setTitle("Ajouter un nouveau client");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        nomLabel = new JLabel("Nom:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nomLabel, constraints);

        nomField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nomField, constraints);

        emailLabel = new JLabel("Email:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(emailLabel, constraints);

        emailField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(emailField, constraints);

        telephoneLabel = new JLabel("Téléphone:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(telephoneLabel, constraints);

        telephoneField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(telephoneField, constraints);

        ajouterBtn = new JButton("Ajouter");
        ajouterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            
        String nom = nomField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();
        
        // Vérification des champs non vides
        if (nom.isEmpty() || email.isEmpty() || telephone.isEmpty()) {
            JOptionPane.showMessageDialog(FormulaireAjoutClient.this,
                    "Veuillez remplir tous les champs.",
                    "Erreur de formulaire",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ajouter la logique pour enregistrer le client
        Client client = new Client(); // Instanciation avec le constructeur par défaut
        client.setNom(nom);
        client.setEmail(email);
        client.setTelephone(telephone);
        ClientService clientService = new ClientService(); // Création du service client
        clientService.saveClient(client);
        
        JOptionPane.showMessageDialog(FormulaireAjoutClient.this,
                "Client ajouté avec succès.",
                "Confirmation",
                JOptionPane.INFORMATION_MESSAGE);
        
        dispose(); // Ferme le dialogue après ajout
    }
});
        
                //
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(ajouterBtn, constraints);

        annulerBtn = new JButton("Annuler");
        annulerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientUI cl = new ClientUI();
                dispose(); // Ferme le dialogue sans rien faire
            }
        });
        //
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(annulerBtn, constraints);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null); // Centrer le dialogue sur l'écran
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FormulaireAjoutClient dialog = new FormulaireAjoutClient(new JFrame(), true);
                dialog.setVisible(true);
            }
        });
    }
}

    

