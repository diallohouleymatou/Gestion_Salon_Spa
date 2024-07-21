/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author HP
 */
public class EntityManagerUtil {
    
    private static EntityManagerFactory EMF;
    public static EntityManagerFactory getEMF(){
        if(EMF==null){
            EMF = Persistence.createEntityManagerFactory("MONSALONPU");
        }
        return EMF ;
    }
    
    public static void closeEMF(){
        if(EMF!=null || EMF.isOpen()){
        EMF.close();
        }
    
    }
}
