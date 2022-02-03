package io.github.jessikafujimura.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoDTOResponse {

    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;

}
