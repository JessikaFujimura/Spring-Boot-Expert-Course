package io.github.jessikafujimura.domain.repository;

import io.github.jessikafujimura.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {

    private static String INSERT = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL = "select * from cliente";
    private static String UPDATE =  "update cliente set nome = ? where id = ?";
    private static String DELETAR =  "delete from cliente where id = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    };

    public List<Cliente> obterTodosCliente(){
        return jdbcTemplate.query(SELECT_ALL, obterClientesMapper() );
    }

    public List<Cliente> buscarPorNome(String nome){
        return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ?"), new Object[] {"%" + nome +"%"}, obterClientesMapper() );
    }

    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }

    public void deletar(Cliente cliente) {
        deletarPorId(cliente.getId());
    }

    public void deletarPorId(Integer id) {
        jdbcTemplate.update(DELETAR, new Object[] {id});
    }

    private RowMapper<Cliente> obterClientesMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");

                String nome = rs.getString("nome");
                return new Cliente(id, nome);
            }
        };
    }

}
