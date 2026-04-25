package br.com.fatec.catalogo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/produtos").permitAll()
                        .requestMatchers(
                                "/produtos/novo",
                                "/produtos/salvar",
                                "/produtos/editar/*",
                                "/produtos/excluir/*"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated() // restante protegido
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/produtos", true)
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/produtos"));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        // Usuário Comum (Leitura)
        UserDetails user = User.builder()
                .username("aluno")
                .password("{noop}12345") // {noop} = sem criptografia (apenas para aula)
                .roles("USER")
                .build();

        // Administrador (Escrita)
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}12345")
                .roles("ADMIN", "USER") // Herança de roles
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}