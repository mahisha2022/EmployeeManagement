package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Holiday;
import com.revature.EmployeeManagement.Repositoty.HolidayRepository;
import com.revature.EmployeeManagement.Service.HolidayService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@NoArgsConstructor
public class HolidayController {

    HolidayService holidayService;
    @Autowired
    public HolidayController(HolidayService holidayService){
        this.holidayService = holidayService;
    }


    /**
     * post holidays
     * ENDPOINT POST localhost:9000/holidays
     * @param holiday
     * @return
     */
    @PostMapping("/holidays")
    public ResponseEntity<String> postHolidays(@RequestBody Holiday holiday){
        try {
            holidayService.addHolidays(holiday);
            return ResponseEntity.ok().body("Holiday added successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get all holidays
     * ENDPOINT GET localhost:9000/holidays
     * @return
     */
    @GetMapping("/holidays")
    public List<Holiday> getAllHolidays(){
        return holidayService.getAllHoliday();
    }

    /**
     * Get future holidays
     * ENDPOINT localhost:9000/holidays/future
     * @return
     */
    @GetMapping("/holidays/future")
    public List<Holiday> getFutureHolidays(){
        return holidayService.getFutureHolidays();
    }



    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }

}
