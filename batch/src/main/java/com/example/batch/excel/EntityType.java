package com.example.batch.excel;

import java.util.*;

public enum EntityType {
    BOOK(FieldName.TITLE, FieldName.DESCRIPTION, FieldName.AUTHOR, FieldName.BOOKCASE_ID),
    USER(FieldName.NAME, FieldName.SURNAME, FieldName.MAIL, FieldName.USERNAME, FieldName.PASSWORD, FieldName.ROLE),
    BOOKCASE(FieldName.CAPACITY);

    private final FieldName[] fieldNames;

    EntityType(FieldName... fieldNames) {
        this.fieldNames = fieldNames;
    }

    public FieldName[] getFieldNames() {
        return fieldNames;
    }

    public static Optional<EntityType> getEntityTypeFromHeader(List<String> header){
        List<EntityType> entityTypes = new ArrayList<>();
        entityTypes.add(BOOK);
        entityTypes.add(USER);
        entityTypes.add(BOOKCASE);
        for(EntityType entityType: entityTypes){
            if(header.size() == entityType.getFieldNames().length && confirmEntityTypeForHeader(entityType, header))
                return Optional.of(entityType);
        }
        return Optional.empty();
    }

    public static boolean confirmEntityTypeForHeader(EntityType entityType, List<String> header){ //these two now have the same size, because if not then the first check in the if condition would not pass anyways
        int i;
        for(i=0; i<header.size(); i++) {
            if(!entityType.getFieldNames()[i].findStringInVariations(header.get(i), entityType.getFieldNames()[i]))
                return false;
        }
        return true;
    }
}
