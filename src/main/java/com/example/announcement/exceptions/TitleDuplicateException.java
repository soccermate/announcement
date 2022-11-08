package com.example.announcement.exceptions;

public class TitleDuplicateException extends RuntimeException{

    public TitleDuplicateException(String msg)
    {
        super(msg);
    }
}
