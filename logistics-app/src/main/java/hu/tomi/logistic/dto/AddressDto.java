package hu.tomi.logistic.dto;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import hu.tomi.logistic.model.Address;
import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {
	
	@Null(message = "ID must be null when creating new address")
    private Long id;
	
	@NotBlank
    private String countryCode;
	
	@NotBlank
    private String city;
	
	@NotBlank
    private String street;
	
	@NotBlank
    private String zipCode;
	
	@NotBlank
    private String houseNumber;
	
    private Double latitude;
    private Double longitude;
    
    public AddressDto() {
    }
    
    public AddressDto(Address address) {
        this.id = address.getId();
        this.countryCode = address.getCountryCode();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
        this.street = address.getStreet();
        this.houseNumber = address.getHouseNumber();
    }
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    @JsonIgnore // According the Java Bean rules it's recognize as a JSON field. So it put into the response
    public boolean isEmpty() {
        boolean allStringsEmpty = Stream.of(countryCode, city, zipCode, street, houseNumber)
            .allMatch(s -> s == null || s.trim().isEmpty());
        boolean allNumbersNull = latitude == null && longitude == null;
        return allStringsEmpty && allNumbersNull;
    }
}

