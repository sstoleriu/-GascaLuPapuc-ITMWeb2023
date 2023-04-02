package ro.gascalupapuc.auth.rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.gascalupapuc.auth.jwt.JwtService;
import ro.gascalupapuc.auth.model.auth.AuthenticationRequest;
import ro.gascalupapuc.auth.model.auth.AuthenticationResponse;
import ro.gascalupapuc.auth.model.auth.RegisterRequest;
import ro.gascalupapuc.auth.model.user.Role;
import ro.gascalupapuc.auth.model.user.User;
import ro.gascalupapuc.auth.rest.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createLocalDateTime(LocalDateTime.now())
                .role(Role.USER)
                .phoneNumber(request.getPhoneNumber())
                .build();

       var us = userRepository.save(user);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        extraClaims.put("firstname",user.getFirstName());
        extraClaims.put("lastname",user.getLastName());
        extraClaims.put("phonenumber",user.getPhoneNumber());
        extraClaims.put("id",us.getId());

        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(); //TODO exception for user not found


        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("Authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        extraClaims.put("firstname",user.getFirstName());
        extraClaims.put("lastname",user.getLastName());
        extraClaims.put("phonenumber",user.getPhoneNumber());

        var us = userRepository.save(user);
        extraClaims.put("id",us.getId());

        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
