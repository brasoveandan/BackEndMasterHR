package controller;

import domain.*;
import domain.dtos.request.AuthenticationRequest;
import domain.dtos.request.ResetPassword;
import domain.dtos.response.*;
import domain.validators.ResetPasswordValidator;
import domain.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import repository.*;
import controller.springSecurity.JwtUtil;
import controller.springSecurity.MyUserDetailsService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@CrossOrigin
@RestController
public class MasterHRController {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil = new JwtUtil();
    private final MyUserDetailsService userDetailsService = new MyUserDetailsService();

    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        Employee employee = employeeRepository.findOne(authenticationRequest.getUsername());
        authenticationResponse.setAdminRole(employee.getAdminRole().toString());
        authenticationResponse.setName(employee.getFirstName());
        authenticationResponse.setJwt(jwt);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody String recipientEmail, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        Employee employee = employeeRepository.findOneByEmail(recipientEmail);
        if (employee != null) {
            final UserDetails userDetails = userDetailsService.loadUserByEmail(employee.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            String link = request.getHeader("Origin") + "/reset_password?token= " + jwt;
            String fullName = employee.getLastName() + " " + employee.getFirstName();

            helper.setFrom("masterHR.contact@gmail.com", "MasterHR Support");
            helper.setTo(recipientEmail);

            String subject = "Resetare parola MasterHR";
            String content = "<p>Salut, " + fullName + "</p>"
                    + "<p>Pentru ati reseta parola, acceseaza link-ul de mai jos.</p>"
                    + "<p><a href=\"" + link + "\">Schimba parola</a></p>"
                    + "<br>"
                    + "<p>Daca nu ai initiat tu aceasta cerere, te rugam sa ignori acest email.";

            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword) throws Validator.ValidationException {
        ResetPasswordValidator validator = new ResetPasswordValidator();
        try {
            validator.validate(resetPassword);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        String usernameEmployee = jwtTokenUtil.extractUsername(resetPassword.getToken());
        Employee employee = employeeRepository.findOne(usernameEmployee);
        if (employee != null) {
            employee.setPassword(resetPassword.getPassword());
            if (employeeRepository.update(employee) == null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
