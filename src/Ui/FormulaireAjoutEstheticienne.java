/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;
import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import services.ServiceService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Estheticienne;
import models.Service;
import services.EntityManagerUtil;



/**
 *
 * @author HP
 */



public class FormulaireAjoutEstheticienne extends JDialog {

    private JLabel nomLabel;
    private JTextField nomField;
    private JLabel specialiteLabel;
    private JComboBox<String> specialiteComboBox;
    private JLabel telephoneLabel;
    private JTextField telephoneField;
    private JLabel adresseLabel;
    private JTextField adresseField;
    private JButton ajouterBtn;
    private JButton annulerBtn;
   private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();

    public FormulaireAjoutEstheticienne(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        setTitle("Ajouter une nouvelle esthéticienne");
        setSize(400, 400);
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

        specialiteLabel = new JLabel("Spécialité:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(specialiteLabel, constraints);

        specialiteComboBox = new JComboBox<>();
        ServiceService serviceService = new ServiceService();
        List<Service> services = serviceService.findAll();
        for (Service service : services) {
            specialiteComboBox.addItem(service.getNom());
        }
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(specialiteComboBox, constraints);

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

        ajouterBtn = new JButton("Ajouter");
        ajouterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                
        String specialite = (String) specialiteComboBox.getSelectedItem();
        String telephone = telephoneField.getText();
        String adresse = adresseField.getText();

        if (nom.isEmpty() || specialite.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
            JOptionPane.showMessageDialog(FormulaireAjoutEstheticienne.this,
                    "Veuillez remplir tous les champs.",
                    "Erreur de formulaire",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidSenegalPhoneNumber(telephone)) {
            JOptionPane.showMessageDialog(FormulaireAjoutEstheticienne.this,
                    "Veuillez entrer un numéro de téléphone sénégalais valide.",
                    "Erreur de formulaire",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Génération de l'identifiant
        String identifiant = generateIdentifiant(nom);

        Estheticienne estheticienne = new Estheticienne();
        estheticienne.setNom(nom);
        estheticienne.setSpecialite(specialite);
        estheticienne.setTelephone(telephone);
        estheticienne.setAdresse(adresse);
        estheticienne.setIdentifiant(identifiant); // Définir l'identifiant généré

        saveEstheticienne(estheticienne);

        JOptionPane.showMessageDialog(FormulaireAjoutEstheticienne.this,
                "Esthéticienne ajoutée avec succès.",
                "Confirmation",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
        });

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(ajouterBtn, constraints);

        annulerBtn = new JButton("Annuler");
        annulerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(annulerBtn, constraints);

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }
    
    private String generateIdentifiant(String nom) {
   int numero = (int) (Math.random() * 900) + 100;
   String identifiant = nom.toLowerCase().replace(" ", "_") + "_" + numero;
    return identifiant;
}

    private boolean isValidSenegalPhoneNumber(String phoneNumber) {
        // Regex pour les numéros de téléphone sénégalais
        String regex = "^(70|75|76|77|78)[0-9]{7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
        public void saveEstheticienne(Estheticienne estheticienne) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(estheticienne);
        transaction.commit();
       // closeEntityManager();
    }
}
