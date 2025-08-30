package hu.tomi.logistic.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Milestone.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Milestone_ {

	
	/**
	 * @see hu.tomi.logistic.model.Milestone#address
	 **/
	public static volatile SingularAttribute<Milestone, Address> address;
	
	/**
	 * @see hu.tomi.logistic.model.Milestone#plannedTime
	 **/
	public static volatile SingularAttribute<Milestone, LocalDateTime> plannedTime;
	
	/**
	 * @see hu.tomi.logistic.model.Milestone#id
	 **/
	public static volatile SingularAttribute<Milestone, Long> id;
	
	/**
	 * @see hu.tomi.logistic.model.Milestone
	 **/
	public static volatile EntityType<Milestone> class_;

	public static final String ADDRESS = "address";
	public static final String PLANNED_TIME = "plannedTime";
	public static final String ID = "id";

}

