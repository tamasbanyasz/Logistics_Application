package hu.tomi.logistic.mapper;

import hu.tomi.logistic.model.Milestone;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.tomi.logistic.dto.MilestoneDto;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {

    @Mapping(source = "address.id", target = "addressId")
    MilestoneDto toDto(Milestone milestone);

    @Mapping(target = "address", ignore = true) 
    Milestone toEntity(MilestoneDto dto);
}