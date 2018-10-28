package at.accounting_otter;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;

import javax.ws.rs.core.SecurityContext;

public class SecurityUtil {

    public String getCurrentUser(SecurityContext securityContext) {
        String userName = null;

        if (securityContext.getUserPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>)  securityContext.getUserPrincipal();
            userName = kp.getKeycloakSecurityContext().getIdToken().getPreferredUsername();
        }

        return userName;
    }

}
