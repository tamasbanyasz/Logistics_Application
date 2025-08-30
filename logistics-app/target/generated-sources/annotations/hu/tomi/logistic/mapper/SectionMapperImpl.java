package hu.tomi.logistic.mapper;

import hu.tomi.logistic.dto.SectionDto;
import hu.tomi.logistic.model.Milestone;
import hu.tomi.logistic.model.Section;
import hu.tomi.logistic.model.TransportPlan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-30T18:19:54+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class SectionMapperImpl implements SectionMapper {

    @Override
    public SectionDto toDto(Section section) {
        if ( section == null ) {
            return null;
        }

        SectionDto sectionDto = new SectionDto();

        sectionDto.setFromMilestoneId( sectionFromMilestoneId( section ) );
        sectionDto.setToMilestoneId( sectionToMilestoneId( section ) );
        sectionDto.setTransportPlanId( sectionTransportPlanId( section ) );
        sectionDto.setId( section.getId() );
        sectionDto.setNumber( section.getNumber() );

        return sectionDto;
    }

    @Override
    public Section toEntity(SectionDto dto) {
        if ( dto == null ) {
            return null;
        }

        Section section = new Section();

        section.setId( dto.getId() );
        section.setNumber( dto.getNumber() );

        return section;
    }

    private Long sectionFromMilestoneId(Section section) {
        if ( section == null ) {
            return null;
        }
        Milestone fromMilestone = section.getFromMilestone();
        if ( fromMilestone == null ) {
            return null;
        }
        Long id = fromMilestone.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long sectionToMilestoneId(Section section) {
        if ( section == null ) {
            return null;
        }
        Milestone toMilestone = section.getToMilestone();
        if ( toMilestone == null ) {
            return null;
        }
        Long id = toMilestone.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long sectionTransportPlanId(Section section) {
        if ( section == null ) {
            return null;
        }
        TransportPlan transportPlan = section.getTransportPlan();
        if ( transportPlan == null ) {
            return null;
        }
        Long id = transportPlan.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
