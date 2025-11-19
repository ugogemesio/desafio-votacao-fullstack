package com.dbserver.ugo.votacao.pauta;

import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PautaMapper {

    PautaResponseDTO toDTO(Pauta entity);

    List<PautaResponseDTO> toDTOList(List<Pauta> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sessoes", ignore = true) 
    Pauta toEntity(PautaCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sessoes", ignore = true)
    void updateFromPatch(PautaPatchDTO dto, @MappingTarget Pauta entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sessoes", ignore = true)
    void updateFromPut(PautaPutDTO dto, @MappingTarget Pauta entity);
}
