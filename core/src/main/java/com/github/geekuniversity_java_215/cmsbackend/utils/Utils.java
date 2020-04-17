package com.github.geekuniversity_java_215.cmsbackend.utils;

import java.lang.reflect.Field;

public class Utils {

    public static void fieldSetter(String fieldName, Object o, Object value) {

        try {
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
            field.set(o, value);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
