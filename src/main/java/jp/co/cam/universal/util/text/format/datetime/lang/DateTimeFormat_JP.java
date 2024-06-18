package jp.co.cam.universal.util.text.format.datetime.lang;

import java.text.ParseException;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <br>Japan(Type Germany)
 * <br>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>########################################################################
 */
public class DateTimeFormat_JP
    extends DateTimeFormat_DE
{
	@Override
    public java.util.Date parse(
        final String pmText
    )
       	throws ParseException
    {
        if (pmText == null || pmText.length() == 0) return null;

        // datetime
    	String date = pmText;

    	date = date.replaceAll("(ミリ秒)",   ".");
    	date = date.replaceAll("(秒 |秒)", ".");
    	date = date.replaceAll("(分　|分)", ":");
    	date = date.replaceAll("(時　|時)", ":");

    	date = date.replaceAll("(日　|日)", " ");
    	date = date.replaceAll("(月 |月)", "/");
    	date = date.replaceAll("(年 |年)", "/");


    	return super.parse(date);
    }
}
