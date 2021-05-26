package services.springSecurity;

import domain.Employee;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.EmployeeRepository;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    public MyUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findOne(username);
        return new User(employee.getUsername(), employee.getPassword(), new ArrayList<>());
    }
}