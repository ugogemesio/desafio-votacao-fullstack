package com.dbserver.ugo.votacao.assembleia;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AssembleiaMapper {

    AssembleiaResponseDTO toDTO(Assembleia entity);

    List<AssembleiaResponseDTO> toDTOList(List<Assembleia> entities);
}
