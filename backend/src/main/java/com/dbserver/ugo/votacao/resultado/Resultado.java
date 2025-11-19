package com.dbserver.ugo.votacao.resultado;

import jakarta.persistence.*;

@Embeddable
public class Resultado {

    @Column(name = "sim")
    private long sim;

    @Column(name = "nao")
    private long nao;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultado_status")
    private ResultadoStatus status;

    public Resultado() {
        this(0, 0);
    }

    public Resultado(long sim, long nao) {
        this.sim = sim;
        this.nao = nao;
        this.status = calcularStatus(sim, nao);
    }

    private ResultadoStatus calcularStatus(long sim, long nao) {
        if (sim > nao) {
            return ResultadoStatus.APROVADO;
        } else if (nao > sim) {
            return ResultadoStatus.REJEITADO;
        } else {
            return ResultadoStatus.EMPATE;
        }
    }

    public long getSim() { return sim; }
    public void setSim(long sim) {
        this.sim = sim;
        this.status = calcularStatus(this.sim, this.nao);
    }

    public long getNao() { return nao; }
    public void setNao(long nao) {
        this.nao = nao;
        this.status = calcularStatus(this.sim, this.nao);
    }

    public ResultadoStatus getStatus() { return status; }
    public void setStatus(ResultadoStatus status) { this.status = status; }
}