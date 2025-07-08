# Keycloak server

## Dockerized keycloak
1. Setup keycloak server with docker compose
2. Setup postgres database with docker compose
3. Setup custom email otp authenticator plugin

```bash
services:
  keycloak:
    container_name: keycloak-bare
    image: quay.io/keycloak/keycloak:25.0.1
    command:
    - start-dev
    ports:
    - 8080:8080
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
      KC_HTTP_PORT: 8080
      KC_HOSTNAME_URL: http://localhost:8080
      KC_HOSTNAME_ADMIN_URL: http://localhost:8080
      KC_HOSTNAME_STRICT_BACKCHANNEL: true
      KC_HTTP_RELATIVE_PATH: /
      KC_HTTP_ENABLED: true
      KC_HEALTH_ENABLED: true
      KC_METRICS_ENABLED: true
      KC_DB: postgres
      KC_DB_URL_HOST: host.docker.internal
      KC_DB_URL_PORT: 5433
      KC_DB_URL_DATABASE: keycloakdb
      KC_DB_USERNAME: postgres
      KC_DB_PASSWORD: java
      TWILIO_ACCOUNT_SID: 
      TWILIO_AUTH_TOKEN: 
      TWILIO_PHONE_NUMBER:
    extra_hosts:
    - "host.docker.internal:host-gateway"
    volumes:
    - "./keycloak/keycloak-2fa-email-authenticator-1.0.0.0-SNAPSHOT.jar:/opt/keycloak/providers/email-otp-authenticator.jar"
    healthcheck:
      test: ['CMD-SHELL', '[ -f /tmp/HealthCheck.java ] || echo "public class HealthCheck { public static void main(String[] args) throws java.lang.Throwable { System.exit(java.net.HttpURLConnection.HTTP_OK == ((java.net.HttpURLConnection)new java.net.URL(args[0]).openConnection()).getResponseCode() ? 0 : 1); } }" > /tmp/HealthCheck.java && java /tmp/HealthCheck.java http://localhost:8080/auth/health/live']
      interval: 5s
      timeout: 5s
      retries: 20
```

## Add frontend client
1. Login to keycloak admin console with credentials set in docker compose `KEYCLOAK_ADMIN` and `KEYCLOAK_ADMIN_PASSWORD`.
2. Select realm or create new realm.
3. Create new `OpenID Connect` client in the clients section of the realm.
4. Set client type to public.
5. Set valid redirect URIs and other uri's to client uri.

## Configuring google login
1. select google from identity providers. 
2. Login to google console and create new project.
3. Create credentials and obtain client id and client secret.
4. Set client id and client secret in keycloak to the obtained values from google console.

## Configuring email verification link
1. Select realm settings. 
2. Under login tab, enable `verify email` option.
3. Then under the email tab, set the from email to the email address you want to use for sending verification emails. And below it, setup the smtp data, also enable  authentication. The email in smtp data should be the same as the 'from' email above and the password for google should be an app password obtained from google account settings. If using google, then the smtp data should be as follows:

```bash
smtpHost: smtp.gmail.com
smtpPort: 587
smtpStarttls: true
smtpUser: your-email@gmail.com
smtpPassword: your-app-specific-password
```
4. This would trigger email verification links to new users who regster with their emails.

## Configuring custom email otp verification flow
1. Select realm settings.
2. Docker is configured with the custom email otp jar file.
3. Create a new browser authentication flow for otp verification. Duplicate the browser flow. 
4. Click the Add (+) button in the browser otp subflow and select add step. Select the `email otp` option, which is the name of the custom otp auth app. Add it after username and password step.
5. Now, at the top click on Action dropdown button, and select `Bind Flow` and select `Browser Flow` option.
6. Otp flow is configured now.