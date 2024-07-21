/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import models.Utilisateur;

/**
 *
 * @author HP
 */
public interface IUtilisateur {
    
    List<Utilisateur> allUser();
    Utilisateur getUserByUsername(String Username);
    Utilisateur getUserById(int id);
    void saveUser(Utilisateur utilisateur);
    
}
