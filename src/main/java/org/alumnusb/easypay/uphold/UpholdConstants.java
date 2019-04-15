package org.alumnusb.easypay.uphold;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class UpholdConstants {
    private final static String AUTH_PATH = "/oauth2/token";

    private final static String CARDS_PATH = "/me/cards";

    public final static String AUTH_GRANT_TYPE = "authorization_code";

    public final static String REFRESH_GRANT_TYPE = "refresh_token";

    public static final Short CREDENTIALS_ID = 1;

    public static final String USER_AGENT = "eltabo";

    @Value("${uphold.base-url}")
    private String baseUrl;

    @Value("${uphold.api-version}")
    private String apiVersion;

    public String getCardsPath() {
        return baseUrl + "/"+ apiVersion + CARDS_PATH;
    }

    public String getAuthPath() {
        return baseUrl + AUTH_PATH;
    }
}
