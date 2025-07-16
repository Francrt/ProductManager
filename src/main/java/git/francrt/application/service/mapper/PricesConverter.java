package git.francrt.application.service.mapper;

import git.francrt.domain.model.Prices;
import org.mapstruct.Mapper;
import org.openapitools.model.PricesDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PricesConverter {

    PricesDTO toDTO(Prices prices);
    List<PricesDTO> toDTOList(List<Prices> pricesList);
}
