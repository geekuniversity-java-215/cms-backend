package com.github.geekuniversity_java_215.cmsbackend.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pivovarit.function.ThrowingRunnable.unchecked;

@UtilityClass
public class Utils {

    public int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    public String boolToStr(boolean b) {
        return b ? "1" : "0";
    }

    public Set<String> rolesToSet(Object authorities) {
        //noinspection unchecked
        return new HashSet<>(((List<String>) authorities));
    }

    public Set<String> grantedAuthorityToSet(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }


    public Set<GrantedAuthority> rolesToGrantedAuthority(Set<String> authorities) {
        return authorities
            .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public Set<GrantedAuthority> rolesToGrantedAuthority(Object authorities) {
        //noinspection unchecked
        return ((List<String>)authorities)
               .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }



    public long toLong(String s) {

        return Long.parseLong(s);
    }

    public int toInt(String s) {

        return Integer.parseInt(s);
    }



    /**
     * Get first field of specified class
     * @param source searching in this class
     * @param pattern which class to find
     * @return field name
     */
    public String getPatternClassFieldName(Class<?> source, Class<?> pattern) {

        String result = null;
        
        outerLoop:
        do {
            for (Field f : source.getDeclaredFields()) {
                if (f.getType() == pattern) {
                    result = f.getName();
                    break outerLoop;
                }
            }
        }
        while((source = source.getSuperclass()) != null);

        return result;
    }









    /**
     * Set object field value
     * @param fieldName
     * @param o object
     * @param value new field value
     */
    public void fieldSetter(String fieldName, final Object o, final Object value) {

        unchecked(() -> {
            Class<?> clazz = o.getClass();
            Field field = null;
            do {
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch(Exception ignore) {}
            }
            while((clazz = clazz.getSuperclass()) != null);  // Пролезет через CGLIB proxy

            Assert.notNull(field, "field == null");
            field.setAccessible(true);
            field.set(o, value);
            System.out.println(o);
        }).run(); // замена try ... catch(Exception e) {throw new RuntimeException(e)}
    }


    /**
     * Invoke object setter
     * @param setterName setter name
     * @param o object
     * @param paramType setter param type
     * @param value new value
     */
    public void propertySetter(String setterName, final Object o, Class<?> paramType, final Object value) {

        unchecked(() -> {
            Class<?> clazz = o.getClass();
            Method method = null;
            do {
                try {
                    method = clazz.getDeclaredMethod(setterName, paramType);
                } catch(Exception ignore) {}
            }
            while((clazz = clazz.getSuperclass()) != null);

            Assert.notNull(method, "method == null");
            method.setAccessible(true);
            method.invoke(o, value);
        }).run();

    }


    /**
     * Read object field value
     * @param fieldName field name
     * @param o object
     * @return
     */
    public Object fieldGetter(String fieldName, final Object o) {

        return com.pivovarit.function.ThrowingSupplier.unchecked(() -> {
            Class<?> clazz = o.getClass();
            Field field = null;
            do {
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch(Exception ignore) {}
            }
            while((clazz = clazz.getSuperclass()) != null);

            Assert.notNull(field, "field == null");
            field.setAccessible(true);
            return field.get(o);
        }).get();
    }


}

