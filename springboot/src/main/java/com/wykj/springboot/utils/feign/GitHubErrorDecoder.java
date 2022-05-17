package com.wykj.springboot.utils.feign;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GitHubErrorDecoder implements ErrorDecoder {

    final Decoder decoder;
    final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    GitHubErrorDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String errMsg = String.format("reason:%s", response.reason()) + String.format(",body:%s", response.body());
            response = response.toBuilder().status(200).body(errMsg.getBytes(StandardCharsets.UTF_8)).build();
            return (Exception) decoder.decode(response, GitHubClientError.class);
        } catch (final IOException fallbackToDefault) {
            return defaultDecoder.decode(methodKey, response);
        }
    }

    static class GitHubClientError extends RuntimeException {

        private String message; // parsed from json

        @Override
        public String getMessage() {
            return message;
        }
    }
}