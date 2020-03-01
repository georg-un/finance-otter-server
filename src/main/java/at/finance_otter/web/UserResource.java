package at.finance_otter.web;

import at.finance_otter.service.UserService;
import at.finance_otter.service.dto.UserDTO;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.RefreshToken;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/fino/users")
@ApplicationScoped
public class UserResource {

    /**
     * Injection point for the ID Token issued by the OpenID Connect Provider
     */
    @Inject
    @IdToken
    JsonWebToken idToken;

    /**
     * Injection point for the Access Token issued by the OpenID Connect Provider
     */
    @Inject
    JsonWebToken accessToken;

    /**
     * Injection point for the Refresh Token issued by the OpenID Connect Provider
     */
    @Inject
    RefreshToken refreshToken;

    @Inject
    SecurityIdentity si;


    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public String getActiveUsers(@Context SecurityContext sec) {
        //return this.userService.getActiveUsers();
        return " IT-issuer: " + idToken.getIssuer() +
                " IT-name: " + idToken.getName() +
                " AT-name " + accessToken.getName() +
                " AT-subject " + accessToken.getSubject() +
                " SI-prinpical" + si.getPrincipal().getName() +
                " SI-isUser "  + si.hasRole("user") +
                " SEC-principal " + sec.getUserPrincipal() +
                " SEC-isUser " + sec.isUserInRole("user") +
                " SEC.isSecure " + sec.isSecure();
    }

}


