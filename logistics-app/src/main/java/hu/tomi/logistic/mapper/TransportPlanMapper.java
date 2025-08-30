package hu.tomi.logistic.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.tomi.logistic.dto.TransportPlanDto;
import hu.tomi.logistic.model.TransportPlan;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {
	
	@Mapping(target = "sectionIds", ignore = true)
    TransportPlanDto toDto(TransportPlan plan);
	
	@Mapping(target = "sections", ignore = true)
    TransportPlan toEntity(TransportPlanDto dto);
}


