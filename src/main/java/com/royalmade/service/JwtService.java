package com.royalmade.service;


import com.royalmade.entity.*;
import com.royalmade.repo.AdminRepository;
import com.royalmade.repo.AppUserRepository;
import com.royalmade.repo.EmployeeRepository;
import com.royalmade.repo.SupervisorRepository;
import com.royalmade.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;


@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtHelper jwtUtil;

    @Autowired
    private EmployeeRepository employeeDao;

    @Autowired
    private AdminRepository adminDao;

    @Autowired
    private SupervisorRepository supervisorDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserRepository appUserDao;
    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String email = jwtRequest.getEmail();
        String password = jwtRequest.getPassword();


        // Authenticate the user
        authenticate(email, password);

        // Load user details and generate JWT token
        UserDetails userDetails = loadUserByUsername(email);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        // Check if the email belongs to Admin, Employee, or Supervisor
        Admin admin = adminDao.findByEmail(email);
        Employee employee = employeeDao.findByEmail(email);
        Supervisor supervisor = supervisorDao.findByEmail(email);
        AppUser appUser=appUserDao.findByEmail(email);

        if (admin != null) {
            return new JwtResponse(null, null, null, admin, newGeneratedToken);
        } else if (employee != null) {
            return new JwtResponse(employee, null, null, null, newGeneratedToken);
        } else if (supervisor != null) {
            return new JwtResponse(null, null, supervisor, null, newGeneratedToken);
        } else if (appUser != null) {
            return new JwtResponse(null, appUser, null, null, newGeneratedToken);
        } else {
            throw new Exception("User not found with email: " + email);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        Admin admin = adminDao.findByEmail(email);
        Employee employee = employeeDao.findByEmail(email);
        Supervisor supervisor = supervisorDao.findByEmail(email);
        AppUser appUser=appUserDao.findByEmail(email);

        if (admin == null && employee == null && supervisor == null && appUser==null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        Collection<GrantedAuthority> authorities = new HashSet<>();
        String password = null;

        // Add roles and password for Admin
        if (admin != null) {
            admin.getRole().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
            );
            password = admin.getPassword();
        }

        // Add roles and password for Employee
        if (employee != null) {
            employee.getRole().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
            );
            password = employee.getPassword();
        }

        // Add roles and password for Supervisor
        if (supervisor != null) {
            supervisor.getRole().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
            );
            password = supervisor.getPassword();
        }

        if (appUser != null) {
            appUser.getRole().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
            );
            password = appUser.getPassword();
        }

        return new org.springframework.security.core.userdetails.User(email, password, authorities);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
