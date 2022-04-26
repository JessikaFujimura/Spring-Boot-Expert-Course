package io.github.jessikafujimura.service.impl;

import io.github.jessikafujimura.domain.entity.Usuario;
import io.github.jessikafujimura.domain.repository.Usuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private final Usuarios usuariorepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuariorepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[] {"USER"};

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario){
        return usuariorepository.save(usuario);
    }
}
