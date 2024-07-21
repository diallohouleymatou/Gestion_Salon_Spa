package models;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Rendezvous;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-07-18T12:35:07", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile ListAttribute<Client, Rendezvous> rendezvousList;
    public static volatile SingularAttribute<Client, String> telephone;
    public static volatile SingularAttribute<Client, Integer> id;
    public static volatile SingularAttribute<Client, String> nom;
    public static volatile SingularAttribute<Client, String> email;

}