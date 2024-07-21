/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;

/**
 *
 * @author HP
 */






import models.Client;
import models.Estheticienne;
import models.Rendezvous;
import services.ClientService;

import services.RendezVousService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import services.EntityManagerUtil;

public class AjouterRendezVousUI extends JDialog {
    private JComboBox<Client> clientComboBox;
    private JComboBox<Estheticienne> estheticienneComboBox;
    private JTextField dateField;
    private JTextField heureField;
    private JCheckBox confirmeCheckBox;
    private JButton ajouterBtn;
    private RendezVousService rendezVousService;
    private ClientService clientService;
    private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();
    public AjouterRendezVousUI(Window parent, boolean modal) {
        super(parent, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
        rendezVousService = new RendezVousService();
        clientService = new ClientService();
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            new RendezVous().setVisible(true);
            dispose();
        }
    });
    }

    private void initComponents() {
        setTitle("Ajouter Rendez-vous");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        JLabel clientLabel = new JLabel("Client:");
        clientComboBox = new JComboBox<>();
        List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            clientComboBox.addItem(client);
        }

        JLabel estheticienneLabel = new JLabel("Esthéticienne:");
        estheticienneComboBox = new JComboBox<>();
        List<Estheticienne> estheticiennes = getAllEstheticiennes();
        for (Estheticienne estheticienne : estheticiennes) {
            estheticienneComboBox.addItem(estheticienne);
        }

        JLabel specialiteLabel = new JLabel("Spécialité:");
        JTextField specialiteField = new JTextField();
        specialiteField.setEditable(false);
        estheticienneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estheticienne selectedEstheticienne = (Estheticienne) estheticienneComboBox.getSelectedItem();
                specialiteField.setText(selectedEstheticienne != null ? selectedEstheticienne.getSpecialite() : "");
            }
        });

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateField = new JTextField();
        dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        JLabel heureLabel = new JLabel("Heure (HH:MM):");
        heureField = new JTextField();
        heureField.setText(new SimpleDateFormat("HH:mm").format(new Date()));

        JLabel confirmeLabel = new JLabel("Confirmé:");
        confirmeCheckBox = new JCheckBox();
        confirmeCheckBox.setEnabled(false);

        ajouterBtn = new JButton("Ajouter");
        ajouterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client selectedClient = (Client) clientComboBox.getSelectedItem();
                    Estheticienne selectedEstheticienne = (Estheticienne) estheticienneComboBox.getSelectedItem();
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText());
                    Date heure = new SimpleDateFormat("HH:mm").parse(heureField.getText());

                    // Vérifier si la date est antérieure à la date actuelle
                    Date currentDate = new Date();
                    if (date.before(currentDate)) {
                        JOptionPane.showMessageDialog(AjouterRendezVousUI.this,
                                "La date du rendez-vous ne peut pas être antérieure à la date actuelle.",
                                "Erreur de Date",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Vérifier si le client a déjà un rendez-vous à la même heure avec la même esthéticienne
                    if (rendezVousService.checkExistingRendezVous(selectedClient.getId(), selectedEstheticienne.getId(), date, heure)) {
                        JOptionPane.showMessageDialog(AjouterRendezVousUI.this,
                                "Ce client a déjà un rendez-vous à la même heure avec cette esthéticienne.",
                                "Conflit de Rendez-vous",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Vérifier la limite de rendez-vous par heure pour l'esthéticienne
                    if (rendezVousService.countRendezVousByEstheticienne(selectedEstheticienne.getId(), date, heure) >= 5) {
                        JOptionPane.showMessageDialog(AjouterRendezVousUI.this,
                                "Cette esthéticienne a atteint la limite de 5 rendez-vous par heure.",
                                "Limite de Rendez-vous",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Rendezvous rendezvous = new Rendezvous();
                    rendezvous.setIdClient(selectedClient);
                    rendezvous.setIdEstheticienne(selectedEstheticienne);
                    rendezvous.setDate(date);
                    rendezvous.setHeure(heure);
                    rendezvous.setConfirme(false); // Par défaut, confirmé est false

                    rendezVousService.save(rendezvous);
                    dispose();
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(AjouterRendezVousUI.this,
                            "Format de date ou d'heure invalide.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(clientLabel);
        add(clientComboBox);
        add(estheticienneLabel);
        add(estheticienneComboBox);
        add(specialiteLabel);
        add(specialiteField);
        add(dateLabel);
        add(dateField);
        add(heureLabel);
        add(heureField);
        add(confirmeLabel);
        add(confirmeCheckBox);
        add(new JLabel());
        add(ajouterBtn);
    }
    public List<Estheticienne> getAllEstheticiennes() {
        Query query = em.createQuery("SELECT e FROM Estheticienne e");
        List<Estheticienne> estheticiennes = query.getResultList();
       // closeEntityManager();
        return estheticiennes;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AjouterRendezVousUI dialog = new AjouterRendezVousUI(null, true);
                dialog.setVisible(true);
            }
        });
    }
    
}
