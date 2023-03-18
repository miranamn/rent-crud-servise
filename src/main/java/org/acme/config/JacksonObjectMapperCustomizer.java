package org.acme.config;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.jackson.ObjectMapperCustomizer;

import javax.inject.Singleton;
@Singleton
public class JacksonObjectMapperCustomizer implements ObjectMapperCustomizer {
    public void customize(ObjectMapper mapper) {
        mapper.enable(SerializationFeature.INDENT_OUTPUT); //JSON pretty view
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); //ignore JSON null fields
    }
}
