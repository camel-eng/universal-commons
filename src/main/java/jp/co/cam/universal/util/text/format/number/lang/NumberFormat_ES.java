package jp.co.cam.universal.util.text.format.number.lang;

import java.text.*;
import java.util.Locale;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <pre>
 * kingdom of Spain(Type Spain)
 * 
 * The main differences between Germany and France
 * Germany 1.000 = 1000
 * France  1.000 = 1.0
 * This happens because for Germany the separator are dots.
 * In addition, French separator are white spaces.
 * 
 * [SAMPLE]
 * 1,001,000.20 ---- 1001000.2
 * 1.001.000,20 ---- 1001000.2
 * 1 001 000,20 ---- 1001000.2
 * 1 001 000.20 ---- 1001000.2
 * 1001000,20 ------ 1001000.2
 * 1001000.20 ------ 1001000.2
 * 1.000      ------ 1000 (!)caution
 * 1,000      ------ 1
 * 1.00       ------ 1
 * 1,00       ------ 1
 * </pre>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024
 * <br>########################################################################
 * <br>(change history)
 * <br>2024.04 Added mone countries
 * <br>########################################################################
 */
public class NumberFormat_ES
    extends NumberFormat
{
	@Override
    public DecimalFormat getNumberFormatInstance()
    {
        return new DecimalFormat("#0.#", new DecimalFormatSymbols(Locale.GERMAN));
    }


    @Override
    protected String cleansingNumberStrings(
    	final String pmData
    )
    {
        if (pmData == null || pmData.length() == 0) return null;

        String data = pmData;

        // japanese traditional minus sign
        if (data.startsWith("▲") || data.startsWith("△")) {
        	data = "-" + data.substring(1);
        }

        // separator unique to france
    	data = data.replace(" ", "");
    	data = data.replace(" ", "");

        // separator unique to switzerland
    	data = data.replace("'", "");

        if (data.contains(",")) {
        // contains some dots

            if (data.contains(".")) {

            	// check decimal point
                if (data.lastIndexOf(",") < data.lastIndexOf(".")) {

                	// delete separator
    	        	data = data.replace(",", "");

                	// change separator
    	        	data = data.replace(".", ",");

                } else {

                	// delete separator
    	        	data = data.replace(".", "");
                }
            }

        } else {

        	// separate
        	final String[] parts = data.split("\\.");

        	// check separator or decimal point
			if ((parts.length == 2)
			 && (3 <  parts[0].length() || 3 != parts[1].length())) {

            	// change separator
	        	data = data.replace(".", ",");

			} else {
				
            	// delete separator
	        	data = data.replace(".", "");
			}
        }
        return data;
    }
}
