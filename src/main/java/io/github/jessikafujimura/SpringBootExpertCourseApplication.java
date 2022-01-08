package io.github.jessikafujimura;

import io.github.jessikafujimura.domain.entity.Cliente;
import io.github.jessikafujimura.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringBootExpertCourseApplication {

    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes){
        return args -> {
            Cliente cliente = new Cliente("Jéssika");
            clientes.salvar(cliente);

            Cliente cliente2 = new Cliente("Yami");
            clientes.salvar(cliente2);

            List<Cliente> todos = clientes.obterTodosCliente();
            todos.forEach(System.out::println);

            todos.forEach(c -> {
                c.setNome(c.getNome() + " atualizado");
                clientes.atualizar(c);
            }
            );

            clientes.buscarPorNome("mi").forEach(System.out::println);

            if(todos.isEmpty()){
                System.out.println("Nenhum cliente encontrado");
            } else {
                todos.forEach(i -> {
                    clientes.deletar(i);
                });
            }

            todos.forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
      SpringApplication.run(SpringBootExpertCourseApplication.class, args);
    }
}
