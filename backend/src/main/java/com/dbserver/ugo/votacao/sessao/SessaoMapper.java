package com.dbserver.ugo.votacao.sessao;

import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SessaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    Sessao toEntity(SessaoCreateDTO dto);

    @Mapping(target = "pautaId", source = "pauta.id")
    SessaoResponseDTO toDTO(Sessao entity);
}
