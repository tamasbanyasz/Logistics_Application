package hu.tomi.logistic.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TransportPlan.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class TransportPlan_ {

	
	/**
	 * @see hu.tomi.logistic.model.TransportPlan#expectedIncome
	 **/
	public static volatile SingularAttribute<TransportPlan, Integer> expectedIncome;
	
	/**
	 * @see hu.tomi.logistic.model.TransportPlan#id
	 **/
	public static volatile SingularAttribute<TransportPlan, Long> id;
	
	/**
	 * @see hu.tomi.logistic.model.TransportPlan
	 **/
	public static volatile EntityType<TransportPlan> class_;
	
	/**
	 * @see hu.tomi.logistic.model.TransportPlan#sections
	 **/
	public static volatile ListAttribute<TransportPlan, Section> sections;

	public static final String EXPECTED_INCOME = "expectedIncome";
	public static final String ID = "id";
	public static final String SECTIONS = "sections";

}

