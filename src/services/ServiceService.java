/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author HP
 */


import dao.ServiceDao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import models.Service;

public class ServiceService implements ServiceDao {

    private EntityManager em = EntityManagerUtil.getEMF().createEntityManager();

    @Override
    public Service findById(int id) {
        return em.find(Service.class, id);
    }

    @Override
    public void save(Service service) {
        EntityTransaction transaction = em.getTransaction();
        try {
            em.getTransaction().begin();
            em.persist(service);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Service service) {
        EntityTransaction transaction = em.getTransaction();
        try {
            em.getTransaction().begin();
            em.merge(service);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            em.getTransaction().begin();
            Service service = em.find(Service.class, id);
            if (service != null) {
                em.remove(service);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Service> findAll() {
        try {
            return em.createNamedQuery("Service.findAll", Service.class).getResultList();
        } finally {
            em.close();
        }
    }
}
