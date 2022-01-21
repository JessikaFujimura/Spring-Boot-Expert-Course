package io.github.jessikafujimura;

import io.github.jessikafujimura.domain.entity.Cliente;
import io.github.jessikafujimura.domain.entity.Pedido;
import io.github.jessikafujimura.domain.repository.Clientes;
import io.github.jessikafujimura.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class SpringBootExpertCourseApplication {

    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes,
                                   @Autowired Pedidos pedidos){
        return args -> {
            Cliente cliente = new Cliente("Jéssika");
            clientes.save(cliente);


            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setDataPedido(LocalDate.now());
            pedido.setTotal(BigDecimal.valueOf(100));
            pedidos.save(pedido);

//            Cliente cli = clientes.findClienteFetchPedido(cliente.getId());
//            System.out.println(cli);
//            System.out.println(cli.getPedidos());

            pedidos.findByCliente(cliente).forEach(System.out::println);

        };
    }

    public static void main(String[] args) {
      SpringApplication.run(SpringBootExpertCourseApplication.class, args);
    }
}
