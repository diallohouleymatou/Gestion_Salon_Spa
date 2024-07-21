package models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Rendezvous;
import models.Utilisateur;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-07-18T12:35:07", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Estheticienne.class)
public class Estheticienne_ { 

    public static volatile SingularAttribute<Estheticienne, String> specialite;
    public static volatile SingularAttribute<Estheticienne, String> identifiant;
    public static volatile ListAttribute<Estheticienne, Rendezvous> rendezvousList;
    public static volatile SingularAttribute<Estheticienne, String> adresse;
    public static volatile SingularAttribute<Estheticienne, Utilisateur> idUtilisateur;
    public static volatile SingularAttribute<Estheticienne, String> telephone;
    public static volatile SingularAttribute<Estheticienne, Integer> id;
    public static volatile SingularAttribute<Estheticienne, String> nom;

}