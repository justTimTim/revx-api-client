package com.revx.api.core;

import com.revx.api.exception.ApiInitializationException;
import com.revx.api.exception.ApiRevxException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RevxSigningInterceptor implements Interceptor {

    private final String apiKey;
    private final PrivateKey privateKey;

    public RevxSigningInterceptor(String apiKey, String secretPem) {
        this.apiKey = apiKey;
        try {
            this.privateKey = loadSecretToPrivateKey(secretPem);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new ApiInitializationException(e.getMessage(), e);
        }
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        String timestamp = String.valueOf(System.currentTimeMillis());
        String method = original.method();
        String path = original.url().encodedPath();

        String body = "";
        if (original.body() != null) {
            Buffer buffer = new Buffer();
            original.body().writeTo(buffer);
            body = buffer.readUtf8();
        }

        String message = timestamp + method + path + body;
        String signature = sign(message);

        Request signed = original.newBuilder()
                .addHeader("X-Revx-API-Key", apiKey)
                .addHeader("X-Revx-Timestamp", timestamp)
                .addHeader("X-Revx-Signature", signature)
                .build();

        return chain.proceed(signed);
    }

    private String sign(String message) {
        try {
            Signature sig = Signature.getInstance("Ed25519");
            sig.initSign(privateKey);
            sig.update(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(sig.sign());
        } catch (Exception e) {
            throw new ApiRevxException("Signing failed", null, System.currentTimeMillis(), e);
        }
    }

    private PrivateKey loadSecretToPrivateKey(String secret) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(secret);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("Ed25519");

        return kf.generatePrivate(spec);
    }
}

