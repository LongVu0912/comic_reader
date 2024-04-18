package com.api.comic_reader.services;

import com.api.comic_reader.dtos.requests.IntrospectRequest;
import com.api.comic_reader.dtos.requests.LoginRequest;
import com.api.comic_reader.dtos.responses.IntrospectResponse;
import com.api.comic_reader.dtos.responses.LoginResponse;
import com.api.comic_reader.entities.ComicUserEntity;
import com.api.comic_reader.entities.InvalidatedTokenEntity;
import com.api.comic_reader.repositories.ComicUserRepository;
import com.api.comic_reader.repositories.InvalidatedTokenRepository;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final ComicUserRepository comicUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = null;
        try {
            Optional<ComicUserEntity> comicUserOptional = comicUserRepository.findByEmail(loginRequest.getEmail());
            if (comicUserOptional.isPresent()) {
                ComicUserEntity comicUser = comicUserOptional.get();
                if (passwordEncoder.matches(loginRequest.getPassword(), comicUser.getPassword())) {
                    String jwtToken = "";
                    jwtToken = jwtService.generateToken(comicUser);
                    loginResponse = LoginResponse.builder()
                            .id(comicUser.getId())
                            .token(jwtToken)
                            .authenticated(true)
                            .build();
                }
            }
        } catch (Exception e) {
            throw new Exception("Can not login, please try again!");
        }
        if (loginResponse == null) {
            throw new Exception("Email or password is incorrect, please try again!");
        }
        return loginResponse;
    }

    public void logout(String token) throws Exception {
        var signedToken = jwtService.verifyToken(token);

        String jit = signedToken.getJWTClaimsSet().getJWTID();

        Date expirationTime = signedToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedTokenEntity invalidatedToken = InvalidatedTokenEntity.builder()
                .id(jit)
                .expirationTime(expirationTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws Exception {
        var token = request.getToken();

        if (token == null) {
            throw new Exception("Token is required");
        }

        return jwtService.introspect(token);
    }
}
