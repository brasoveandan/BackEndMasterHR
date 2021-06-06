package bdir.masterhr.security;

import bdir.masterhr.domain.Employee;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import bdir.masterhr.repository.EmployeeRepository;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    public MyUserDetailsService() {
        //default constructor
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findOne(username);
        return new User(employee.getUsername(), employee.getPassword(), new ArrayList<>());
    }

    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findOne(username);
        return new User(employee.getUsername(), employee.getMail(), new ArrayList<>());
    }
}