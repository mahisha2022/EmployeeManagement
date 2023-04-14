package com.revature.EmployeeManagement.Exception;

public class InvalidCredential extends RuntimeException{
    public InvalidCredential(String message){
        super(message);
    }
}
