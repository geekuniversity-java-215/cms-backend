package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.user;


import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.CourierService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;


// ToDo: перенести это все в управлялку ManagerUserController, кроме последнего метод

/**
 * User management
 */
@JrpcController(HandlerName.user.path)
@Secured(UserRole.VAL.USER)
public class UserController {

    private final UserService userService;
    private final UserConverter converter;
    private final PasswordEncoder passwordEncoder;

    private final ClientService clientService;
    private final CourierService courierService;

    public UserController(UserService userService, UserConverter converter,
                          PasswordEncoder passwordEncoder, ClientService clientService,
                          CourierService courierService) {
        this.userService = userService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
        this.clientService = clientService;
        this.courierService = courierService;
    }

    /**
     * Return current user
     * @return UserDto
     */
    @JrpcMethod(HandlerName.user.getCurrent)
    public UserDto getCurrent() {

        User user = userService.getCurrent();
        return converter.toDto(user);
    }

    /**
     * Update user data (password, payPalEmail)
     * <br> Will not save firstName, lastName, account, UserRoles, client, courier, refreshTokenList
     * @param params Userdto
     * @return Long userId
     */
    @JrpcMethod(HandlerName.user.save)
    public Long save(UserDto userDto) {

        User user = converter.toEntity(userDto);

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
        // preserve enabled
        user.setEnabled(currentUser.isEnabled());

        user = userService.save(user);
        return user.getId();
    }


    /**
     * Up current user to Client
     * @return Long id of created Client
     */
    @JrpcMethod(HandlerName.user.makeClient)
    public Long makeClient() {

        Client result;
        User user = userService.getCurrent();

        // already has ROLE_CLIENT
        if(user.getRoles().contains(UserRole.CLIENT)) {

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
            user.getRoles().add(UserRole.CLIENT);

            // Add Client
            result = new Client();
            result.setUser(user);
            clientService.save(result);

            userService.save(user);
        }
        return result.getId();
    }


    /**
     * Up current user to Courier
     * @return Long id of created Client
     */
    @JrpcMethod(HandlerName.user.makeCourier)
    public Long makeCourier() {

        Courier result;
        User user = userService.getCurrent();

        // already has ROLE_CLIENT
        if(user.getRoles().contains(UserRole.COURIER)) {

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
            user.getRoles().add(UserRole.COURIER);

            // Add Client
            result = new Courier();
            result.setUser(user);
            courierService.save(result);

            userService.save(user);
        }
        return result.getId();
    }
}


//        // Security, update confidential data from DB
//        user.getRoles().clear(); // Clear roles from DTO
//        user.setAccount(null);   // Clear account from DTO
//        User result = userService.getCurrent();
//        SpringBeanUtilsEx.CopyPropertiesExcludeNull(user, result);
