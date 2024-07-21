/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

/**
 *
 * @author HP
 */


import java.util.List;
import models.Service;

public interface ServiceDao {
    Service findById(int id);
    void save(Service service);
    void update(Service service);
    void delete(int id);
    List<Service> findAll();
}

