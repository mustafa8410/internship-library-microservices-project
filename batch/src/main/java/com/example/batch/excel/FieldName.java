package com.example.batch.excel;

public enum FieldName {

    BOOK_ID("book_id"),
    TITLE("title"),
    DESCRIPTION("desc", "description"),
    AUTHOR("author"),
    BOOKCASE_ID("bookcase"),
    USER_ID("user_id"),
    NAME("name"),
    SURNAME("surname"),
    MAIL("mail"),
    USERNAME("username"),
    PASSWORD("password"),
    ROLE("role"),
    CAPACITY("capacity"),
    START_DATE("start_date"),
    END_DATE("end_date"),
    ALERT_SENT("alert_sent");


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
