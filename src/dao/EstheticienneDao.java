/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import models.Estheticienne;

/**
 *
 * @author Ouly DIALLO
 */
public interface EstheticienneDao {
    
    // Recherche une esthéticienne par son nom
    List<Estheticienne> findByNom(String nom);

    // Recherche une esthéticienne par sa spécialité
    List<Estheticienne> findBySpecialite(String specialite);
    
    Estheticienne findById(int id);

    // Supprime une esthéticienne par son ID
    void deleteById(int id);

    // Ajoute ou met à jour une esthéticienne
    void save(Estheticienne estheticienne);
    

    
}
