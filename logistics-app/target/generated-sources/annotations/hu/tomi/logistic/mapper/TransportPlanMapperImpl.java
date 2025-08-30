package hu.tomi.logistic.mapper;

import hu.tomi.logistic.dto.TransportPlanDto;
import hu.tomi.logistic.model.TransportPlan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-30T18:19:54+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class TransportPlanMapperImpl implements TransportPlanMapper {

    @Override
    public TransportPlanDto toDto(TransportPlan plan) {
        if ( plan == null ) {
            return null;
        }

        TransportPlanDto transportPlanDto = new TransportPlanDto();

        transportPlanDto.setId( plan.getId() );
        transportPlanDto.setExpectedIncome( plan.getExpectedIncome() );

        return transportPlanDto;
    }

    @Override
    public TransportPlan toEntity(TransportPlanDto dto) {
        if ( dto == null ) {
            return null;
        }

        TransportPlan transportPlan = new TransportPlan();

        transportPlan.setId( dto.getId() );
        transportPlan.setExpectedIncome( dto.getExpectedIncome() );

        return transportPlan;
    }
}
