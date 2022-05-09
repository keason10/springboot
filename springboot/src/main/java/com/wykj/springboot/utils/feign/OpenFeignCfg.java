package com.wykj.springboot.utils.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wykj.springboot.utils.trace.TraceContextHolder;
import com.wykj.springboot.utils.trace.TraceHttpHeader;
import feign.AsyncFeign;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * open-feign 文档
 * @link {https://github.com/OpenFeign/feign#async-execution-via-completablefuture}
 */
public class OpenFeignCfg {

    @Bean
    public Feign.Builder feignBuilder(ObjectMapper objectMapper) {
        final Decoder decoder = new JacksonDecoder(objectMapper);
        final Encoder encoder = new JacksonEncoder(objectMapper);
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .errorDecoder(new GitHubErrorDecoder(decoder))
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.BASIC)
                .requestInterceptor(template -> {
                    template.header(TraceHttpHeader.HTTP_HEADER_TRACE_ID.getCode(),
                            TraceContextHolder.getContext().getTraceId());
                    template.header("Content-Type", "application/json");
                }).options(new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true));

    }


    @Bean
    public AsyncFeign.AsyncBuilder feignSyncBuilder(ObjectMapper objectMapper) {
        final Decoder decoder = new JacksonDecoder(objectMapper);
        final Encoder encoder = new JacksonEncoder(objectMapper);
        return AsyncFeign.asyncBuilder()
                .encoder(encoder)
                .decoder(decoder)
                .errorDecoder(new GitHubErrorDecoder(decoder))
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.BASIC)
                .requestInterceptor(template -> {
                    template.header(TraceHttpHeader.HTTP_HEADER_TRACE_ID.getCode(),
                            TraceContextHolder.getContext().getTraceId());
                    template.header("Content-Type", "application/json");
                }).options(new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true));

    }

    @Bean
    public OpenFeignService openFeignTestService(Feign.Builder builder) {
        return builder.target(OpenFeignService.class, OpenFeignService.FEIGN_REQUEST_URL);
    }




    @Bean
    public Object openFeignSyncTestService(AsyncFeign.AsyncBuilder builder) {
        return builder.target(OpenFeignSyncService.class, OpenFeignSyncService.FEIGN_REQUEST_URL);
    }
}
