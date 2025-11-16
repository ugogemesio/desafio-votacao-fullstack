package com.dbserver.ugo.votacao.sessao;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SessaoMapper {

    @Mapping(source = "pauta.idPauta", target = "idPauta")
    SessaoResponseDTO toDTO(Sessao entity);

    default Sessao toEntity(SessaoCreateDTO dto) {
        if (dto == null) return null;

        Sessao sessao = new Sessao();
        sessao.setAssunto(dto.getAssunto());
        return sessao;
    }

    default void updateEntityFromDTO(SessaoUpdateDTO dto, @MappingTarget Sessao entity) {
        if (dto == null) return;

        if (dto.getAssunto() != null) {
            entity.setAssunto(dto.getAssunto());
        }
    }
}
