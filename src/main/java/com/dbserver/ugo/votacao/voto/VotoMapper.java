package com.dbserver.ugo.votacao.voto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VotoMapper {

    @Mapping(source = "associado.idAssociado", target = "idAssociado")
    @Mapping(source = "sessao.idSessao", target = "idSessao")
    VotoResponseDTO toDTO(Voto entity);

}
