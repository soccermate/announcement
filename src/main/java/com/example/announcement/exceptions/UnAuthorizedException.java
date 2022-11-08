package com.example.announcement.exceptions;

public class UnAuthorizedException extends  RuntimeException{
    public UnAuthorizedException(String msg)
    {
        super(msg);
    }
}
