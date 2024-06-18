package jp.co.cam.universal.util.text.format.number.lang;

import java.text.*;
import java.util.Locale;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <pre>
 * French Republic(Type France)
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
 * 1.000      ------ 1 (!)caution
 * 1,000      ------ 1
 * 1.00       ------ 1
 * 1,00       ------ 1
 * </pre>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024
 * <br>########################################################################
 * <br>(change history)
 * <br>2024.04 Added mone countries
 * <br>[JDK19]Change whitespace delimiter.
 * <br>########################################################################
 */
public class NumberFormat_FR
    extends NumberFormat
{
	@Override
    public String formatCurrency(
        final Number pmData
      , final int    pmScale
    )
    {
    	// french number strings
        String data = super.formatCurrency(pmData, pmScale);

        if (data != null) {
        	// [JDK19]' ' change to ' '
        	data = data.replace(" ", " ");
        	data = data.replace(" ", " ");
        }
    	return data;
    }


    @Override
    public String format(
        final Number pmData
      , final String pmText
    )
    {
    	// french number strings
        String data = super.format(pmData, pmText);

        if (data != null) {
        	// [JDK19]' ' change to ' '
        	data = data.replace(" ", " ");
        	data = data.replace(" ", " ");
        }
    	return data;
    }

    @Override
    public DecimalFormat getNumberFormatInstance()
    {
        return new DecimalFormat("#0.#", new DecimalFormatSymbols(Locale.FRANCE));
    }


    @Override
    protected String cleansingNumberStrings(
    	final String pmData
    )
    {
        if (pmData == null || pmData.length() == 0) return null;

        String data = pmData;

        // Japanese traditional minus sign
        if (data.startsWith("▲") || data.startsWith("△")) {
        	data = "-" + data.substring(1);
        }

        // separator unique to France
    	data = data.replace(" ", "");
    	data = data.replace(" ", "");
    	data = data.replace(" ", "");

        // separator unique to Switzerland
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

            if (2 < data.split("\\.").length) {
            // contains multiple dots

            	// delete separator
	        	data = data.replace(".", "");

            } else {
            // contains single dot

            	// change decimal point
	        	data = data.replace(".", ",");
            }
        }
        return data;
    }
}
