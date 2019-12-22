package com.yewei.sample.common.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ListTransformUtil {
    /**
     * @param fromList
     * @param clazz
     * @return
     */
    public static <F, T> List<T> transform(List<F> fromList, Class<T> clazz) {
        List<T> ret = null;
        if (!CollectionUtils.isEmpty(fromList)) {
            ret = Lists.transform(fromList, new ListTransformFunction<F, T>(clazz));
        }
        return ret;
    }
}


@Slf4j
class ListTransformFunction<F, T> implements Function<F, T> {
    private Class<T> clazz;

    public ListTransformFunction(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public @Nullable
    T apply(@Nullable F input) {
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(input, t);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("new instance fail:", e);
        }
        return t;
    }
}
