package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Holiday;
import com.revature.EmployeeManagement.Repositoty.HolidayRepository;
import com.revature.EmployeeManagement.Service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HolidayController {

    HolidayService holidayService;
    @Autowired
    public HolidayController(HolidayService holidayService){
        this.holidayService = holidayService;
    }



    @GetMapping("/holidays")
    public List<Holiday> getAllHolidays(){
        return holidayService.getAllHoliday();
    }


    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}
