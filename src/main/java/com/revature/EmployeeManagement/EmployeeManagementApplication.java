package com.revature.EmployeeManagement;

import com.revature.EmployeeManagement.Model.Holiday;
import com.revature.EmployeeManagement.Repositoty.HolidayRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static java.util.logging.Level.parse;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EmployeeManagementApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EmployeeManagementApplication.class, args);

		/*
		for now, this is a working solution that is adding all of this logic directly. This is more hard code of the total amount of holidays we can add.
		This serves as a way to add logic to our code and gives us the information we need to have for holidays
		 */
		HolidayRepository holidayRepository = context.getBean(HolidayRepository.class);

		Holiday newHoliday = new Holiday(1L, ("2023-01-01"), "Monday", "New Year", "Public");
		Holiday newYearHoliday = new Holiday(1L, ("2023-01-01"), "Monday", "New Year", "Public");
		Holiday christmasHoliday = new Holiday(2L, ("2023-12-25"), "Monday", "Christmas", "Public");
		Holiday easterHoliday = new Holiday(3L, ("2023-04-09"), "Sunday", "Easter", "Public");
		Holiday independenceDayHoliday = new Holiday(4L, ("2023-07-04"), "Tuesday", "Independence Day", "Public");
		Holiday laborDayHoliday = new Holiday(5L, ("2023-09-04"), "Monday", "Labor Day", "Public");
		Holiday thanksgivingHoliday = new Holiday(6L, ("2023-11-23"), "Thursday", "Thanksgiving", "Public");
		Holiday memorialDayHoliday = new Holiday(7L, ("2023-05-29"), "Monday", "Memorial Day", "Public");
		Holiday martinLutherKingJrDayHoliday = new Holiday(8L, ("2023-01-16"), "Monday", "Martin Luther King Jr. Day", "Public");
		Holiday presidentsDayHoliday = new Holiday(9L, ("2023-02-20"), "Monday", "Presidents' Day", "Public");
		Holiday veteransDayHoliday = new Holiday(10L,("2023-11-11"), "Saturday", "Veterans Day", "Public");
		Holiday columbusDayHoliday = new Holiday(11L, ("2023-10-09"), "Monday", "Columbus Day", "Public");
		List<Holiday> holidays = Arrays.asList(
				newYearHoliday,
				christmasHoliday,
				easterHoliday,
				independenceDayHoliday,
				laborDayHoliday,
				thanksgivingHoliday,
				memorialDayHoliday,
				martinLutherKingJrDayHoliday,
				presidentsDayHoliday,
				veteransDayHoliday,
				columbusDayHoliday
		);

		holidayRepository.saveAll(holidays);

	}

}
