package com.github.geekuniversity_java_215.cmsbackend.core.entities.user;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserRoleDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

// https://stackoverflow.com/questions/13253624/how-to-supply-enum-value-to-an-annotation-from-a-constant-in-java
// All problems in computer science can be solved by another level of indirection,
// except for the problem of too many layers of indirection.


// ToDo: Use
//  https://www.baeldung.com/jpa-persisting-enums-in-jpa#converter


// Entity-Enum (stored in code and in DB)
// наверное стоит убрать из БД
@Entity
@Table(name="uzer_role")
@Data
@NoArgsConstructor
public class UserRole extends AbstractEntity {

    //public static final String REGISTRAR              = "ROLE_REGISTRAR";
    public static final String ADMIN                  = "ROLE_ADMIN";
    public static final String USER                   = "ROLE_USER";
    public static final String ANONYMOUS              = "ROLE_ANONYMOUS";
    public static final String RESOURCE               = "ROLE_RESOURCE";
    public static final String REFRESH                = "ROLE_REFRESH";
    public static final String CONFIRM_REGISTRATION   = "ROLE_CONFIRM_REGISTRATION";

    public static final String CLIENT   = "ROLE_CLIENT";
    public static final String COURIER   = "ROLE_COURIER";
    public static final String MANAGER   = "ROLE_MANAGER";

    public final static List<String> ROLE_NAMES = new ArrayList<>();

    static {
        ROLE_NAMES.add(ADMIN);
        ROLE_NAMES.add(USER);
        ROLE_NAMES.add(ANONYMOUS);
        ROLE_NAMES.add(RESOURCE);
        ROLE_NAMES.add(REFRESH);
        ROLE_NAMES.add(CONFIRM_REGISTRATION);
        ROLE_NAMES.add(CLIENT);
        ROLE_NAMES.add(COURIER);
        ROLE_NAMES.add(MANAGER);
    }

    @Column(unique=true)
    @Setter(AccessLevel.NONE)
    private String name;

//    @Getter
//    @ManyToMany(mappedBy = "roles")
//    private final Set<User> userList = new HashSet<>();

//    @Getter
//    @ManyToMany(mappedBy = "roles")
//    private final Set<User> userList = new HashSet<>();


    public static UserRole getByName(String role) {
        return new UserRole(role);
    }

    public UserRole(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(name, userRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    public static Set<GrantedAuthority> rolesToGrantedAuthority(Set<UserRole> roles) {
        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
















    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return name.equals(userRole.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public enum Values {
        ADMIN("ROLE_ADMIN"),
        USER("ROLE_USER"),
        ANONYMOUS("ROLE_ANONYMOUS"),
        RESOURCE("ROLE_RESOURCE"),
        REFRESH("ROLE_REFRESH");

        private final static Map<String, Values> ENUM_MAP = new HashMap<>();

        static {
            for(Values item : Values.values()) {
                if (ENUM_MAP.containsKey(item.name)){
                    throw new RuntimeException("UserRole.Values duplicate code");
                }
                ENUM_MAP.put(item.name, item);
            }
        }

        @NotNull
        public static Values nameOf(String name) throws IllegalArgumentException {

            Values result = ENUM_MAP.get(name);
            if (result == null) {
                throw new IllegalArgumentException("Values - no matching constant for [" + name + "]");
            }
            return result;
        }

        @Getter
        private String name;

        Values(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    */


