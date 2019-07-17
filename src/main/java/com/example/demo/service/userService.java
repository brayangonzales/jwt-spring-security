package com.example.demo.service;

import com.example.demo.interfaceMapper.RoleMapper;
import com.example.demo.interfaceMapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "UserService")
public class userService implements UserDetailsService {
    @Autowired
    public UserMapper userMapper;

    @Autowired
    public RoleMapper roleMapper;

    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        User userDetalle = userMapper.getUser(usuario);
        return new org.springframework.security.core.userdetails.User(userDetalle.getUserName(),
                userDetalle.getPassword(), getAuthorities());
    }

    /*private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("USUARIO"));
    }*/

    private List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> listaRole=roleMapper.getRoles();
        for(Role rol : listaRole){
            authorities.add(new SimpleGrantedAuthority(rol.getRol()));
        }
        //role.getPrivileges().stream().map(p -> new SimpleGrantedAuthority(p.getName())).forEach(authorities::add);
        return authorities;
    }
    public User findOne(String username){
        return userMapper.getUser(username);
    }
}