package org.alumnusb.easypay.uphold;

import lombok.extern.slf4j.Slf4j;
import org.alumnusb.easypay.model.Payment;
import org.alumnusb.easypay.request.CreatePayment;
import org.alumnusb.easypay.uphold.dto.UpholdCard;
import org.alumnusb.easypay.uphold.dto.UpholdCredentials;
import org.alumnusb.easypay.uphold.model.UpholdCredentialsEntity;
import org.alumnusb.easypay.uphold.repository.UpholdCredentialsRepository;
import org.alumnusb.easypay.util.LoggingRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

@Component
@Slf4j
public class UpholdClient {

    @Value("${uphold.client-id}")
    private String clientId;

    @Value("${uphold.client-secret}")
    private String clientSecret;

    @Value("${uphold.base-url}")
    private String baseUrl;

    @Value("${uphold.api-version}")
    private String apiVersion;

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

    private String getCardsPath() {
        return baseUrl + "/"+ apiVersion + UpholdConstants.CARDS_PATH;
    }

    private String getTransactionsByCardPath(String cardId) {
        return String.format("%s/%s%s/%s/transactions", baseUrl, apiVersion, UpholdConstants.CARDS_PATH, cardId);
    }

    private String getAuthPath() {
        return baseUrl + UpholdConstants.AUTH_PATH;
    }

    public void saveCredentials(String code, String state) {
        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("code", code);
        payload.add("grant_type", UpholdConstants.AUTH_GRANT_TYPE);

        UpholdCredentials values = Objects.requireNonNull(this.getCredentials(payload));
        UpholdCredentialsEntity credentials = UpholdCredentialsEntity.builder()
                .credentialsId(UpholdConstants.CREDENTIALS_ID)
                .accessToken(values.getAccessToken())
                .refreshToken(values.getRefreshToken())
                .build();
        credentialsRepository.save(credentials);
    }

    public UpholdCredentialsEntity refreshToken() {
        UpholdCredentialsEntity credentials = credentialsRepository.findById(UpholdConstants.CREDENTIALS_ID)
                .orElseThrow(() -> new NoSuchElementException("Credentials not found"));

        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("refresh_token", credentials.getRefreshToken());
        payload.add("grant_type", UpholdConstants.REFRESH_GRANT_TYPE);

        UpholdCredentials values = Objects.requireNonNull(this.getCredentials(payload));
        credentials.setAccessToken(values.getAccessToken());
        credentials.setAccessToken(values.getRefreshToken());

        return credentialsRepository.save(credentials);
    }

    public Set<UpholdCard> getAllCards() {
        UpholdCredentialsEntity credentials = credentialsRepository.findById(UpholdConstants.CREDENTIALS_ID)
                .orElseThrow(() -> new NoSuchElementException("Credentials not found"));

        RequestEntity<Void> request = RequestEntity.get(URI.create(this.getCardsPath()))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + credentials.getAccessToken())
                .header("User-Agent", UpholdConstants.USER_AGENT)
                .build();

        ResponseEntity<Set<UpholdCard>> response =  client.exchange(request,
                new ParameterizedTypeReference<Set<UpholdCard>>() {});

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Error from Uphold: {}", response.getStatusCode().getReasonPhrase());
            throw new IllegalStateException("Error getting cards information from Uphold");
        }
        return response.getBody();
    }

    public UpholdCard getCardById(String cardId) {
        UpholdCredentialsEntity credentials = credentialsRepository.findById(UpholdConstants.CREDENTIALS_ID)
                .orElseThrow(() -> new NoSuchElementException("Credentials not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(credentials.getAccessToken());
        headers.set("User-Agent", UpholdConstants.USER_AGENT);

        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UpholdCard> response = client.exchange( this.getCardsPath()+ "/{cardId}",
                HttpMethod.GET, httpEntity, UpholdCard.class, cardId);

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Error from Uphold: {}", response.getStatusCode().getReasonPhrase());
            throw new IllegalStateException("Error getting cards information from Uphold");
        }
        return response.getBody();
    }

    public Payment createTransaction(String user, Float amount, String upholdCardId) {
        UpholdCredentialsEntity credentials = credentialsRepository.findById(UpholdConstants.CREDENTIALS_ID)
                .orElseThrow(() -> new NoSuchElementException("Credentials not found"));

        RequestEntity<Void> request = RequestEntity.get(URI.create(this.getTransactionsByCardPath(upholdCardId)))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + credentials.getAccessToken())
                .header("User-Agent", UpholdConstants.USER_AGENT)
                .build();

        ResponseEntity<Set<UpholdCard>> response =  client.exchange(request,
                new ParameterizedTypeReference<Set<UpholdCard>>() {});

        return Payment.builder().build();
    }

    private <T> UpholdCredentials getCredentials(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);
        headers.set("User-Agent", UpholdConstants.USER_AGENT);

        HttpEntity<T> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<UpholdCredentials> response =  client.postForEntity(
                this.getAuthPath(),
                httpEntity,
                UpholdCredentials.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Error from Uphold: {}", response.getStatusCode().getReasonPhrase());
            throw new IllegalStateException("Error storing Uphold Credentials");
        }

        return response.getBody();
    }
}
