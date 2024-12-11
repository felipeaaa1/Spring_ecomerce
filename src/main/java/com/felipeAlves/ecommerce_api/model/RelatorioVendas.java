package com.felipeAlves.ecommerce_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "relatorio_vendas") // Mapeando a view
public class RelatorioVendas {

    @Id
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "periodo")
    private LocalDate periodo;

    @Column(name = "total_venda")
    private BigDecimal totalVenda;

    @Column(name = "produtos_vendidos")
    private Integer produtosVendidos;

    @Column(name = "status")
    private String status;

    @Column(name = "id_produto")
    private Long idProduto;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "quantidade_vendida")
    private Integer quantidadeVendida;

    @Column(name = "faturamento_por_item")
    private BigDecimal faturamentoPorItem;
}
