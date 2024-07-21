package Ui;


import Services.EstheticienneService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import models.Estheticienne;

public class FormulaireModificationEstheticienne extends JDialog {

    private JLabel nomLabel;
    private JTextField nomField;
    private JLabel specialiteLabel;
    private JTextField specialiteField;
    private JLabel telephoneLabel;
    private JTextField telephoneField;
    private JLabel adresseLabel;
    private JTextField adresseField;
    private JButton modifierBtn;
    private JButton annulerBtn;

    private Estheticienne estheticienne;

    public FormulaireModificationEstheticienne(Estheticienne estheticienne) {
        this.estheticienne = estheticienne;
        initComponents();
        populateFields();
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            new Ui.Estheticienne().setVisible(true);
            dispose();
        }
    });
    }

    private void initComponents() {
        setTitle("Modifier Esthéticienne");
        setSize(400, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

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

        specialiteLabel = new JLabel("Spécialité:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(specialiteLabel, constraints);

        specialiteField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(specialiteField, constraints);

        telephoneLabel = new JLabel("Téléphone:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(telephoneLabel, constraints);

        telephoneField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(telephoneField, constraints);

        adresseLabel = new JLabel("Adresse:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(adresseLabel, constraints);

        adresseField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(adresseField, constraints);

        modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les nouvelles valeurs
                String nom = nomField.getText();
                String specialite = specialiteField.getText();
                String telephone = telephoneField.getText();
                String adresse = adresseField.getText();

                // Valider les champs
                if (nom.isEmpty() || specialite.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
                    JOptionPane.showMessageDialog(FormulaireModificationEstheticienne.this,
                            "Veuillez remplir tous les champs.",
                            "Erreur de formulaire",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Vérifier le format du numéro de téléphone
                if (!telephone.matches("^(77|78|70|76|75)\\d{7}$")) {
                    JOptionPane.showMessageDialog(FormulaireModificationEstheticienne.this,
                            "Le numéro de téléphone n'est pas valide. Il doit commencer par 77, 78, 70, 76 ou 75 et contenir 9 chiffres au total.",
                            "Erreur de formulaire",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Mettre à jour l'esthéticienne
                estheticienne.setNom(nom);
                estheticienne.setSpecialite(specialite);
                estheticienne.setTelephone(telephone);
                estheticienne.setAdresse(adresse);

                EstheticienneService estheticienneService = new EstheticienneService();
                estheticienneService.updateEstheticienne(estheticienne);

                JOptionPane.showMessageDialog(FormulaireModificationEstheticienne.this,
                        "Esthéticienne modifiée avec succès.",
                        "Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose(); // Ferme le dialogue après modification
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(modifierBtn, constraints);

        annulerBtn = new JButton("Annuler");
        annulerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme le dialogue sans rien faire
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(annulerBtn, constraints);

        add(panel, BorderLayout.CENTER);
    }

    private void populateFields() {
        // Remplir les champs avec les données de l'esthéticienne existante
        if (estheticienne != null) {
            nomField.setText(estheticienne.getNom());
            specialiteField.setText(estheticienne.getSpecialite());
            telephoneField.setText(estheticienne.getTelephone());
            adresseField.setText(estheticienne.getAdresse());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Exemple d'utilisation : à adapter selon votre contexte d'application
                Estheticienne estheticienne = new Estheticienne();
                estheticienne.setNom("Nom Esthéticienne");
                estheticienne.setSpecialite("Spécialité Esthéticienne");
                estheticienne.setTelephone("771234567");
                estheticienne.setAdresse("123 Rue Exemple");

                FormulaireModificationEstheticienne dialog = new FormulaireModificationEstheticienne(estheticienne);
                dialog.setVisible(true);
            }
        });
    }
}
