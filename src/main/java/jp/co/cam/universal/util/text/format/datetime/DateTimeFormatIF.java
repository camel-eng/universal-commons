package jp.co.cam.universal.util.text.format.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * [universal](commons)Provides datetime format conversion functionality.
 * <br>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>########################################################################
 * @see jp.co.cam.universal.util.text.format.datetime.DateTimeFactory
 */
public interface DateTimeFormatIF
{
	/** Months */
	String[][] months = new String[][]
	{
		{"JANUARY",   "JAN"}
	   ,{"FEBRUARY",  "FEB"}
	   ,{"MARCH",     "MAR"}
	   ,{"APRIL",     "APR"}
	   ,{"MAY",       "MAY"}
	   ,{"JUNE",      "JUN"}
	   ,{"JULY",      "JUL"}
	   ,{"AUGUST",    "AUG"}
	   ,{"SEPTEMBER", "SEP"}
	   ,{"OCTOBER",   "OCT"}
	   ,{"NOVEMBER",  "NOV"}
	   ,{"DECEMBER",  "DEC"}
	};

	/**
	 * Set datetime.
	 * @param pmBaseDate base date
	 * @param pmDateYY year
	 * @param pmDateMM month
	 * @param pmDateDD date
	 * @param pmTimeHH hours
	 * @param pmTimeMM minutes
	 * @param pmTimeSS seconds
	 * @param pmTimeMS millisecond
	 * @return new date
	 */
	Date setDateTime(
        final Date pmBaseDate
      , final int  pmDateYY
      , final int  pmDateMM
      , final int  pmDateDD
      , final int  pmTimeHH
      , final int  pmTimeMM
      , final int  pmTimeSS
      , final int  pmTimeMS
    );

	/**
	 * Set time.
	 * @param pmBaseDate base date
	 * @param pmDateYY year
	 * @param pmDateMM month
	 * @param pmDateDD date
	 * @return new date
	 */
	Date setDate(
        final Date pmBaseDate
      , final int  pmDateYY
      , final int  pmDateMM
      , final int  pmDateDD
    );

    /**
	 * Add time.
	 * @param pmBaseDate base date
	 * @param pmDateYY year
	 * @param pmDateMM month
	 * @param pmDateDD date
	 * @return new date
	 */
	Date addDate(
        final Date pmBaseDate
      , final int  pmDateYY
      , final int  pmDateMM
      , final int  pmDateDD
    );

	/**
	 * Set time.
	 * @param pmBaseDate base date
	 * @param pmTimeHH hours
	 * @param pmTimeMM minutes
	 * @param pmTimeSS seconds
	 * @param pmTimeMS millisecond
	 * @return new date
	 */
	Date setTime(
        final Date pmBaseDate
      , final int  pmTimeHH
      , final int  pmTimeMM
      , final int  pmTimeSS
      , final int  pmTimeMS
    );

	/**
	 * Add time.
	 * @param pmBaseDate base date
	 * @param pmTimeHH hours
	 * @param pmTimeMM minutes
	 * @param pmTimeSS seconds
	 * @param pmTimeMS millisecond
	 * @return new date
	 */
	Date addTime(
        final Date pmBaseDate
      , final int  pmTimeHH
      , final int  pmTimeMM
      , final int  pmTimeSS
      , final int  pmTimeMS
    );

	
	/**
     * Get days from start date to end date.
     * @param  pmFrom start date
     * @param  pmTo   end date
     * @return days
     */
	int getDays(
        final Date pmFrom
      , final Date pmTo
    );

    
    /**
     * Convert datetime to datetime strings.
     * @param pmDate datetime
     * @return datetime strings
     */
	String formatDateTime(
        final java.util.Date pmDate
    );

    /**
     * Convert datetime to date strings.
     * @param pmDate datetime
     * @return date strings
     */
	String formatDate(
        final java.util.Date pmDate
    );

    /**
     * Convert datetime to time strings.
     * @param pmDate datetime
     * @return time strings
     */
	String formatTime(
        final java.util.Date pmDate
    );

	
    /**
     * Convert datetime to custom format.
     * @param  pmText datetime
     * @param  pmTemp format
     * @return datetime in custom format
     */
    String format(
        final Date   pmText
      , final String pmTemp
    );


    /**
     * Convert string to datetime.
     * <pre>
     * [sample format]
     * MMM/dd/yyyy
     * dd/MMM/yyyy
     * yyyy/MM/dd
     * </pre>
     * @param  pmText string of datetime
     * @param  pmTemp format
     * @return java.util.Date
     * @throws ParseException can't convertion data
     */
    Date parse(
        final String pmText
      , final String pmTemp
    )
        throws ParseException
    ;

    /**
     * Convert string to datetime.
     * <pre>
     * [sample date format]
     * (Recommended format)
     * DD/MMM/YYYY
     * DD-MMM-YYYY
     * DD.MMM.YYYY
     * MMM/DD/YYYY
     * MMM-DD-YYYY
     * MMM.DD.YYYY
     * DD/MMM, YYYY
     * MMM/DD, YYYY
     * 
     * * The position of the day and month differs depending on the country.
     * DD/MM/YYYY, MM/DD/YYYY, YYYY/MM/DD
     * DD-MM-YYYY, MM-DD-YYYY, YYYY/MM/DD
     * DD.MM.YYYY, MM.DD.YYYY, YYYY/MM/DD
     * 
     * [sample time format]
     * HH:MM:ss.SSS
     * HH:MM:ss,SSS
     * </pre>
     * @param  pmText string of datetime
     * @return java.util.Date
     * @throws ParseException can't convertion data
     */
    Date parse(
        final String pmText
    )
       	throws ParseException
    ;


    /**
     * Get DateFormat.
     * @return DateFormat
     */
    SimpleDateFormat getDateFormatInstance();

    /**
     * Get Calendar.
     * @return Calendar
     */
    Calendar getCalendarInstance();
}
