package hu.tomi.logistic.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Address.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Address_ {

	
	/**
	 * @see hu.tomi.logistic.model.Address#zipCode
	 **/
	public static volatile SingularAttribute<Address, String> zipCode;
	
	/**
	 * @see hu.tomi.logistic.model.Address#city
	 **/
	public static volatile SingularAttribute<Address, String> city;
	
	/**
	 * @see hu.tomi.logistic.model.Address#countryCode
	 **/
	public static volatile SingularAttribute<Address, String> countryCode;
	
	/**
	 * @see hu.tomi.logistic.model.Address#street
	 **/
	public static volatile SingularAttribute<Address, String> street;
	
	/**
	 * @see hu.tomi.logistic.model.Address#latitude
	 **/
	public static volatile SingularAttribute<Address, Double> latitude;
	
	/**
	 * @see hu.tomi.logistic.model.Address#houseNumber
	 **/
	public static volatile SingularAttribute<Address, String> houseNumber;
	
	/**
	 * @see hu.tomi.logistic.model.Address#id
	 **/
	public static volatile SingularAttribute<Address, Long> id;
	
	/**
	 * @see hu.tomi.logistic.model.Address
	 **/
	public static volatile EntityType<Address> class_;
	
	/**
	 * @see hu.tomi.logistic.model.Address#longitude
	 **/
	public static volatile SingularAttribute<Address, Double> longitude;

	public static final String ZIP_CODE = "zipCode";
	public static final String CITY = "city";
	public static final String COUNTRY_CODE = "countryCode";
	public static final String STREET = "street";
	public static final String LATITUDE = "latitude";
	public static final String HOUSE_NUMBER = "houseNumber";
	public static final String ID = "id";
	public static final String LONGITUDE = "longitude";

}

