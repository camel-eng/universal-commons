package jp.co.cam.universal.context.impl;

import java.io.IOException;
import java.util.TimeZone;
import java.util.Locale;

import jp.co.cam.universal.configuration.ResourceFactory;
import jp.co.cam.universal.context.UserInfoIF;
import jp.co.cam.universal.util.DataMap;


/**
 * [universal](commons)This class is the abstract class of user information management class.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2020.12 Reconfigured.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 */
public abstract class AbstractUserInfo
    implements UserInfoIF
{
    /** UID */
    private static final long serialVersionUID = 2023L;

    /** User data container */
    final DataMap<String, Object> _infinityDataMap
     = new DataMap.SynchronizeDataMap<>();

    /** Default timezone */
    TimeZone _defaultTimezone;

    /** User timezone */
    TimeZone _timezone;

    /** User language */
    String _language;

    /** User country */
    String _country;


    @Override
    public DataMap<String, Object> getInfinityDataMap()
    {
        return _infinityDataMap;
    }


    @Override
    public TimeZone getDefaultTimezone()
    {
        return _defaultTimezone;
    }

    @Override
    public void setDefaultTimezone(
        final TimeZone pmData
    )
    {
    	if (pmData == null) {
        // ========================================================================================
        // default timezone
        // ========================================================================================

    		_defaultTimezone = TimeZone.getDefault();

    	} else {
        // ========================================================================================
        // custom
        // ========================================================================================

    		_defaultTimezone = pmData;
    	}
    }


    @Override
    public TimeZone getTimezone()
    {
        return _timezone;
    }

    @Override
    public void setTimezone(
        final TimeZone pmData
    )
    {
        if (pmData == null) {
        // ========================================================================================
        // default timezone
        // ========================================================================================

        	_timezone = _defaultTimezone;

        } else {
        // ========================================================================================
        // custom
        // ========================================================================================

        	_timezone = pmData;
        }
    }


    @Override
    public String getLanguage()
    {
    	return _language;
    }

    @Override
    public void setLanguage(
        final String pmData
    )
    {
        if (pmData == null || pmData.length() == 0) {
        // ========================================================================================
        // default language
        // ========================================================================================

        	// ------------------------------------------------------------------------------------
        	// settings
        	// ------------------------------------------------------------------------------------
        	try {
        		_language = ResourceFactory.getDefault().get("language");
        	} catch (final IOException wr) {
        		_language = null;
			}

        	// ------------------------------------------------------------------------------------
        	// locale
        	// ------------------------------------------------------------------------------------
        	if (_language == null) {
        		_language = Locale.getDefault().getLanguage();
        	}

        	if (_language != null) {
    			_language = _language.toLowerCase();
    		}

        } else {
        // ========================================================================================
        // custom
        // ========================================================================================

        	_language = pmData.toLowerCase();
        }
    }


    @Override
    public String getCountry()
    {
        return _country;
    }

    @Override
    public void setCountry(
        final String pmData
    )
    {
        if (pmData == null || pmData.length() == 0) {
        // ========================================================================================
        // default countory
        // ========================================================================================

        	// ------------------------------------------------------------------------------------
        	// settings
        	// ------------------------------------------------------------------------------------
        	try {
        		_country = ResourceFactory.getDefault().get("language");
        	} catch (final IOException wr) {
        		_country = null;
			}

        	// ------------------------------------------------------------------------------------
        	// locale
        	// ------------------------------------------------------------------------------------
        	if (_country == null) {
        		_country = Locale.getDefault().getCountry();
        	}

        	if (_country != null) {
        		_country = _country.toLowerCase();
    		}

        } else {
        // ========================================================================================
        // custom
        // ========================================================================================

        	_country = pmData.toLowerCase();
        }
    }


    @Override
    public void clear()
    {
    	_infinityDataMap.clear();

    	setDefaultTimezone(null);
    	setTimezone(null);
    	setLanguage(null);
    	setCountry(null);
    }


    /** Constructor. */
    protected AbstractUserInfo()
    {
        super();

        clear();
    }
}
