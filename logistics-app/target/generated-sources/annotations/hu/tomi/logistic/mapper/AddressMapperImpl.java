package hu.tomi.logistic.mapper;

import hu.tomi.logistic.dto.AddressDto;
import hu.tomi.logistic.model.Address;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-30T18:19:54+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressDto toDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDto addressDto = new AddressDto();

        addressDto.setId( address.getId() );
        addressDto.setCountryCode( address.getCountryCode() );
        addressDto.setCity( address.getCity() );
        addressDto.setStreet( address.getStreet() );
        addressDto.setZipCode( address.getZipCode() );
        addressDto.setHouseNumber( address.getHouseNumber() );
        addressDto.setLatitude( address.getLatitude() );
        addressDto.setLongitude( address.getLongitude() );

        return addressDto;
    }

    @Override
    public Address toEntity(AddressDto dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.setId( dto.getId() );
        address.setCountryCode( dto.getCountryCode() );
        address.setCity( dto.getCity() );
        address.setStreet( dto.getStreet() );
        address.setZipCode( dto.getZipCode() );
        address.setHouseNumber( dto.getHouseNumber() );
        address.setLatitude( dto.getLatitude() );
        address.setLongitude( dto.getLongitude() );

        return address;
    }
}
