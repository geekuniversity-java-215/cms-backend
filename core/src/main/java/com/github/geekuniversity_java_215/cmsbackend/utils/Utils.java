package com.github.geekuniversity_java_215.cmsbackend.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.pivovarit.function.ThrowingRunnable.unchecked;

public class Utils {

    /**
     * Set object field value
     * @param fieldName
     * @param o object
     * @param value new field value
     */
    public static void fieldSetter(String fieldName, Object o, Object value) {

        unchecked(() -> {
            Class<?> clazz = o.getClass();
            Field field = null;
            do {
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch(Exception ignore) {}
            }
            while((clazz = clazz.getSuperclass()) != null);  // Пролезет через CGLIB proxy

            assert field != null;
            field.setAccessible(true);
            field.set(o, value);
        }).run(); // замена try ... catch(Exception e) {throw new RuntimeException(e)}
    }


    /**
     * Invoke object setter
     * @param setterName setter name
     * @param o object
     * @param paramType setter param type
     * @param value new value
     */
    public static void propertySetter(String setterName, Object o, Class paramType, Object value) {

        unchecked(() -> {
            Class<?> clazz = o.getClass();
            Method method = null;
            do {
                try {
                    method = clazz.getDeclaredMethod(setterName, paramType);
                } catch(Exception ignore) {}
            }
            while((clazz = clazz.getSuperclass()) != null);

            assert method != null;
            method.setAccessible(true);
            method.invoke(o, value);
        }).run();

    }


    /**
     * Rread object field value
     * @param fieldName field name
     * @param o object
     * @return
     */
    public static Object fieldGetter(String fieldName, Object o) {

        return com.pivovarit.function.ThrowingSupplier.unchecked(() -> {
            Class<?> clazz = o.getClass();
            Field field = null;
            do {
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch(Exception ignore) {}
            }
            while((clazz = clazz.getSuperclass()) != null);

            assert field != null;
            field.setAccessible(true);
            return field.get(o);
        }).get();
    }
}
