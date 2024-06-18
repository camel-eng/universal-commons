package jp.co.cam.universal.util.text.format.datetime.lang;

import java.text.*;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.cam.universal.context.UserInfoFactory;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <br>Federal Republic of Germany(Type Germany)
 * <br>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>########################################################################
 */
public class DateTimeFormat_DE
    extends AbstractDateTimeFormat
{
	/** Date format */
	public static final String FORMAT_DATE = "yyyy/MM/dd";

	/** Time format */
	public static final String FORMAT_TIME = "HH:mm:ss.SSS";

	@Override
    public String formatDateTime(
        final java.util.Date pmDate
    )
    {
        return format(pmDate, DateTimeFormat_DE.FORMAT_DATE + " " + DateTimeFormat_DE.FORMAT_TIME);
    }

    @Override
    public String formatDate(
        final java.util.Date pmDate
    )
    {
        return format(pmDate, DateTimeFormat_DE.FORMAT_DATE);
    }

    @Override
    public String formatTime(
        final java.util.Date pmDate
    )
    {
        return format(pmDate, DateTimeFormat_DE.FORMAT_TIME);
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

    		ret = super.parse(datetime[0] + " " + datetime[1], DateTimeFormat_DE.FORMAT_DATE + " " + DateTimeFormat_DE.FORMAT_TIME);

    	} else
    	if (datetime[0] != null) {
      	// date

    		ret = super.parse(datetime[0], DateTimeFormat_DE.FORMAT_DATE);

    	} else
    	if (datetime[1] != null) {
       	// time

    		ret = super.parse(datetime[1], DateTimeFormat_DE.FORMAT_TIME);

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

           		// Update exception
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
    	 = new SimpleDateFormat(DateTimeFormat_DE.FORMAT_DATE, Locale.GERMANY);

    	ret.setTimeZone(UserInfoFactory.get().getTimezone());

        return ret;
    }

    @Override
    public Calendar getCalendarInstance()
    {
        return Calendar.getInstance(UserInfoFactory.get().getTimezone());
    }


    /**
     * Cleanse country-specific date strings.
     * @param  pmText date strings
     * @return formed date strings(MMM/DD/YYYY)
     * @throws ParseException can't convertion data
     */
    protected String cleansingDateStrings(
    	final String pmText
    )
    	throws ParseException
    {
        if (pmText == null || pmText.length() == 0) return null;

        // ========================================================================================
        // unification of delimiters
        // ========================================================================================

        // date
    	String date = super.cleansingDateStrings(pmText);

        // ========================================================================================
    	// data completion
        // ========================================================================================

    	// months
    	final List<String> monthsArray = new ArrayList<>();
 
    	for (final String[] month : months)
    	{
    		monthsArray.add(month[1]);
    	}

		// [YYYY, MM, DD]
		final String[] parts = date.split("/");

		if (parts.length != 3) {
			throw new ParseException("can't convertion date(YYYY/MM/DD) - " + pmText, -1);
		}

		try {

	    	// pattern of month
	    	final Pattern pattern = Pattern.compile(String.join("|", monthsArray));

	    	if (pattern.matcher(date).find()) {
	    	// MMM(DD/MMM/YYYY)

				if (pattern.matcher(parts[0]).find()) {
				// MMM/DD/YYYY -> DD/MMM/YYYY

					// MMM
					final String mmm = parts[0];

					parts[0] = parts[1];
					parts[1] = mmm;
				}

				if (pattern.matcher(parts[1]).find()) {
				// DD/MMM/YYYY -> DD/MMM/YYYY

					// M
					int m = -1;

					for (int i = 0; i < monthsArray.size(); i++)
					{
						if (parts[1].equals(monthsArray.get(i))) {
							m = i + 1;
						}
					}
					parts[1] = String.valueOf(m);

					if (m < 1 || 12 < m) {
						throw new IllegalArgumentException("month - " + m);
					}
			       	for (int i = parts[1].length(); i < 2; i++) parts[1] = "0" + parts[1];

					// D
					int d = Integer.parseInt(parts[0]);

					if (d < 1 || 31 < d) {
						throw new IllegalArgumentException("day - " + d);
					}
					for (int i = parts[0].length(); i < 2; i++) parts[0] = "0" + parts[0];

					// Y
					int y = Integer.parseInt(parts[2]);
					if (y < 0) {
						throw new IllegalArgumentException();
					}

					return String.join("/", new String[]{parts[2], parts[1], parts[0]});
				}

	    	} else {
			// YYYY/MM/DD

				// Y
				int y = Integer.parseInt(parts[0]);
				if (y < 0) {
					throw new IllegalArgumentException();
				}

				// M
				int m = Integer.parseInt(parts[1]);

				if (m < 1 || 12 < m) {
					throw new IllegalArgumentException("month - " + m);
				}
				for (int i = parts[1].length(); i < 2; i++) parts[1] = "0" + parts[1];

				// D
				int d = Integer.parseInt(parts[2]);

				if (d < 1 || 31 < d) {
					throw new IllegalArgumentException("day - " + d);
				}
				for (int i = parts[2].length(); i < 2; i++) parts[2] = "0" + parts[2];

				return String.join("/", parts);
	    	}

			throw new IllegalArgumentException("can't parse data");

		} catch (final Exception ex) {

       		// update exception
			final ParseException e = new ParseException("can't convertion date(DD/MM/YYY) - " + pmText, -1);

			e.initCause(ex);

			throw e;
		}
    }
}
