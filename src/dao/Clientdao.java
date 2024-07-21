/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import models.Client;

/**
 *
 * @author Ouly DIALLO
 */
public interface Clientdao {
    
    // Recherche un client par son nom
    List<Client> findByNom(String nom);

    // Recherche un client par son email
    Client findByEmail(String email);

    // Recherche un client par son numéro de téléphone
    Client findByTelephone(String telephone);
    
    List<Client> getAllClients();
    Client getClientById(int id);
    
    void deleteClient(int id);
    
    
    
   // saveClient(Client client);

    
    
}
