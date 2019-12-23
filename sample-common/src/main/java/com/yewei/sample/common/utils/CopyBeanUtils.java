package com.yewei.sample.common.utils;

import org.dozer.DozerBeanMapper;
import org.dozer.MappingException;

public final class CopyBeanUtils {

    private final static DozerBeanMapper dozer = new DozerBeanMapper();

    private CopyBeanUtils() {
    }

    /**
     * 深度复制Bean（NestedBean,静态内部类等）
     *
     * @param sourceObject
     * @param targetClass
     * @param <T>
     * @return
     * @throws MappingException
     */
    public static <T> T copy(Object sourceObject, Class<T> targetClass) throws MappingException {
        return dozer.map(sourceObject, targetClass);
    }
}
