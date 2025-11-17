package com.dbserver.ugo.votacao.voto;

import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VotoMapper {


    @Mapping(source = "associado.id", target = "idAssociado")
    @Mapping(source = "sessao.id", target = "idSessao")
    VotoResponseDTO toDTO(Voto entity);

    List<VotoResponseDTO> toDTOList(List<Voto> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "associado", ignore = true)
    @Mapping(target = "sessao", ignore = true)
    Voto toEntity(VotoCreateDTO dto);


}
