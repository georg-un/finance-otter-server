package at.accounting_otter;

import at.accounting_otter.rest.RestMethod;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;

import javax.ws.rs.core.SecurityContext;

public class SecurityUtil {

    public String getCurrentUser(SecurityContext securityContext, RestMethod restMethod) {
        String userName = null;

        if (securityContext.getUserPrincipal() instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) securityContext.getUserPrincipal();

            if (restMethod == RestMethod.GET) {
                userName = kp.getKeycloakSecurityContext().getIdToken().getPreferredUsername();
            } else if (restMethod == RestMethod.POST) {
                userName = kp.getKeycloakSecurityContext().getToken().getPreferredUsername();
            }
        }

        return userName;
    }

}
