package ru.mirea.tapki_lapki.config;

import graphql.schema.GraphQLScalarType;
import graphql.servlet.core.ApolloScalars;
import org.springframework.context.annotation.Bean;

public class GraphqlConfig {
    @Bean
    public GraphQLScalarType uploadScalar() {
        return ApolloScalars.Upload;
    }
}