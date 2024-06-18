package jp.co.cam.universal.util.text.format.datetime.lang;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import jp.co.cam.universal.util.text.format.datetime.DateTimeFormatIF;


/**
 * [universal](commons)Abstract class for datetime format conversion functionality.
 * <br>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 */
public abstract class AbstractDateTimeFormat
    implements DateTimeFormatIF
{
    @Override
    public Date setDateTime(
        final Date pmDate
      , final int  pmYer
      , final int  pmMon
      , final int  pmDay
      , final int  pmHou
      , final int  pmMim
      , final int  pmSec
      , final int  pmMil
    )
    {
    	if (pmDate == null) {
    		throw new IllegalArgumentException("'pmDate' is Necessary");
    	}

    	// calendar
    	final Calendar calendar = getCalendarInstance();

        calendar.setTime(pmDate);

        // preventing the moon from shifting
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));

        // set date
        calendar.set(Calendar.YEAR,  pmYer);
        calendar.set(Calendar.MONTH, pmMon - 1);
        calendar.set(Calendar.DATE,  pmDay);

        // set time
        calendar.set(Calendar.HOUR_OF_DAY, pmHou);
        calendar.set(Calendar.MINUTE,      pmMim);
        calendar.set(Calendar.SECOND,      pmSec);
        calendar.set(Calendar.MILLISECOND, pmMil);

        return calendar.getTime();
    }

    @Override
    public Date setDate(
        final Date pmDate
      , final int  pmYer
      , final int  pmMon
      , final int  pmDay
    )
    {
    	if (pmDate == null) {
    		throw new IllegalArgumentException("'pmDate' is Necessary");
    	}

    	// calendar
    	final Calendar calendar = getCalendarInstance();

        calendar.setTime(pmDate);

        // preventing the moon from shifting
        calendar.set(Calendar.DATE,  calendar.getActualMinimum(Calendar.DATE));

        calendar.set(Calendar.YEAR,  pmYer);
        calendar.set(Calendar.MONTH, pmMon - 1);
        calendar.set(Calendar.DATE,  pmDay);

        return calendar.getTime();
    }

    @Override
    public Date addDate(
        final Date pmDate
      , final int  pmYY
      , final int  pmMM
      , final int  pmDD
    )
    {
    	if (pmDate == null) {
    		throw new IllegalArgumentException("'pmDate' is Necessary");
    	}

    	// calendar
    	final Calendar calendar = getCalendarInstance();

        calendar.setTime(pmDate);

        // base date
        int date = calendar.get(Calendar.DATE);

        // preventing the moon from shifting
        calendar.set(Calendar.DATE,  calendar.getActualMinimum(Calendar.DATE));

        calendar.add(Calendar.YEAR,  pmYY);
        calendar.add(Calendar.MONTH, pmMM);

        calendar.set(Calendar.DATE,  date);
        calendar.add(Calendar.DATE,  pmDD);

        return new Date(calendar.getTimeInMillis());
    }

    @Override
    public Date setTime(
        final Date pmDate
      , final int  pmHou
      , final int  pmMim
      , final int  pmSec
      , final int  pmMil
    )
    {
    	if (pmDate == null) {
    		throw new IllegalArgumentException("'pmDate' is Necessary");
    	}

    	// calendar
        final Calendar calendar = getCalendarInstance();

        calendar.setTime(pmDate);
        calendar.set(Calendar.HOUR_OF_DAY, pmHou);
        calendar.set(Calendar.MINUTE,      pmMim);
        calendar.set(Calendar.SECOND,      pmSec);
        calendar.set(Calendar.MILLISECOND, pmMil);

        return calendar.getTime();
    }

    @Override
    public Date addTime(
        final Date pmDate
       ,final int  pmHH
       ,final int  pmMM
       ,final int  pmSS
       ,final int  pmMS
    )
    {
    	if (pmDate == null) {
    		throw new IllegalArgumentException("'pmDate' is Necessary");
    	}

    	// calendar
        final Calendar calendar = getCalendarInstance();

        calendar.setTime(pmDate);
        calendar.add(Calendar.HOUR_OF_DAY, pmHH);
        calendar.add(Calendar.MINUTE,      pmMM);
        calendar.add(Calendar.SECOND,      pmSS);
        calendar.add(Calendar.MILLISECOND, pmMS);

        return new Date(calendar.getTimeInMillis());
    }


    @Override
    public int getDays(
        final Date pmFrom
      , final Date pmTo
    )
    {
        // calendar
        final Calendar calendar = getCalendarInstance();

        // from
        final long from;

        // to
        final long to;

        calendar.setTime(pmFrom);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE,      0);
        calendar.set(Calendar.SECOND,      0);
        calendar.set(Calendar.MILLISECOND, 0);
        from = calendar.getTimeInMillis();

        calendar.setTime(pmTo);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE,      0);
        calendar.set(Calendar.SECOND,      0);
        calendar.set(Calendar.MILLISECOND, 0);
        to = calendar.getTimeInMillis();

        return (int)((to - from) / (24 * 60 * 60 * 1000));
    }


    @Override
    public String format(
        final Date   pmDate
      , final String pmText
    )
    {
        if (pmDate == null) return null;

        // datetime format
        final SimpleDateFormat format = getDateFormatInstance();

        if (pmText != null && pmText.length() != 0) {
            format.applyPattern(pmText);
            format.setLenient(false);
        }
        return format.format(pmDate);
    }


    @Override
    public java.util.Date parse(
        final String pmDate
      , final String pmText
    )
       	throws ParseException
    {
        if (pmDate == null || pmDate.length() == 0) return null;

        // datetime format
        final SimpleDateFormat format = getDateFormatInstance();

        if (pmText != null && pmText.length() != 0) {
            format.applyPattern(pmText);
        }
        return format.parse(pmDate);
    }


    @Override
    public abstract SimpleDateFormat getDateFormatInstance();

    @Override
    public abstract Calendar getCalendarInstance();


    /**
     * Cleanse country-specific datetime strings.
     * <pre>
     * Datetime is split into date and time by trailing whitespace.
     * See dedicated method for date and time cleaning process.
     * </pre>
     * @param  pmText datetime strings
     * @return date and time
     * @throws ParseException can't convertion data
     */
    protected String[] cleansingDatetimeStrings(
        final String pmText
    )
       	throws ParseException
    {
        if (pmText == null || pmText.length() == 0) return null;

        // datetime
    	final String[] datetime = new String[2];

        // date
    	datetime[0] = pmText.replace(" ", " ").toUpperCase().trim();

    	// ========================================================================================
    	// remove excess whitespace in AM/PM
    	// ========================================================================================
		if (datetime[0].contains("AM") || datetime[0].contains("PM")) {

			if (datetime[0].endsWith(" AM") || datetime[0].endsWith(" PM")) {
				datetime[0] = datetime[0].replaceAll(" (AM|PM)", "$1");
			} else
			if (datetime[0].contains("AM ") || datetime[0].contains("PM ")) {
				datetime[0] = datetime[0].replaceAll("(AM|PM) ", "$1");
			}
		}

    	// ========================================================================================
    	// split date and time
    	// ========================================================================================
		if (datetime[0].contains(":") || datetime[0].contains("AM") || datetime[0].contains("PM")) {
		// exists time

			// separation position
    		int position = datetime[0].lastIndexOf(" ");

        	if (position != -1) {
           	// date and time

        		try {

        			datetime[1] = datetime[0].substring(position + 1);
        			datetime[1] = cleansingTimeStrings(datetime[1]);
	
        			datetime[0] = datetime[0].substring(0, position);
        			datetime[0] = cleansingDateStrings(datetime[0]);

        		} catch (final Exception ex) {
	
	           		// update exception
	    			final ParseException e = new ParseException("can't parse datetime strings - " + pmText, -1);
	
	    			e.initCause(ex);
	
	    			throw e;
				}
        		return datetime;
        	}

    		try {

    			datetime[1] = cleansingTimeStrings(datetime[0]);
    			datetime[0] = null;

    		} catch (final Exception ex) {

           		// update exception
    			final ParseException e = new ParseException("can't parse time strings - " + pmText, -1);

    			e.initCause(ex);

    			throw e;
			}

		} else {
       	// date only

        	try {

        		datetime[0] = cleansingDateStrings(datetime[0]);
        		datetime[1] = null;

        	} catch (final Exception ex) {

           		// update exception
    			final ParseException e = new ParseException("can't parse date strings - " + pmText, -1);

    			e.initCause(ex);

    			throw e;
			}
		}
		return datetime;
	}


    /**
     * Cleanse country-specific date strings.
     * <pre>
     * (1)Standardize the following delimiters to the '/' delimiter.
     *    (A)'-', (B)'.', (C)',', (D)'.', (E)' '
     * (2)Unification of months
     *    (example)'September' to 'SEP'
     * 
     * [Example of conversion result]
     * 01/SEP/1999
     * 
     * [Examples of formats]
     * 01/Sep. 1999(01/Sep, 1999)
     * 01-Sep. 1999(01-Sep, 1999)
     * 01.Sep. 1999(01.Sep, 1999)
     * 01 Sep. 1999(01 Sep, 1999)
     * 01-Sep-1999
     * 1st Sep, 1999
     * </pre>
     * @param  pmText date strings
     * @return date strings
     * @throws ParseException can't convertion data
     */
    protected String cleansingDateStrings(
        final String pmText
    )
        throws ParseException
    {
        if (pmText == null || pmText.length() == 0) return null;

        // Date
    	String date = pmText.replace(" ", " ").toUpperCase().trim();

    	// ========================================================================================
    	// Delete days anniversary(1st Sep, 1999)
        // ========================================================================================
    	date = date.replaceFirst("([0-9])(ST|ND|RD|TH)", "$1");

        // ========================================================================================
        // Unification of months
        // ========================================================================================
    	for (final String[] month : months)
    	{
        	date = date.replace(month[0], month[1]);
    	}

        // ========================================================================================
        // Unification of date separators.
        // ========================================================================================

        // dd/MMM. YYYY(dd/MMM, YYYY)
    	date = date.replace(", ", "/");
    	date = date.replace(". ", "/");
    	date = date.replace(",",  "/");
    	date = date.replace(".",  "/");

        // dd-MMM-YYYY
    	date = date.replace("-", "/");

    	// dd MMM YYYY
    	date = date.replace(" ", "/");

    	// 01/Sep.,1999 -> 01/Sep//1999 -> 01/Sep/1999
    	while (date.contains("//"))
    	{
        	date = date.replace("//", "/");
		}
    	return date;
    }

    /**
     * Cleanse country-specific time strings.
     * <pre>
     * (1)Standardize the following delimiters to the ':' delimiter.
     * (2)Conversion of time beyond 24 hours(add '(+{n} days)').
     *    (example)26:00:00.000 converted to 02:00:00.000
     *    
     * (3)Convert time with AM/PM.
     *    (example)2:00PM converted to 14:00:00.000
     * (4)Unification of time digits(HH:mm:ss.SSS).
     *    (example)2:00 converted to 02:00:00.000
     *    
     * [Example of conversion result]
     * 02:00:00.000
     *
     * [Examples of formats]
     * 2:00:00,000
     * 2:00.00
     * 2:00AM(AM02:00)
     * </pre>
     * @param  pmText time strings
     * @return formed time strings(HH:mm:ss.SSS)
     * @throws ParseException can't convertion data
     */
    protected String cleansingTimeStrings(
    	final String pmText
    )
        throws ParseException
    {
        if (pmText == null || pmText.length() == 0) return null;

        // Time
    	String time = pmText.replace(" ", " ").toUpperCase().trim();

    	// ========================================================================================
    	// 12-hour or 24-hour
    	// ========================================================================================

    	// pm(+12)
    	boolean plus12;

    	// additional days
   		int dd;

   		if (time.startsWith("PM") || time.endsWith("PM")) {

    		time = time.replace("PM", "").trim();

    		plus12 = true;

    	} else
   		if (time.startsWith("AM") || time.endsWith("AM")) {

       		time = time.replace("AM", "").trim();

       		plus12 = false;

       	} else {
       		plus12 = false;
    	}

    	// ========================================================================================
        // unification of time separators.
    	// ========================================================================================

    	// HH:MM:ss,SSS
    	time = time.replace(",", ":");

        // HH:mm.ss(.SSS) -> HH:mm:ss(:SSS)
		time = time.replace(".", ":");

    	String[] temp = time.split(":");

       	if (temp.length < 4) {

           	// [H, m, s, S]
       		final String[] copy = new String[4];

       		System.arraycopy(temp, 0, copy, 0, temp.length);

       		if (copy[1] == null) copy[1] = "0";
       		if (copy[2] == null) copy[2] = "0";
       		if (copy[3] == null) copy[3] = "0";

       		temp = copy;
       	}

    	// ========================================================================================
    	// 12-hour or 24-hour
    	// ========================================================================================

       	try {

       		int hh = Integer.parseInt(temp[0]);
       		int mm = Integer.parseInt(temp[1]);
       		int ss = Integer.parseInt(temp[2]);
       		int ms = Integer.parseInt(temp[3]);

   			if (ms < 0) {
   				throw new IllegalArgumentException("Millisecond - " + ms);
   			}
   			ss += ms / 1000;
   			ms = ms % 100;

   			if (ss < 0) {
   				throw new IllegalArgumentException("Second - " + ms);
   			}
   			mm += ss / 60;
   			ss = ss % 60;

   			if (mm < 0) {
   				throw new IllegalArgumentException("Minutes - " + ms);
   			}
   			hh += mm / 60;
   			mm = mm % 60;

   			if (hh < 0) {
   				throw new IllegalArgumentException("hour - " + ms);
   			}

       		if (plus12) hh = hh + 12;
       		dd = hh / 24;
       		hh = hh % 24;

       		temp[0] = String.valueOf(hh);
       		temp[1] = String.valueOf(mm);
       		temp[2] = String.valueOf(ss);
       		temp[3] = String.valueOf(ms);

       	} catch (final Exception ex) {

       		// update exception
       		final ParseException c = new ParseException("can't convertion data - " + pmText, -1);

       		c.initCause(ex);

       		throw c;
		}

    	// ========================================================================================
    	// unification of time digits
    	// ========================================================================================

       	// [H, m, s, S] -> [HH, mm, ss, SSS]
       	for (int i = temp[0].length(); i < 2; i++) temp[0] = "0" + temp[0];
       	for (int i = temp[1].length(); i < 2; i++) temp[1] = "0" + temp[1];
       	for (int i = temp[2].length(); i < 2; i++) temp[2] = "0" + temp[2];
       	for (int i = temp[3].length(); i < 3; i++) temp[3] = "0" + temp[3];

   		if (0 < dd) {
   			temp[3] += "(+" + dd + "days)";
   		}
    	return temp[0] + ":" + temp[1] + ":" + temp[2] + "." + temp[3];
    }

    /**
     * Remove option strings. 
     * @param  pmText datetime strings
     * @return datetime strings
     */
    protected String removeOptionStrings
    (
       	final String pmText
    )
    {
        if (pmText == null || pmText.length() == 0) return null;

    	// pattern of days(+{n}days)
    	final Pattern pattern = Pattern.compile("(\\(\\+)([0-9]+)(days\\))");

    	String ret = pmText;

    	if (pattern.matcher(ret).find()) {
    	// additional days option

    		ret = pattern.matcher(ret).replaceAll("");
    	}

    	// add remove process when increasing options.

    	return ret;
    }
}
