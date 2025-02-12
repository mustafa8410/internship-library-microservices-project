package com.example.batch.excel;

public enum FieldName {

    TITLE("title"),
    DESCRIPTION("desc", "description"),
    AUTHOR("author"),
    BOOKCASE_ID("bookcase"),
    NAME("name"),
    SURNAME("surname"),
    MAIL("mail"),
    USERNAME("username"),
    PASSWORD("password"),
    ROLE("role"),
    CAPACITY("capacity");


    private final String[] variations;
    FieldName(String... variations){
        this.variations = variations;
    }

    public String[] getVariations() {
        return variations;
    }

    public static boolean findStringInVariations(String toFind, FieldName fieldName) {
        for(String name: fieldName.getVariations())
            if(toFind.equals(name))
                return true;
        return false;
    }
}
