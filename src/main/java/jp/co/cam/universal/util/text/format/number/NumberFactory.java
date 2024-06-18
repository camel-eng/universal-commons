package jp.co.cam.universal.util.text.format.number;

import java.util.Hashtable;


/**
 * [universal](commons))This class is the factory class for number format class.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>[JDK9]Class.newInstance() to Constructor.newInstance()
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 */
public class NumberFactory
{
    /** Default class(Format:#,##0.##) */
    public static final String DEFAULT_CLASSNAME
     = "jp.co.cam.universal.util.text.format.number.lang.NumberFormat";


    /**
     * Get default number format.
     * @return NumberFormatIF
     * @throws IllegalAccessException can't generate class
     */
    public static NumberFormatIF get()
       	throws IllegalAccessException
    {
        return NumberFactory.get(null, null);
    }


    /**
     * Get number format.
     * @param  pmLanguage language
     * @param  pmCountry  country
     * @return NumberFormatIF
     * @throws IllegalAccessException can't generate class
     */
    public static NumberFormatIF get(
        final String pmLanguage
      , final String pmCountry
    )
    	throws IllegalAccessException
    {
        return NumberFactory.instance.getResource(pmLanguage, pmCountry);
    }

    /** Singleton Instance */
    private final static NumberFactory instance = new NumberFactory();


    /** No instantiation. */
    private NumberFactory()
    {
    	super();
    }

    /** cache */
    private final Hashtable<String, NumberFormatIF> _cache
     = new Hashtable<>();


    /**
     * Get number format.
     * @param  pmLanguage language
     * @param  pmCountry  country
     * @return NumberFormatIF
     * @throws IllegalAccessException can't generate class
     */
    private NumberFormatIF getResource(
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
                    final NumberFormatIF ret = generator(pmLanguage, pmCountry);

                    _cache.put(name, ret);
                    
                    return ret;
                }
            }
        }
        return _cache.get(name);
    }

    /**
     * Generate number format.
     * @param  pmLanguage language
     * @param  pmCountry  country
     * @return NumberFormatIF
     * @throws IllegalAccessException can't generate class
     */
    private NumberFormatIF generator(
        final String pmLanguage
      , final String pmCountry
    )
    	throws IllegalAccessException
    {
        // use class
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

            return (NumberFormatIF)type.getConstructor(new Class[0]).newInstance();

        } catch (final Exception ex) {
        	throw new IllegalAccessException("can't instantiate class - " + type.getName());
        }
    }
}
