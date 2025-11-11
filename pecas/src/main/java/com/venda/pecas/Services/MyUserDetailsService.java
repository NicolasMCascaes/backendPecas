package com.venda.pecas.Services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.venda.pecas.Models.Clientes;
import com.venda.pecas.Repositories.ClientesRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final ClientesRepository clientesRepository;

    public MyUserDetailsService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Clientes user = clientesRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return User.builder()
                .username(user.getEmail())
                .password(user.getSenha())
                .roles("CLIENTE")
                .build();

    }
}
