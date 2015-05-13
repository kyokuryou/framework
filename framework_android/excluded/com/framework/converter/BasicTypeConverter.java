package org.smarty.core.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class BasicTypeConverter implements ConditionalGenericConverter {

    private static final Set<Class> types = new HashSet<Class>();

    static {
        types.add(boolean.class);
        types.add(byte.class);
        types.add(char.class);
        types.add(short.class);
        types.add(int.class);
        types.add(float.class);
        types.add(double.class);
        types.add(long.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> pairs = new HashSet<ConvertiblePair>();
        for (Class type : types) {
            pairs.add(new ConvertiblePair(String.class, type));
        }
        return pairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(sourceType, "Source type must not be null");
        Assert.notNull(targetType, "Target type must not be null");
        Assert.isAssignable(sourceType.getType(), String.class, "Source type must be String");

        String src = (String) source;
        if (targetType.getType().isAssignableFrom(boolean.class)) {
            return Boolean.parseBoolean(src);
        } else if (targetType.getType().isAssignableFrom(byte.class)) {
            return Boolean.parseBoolean(src);
        } else if (targetType.getType().isAssignableFrom(short.class)) {
            return Short.parseShort(src);
        } else if (targetType.getType().isAssignableFrom(int.class)) {
            return Integer.parseInt(src);
        } else if (targetType.getType().isAssignableFrom(float.class)) {
            return Float.parseFloat(src);
        } else if (targetType.getType().isAssignableFrom(double.class)) {
            return Double.parseDouble(src);
        } else if (targetType.getType().isAssignableFrom(long.class)) {
            return Long.parseLong(src);
        }
        return null;
    }

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        Class<?> srcType = sourceType.getType();
        Class<?> descType = targetType.getType();

        if (!srcType.isAssignableFrom(String.class)) {
            return false;
        }

        for (Class type : types) {
            if (descType.isAssignableFrom(type)) {
                return true;
            }
        }
        return false;
    }
}
