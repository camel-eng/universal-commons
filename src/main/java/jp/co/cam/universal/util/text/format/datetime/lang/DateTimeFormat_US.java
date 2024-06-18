package jp.co.cam.universal.util.text.format.datetime.lang;

import java.text.*;
import java.util.Locale;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.regex.Pattern;

import jp.co.cam.universal.context.UserInfoFactory;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <br>United States of America(Type America)
 * <br>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>########################################################################
 */
public class DateTimeFormat_US
    extends AbstractDateTimeFormat
{
	/** Date format */
	public static final String FORMAT_DATE = "MMM/dd/yyyy";

	/** Time format */
	public static final String FORMAT_TIME = "HH:mm:ss.SSS";


	@Override
    public String formatDateTime(
        final java.util.Date pmDate
    )
    {
        return format(pmDate, DateTimeFormat_US.FORMAT_DATE + " " + DateTimeFormat_US.FORMAT_TIME);
    }

    @Override
    public String formatDate(
        final java.util.Date pmDate
    )
    {
        return format(pmDate, DateTimeFormat_US.FORMAT_DATE);
    }

    @Override
    public String formatTime(
        final java.util.Date pmDate
    )
    {
        return format(pmDate, DateTimeFormat_US.FORMAT_TIME);
    }


    @Override
    public java.util.Date parse(
        final String pmText
    )
       	throws ParseException
    {
        if (pmText == null || pmText.length() == 0) return null;

        // ========================================================================================
        // split date and time
        // ========================================================================================

        // datetime strings
    	String[] datetime = cleansingDatetimeStrings(pmText);

    	// (+{n}days)
		String days = null;

		// result date
		Date ret;

        // ========================================================================================
        // parse options
        // ========================================================================================

		// time
		final String time = removeOptionStrings(datetime[1]);

		if (!datetime[1].equals(time)) {
    		days = datetime[1].replace(time, "");
		}
  		datetime[1] = time;

        // ========================================================================================
        // conversion data
        // ========================================================================================
    	if (datetime[0] != null && datetime[1] != null) {
    	// datetiem

    		ret = super.parse(datetime[0] + " " + datetime[1], DateTimeFormat_US.FORMAT_DATE + " " + DateTimeFormat_US.FORMAT_TIME);

    	} else
    	if (datetime[0] != null) {
      	// date

    		ret = super.parse(datetime[0], DateTimeFormat_US.FORMAT_DATE);

    	} else
    	if (datetime[1] != null) {
       	// time

    		ret = super.parse(datetime[1], DateTimeFormat_US.FORMAT_TIME);

    	} else {

    		throw new ParseException("can't convertion data - " + pmText, -1);
    	}

        // ========================================================================================
        // additional days option
        // ========================================================================================
    	if (days != null) {

    		try {

    			ret = addDate(ret, 0, 0, Integer.parseInt(days.replaceAll("([^0-9]+)", "")));

    		} catch (final Exception ex) {

           		// update exception
    			final ParseException e = new ParseException("invalid option(+{n}days) - " + pmText, -1);

    			e.initCause(ex);

    			throw e;
			}
    	}
    	return ret;
    }


    @Override
    public SimpleDateFormat getDateFormatInstance()
    {
    	// datetime format
    	final SimpleDateFormat ret
    	 = new SimpleDateFormat(DateTimeFormat_US.FORMAT_DATE, Locale.US);

    	ret.setTimeZone(UserInfoFactory.get().getTimezone());

        return ret;
    }

    @Override
    public Calendar getCalendarInstance()
    {
        return Calendar.getInstance(UserInfoFactory.get().getTimezone());
    }


	@Override
    protected String cleansingDateStrings(
    	final String pmData
    )
    	throws ParseException
    {
        if (pmData == null || pmData.length() == 0) return null;

        // ========================================================================================
        // unification of delimiters
        // ========================================================================================

        // date
    	String date = super.cleansingDateStrings(pmData);

        // ========================================================================================
    	// data completion
        // ========================================================================================

    	// months
    	final List<String> monthsArray = new ArrayList<>();
 
    	for (final String[] month : months)
    	{
    		monthsArray.add(month[1]);
    	}

		// [MM, DD, YYYY]
		final String[] parts = date.split("/");

		if (parts.length != 3) {
			throw new ParseException("can't convertion date(MM/DD/YYYY) - " + pmData, -1);
		}

		try {

	    	// pattern of month
	    	final Pattern pattern = Pattern.compile(String.join("|", monthsArray));

			if (!pattern.matcher(date).find()) {
			// MM -> MMM

				// M
				int m = Integer.parseInt(parts[0]);
				if (m < 1 || 12 < m) {
	    			throw new IllegalArgumentException();
	    		}

	    		// MM/DD/YYYY -> MMM/DD/YYYY
	    		parts[0] = months[m - 1][1];
			}
			
			if (pattern.matcher(parts[1]).find()) {
			// DD/MMM/YYYY -> MMM/DD/YYYY

				// MMM
				final String mmm = parts[1];

				parts[1] = parts[0];
				parts[0] = mmm;
			}

			if (pattern.matcher(parts[0]).find()) {
			// MMM/DD/YYYY

				// D
				int d = Integer.parseInt(parts[1]);

				if (d < 1 || 31 < d) {
					throw new IllegalArgumentException("day - " + d);
				}
				for (int i = parts[1].length(); i < 2; i++)
				{
					parts[1] = "0" + parts[1];
				}

				// Y
				int y = Integer.parseInt(parts[2]);
				if (y < 0) {
					throw new IllegalArgumentException("year - " + d);
				}

				return String.join("/", parts);
			}

			throw new IllegalArgumentException("month - " + parts[0]);

		} catch (final Exception ex) {

       		// update exception
			final ParseException e = new ParseException("can't convertion date(MM/DD/YYY) - " + pmData, -1);

			e.initCause(ex);

			throw e;
		}
    }
}
