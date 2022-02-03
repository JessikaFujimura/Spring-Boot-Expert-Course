package io.github.jessikafujimura.service.impl;

import io.github.jessikafujimura.api.dto.ItemPedidoDTO;
import io.github.jessikafujimura.api.dto.PedidoDTO;
import io.github.jessikafujimura.domain.entity.Cliente;
import io.github.jessikafujimura.domain.entity.ItemPedido;
import io.github.jessikafujimura.domain.entity.Pedido;
import io.github.jessikafujimura.domain.entity.Produto;
import io.github.jessikafujimura.domain.repository.Clientes;
import io.github.jessikafujimura.domain.repository.ItensPedido;
import io.github.jessikafujimura.domain.repository.Pedidos;
import io.github.jessikafujimura.domain.repository.Produtos;
import io.github.jessikafujimura.exception.RegraNegocioException;
import io.github.jessikafujimura.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidos;
    private final Clientes clientes;
    private final Produtos produtos;
    private final ItensPedido itensPedido;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());

        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clientes.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));
        pedido.setCliente(cliente);

        List<ItemPedido> itemPedidos = converterItens(pedido, pedidoDTO.getItens());
        pedidos.save(pedido);
        itensPedido.saveAll(itemPedidos);
        pedido.setItems(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoDetalhado(Integer id) {
       return pedidos.findByIdFetchItems(id);

    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possivel realizar pedido sem itens.");
        }
        return  itens.stream().map( dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtos.findById(idProduto).orElseThrow(() -> new RegraNegocioException("Código do produto inválido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
