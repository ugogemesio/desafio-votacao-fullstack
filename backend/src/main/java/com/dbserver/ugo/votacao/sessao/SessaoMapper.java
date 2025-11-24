package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.pauta.Pauta;
import com.dbserver.ugo.votacao.pauta.PautaPatchDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SessaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    Sessao toEntity(SessaoCreateDTO dto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateFromPatch(SessaoPatchDTO dto, @MappingTarget Sessao entity);

    @Mapping(target = "pautaId", source = "pauta.id")
    SessaoResponseDTO toDTO(Sessao entity);
}
