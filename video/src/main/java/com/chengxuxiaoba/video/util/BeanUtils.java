package com.chengxuxiaoba.video.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

public abstract class BeanUtils extends org.springframework.beans.BeanUtils {
	/**
	 *
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void copyProperties(Object source, Object target) throws BeansException
    {
        copyPropertiesCanIgnoreProperty(source, target, null);
	}
	/**
	 *
	 * @param source
	 * @param target
	 * @param specifyProperties, item must named by little camel-case format
	 * @throws BeansException
	 */
	public static void copyProperties(Object source, Object target, String... specifyProperties) throws BeansException {
        List<String> specifyList = specifyProperties != null ? Arrays.asList(specifyProperties) : null;

        copyPropertiesCanIgnoreProperty(source, target, specifyList);
	}

    private static void copyPropertiesCanIgnoreProperty(Object source, Object target, List<String> specifyPropertyList)
    {
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            //judge fields specified collection is contain property name, if no then does not copy value
            if(!ListUtil.isNullOrEmpty(specifyPropertyList) && !specifyPropertyList.contains(targetPd.getName()))
            {
                continue;
            }

            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }
}
