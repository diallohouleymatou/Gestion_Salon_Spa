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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import models.Client;
import services.ClientService;

public class FormulaireModificationClient extends JDialog {

    private JLabel nomLabel;
    private JTextField nomField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel telephoneLabel;
    private JTextField telephoneField;
    private JButton modifierBtn;
    private JButton annulerBtn;

    private Client client;

    public FormulaireModificationClient(Client client) {
        this.client = client;
        initComponents();
        populateFields();
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
        setTitle("Modifier le Client");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Centrer le dialogue sur l'écran

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

        modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les nouvelles valeurs des champs
                String nouveauNom = nomField.getText();
                String nouveauEmail = emailField.getText();
                String nouveauTelephone = telephoneField.getText();

                // Vérifier que les champs ne sont pas vides
                if (nouveauNom.isEmpty() || nouveauEmail.isEmpty() || nouveauTelephone.isEmpty()) {
                    JOptionPane.showMessageDialog(FormulaireModificationClient.this,
                            "Veuillez remplir tous les champs.",
                            "Erreur de formulaire",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Mettre à jour les données du client
                client.setNom(nouveauNom);
                client.setEmail(nouveauEmail);
                client.setTelephone(nouveauTelephone);

                // Appeler le service pour mettre à jour le client
                ClientService clientService = new ClientService();
                clientService.updateClient(client);

                // Afficher un message de confirmation
                JOptionPane.showMessageDialog(FormulaireModificationClient.this,
                        "Client modifié avec succès.",
                        "Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);

                // Fermer le dialogue après modification
                dispose();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(modifierBtn, constraints);

        annulerBtn = new JButton("Annuler");
        annulerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer le dialogue sans rien faire
                dispose();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(annulerBtn, constraints);

        add(panel, BorderLayout.CENTER);
    }

    private void populateFields() {
        // Remplir les champs avec les données du client existant
        nomField.setText(client.getNom());
        emailField.setText(client.getEmail());
        telephoneField.setText(client.getTelephone());
    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Client client = new Client(); // Remplacer par le client à modifier
                FormulaireModificationClient dialog = new FormulaireModificationClient(client);
                dialog.setVisible(true);
            }
        });
    }
}

