package org.alumnusb.easypay.uphold;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UpholdConstants {
    public final static String AUTH_PATH = "/oauth2/token";

    public final static String AUTH_GRANT_TYPE = "authorization_code";

    public final static String REFRESH_GRANT_TYPE = "refresh_token";

    public static final Short CREDENTIALS_ID = 1;

    public static final String USER_AGENT = "eltabo";
}
