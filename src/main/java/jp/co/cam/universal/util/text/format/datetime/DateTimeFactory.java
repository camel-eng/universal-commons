package jp.co.cam.universal.util.text.format.datetime;

import java.util.Hashtable;


/**
 * [universal](commons)This class is the factory class for datetime format class.
 * <br>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>[JDK9]Class.newInstance() to Constructor.newInstance()
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 */
public class DateTimeFactory
{
    /** Default class(Format:MMM/dd/yyyy) */
    public static final String DEFAULT_CLASSNAME
     = "jp.co.cam.universal.util.text.format.datetime.lang.DateTimeFormat";


    /**
     * Get default datetime format.
     * @return DateTimeFormatIF
     * @throws IllegalAccessException can't generate class
     */
    public static DateTimeFormatIF get()
       	throws IllegalAccessException
    {
        return DateTimeFactory.get(null, null);
    }


    /**
     * Get datetime format.
     * @param  pmLanguage language
     * @param  pmCountry  country
     * @return DateTimeFormatIF
     * @throws IllegalAccessException can't generate class
     */
    public static DateTimeFormatIF get(
        final String pmLanguage
      , final String pmCountry
    )
    	throws IllegalAccessException
    {
        return DateTimeFactory.instance.getResource(pmLanguage, pmCountry);
    }

    /** Singleton Instance */
    private final static DateTimeFactory instance = new DateTimeFactory();


    /** No instantiation. */
    private DateTimeFactory()
    {
    	super();
    }

    /** cache */
    private final Hashtable<String, DateTimeFormatIF> _cache
     = new Hashtable<>();


    /**
     * Get datetime format.
     * @param  pmLanguage language
     * @param  pmCountry  country
     * @return NumberFormatIF
     * @throws IllegalAccessException can't generate class
     */
    private DateTimeFormatIF getResource(
        final String pmLanguage
      , final String pmCountry
    )
       	throws IllegalAccessException
    {
        // cache storage name
        String name;

        if (pmLanguage != null && pmCountry != null) {
            name = pmLanguage + "_" + pmCountry;
        } else
        if (pmLanguage != null) {
            name = pmLanguage;
        } else
        if (pmCountry != null) {
            name = pmCountry;
        } else {
            name = "default";
        }

        if (!_cache.containsKey(name)) {
        	
            synchronized (_cache)
            {
                if (!_cache.containsKey(name)) {

                	// number format
                    final DateTimeFormatIF ret = generator(pmLanguage, pmCountry);

                    _cache.put(name, ret);
                    
                    return ret;
                }
            }
        }
        return _cache.get(name);
    }

    /**
     * Generate datetime format.
     * @param  pmLanguage language
     * @param  pmCountry  country
     * @return DateTimeFormatIF
     * @throws IllegalAccessException can't generate class
     */
    private DateTimeFormatIF generator(
        final String pmLanguage
      , final String pmCountry
    )
    	throws IllegalAccessException
    {
        // Use Class
        Class<?> type;

        // ========================================================================================
        // select class
        // ========================================================================================
        if ((pmLanguage == null || pmLanguage.length() == 0)
         && (pmCountry == null || pmCountry.length() == 0)) {

        	try {

        		type = Class.forName(DEFAULT_CLASSNAME);

            } catch (ClassNotFoundException ex) {
	        	throw new IllegalAccessException("class not found - " + DEFAULT_CLASSNAME);
			}

        } else
        if (pmLanguage == null || pmLanguage.length() == 0) {

            // ------------------------------------------------------------------------------------
            // country
            // ------------------------------------------------------------------------------------
            try {

                type = Class.forName(DEFAULT_CLASSNAME + "_" + pmCountry.toUpperCase());

            } catch (final Throwable skip1) {

            	try {

            		type = Class.forName(DEFAULT_CLASSNAME);

                } catch (ClassNotFoundException ex) {
		        	throw new IllegalAccessException("class not found - " + DEFAULT_CLASSNAME);
				}
            }

        } else
        if (pmCountry == null || pmCountry.length() == 0) {

            // ------------------------------------------------------------------------------------
            // language
            // ------------------------------------------------------------------------------------
            try {

                type = Class.forName(DEFAULT_CLASSNAME + "_" + pmLanguage.toLowerCase());

            } catch (final Throwable skip1) {

            	try {

            		type = Class.forName(DEFAULT_CLASSNAME);

                } catch (ClassNotFoundException ex) {
		        	throw new IllegalAccessException("class not found - " + DEFAULT_CLASSNAME);
				}
            }

        } else {

        	// lowerCase-language
        	final String language = pmLanguage.toLowerCase();

        	// upperCase-country
        	final String country = pmCountry.toUpperCase();

        	// ------------------------------------------------------------------------------------
            // language + country
            // ------------------------------------------------------------------------------------
            try {

                type = Class.forName(DEFAULT_CLASSNAME + "_" + language + "_" + country);

            } catch (final Throwable skip1) {

                // --------------------------------------------------------------------------------
                // country
                // --------------------------------------------------------------------------------
                try {

                    type = Class.forName(DEFAULT_CLASSNAME + "_" + country);

                } catch (final Throwable skip2) {

                    // ----------------------------------------------------------------------------
                    // language
                    // ----------------------------------------------------------------------------
                    try {

                        type = Class.forName(DEFAULT_CLASSNAME + "_" + language);

                    } catch (final Throwable skip3) {

                    	try {

                    		type = Class.forName(DEFAULT_CLASSNAME);

                        } catch (ClassNotFoundException ex) {
        		        	throw new IllegalAccessException("class not found - " + DEFAULT_CLASSNAME);
        				}
                    }
                }
            }
        }

        // ========================================================================================
        // create new instance
        // ========================================================================================
        try {

            return (DateTimeFormatIF)type.getConstructor(new Class[0]).newInstance();

        } catch (final Exception ex) {
        	throw new IllegalAccessException("can't instantiate class - " + type.getName());
        }
    }
}
