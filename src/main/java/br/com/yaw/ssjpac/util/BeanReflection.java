package br.com.yaw.ssjpac.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import javax.persistence.Id;

public final class BeanReflection {

    public static String getPrimaryKeyName(Object bean, Class<?> stopClass) throws Exception {
        Field field = getPrimaryKeyField(bean, stopClass);
        return field != null ? field.getName() : null;
    }

    public static Long getPrimaryKeyValue(Object bean) {
        return getPrimaryKeyValue(bean, bean.getClass().getSuperclass());
    }

    public static Long getPrimaryKeyValue(Object bean, Class<?> stopClass) {
        try {
            Field field = getPrimaryKeyField(bean, stopClass);

            return (Long) getPropertyByField(bean.getClass(), stopClass, field).getReadMethod().invoke(bean);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o valor da Chave Primária da entidade (" + bean.getClass().getSimpleName() + ").", e);
        }
    }

    private static PropertyDescriptor getPropertyByField(Class<?> beanClass, Class<?> stopClass, Field field) throws IntrospectionException {
        PropertyDescriptor[] pd = Introspector.getBeanInfo(beanClass, stopClass).getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : pd) {
            if (field.getName().equals(propertyDescriptor.getName())) {
                return propertyDescriptor;
            }
        }

        throw new RuntimeException("Não foi encontrado o Descritor de Propriedade(Get,Set) para o atributo(" + field.getName() + ") da entidade(" + beanClass.getSimpleName() + ").");
    }

    public static Field getPrimaryKeyField(Object bean, Class<?> stopClass) throws Exception {
        Class<?> clazz = bean.getClass();

        while (true) {

            for (Field field : clazz.getDeclaredFields()) {

                Id idAnot = field.getAnnotation(Id.class);
                if (idAnot != null) {
                    return field;
                }
            }
            clazz = clazz.getSuperclass();

            if (clazz == stopClass) {
                throw new Exception("Chave Primária não encontrada para a entidade (" + bean.getClass().getSimpleName() + ".");
            }
        }
    }

}
