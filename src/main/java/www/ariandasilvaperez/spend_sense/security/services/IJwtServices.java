package www.ariandasilvaperez.spend_sense.security.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

public interface IJwtServices {
    String generateJWT(String email) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException;
    JWTClaimsSet parseJWT(String jwt) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, ParseException, JOSEException, ParseException;
}
