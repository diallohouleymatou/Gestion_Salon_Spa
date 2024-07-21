/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.Clientdao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import models.Client;

/**
 *
 * @author Ouly DIALLO
 */
public class ClientService  implements Clientdao {
    private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();


    @Override
    public List<Client> findByNom(String nom) {
        
         Query query = em.createQuery("SELECT c FROM Client c WHERE c.nom = :nom");
        query.setParameter("nom", nom);
        List<Client> clients = query.getResultList();
       // closeEntityManager();
        return clients;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Client findByEmail(String email) {
        
         Query query = em.createQuery("SELECT c FROM Client c WHERE c.email = :email");
        query.setParameter("email", email);
        Client client = (Client) query.getSingleResult();
        //closeEntityManager();
        return client;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Client findByTelephone(String telephone) {
        
         Query query = em.createQuery("SELECT c FROM Client c WHERE c.telephone = :telephone");
        query.setParameter("telephone", telephone);
        Client client = (Client) query.getSingleResult();
        //closeEntityManager();
        return client;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
  
    /**
     *
     * @return
     */
    @Override
   public List<Client> getAllClients() {
        Query query = em.createQuery("SELECT c FROM Client c");
        List<Client> clients = query.getResultList();
        //closeEntityManager();
        return clients;
    } 
    
   public void saveClient(Client client) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(client);
        transaction.commit();
        //closeEntityManager();
    }
   
   public void updateClient(Client client) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(client);
        transaction.commit();
        //closeEntityManager();
    }
   
    @Override
   public void deleteClient(int id) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Client client = em.find(Client.class, id);
        if (client != null) {
            em.remove(client);
        }
        transaction.commit();
        //closeEntityManager();
    }

    @Override
    public Client getClientById(int id) {
         Query query = em.createQuery("SELECT c FROM Client c WHERE c.id= :id");
        query.setParameter("id", id);
        Client client = (Client) query.getSingleResult();
        //closeEntityManager();
        return client;
        
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
