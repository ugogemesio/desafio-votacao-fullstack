package com.dbserver.ugo.votacao.pauta;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    @Mapping(source = "assembleia.idAssembleia", target = "idAssembleia")
    PautaResponseDTO toDTO(Pauta entity);

    default Pauta toEntity(PautaCreateDTO dto) {
        if (dto == null) return null;

        Pauta pauta = new Pauta();
        pauta.setDescricaoPauta(dto.getDescricaoPauta());
        pauta.setTitulo(dto.getTitulo());
        return pauta;
    }

    default void updateEntityFromDTO(PautaUpdateDTO dto, @MappingTarget Pauta entity) {
        if (dto == null) return;

        if (dto.getDescricaoPauta() != null) {
            entity.setDescricaoPauta(dto.getDescricaoPauta());
        }

        if (dto.getTitulo() != null) {
            entity.setTitulo(dto.getTitulo());
        }
    }
}
