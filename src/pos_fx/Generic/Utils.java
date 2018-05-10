/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos_fx.Generic;

import java.util.Calendar;

/**
 * 
 * @author Eduardo Carbajal Reyes
 */
public class Utils {
    
    public static String getDiaFecha(){
        	Calendar now = Calendar.getInstance();
	// Array con los dias de la semana

        String[] strDays = new String[]{"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
        String[] strMonths = new String[]{"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre", "octubre", "noviembre", "diciembre"};

       return strDays[now.get(Calendar.DAY_OF_WEEK) - 1] + ", "+ (now.get(Calendar.DATE))
						+ " de "
						+ strMonths[now.get(Calendar.MONTH)]
						+ " del "
						+ now.get(Calendar.YEAR);
    }

}
