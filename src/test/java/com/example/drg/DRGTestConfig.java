package com.example.drg;

import com.example.drg.core.config.MetaFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@TestConfiguration
public class DRGTestConfig {

    @AllArgsConstructor
    @Getter
    public static class TestEntity {
        private String firstField;
        private String secondField;
        private String thirdField;
    }

    @Bean
    MetaFunction<List<TestEntity>> getTestMetaFunction() {
        return new MetaFunction<>() {
            @Override
            public List<Class<?>> getArgTypes() {
                return List.of(String.class, String.class, String.class, Integer.class);
            }

            @Override
            public List<TestEntity> compute(Map<String, String> params, List<?> args) {
                String firstField = (String) args.get(0);
                String secondField = (String) args.get(1);
                String thirdField = (String) args.get(2);
                TestEntity testEntity = new TestEntity(firstField, secondField, thirdField);

                int copyCount = (int) args.get(3);

                return Collections.nCopies(copyCount, testEntity);
            }

            @Override
            public String getMetaAlias() {
                return "testEntityList";
            }

            @Override
            public Class<List<TestEntity>> getReturnedType() {
                List<TestEntity> testEntityList = new ArrayList<>();
                return (Class<List<TestEntity>>) testEntityList.getClass();
            }

            @Override
            public List<TestEntity> getTestValue() {
                TestEntity testEntity = new TestEntity("firstField", "secondField", "thirdField");
                return Collections.nCopies(3, testEntity);
            }
        };
    }


}
