package hu.tomi.logistic.mapper;

import hu.tomi.logistic.model.Address;

import org.mapstruct.Mapper;

import hu.tomi.logistic.dto.AddressDto;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toDto(Address address);

    Address toEntity(AddressDto dto);
}
