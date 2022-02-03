package io.github.jessikafujimura.api.controller;

import io.github.jessikafujimura.api.dto.ItemPedidoDTOResponse;
import io.github.jessikafujimura.api.dto.PedidoDTO;
import io.github.jessikafujimura.api.dto.PedidoDTOResponse;
import io.github.jessikafujimura.domain.entity.ItemPedido;
import io.github.jessikafujimura.domain.entity.Pedido;
import io.github.jessikafujimura.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createPedido(@RequestBody PedidoDTO pedidoDTO){
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public PedidoDTOResponse getPedidoById(@PathVariable Integer id){
        return pedidoService
                .obterPedidoDetalhado(id)
                .map( p -> {
                    return convertToPedidoDTOResponse(p);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    private PedidoDTOResponse convertToPedidoDTOResponse(Pedido pedido){
       return PedidoDTOResponse
                .builder()
                .codigo(pedido.getId())
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .total(pedido.getTotal())
                .itens(convertItemPedidoDTOResponse(pedido.getItems()))
                .build();
    }

    private List<ItemPedidoDTOResponse> convertItemPedidoDTOResponse(List<ItemPedido> itemPedido){
        if(CollectionUtils.isEmpty(itemPedido)){
            return Collections.emptyList();
        }
        return itemPedido.stream().map(
                i -> ItemPedidoDTOResponse
                            .builder()
                            .descricaoProduto(i.getProduto().getDescricao())
                            .precoUnitario(i.getProduto().getPreco())
                            .quantidade(i.getQuantidade())
                            .build()
        ).collect(Collectors.toList());
    }
}
