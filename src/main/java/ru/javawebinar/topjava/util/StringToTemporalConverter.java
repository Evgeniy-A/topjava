package ru.javawebinar.topjava.util;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.Set;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class StringToTemporalConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(
                new ConvertiblePair(String.class, LocalDate.class),
                new ConvertiblePair(String.class, LocalTime.class));
    }

    @Override
    public TemporalAccessor convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        String str = (String) source;
        if (targetType.getType() == LocalDate.class) {
            return parseLocalDate(str);
        }
        return parseLocalTime(str);
    }
}