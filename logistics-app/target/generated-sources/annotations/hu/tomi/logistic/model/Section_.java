package hu.tomi.logistic.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Section.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Section_ {

	
	/**
	 * @see hu.tomi.logistic.model.Section#number
	 **/
	public static volatile SingularAttribute<Section, Integer> number;
	
	/**
	 * @see hu.tomi.logistic.model.Section#fromMilestone
	 **/
	public static volatile SingularAttribute<Section, Milestone> fromMilestone;
	
	/**
	 * @see hu.tomi.logistic.model.Section#transportPlan
	 **/
	public static volatile SingularAttribute<Section, TransportPlan> transportPlan;
	
	/**
	 * @see hu.tomi.logistic.model.Section#toMilestone
	 **/
	public static volatile SingularAttribute<Section, Milestone> toMilestone;
	
	/**
	 * @see hu.tomi.logistic.model.Section#id
	 **/
	public static volatile SingularAttribute<Section, Long> id;
	
	/**
	 * @see hu.tomi.logistic.model.Section
	 **/
	public static volatile EntityType<Section> class_;

	public static final String NUMBER = "number";
	public static final String FROM_MILESTONE = "fromMilestone";
	public static final String TRANSPORT_PLAN = "transportPlan";
	public static final String TO_MILESTONE = "toMilestone";
	public static final String ID = "id";

}

