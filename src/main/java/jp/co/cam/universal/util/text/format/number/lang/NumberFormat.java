package jp.co.cam.universal.util.text.format.number.lang;

import java.math.*;
import java.text.*;
import java.util.Locale;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <pre>
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
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>[JDK9]new Integer(String) to Integer.parseInt(String)
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 */
public class NumberFormat
    extends AbstractNumberFormat
{
	@Override
    public String formatCurrency(
        final Number pmData
      , final int    pmScale
    )
    {
        if (pmData == null) return null;

        // pattern
        final StringBuilder temp
         = new StringBuilder("#,##0");

        for (int i = 0; i < pmScale; i++)
        {
            if (i == 0) temp.append(".");
            temp.append("0");
        }
        return format(pmData, temp.toString());
    }

    @Override
    public String formatCurrency(
        final Number pmData
    )
    {
    	// scale
    	int scale = 0;

    	if (pmData instanceof BigDecimal) {
    		scale = ((BigDecimal)pmData).scale();
    	} else
    	if (pmData instanceof Double) {
    		scale = 2;
    	} else
    	if (pmData instanceof Float) {
       		scale = 1;
    	}
    	return formatCurrency(pmData, scale);
    }


    @Override
    public String formatNumeric(
        final Number pmData
      , final int    pmScale
    )
    {
        if (pmData == null) return null;

        // pattern
        final StringBuilder temp = new StringBuilder("#0");

        for (int i = 0; i < pmScale; i++)
        {
            if (i == 0) temp.append(".");
            temp.append("0");
        }
        return format(pmData, temp.toString());
    }

    @Override
    public String formatNumeric(
        final Number pmData
    )
    {
    	// scale
    	int scale = 0;

    	if (pmData instanceof BigDecimal) {
    		scale = ((BigDecimal)pmData).scale();
    	} else
    	if (pmData instanceof Double) {
    		scale = 2;
    	} else
    	if (pmData instanceof Float) {
       		scale = 1;
    	}
        return formatNumeric(pmData, scale);
    }


    @Override
    public BigDecimal parseDecimal(
        final String pmText
    )
    	throws ParseException
    {
        if (pmText == null || pmText.length() == 0) return null;

        // formed number strings(#0.##)
    	final String number = cleansingNumberStrings(pmText);

    	try {

    		return new BigDecimal(super.parse(number, null).toString());

        } catch (final Exception ex) {
        	throw new ParseException("Decimal parse failed - " + pmText, -1);
		}
    }

    @Override
    public Integer parseInteger(
        final String pmText
    )
       	throws ParseException
    {
        if (pmText == null || pmText.length() == 0) return null;

        // formed number strings(#0.##)
    	final String number = cleansingNumberStrings(pmText);

    	try {

            return Integer.parseInt(super.parse(number, null).toString());

        } catch (final Exception ex) {
        	throw new ParseException("Decimal parse failed - " + pmText, -1);
		}
    }


    @Override
    public String format(
        final Number pmData
      , final String pmText
    )
    {
        // ========================================================================================
        // Common processing
        // ========================================================================================
        return super.format(pmData, pmText);
    }


    @Override
    public Number parse(
        final String pmData
      , final String pmText
    )
    	throws ParseException
    {
        if (pmData == null || pmData.length() == 0) return null;

        //  string of numbers
        String data = cleansingNumberStrings(pmData);

        // ========================================================================================
        // Common processing
        // ========================================================================================
        return super.parse(data, pmText);
    }


    @Override
    public DecimalFormat getNumberFormatInstance()
    {
        return new DecimalFormat("#0.#", new DecimalFormatSymbols(Locale.US));
    }


    /**
     * Cleanse country-specific number strings.
     * @param  pmData number strings
     * @return formed number strings(#0.##)
     */
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
    	data = data.replace(" ", "");

        // separator unique to Switzerland
    	data = data.replace("'", "");

        if (data.contains(".")) {
        // contains some dots

            if (data.contains(",")) {

            	// check decimal point
                if (data.lastIndexOf(".") < data.lastIndexOf(",")) {

                	// delete separator
    	        	data = data.replace(".", "");

                	// change separator
    	        	data = data.replace(",", ".");

                } else {

                	// delete separator
    	        	data = data.replace(",", "");
                }
            }

        } else {

        	// delete separator
        	data = data.replace(",", "");
        }
        return data;
    }
}
