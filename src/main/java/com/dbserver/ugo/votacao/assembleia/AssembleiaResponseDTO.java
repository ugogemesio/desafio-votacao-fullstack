package com.dbserver.ugo.votacao.assembleia;

import com.dbserver.ugo.votacao.pauta.PautaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssembleiaResponseDTO {

    private Long idAssembleia;
    private String nomeAssembleia;
    private String descricaoAssembleia;
    private LocalDateTime dataRealizacaoAssembleia;
    private List<PautaResponseDTO> pautas;
}
