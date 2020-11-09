package com.teste.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class UtilTeste {
	public static Integer calculaAge(Date birthday) {
    	return Period.between(birthday.toLocalDate(), LocalDate.now()).getYears();
    }
}
