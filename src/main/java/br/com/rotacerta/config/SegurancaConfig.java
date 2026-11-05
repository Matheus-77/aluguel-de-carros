package br.com.rotacerta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import br.com.rotacerta.repository.ContaAcessoRepository;

@Configuration
public class SegurancaConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(ContaAcessoRepository contas) {
        return login -> contas.findByLogin(login)
                .map(conta -> User.withUsername(conta.getLogin())
                        .password(conta.getSenha())
                        .roles(conta.getPerfil())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(login));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(autorizacao -> autorizacao
                        .requestMatchers(HttpMethod.POST, "/clientes").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clientes", "/clientes/**").hasRole("AGENTE")
                        .requestMatchers(HttpMethod.POST, "/pedidos", "/pedidos/" + "*" + "/cancelar").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/pedidos/" + "*" + "/avaliar").hasRole("AGENTE")
                        .anyRequest().authenticated())
                .formLogin(formulario -> formulario
                        .successHandler((request, response, auth) -> response.setStatus(HttpStatus.OK.value()))
                        .failureHandler((request, response, erro) -> response.setStatus(HttpStatus.UNAUTHORIZED.value())))
                .logout(logout -> logout
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
                .exceptionHandling(excecoes -> excecoes
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .csrf(csrf -> csrf.spa());
        return http.build();
    }
}