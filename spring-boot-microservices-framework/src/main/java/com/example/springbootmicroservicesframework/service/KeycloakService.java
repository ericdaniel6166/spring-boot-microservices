package com.example.springbootmicroservicesframework.service;

import com.example.springbootmicroservicesframework.config.security.KeycloakProps;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true")
public class KeycloakService {
    Keycloak keycloak;
    KeycloakProps keycloakProps;

    public Optional<UserRepresentation> searchUserByUsername(String username) {
        return getUsersResource().searchByUsername(username, true).stream().findFirst();
    }

    private RealmResource getRealm() {
        return keycloak.realm(keycloakProps.getRealm());
    }

    public Optional<UserRepresentation> searchUserByEmail(String email) {
        return getUsersResource().searchByEmail(email, true).stream().findFirst();

    }

    private UsersResource getUsersResource() {
        return getRealm().users();
    }

    public Optional<GroupRepresentation> searchGroupByName(String name) {
        return getRealm().groups().groups(name, 0, 1).stream().findFirst();
    }

    public Response createUser(UserRepresentation user) {
        return getUsersResource().create(user);
    }

}
