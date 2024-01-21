/**
 * ----------------------------------------------------------------------------
 * Autor: Kaue de Matos
 * Empresa: Nova Software
 * Propriedade da Empresa: Todos os direitos reservados
 * ----------------------------------------------------------------------------
 */
package com.auth.auth.modules.Auth.Domain.component;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.auth.auth.modules.Auth.Domain.Enum.CustomGrantedAuthority;
import com.auth.auth.modules.Customer.Application.DTOs.registration.CustomerDTO;

@Component
public class UserRolesDeterminesComponent {
    public Set<CustomGrantedAuthority> determineUserRoles(CustomerDTO customerDTO) {
        Set<CustomGrantedAuthority> userRoles = new HashSet<>();
        userRoles.add(customerDTO.getIsAdmin() ? CustomGrantedAuthority.ADMIN : CustomGrantedAuthority.USER);
        return userRoles;
    }

    public Set<CustomGrantedAuthority> convertRolesToCustomAuthorities(Set<CustomGrantedAuthority> roles) {
        Set<CustomGrantedAuthority> authorities = new HashSet<>();
        if (roles != null) {
            for (CustomGrantedAuthority role : roles) {
                authorities.add(role);
            }
        }
        return authorities;
    }
}
