package jp.co.cam.universal.util.text.format.number;

import java.math.*;
import java.text.DecimalFormat;
import java.text.ParseException;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <br>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>########################################################################
 * @see jp.co.cam.universal.util.text.format.number.NumberFactory
 */
public interface NumberFormatIF
{
    /**
     * Convert numbers to national currency format.
     * @param  pmData  numeric
     * @param  pmScale scale
     * @return numbers in national currency format
     */
    String formatCurrency(
        final Number pmData
      , final int pmScale
    );

    /**
     * Convert numbers to national currency format.
     * @param  pmData numeric
     * @return numbers in national currency format
     */
    String formatCurrency(
        final Number pmData
    );


    /**
     * Convert numbers to decimal format.
     * @param  pmData  numeric
     * @param  pmScale scale
     * @return numbers in decimal format
     */
    String formatNumeric(
        final Number pmData
      , final int pmScale
    );

    /**
     * Convert numbers to decimal format.
     * @param  pmData  numbers
     * @return numbers in decimal format
     */
    String formatNumeric(
        final Number pmData
    );


    /**
     * Convert numbers to custom format.
     * @param  pmData numeric
     * @param  pmText format
     * @return numbers in custom format
     */
    String format(
        final Number pmData
      , final String pmText
    );


    /**
     * Convert string to decimal.
     * @param  pmData string of numbers
     * @return java.math.BigDecimal
     * @throws ParseException can't convertion data
     */
    BigDecimal parseDecimal(
        final String pmData
    )
    	throws ParseException
    ;

    /**
     * Convert string to integer.
     * @param  pmData string of numbers
     * @return java.lang.Integer
     * @throws ParseException can't convertion data
     */
    Integer parseInteger(
        final String pmData
    )
    	throws ParseException
    ;


    /**
     * Convert string to numeric.
     * <pre>
     * [sample format]
     * #,###.## --- 1,000.1
     * #,###.00 --- 1,000.10
     * </pre>
     * @param  pmData string of numbers
     * @param  pmText format
     * @return java.lang.Number
     * @throws ParseException can't convertion data
     */
    Number parse(
        final String pmData
      , final String pmText
    )
        throws ParseException
    ;


    /**
     * Get DecimalFormat.
     * @return DecimalFormat
     * @throws ParseException can't convertion data
     */
    DecimalFormat getNumberFormatInstance()
        throws ParseException
    ;
}
