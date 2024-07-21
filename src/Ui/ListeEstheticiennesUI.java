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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Estheticienne;
import services.EntityManagerUtil;

public class ListeEstheticiennesUI extends JDialog {

    private JTable estheticiennesTable;
    private DefaultTableModel tableModel;
    private JButton modifierBtn;
    private JButton supprimerBtn;
    private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();
    

    public ListeEstheticiennesUI(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        populateTable();
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
        setTitle("Liste des Esthéticiennes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Création du modèle de table avec les colonnes appropriées
        String[] columnNames = {"ID", "Nom", "Spécialité", "Téléphone", "Adresse"};
        tableModel = new DefaultTableModel(columnNames, 0);
        estheticiennesTable = new JTable(tableModel);

        // Ajouter la table dans un JScrollPane pour le défilement
        JScrollPane scrollPane = new JScrollPane(estheticiennesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Boutons pour modifier et supprimer une esthéticienne
        modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique pour récupérer et modifier l'esthéticienne sélectionnée
                int selectedRowIndex = estheticiennesTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    // Récupérer l'ID de l'esthéticienne sélectionnée depuis la table
                    int estheticienneId = (int) tableModel.getValueAt(selectedRowIndex, 0);

                    // Appeler la méthode de modification de l'esthéticienne
                    modifierEstheticienne(estheticienneId);
                } else {
                    JOptionPane.showMessageDialog(ListeEstheticiennesUI.this,
                            "Veuillez sélectionner une esthéticienne à modifier.",
                            "Sélection Requise",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        supprimerBtn = new JButton("Supprimer");
        supprimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique pour supprimer l'esthéticienne sélectionnée
                int selectedRowIndex = estheticiennesTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    // Récupérer l'ID de l'esthéticienne sélectionnée depuis la table
                    int estheticienneId = (int) tableModel.getValueAt(selectedRowIndex, 0);

                    // Appeler la méthode de suppression de l'esthéticienne
                    supprimerEstheticienne(estheticienneId);
                } else {
                    JOptionPane.showMessageDialog(ListeEstheticiennesUI.this,
                            "Veuillez sélectionner une esthéticienne à supprimer.",
                            "Sélection Requise",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(modifierBtn);
        buttonsPanel.add(supprimerBtn);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void populateTable() {
        // Récupérer la liste des esthéticiennes depuis le service
        List<Estheticienne> estheticiennes = getAllEstheticiennes() ;

        // Remplir le modèle de la table avec les données des esthéticiennes
        for (Estheticienne estheticienne : estheticiennes) {
            Object[] rowData = {estheticienne.getId(), estheticienne.getNom(), estheticienne.getSpecialite(), estheticienne.getTelephone(), estheticienne.getAdresse()};
            tableModel.addRow(rowData);
        }
    }
        public List<Estheticienne> getAllEstheticiennes() {
        Query query = em.createQuery("SELECT e FROM Estheticienne e");
        List<Estheticienne> estheticiennes = query.getResultList();
       // closeEntityManager();
        return estheticiennes;
    }


    private void modifierEstheticienne(int estheticienneId) {
        // Implémenter la logique de modification de l'esthéticienne ici
        Estheticienne estheticienne = findById(estheticienneId);
        if (estheticienne != null) {
            FormulaireModificationEstheticienne dialog = new FormulaireModificationEstheticienne(estheticienne);
            dialog.setVisible(true);
            // Rafraîchir la table après la modification
            refreshTable();
        }
    }
        public Estheticienne findById(int id) {
        Query query = em.createQuery("SELECT e FROM Estheticienne e WHERE e.id = :id");
        query.setParameter("id", id);
        Estheticienne estheticienne = (Estheticienne) query.getSingleResult();
        //closeEntityManager();
        return estheticienne;
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
         public void deleteById(int id) {
         Estheticienne estheticienne = findById(id);
        if (estheticienne != null) {
            em.getTransaction().begin();
            em.remove(estheticienne);
            em.getTransaction().commit();
        }
        
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void supprimerEstheticienne(int estheticienneId) {
        // Implémenter la logique de suppression de l'esthéticienne ici
        int confirmation = JOptionPane.showConfirmDialog(ListeEstheticiennesUI.this,
                "Êtes-vous sûr de vouloir supprimer cette esthéticienne ?",
                "Confirmation de Suppression",
                JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            deleteById(estheticienneId);

            // Rafraîchir la table après la suppression
            refreshTable();
        }
    }

    private void refreshTable() {
        // Effacer toutes les lignes existantes dans le modèle de la table
        tableModel.setRowCount(0);

        // Remplir à nouveau le modèle avec les données mises à jour
        populateTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Liste des Esthéticiennes");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ListeEstheticiennesUI dialog = new ListeEstheticiennesUI(frame, true);
                dialog.setVisible(true);
            }
        });
    }
}
