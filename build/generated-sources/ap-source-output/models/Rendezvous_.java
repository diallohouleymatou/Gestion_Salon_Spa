package models;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Client;
import models.Estheticienne;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-07-18T12:35:07", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Rendezvous.class)
public class Rendezvous_ { 

    public static volatile SingularAttribute<Rendezvous, Date> date;
    public static volatile SingularAttribute<Rendezvous, Estheticienne> idEstheticienne;
    public static volatile SingularAttribute<Rendezvous, Date> heure;
    public static volatile SingularAttribute<Rendezvous, Client> idClient;
    public static volatile SingularAttribute<Rendezvous, Integer> id;
    public static volatile SingularAttribute<Rendezvous, Boolean> confirme;

}