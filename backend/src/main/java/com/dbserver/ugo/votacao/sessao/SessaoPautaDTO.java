package com.dbserver.ugo.votacao.sessao;

import com.dbserver.ugo.votacao.resultado.Resultado;

import java.time.LocalDateTime;

public class SessaoPautaDTO {

    private Long id;
    private LocalDateTime abertura;
    private LocalDateTime fechamento;
    private Integer duracaoMinutos;
    private Long pautaId;
    private SessaoStatus status;
    private String pautaTitulo;
    private String pautaDescricao;
    private Resultado resultado;

    public SessaoPautaDTO(Long id,
                          LocalDateTime abertura,
                          LocalDateTime fechamento,
                          Integer duracaoMinutos,
                          Long pautaId,
                          SessaoStatus status,
                          String pautaTitulo,
                          String pautaDescricao,
                          Resultado resultado) {
        this.id = id;
        this.abertura = abertura;
        this.fechamento = fechamento;
        this.duracaoMinutos = duracaoMinutos;
        this.pautaId = pautaId;
        this.status = status;
        this.pautaTitulo = pautaTitulo;
        this.pautaDescricao = pautaDescricao;
        this.resultado = resultado;
    }


    public Long getId() {
        return id;
    }

    public LocalDateTime getAbertura() {
        return abertura;
    }

    public LocalDateTime getFechamento() {
        return fechamento;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public Long getPautaId() {
        return pautaId;
    }

    public SessaoStatus getStatus() {
        return status;
    }

    public String getPautaTitulo() {
        return pautaTitulo;
    }

    public String getPautaDescricao() {
        return pautaDescricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAbertura(LocalDateTime abertura) {
        this.abertura = abertura;
    }

    public void setFechamento(LocalDateTime fechamento) {
        this.fechamento = fechamento;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public void setPautaId(Long pautaId) {
        this.pautaId = pautaId;
    }

    public void setStatus(SessaoStatus status) {
        this.status = status;
    }

    public void setPautaTitulo(String pautaTitulo) {
        this.pautaTitulo = pautaTitulo;
    }

    public void setPautaDescricao(String pautaDescricao) {
        this.pautaDescricao = pautaDescricao;
    }

    public void setResultado(Resultado resultado){
        this.resultado = resultado;
    }
    public Resultado getResultado(){
        return resultado;
    }
}
