/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import models.Produit;

/**
 *
 * @author HP
 */
public interface Produitdao {
     Produit findById(int id);
    
    void save(Produit produit);
    
    void update( Produit produit);
    
    void delete(int id);
    
    List<Produit> findAll();
    
}
