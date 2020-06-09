package com.github.geekuniversity_java_215.cmsbackend.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

// https://stackoverflow.com/a/19739041/13174445
public class SpringBeanUtilsEx {

    public static String[] getNullPropertyNames (Object source, Object target) {

        // Add null fields from source
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        /*
        // add lazy initialization proxy holder(PersistentBag) fields from target
        final BeanWrapper tgt = new BeanWrapperImpl(target);
        java.beans.PropertyDescriptor[] pdt = tgt.getPropertyDescriptors();
        for(java.beans.PropertyDescriptor pd : pdt) {
            Object srcValue = tgt.getPropertyValue(pd.getName());
            if (srcValue!=null && srcValue.getClass().getSimpleName().equals("PersistentBag")) {
                emptyNames.add(pd.getName());
            }
        }
        */
        return emptyNames.toArray(new String[0]);
    }

    // Copy upper level fields if not null from from source to target
    public static void CopyPropertiesExcludeNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source, target));
    }
}
