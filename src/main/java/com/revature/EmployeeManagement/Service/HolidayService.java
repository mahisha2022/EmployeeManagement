package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Model.Holiday;
import com.revature.EmployeeManagement.Repositoty.HolidayRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;


    /**
     * Add holidays
     * @param holiday
     * @return
     */
    public void addHolidays(Holiday holiday){
        Holiday newHoliday = new Holiday();
        newHoliday.setName(holiday.getName());
        holiday.setDays(holiday.getDays());
        holiday.setType(holiday.getType());
        holiday.setDates(holiday.getDates());
        holidayRepository.save(newHoliday);

    }

    /**
     * Get all holidays
     * @return
     */
    public List<Holiday> getAllHoliday(){
        return holidayRepository.findAll();
    }

    /**
     * Get Future Holidays
     * @return
     */
    public List<Holiday> getFutureHolidays(){
        LocalDate currentDate = LocalDate.now();
        List<Holiday> futureHolidays = holidayRepository.findByDatesAfter(currentDate.toString());
        return futureHolidays;
    }

}
