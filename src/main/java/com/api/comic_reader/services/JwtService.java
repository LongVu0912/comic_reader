package com.api.comic_reader.services;

import com.api.comic_reader.dtos.responses.IntrospectResponse;
import com.api.comic_reader.entities.ComicUserEntity;
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

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private String SIGNER_KEY = "Cf3X07omDRzLIp2hYuvrBmZ5vGlIcge12VEllyTdD1Q";

    public String generateToken(ComicUserEntity comicUser) {
        
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(comicUser.getEmail())
                .issueTime(new Date())
                .claim("scope", comicUser.getRole().name())
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
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

    public IntrospectResponse introspect(String token) throws Exception {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .isValid(verified && expirationTime.after(new Date()))
                .build();
    }

}