package jp.co.cam.universal.util.text.format.number.lang;

import java.text.*;

import jp.co.cam.universal.util.text.format.number.NumberFormatIF;


/**
 * [universal](commons)Abstract class for number format conversion functionality.
 * <br>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>########################################################################
 */
public abstract class AbstractNumberFormat
    implements NumberFormatIF
{
    @Override
    public String format(
        final Number pmData
      , final String pmText
    )
    {
        if (pmData == null) return null;

        // decimal format
        final DecimalFormat format = getNumberFormatInstance();

        if (pmText != null && pmText.length() != 0) {
        	format.applyPattern(pmText);
        }
        return format.format(pmData);
    }

    @Override
    public Number parse(
        final String pmData
      , final String pmText
    )
    	throws ParseException
    {
        if (pmData == null || pmData.length() == 0) return null;

        // decimal format
        final DecimalFormat format = getNumberFormatInstance();

        if (pmText != null && pmText.length() != 0) {
        	format.applyPattern(pmText);
        }
        return format.parse(pmData);
    }


    @Override
    public abstract DecimalFormat getNumberFormatInstance();
}
