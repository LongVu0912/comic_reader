package com.api.comic_reader.services;

import com.api.comic_reader.config.EnvironmentVariable;
import com.api.comic_reader.dtos.responses.IntrospectResponse;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.repositories.InvalidatedTokenRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {
    private final String SIGNER_KEY = EnvironmentVariable.jwtSignerKey;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public String generateToken(UserEntity comicUser) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(comicUser.getUsername())
                .issueTime(new Date())
                .claim("scope", comicUser.getRole().name())
                .expirationTime(new Date(new Date().getTime() + 5 * 60 * 1000))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(String token) throws AppException {
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public SignedJWT verifyToken(String token) throws Exception {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expirationTime.after(new Date())))
            throw new Exception();

        String jit = signedJWT.getJWTClaimsSet().getJWTID();

        if (invalidatedTokenRepository.existsById(jit))
            throw new Exception();

        return signedJWT;
    }
}