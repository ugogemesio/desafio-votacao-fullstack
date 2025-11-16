package com.dbserver.ugo.votacao.associado;

import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AssociadoMapper {

    AssociadoResponseDTO toDTO(Associado entity);

    List<AssociadoResponseDTO> toDTOList(List<Associado> entities);

    @Mapping(target = "idAssociado", ignore = true)
    Associado toEntity(AssociadoCreateDTO dto);

    void updateEntityFromDTO(AssociadoUpdateDTO dto, @MappingTarget Associado entity);
}
