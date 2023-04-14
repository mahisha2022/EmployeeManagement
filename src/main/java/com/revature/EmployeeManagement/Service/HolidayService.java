package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Model.Holiday;
import com.revature.EmployeeManagement.Repositoty.HolidayRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {

    @Autowired

    HolidayRepository holidayRepository;

//    public HolidayService(HolidayRepository holidayRepository){
//        this.holidayRepository = holidayRepository;
//    }



    public List<Holiday> getAllHoliday(){
        return holidayRepository.findAll();
    }
}
