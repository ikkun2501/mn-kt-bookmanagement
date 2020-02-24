package com.ikkun2501.bookmanagement.infrastructure;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

@SuppressWarnings("unused")
public class OriginalPrefixGeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(final Definition definition, final Mode mode) {

        String name = super.getJavaClassName(definition, mode);

        switch (mode) {
            case POJO:
                return name + "Vo";
            case DEFAULT:
                return 'J' + name;
        }

        return name;
    }
}