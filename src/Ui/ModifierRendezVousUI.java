/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;

/**
 *
 * @author HP
 */






import Services.EstheticienneService;
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

public class ModifierRendezVousUI extends JDialog {
    private JComboBox<Client> clientComboBox;
    private JComboBox<Estheticienne> estheticienneComboBox;
    private JTextField dateField;
    private JTextField heureField;
    private JButton modifierBtn;
    private Rendezvous rendezvous;
    private RendezVousService rendezVousService;
    private ClientService clientService;
    private EstheticienneService estheticienneService;

    public ModifierRendezVousUI(Window parent, boolean modal, Rendezvous rendezvous) {
        super(parent, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
        this.rendezvous = rendezvous;
        rendezVousService = new RendezVousService();
        clientService = new ClientService();
        estheticienneService = new EstheticienneService();
        initComponents();
        populateFields();
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
        setTitle("Modifier Rendez-vous");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JLabel clientLabel = new JLabel("Client:");
        clientComboBox = new JComboBox<>();
        List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            clientComboBox.addItem(client);
        }

        JLabel estheticienneLabel = new JLabel("Esthéticienne:");
        estheticienneComboBox = new JComboBox<>();
        List<Estheticienne> estheticiennes = estheticienneService.getAllEstheticiennes();
        for (Estheticienne estheticienne : estheticiennes) {
            estheticienneComboBox.addItem(estheticienne);
        }

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateField = new JTextField();

        JLabel heureLabel = new JLabel("Heure (HH:MM):");
        heureField = new JTextField();

        modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client selectedClient = (Client) clientComboBox.getSelectedItem();
                    Estheticienne selectedEstheticienne = (Estheticienne) estheticienneComboBox.getSelectedItem();
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText());
                    Date heure = new SimpleDateFormat("HH:mm").parse(heureField.getText());

                    rendezvous.setIdClient(selectedClient);
                    rendezvous.setIdEstheticienne(selectedEstheticienne);
                    rendezvous.setDate(date);
                    rendezvous.setHeure(heure);

                    rendezVousService.update(rendezvous.getId());
                    dispose();
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(ModifierRendezVousUI.this,
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
        add(dateLabel);
        add(dateField);
        add(heureLabel);
        add(heureField);
        add(new JLabel());
        add(modifierBtn);
    }

    private void populateFields() {
        clientComboBox.setSelectedItem(rendezvous.getIdClient());
        estheticienneComboBox.setSelectedItem(rendezvous.getIdEstheticienne());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateField.setText(dateFormat.format(rendezvous.getDate()));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        heureField.setText(timeFormat.format(rendezvous.getHeure()));
    }
}
