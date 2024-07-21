/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "estheticienne")
@NamedQueries({
    @NamedQuery(name = "Estheticienne.findAll", query = "SELECT e FROM Estheticienne e"),
    @NamedQuery(name = "Estheticienne.findById", query = "SELECT e FROM Estheticienne e WHERE e.id = :id"),
    @NamedQuery(name = "Estheticienne.findByNom", query = "SELECT e FROM Estheticienne e WHERE e.nom = :nom"),
    @NamedQuery(name = "Estheticienne.findBySpecialite", query = "SELECT e FROM Estheticienne e WHERE e.specialite = :specialite")})
public class Estheticienne implements Serializable {

    @Column(name = "identifiant")
    private String identifiant;
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur idUtilisateur;

    @Column(name = "telephone")
    private String telephone;
    @Column(name = "adresse")
    private String adresse;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    @Column(name = "specialite")
    private String specialite;
    @OneToMany(mappedBy = "idEstheticienne", fetch = FetchType.LAZY)
    private List<Rendezvous> rendezvousList;

    public Estheticienne() {
    }

    public Estheticienne(Integer id) {
        this.id = id;
    }

    public Estheticienne(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public List<Rendezvous> getRendezvousList() {
        return rendezvousList;
    }

    public void setRendezvousList(List<Rendezvous> rendezvousList) {
        this.rendezvousList = rendezvousList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estheticienne)) {
            return false;
        }
        Estheticienne other = (Estheticienne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Utilisateur getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Utilisateur idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
}
