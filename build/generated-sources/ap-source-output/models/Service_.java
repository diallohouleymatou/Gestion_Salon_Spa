package models;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-07-18T12:35:07", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Service.class)
public class Service_ { 

    public static volatile SingularAttribute<Service, BigDecimal> prix;
    public static volatile SingularAttribute<Service, String> description;
    public static volatile SingularAttribute<Service, Integer> id;
    public static volatile SingularAttribute<Service, String> nom;

}