package hu.tomi.logistic.mapper;

import hu.tomi.logistic.model.Section;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.tomi.logistic.dto.SectionDto;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(source = "fromMilestone.id", target = "fromMilestoneId")
    @Mapping(source = "toMilestone.id", target = "toMilestoneId")
    @Mapping(source = "transportPlan.id", target = "transportPlanId")
    SectionDto toDto(Section section);

    @Mapping(target = "fromMilestone", ignore = true)
    @Mapping(target = "toMilestone", ignore = true)
    @Mapping(target = "transportPlan", ignore = true)
    Section toEntity(SectionDto dto);
}

