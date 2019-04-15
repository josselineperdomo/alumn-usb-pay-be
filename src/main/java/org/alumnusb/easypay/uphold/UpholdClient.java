package org.alumnusb.easypay.uphold;

import lombok.extern.slf4j.Slf4j;
import org.alumnusb.easypay.uphold.dto.CodeCredentialsResponse;
import org.alumnusb.easypay.uphold.model.UpholdCredentials;
import org.alumnusb.easypay.uphold.repository.UpholdCredentialsRepository;
import org.alumnusb.easypay.util.LoggingRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Component
@Slf4j
public class UpholdClient {

    @Value("${uphold.client-id}")
    private String clientId;

    @Value("${uphold.client-secret}")
    private String clientSecret;

    @Value("${uphold.base-url}")
    private String baseUrl;

    private RestTemplate client;

    private UpholdCredentialsRepository credentialsRepository;

    @Autowired
    public UpholdClient(UpholdCredentialsRepository repository) {
        this.client = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        this.client.setInterceptors(interceptors);

        this.credentialsRepository = repository;
    }

    public void saveCredentials(String code, String state) {
        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("code", code);
        payload.add("grant_type", UpholdConstants.AUTH_GRANT_TYPE);

        // TODO: adding some validation related to state

        CodeCredentialsResponse values = Objects.requireNonNull(this.getCredentials(payload));
        UpholdCredentials credentials = UpholdCredentials.builder()
                .credentialsId(UpholdConstants.CREDENTIALS_ID)
                .accessToken(values.getAccessToken())
                .refreshToken(values.getRefreshToken())
                .build();
        credentialsRepository.save(credentials);
    }

    public UpholdCredentials refreshToken() {
        UpholdCredentials credentials = credentialsRepository.findById(UpholdConstants.CREDENTIALS_ID)
                .orElseThrow(() -> new NoSuchElementException("Credentials not found"));

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("refresh_token", credentials.getRefreshToken());
        payload.add("grant_type", UpholdConstants.REFRESH_GRANT_TYPE);

        CodeCredentialsResponse values = Objects.requireNonNull(this.getCredentials(payload));
        credentials.setAccessToken(values.getAccessToken());
        credentials.setAccessToken(values.getRefreshToken());

        return credentialsRepository.save(credentials);
    }

    private <T> CodeCredentialsResponse getCredentials(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);
        headers.set("User-Agent", UpholdConstants.USER_AGENT);

        HttpEntity<T> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<CodeCredentialsResponse> response =  client.postForEntity(
                baseUrl + UpholdConstants.AUTH_PATH,
                httpEntity,
                CodeCredentialsResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Error from Uphold: {}", response.getStatusCode().getReasonPhrase());
            throw new IllegalStateException("Error storing Uphold Credentials");
        }

        return response.getBody();
    }
}
