package com.bezkoder.springjwt.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "carrinhoCompras")
public class CarrinhoCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "idCliente")
    private Long idCliente;

    @Column(name = "idProduto")
    private Long idProduto;

    @Column(name = "quantidade")
    private String quantidade;

    @Column(name = "peso")
    private Integer peso;

    @Column(name = "diametro")
    private Float diametro;

    @Column(name = "data_criacao")
    private Date data_criacao = new Date();

    @Column(name = "status")
    private String status = "Aguardando Pagamento";

    public CarrinhoCompras(){

    }

    public CarrinhoCompras(Long idCliente, Long idProduto, String quantidade) {
        this.idCliente = idCliente;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Float getDiametro() {
        return diametro;
    }

    public void setDiametro(Float diametro) {
        this.diametro = diametro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
