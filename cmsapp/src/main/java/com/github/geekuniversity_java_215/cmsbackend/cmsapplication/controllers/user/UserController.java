package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.user;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;


// ToDo: перенести это все в управлялку ManagerUserController, кроме последнего метод

/**
 * User management
 */
@JrpcController(HandlerName.user.path)
@Secured(UserRole.USER)
public class UserController {

    private final UserService userService;
    private final UserConverter converter;
    private final PasswordEncoder passwordEncoder;

    private final UserRoleService userRoleService;
    private final ClientService clientService;
    private final CourierService courierService;

    public UserController(UserService userService, UserConverter converter,
                          PasswordEncoder passwordEncoder, UserRoleService userRoleService,
                          ClientService clientService, CourierService courierService) {
        this.userService = userService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.clientService = clientService;
        this.courierService = courierService;
    }

    @JrpcMethod(HandlerName.user.getCurrent)
    public JsonNode getCurrent(JsonNode params) {

        User user = userService.getCurrent();
        return converter.toDtoJson(user);
    }

    /**
     * Update user data (password, firstName, lastName)
     * <br> Will not save Account, UserRoles
     * @param params User
     * @return
     */
    @JrpcMethod(HandlerName.user.save)
    public JsonNode save(JsonNode params) {

        User user = converter.toEntity(params);

        // Check that userDto have same username, mail, phone
        if(!user.equals(userService.getCurrent())) {
            throw new IllegalArgumentException("Invalid user params. Username, mail, phone should be the same");
        }

        // update password - if not empty and not loaded from DB
        if (!StringUtils.isBlank(user.getPassword()) &&
            !user.getPassword().contains("{bcrypt}")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Security
        User currentUser = userService.getCurrent();
        // preserve roles
        user.setRoles(currentUser.getRoles());
        // preserve Account
        user.setAccount(currentUser.getAccount());

        user = userService.save(user);
        return converter.toIdJson(user);
    }


    /**
     * Up user to Client
     * @param params
     * @return Long id of created Client
     */
    @JrpcMethod(HandlerName.user.makeClient)
    public JsonNode makeClient(JsonNode params) {

        Client result;
        User user = userService.getCurrent();

        // already has ROLE_CLIENT
        if(user.getRoles().contains(UserRole.getByName(UserRole.CLIENT))) {

            result = clientService.getCurrent();
            Assert.notNull(result,user.getUsername() + " has role ROLE_CLIENT, but user.client == null");
        }
        // create new Client
        else {
            clientService.findOneByUser(user).ifPresent(client -> {
                throw new RuntimeException(user.getUsername() +
                    " had no ROLE_CLIENT but have Client, clientId: " + client.getId());
            });

            // Add ROLE_CLIENT
            //noinspection OptionalGetWithoutIsPresent
            user.getRoles().add(userRoleService.findByName(UserRole.CLIENT).get());

            // Add Client
            result = new Client();
            result.setUser(user);
            clientService.save(result);

            userService.save(user);
        }
        return converter.toIdJson(result);
    }


    @JrpcMethod(HandlerName.user.makeCourier)
    public JsonNode makeCourier(JsonNode params) {

        Courier result;
        User user = userService.getCurrent();

        // already has ROLE_CLIENT
        if(user.getRoles().contains(UserRole.getByName(UserRole.COURIER))) {

            result = courierService.getCurrent();
            Assert.notNull(result,user.getUsername() + " has role ROLE_COURIER, but user.courier == null");
        }
        // create new Courier
        else {
            courierService.findOneByUser(user).ifPresent(courier -> {
                throw new RuntimeException(user.getUsername() +
                    " had no ROLE_COURIER but have Courier, courierId: " + courier.getId());
            });

            // Add ROLE_COURIER
            //noinspection OptionalGetWithoutIsPresent
            user.getRoles().add(userRoleService.findByName(UserRole.COURIER).get());

            // Add Client
            result = new Courier();
            result.setUser(user);
            courierService.save(result);

            userService.save(user);
        }
        return converter.toIdJson(result);
    }
}


//        // Security, update confidential data from DB
//        user.getRoles().clear(); // Clear roles from DTO
//        user.setAccount(null);   // Clear account from DTO
//        User result = userService.getCurrent();
//        SpringBeanUtilsEx.CopyPropertiesExcludeNull(user, result);
