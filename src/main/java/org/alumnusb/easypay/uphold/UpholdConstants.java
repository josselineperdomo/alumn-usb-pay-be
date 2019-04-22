package org.alumnusb.easypay.uphold;

import lombok.experimental.UtilityClass;

@UtilityClass
class UpholdConstants {
    final static String AUTH_PATH = "/oauth2/token";

    final static String CARDS_PATH = "/me/cards";

    final static String AUTH_GRANT_TYPE = "authorization_code";

    final static String REFRESH_GRANT_TYPE = "refresh_token";

    static final Short CREDENTIALS_ID = 1;

    static final String USER_AGENT = "eltabo";
}
