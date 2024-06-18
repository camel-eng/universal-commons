package jp.co.cam.universal.context;

import java.io.Serializable;
import java.util.TimeZone;

import jp.co.cam.universal.util.DataMap;


/**
 * [universal](commons)This class is the management class for user information.
 * <br>
 * <br>See UserInfoFactory for usage
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>######################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>######################################################################
 * @see jp.co.cam.universal.context.UserInfoFactory
 * @see jp.co.cam.universal.context.UserContext
 */
public interface UserInfoIF
	extends Serializable
{
    /** Key : Key of Thread Local */
    String CONTEXT_SAVE_KEY = "@" + UserInfoIF.class.getName();


    /** Key : User information for display(user id + user name) */
    String KEY_USER_DISP  = "USER_DISP";

    /** Key : User ID */
    String KEY_USER_CODE  = "USER_CODE";

    /** Key : User name */
    String KEY_USER_NAME  = "USER_NAME";

    /** Key : Language */
    String KEY_LANGUAGE   = "LANGUAGE";

    /** Key : Country */
    String KEY_COUNTRY    = "COUNTRY";


    /** Default : User ID if user is not authenticated */
    String GUEST_USER = "GUEST";


    /**
     * This method returns the data container for user.
     * <br>The value of this container will be stored with user information.
     * <br>And It's available using @context:key.
     * @return Data container
     */
    DataMap<String, Object> getInfinityDataMap();


    /**
     * This method returns the default TimeZone.
     * <br>* Initial value : Refers to the system TimeZone setting.
     * @return TimeZone
     */
    TimeZone getDefaultTimezone();

    /**
     * This method sets the default TimeZone.
     * <br>* Initial value : Refers to the system TimeZone setting.
     * @param  pmZone TimeZone
     */
    void setDefaultTimezone(
    	final TimeZone pmZone
    );

    /**
     * This method returns the TimeZone for user.
     * <br>* Initial value : Refers to the system TimeZone setting.
     * @return TimeZone
     */
    TimeZone getTimezone();

    /**
     * This method sets the TimeZone for user.
     * <br>* Initial value : Refers to the system TimeZone setting.
     * @param  pmZone TimeZone
     */
    void setTimezone(
    	final TimeZone pmZone
    );

    /**
     * This method returns the language for user.
     * <br>* Initial value : Refers to the system Locale setting.
     * @return Language (example:ja, en)
     */
    String getLanguage();

    /**
     * This method sets the language for user.
     * <br>* Initial value : Refers to the system Locale setting.
     * @param  pmLang Language (example:ja, en)
     */
    void setLanguage(
    	final String pmLang
    );

    /**
     * This method returns the country for user.
     * <br>* Initial value : Refers to the system Locale setting.
     * @return Country (example:JP, US)
     */
    String getCountry();

    /**
     * This method sets the country for user.
     * <br>* Initial value : Refers to the system Locale setting.
     * @param  pmCountry Country (example:JP, US)
     */
    void setCountry(
    	final String pmCountry
    );


    /**
     * This method returns the User ID.
     * @return User ID
     */
    String getUserID();

    /**
     * This method sets the User ID.
     * @param  pmCode User ID
     */
    public void setUserID(
        final String pmCode
    );

    /**
     * This method returns the User name.
     * @return User name
     */
    String getUserName();

    /**
     * This method sets the User name.
     * @param  pmName User name
     */
    public void setUserName(
        final String pmName
    );


    /**
     * This method returns the value corresponding to "@context:command".
     * <br>Method for handling @context:command in rule engine.
     * <br>You can override and extend this method.
     * @param  pmCmd {@literal @}context:command
     * @return value
     */
	Object getCommandValue(
		final String pmCmd
	);


	/**
     * This method clears all data.
     * <br>* Excluding : Language/Country/TimeZone
     */
    void clear();
}
