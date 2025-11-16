package com.dbserver.ugo.votacao.assembleia;

import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AssembleiaMapper {

    AssembleiaResponseDTO toDTO(Assembleia entity);

    List<AssembleiaResponseDTO> toDTOList(List<Assembleia> entities);

    @Mapping(target = "idAssembleia", ignore = true)
    @Mapping(target = "pautas", expression = "java(new java.util.ArrayList<>())")
    Assembleia toEntity(AssembleiaCreateDTO dto);

    void updateEntityFromDTO(AssembleiaUpdateDTO dto, @MappingTarget Assembleia entity);
}
//
//> Task :compileJava
///home/ugogemesio/votacao/src/main/java/com/dbserver/ugo/votacao/assembleia/AssembleiaMapper.java:12: warning: Unmapped target property: "idAssembleia". Mapping from Collection element "Pauta pautas" to "PautaResponseDTO pautas".
//AssembleiaResponseDTO toDTO(Assembleia entity);
//                          ^
//                                  /home/ugogemesio/votacao/src/main/java/com/dbserver/ugo/votacao/assembleia/AssembleiaMapper.java:20: warning: Unmapped target properties: "idAssembleia, pautas".
//void updateEntityFromDTO(AssembleiaUpdateDTO dto, @MappingTarget Assembleia entity);
//         ^