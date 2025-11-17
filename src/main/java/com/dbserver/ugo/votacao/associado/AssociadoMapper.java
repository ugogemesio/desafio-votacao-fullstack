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

    @Mapping(target = "id", ignore = true)
    Associado toEntity(AssociadoCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "id", ignore = true)
    void updateFromPut(AssociadoPutDTO dto, @MappingTarget Associado entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateFromPatch(AssociadoPatchDTO dto, @MappingTarget Associado entity);

}
