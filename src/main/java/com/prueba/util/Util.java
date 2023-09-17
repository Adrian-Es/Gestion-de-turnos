package com.prueba.util;

import java.time.LocalTime;

public class Util {

	public static boolean horaValida(LocalTime hora) {
		LocalTime horaApertura = LocalTime.of(8, 0);
		LocalTime horaCierre = LocalTime.of(23, 0);
		
		return !hora.isBefore(horaApertura) && !hora.isAfter(horaCierre);
	}
	
	public static String dia(Integer diaValue) {
		String[] dia = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
		
		return dia[diaValue.intValue()-1];
	}

}
