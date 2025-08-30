package hu.tomi.logistic.mapper;

import hu.tomi.logistic.dto.MilestoneDto;
import hu.tomi.logistic.model.Address;
import hu.tomi.logistic.model.Milestone;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-30T18:19:54+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class MilestoneMapperImpl implements MilestoneMapper {

    @Override
    public MilestoneDto toDto(Milestone milestone) {
        if ( milestone == null ) {
            return null;
        }

        MilestoneDto milestoneDto = new MilestoneDto();

        milestoneDto.setAddressId( milestoneAddressId( milestone ) );
        milestoneDto.setId( milestone.getId() );
        milestoneDto.setPlannedTime( milestone.getPlannedTime() );

        return milestoneDto;
    }

    @Override
    public Milestone toEntity(MilestoneDto dto) {
        if ( dto == null ) {
            return null;
        }

        Milestone milestone = new Milestone();

        milestone.setId( dto.getId() );
        milestone.setPlannedTime( dto.getPlannedTime() );

        return milestone;
    }

    private Long milestoneAddressId(Milestone milestone) {
        if ( milestone == null ) {
            return null;
        }
        Address address = milestone.getAddress();
        if ( address == null ) {
            return null;
        }
        Long id = address.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
