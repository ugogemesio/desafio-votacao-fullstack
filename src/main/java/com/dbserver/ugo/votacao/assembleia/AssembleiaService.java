package com.dbserver.ugo.votacao.assembleia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssembleiaService {
    @Autowired
    private AssembleiaRepository assembleiaRepository;
    public List<Assembleia> findByNomeAssembleia(String nomeAssembleia){
        return assembleiaRepository.findByNomeAssembleia(nomeAssembleia);
    }

}
