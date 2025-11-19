package com.dbserver.ugo.votacao.voto;

import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VotoMapper {

    @Mapping(source = "associado.id", target = "idAssociado")
    @Mapping(source = "sessao.id", target = "idSessao")
    VotoResponseDTO toDTO(Voto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "associado", ignore = true)
    @Mapping(target = "sessao", ignore = true)
    @Mapping(target = "opcao", ignore = true)
    Voto toEntity(VotoCreateDTO dto);
}
