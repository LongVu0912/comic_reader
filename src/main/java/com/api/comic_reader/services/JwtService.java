package com.api.comic_reader.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.comic_reader.dtos.responses.IntrospectResponse;
import com.api.comic_reader.entities.UserEntity;
import com.api.comic_reader.exception.AppException;
import com.api.comic_reader.exception.ErrorCode;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.signer-key}")
    private String SIGNER_KEY;

    @Value("${jwt.expiration}")
    private Long JWT_EXPIRATION;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    // This method generates a JWT token for a given user.
    // The token includes the user's username and role, and is signed with the SIGNER_KEY.
    // The token's expiration time is set to the current time plus JWT_EXPIRATION.
    public String generateToken(UserEntity user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(user.getUsername())
                .issueTime(new Date())
                .claim("scope", user.getRole().name())
                .expirationTime(new Date(new Date().getTime() + JWT_EXPIRATION))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    // This method checks if a given token is valid.
    // It returns an IntrospectResponse object that contains a boolean indicating whether the token is valid.
    public IntrospectResponse introspect(String token) throws AppException {
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    // This method verifies a given token.
    // It checks if the token is correctly signed, if it has not expired, and if it has not been invalidated.
    // If the token is valid, it returns the token as a SignedJWT object.
    // If the token is not valid, it throws an AppException with the error code INVALID_TOKEN.
    public SignedJWT verifyToken(String token) throws AppException {
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = signedJWT.verify(verifier);

            if (!(verified && expirationTime.after(new Date()))) throw new AppException(ErrorCode.INVALID_TOKEN);

            String jit = signedJWT.getJWTClaimsSet().getJWTID();

            if (invalidatedTokenRepository.existsById(jit)) throw new AppException(ErrorCode.INVALID_TOKEN);

            return signedJWT;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
