package com.example.kafkademo.util;

import com.example.kafkademo.exception.InternalServerException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class JacksonSerializeUtil {
    private static class Serialize {
        protected static ObjectMapper mapper = new ObjectMapper();

        static {
            // 禁用遇到未知属性抛出异常
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 序列化BigDecimal时不使用科学计数法输出
            mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 对于空的对象转json的时候不抛出错误
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            // 时间格式支持LocalDateTime/LocalDate/LocalTime
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            // TODO 时间的时区的问题, 以后再细看
            // 下面是自己定义的格式, javaTimeModule有默认的格式化格式
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            mapper.registerModule(javaTimeModule);
        }
    }

    /**
     * 反序列化为对象
     *
     * @param json
     * @param t
     * @return
     */
    public static <T> T deserialization(String json, Class<T> t) {
        if (StringUtils.isEmpty(json)) return null;
        try {
            return Serialize.mapper.readValue(json, t);
        } catch (IOException e) {
            throw new InternalServerException("Could not read JSON: " + e.getMessage(), e);
        }
    }

    /**
     * 序列化
     *
     * @param t
     * @return
     */
    public static <T> String serialize(T t) {
        if (Objects.isNull(t)) return null;
        try {
            return Serialize.mapper.writeValueAsString(t);
        } catch (IOException e) {
            throw new InternalServerException("Could not write JSON: " + e.getMessage(), e);
        }
    }

    /**
     * JSON反序列化对象集合
     *
     * @param <T>
     * @param json
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static <T> T deserialization(String json, Class<?> collectionClass, Class<?>... elementClasses) {
        if (StringUtils.isEmpty(json)) return null;
        try {
            JavaType constructParametricType = Serialize.mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return Serialize.mapper.readValue(json, constructParametricType);
        } catch (Exception e) {
            throw new InternalServerException(e);
        }
    }

    public static ObjectMapper getMapper() {
        return Serialize.mapper;
    }
}
