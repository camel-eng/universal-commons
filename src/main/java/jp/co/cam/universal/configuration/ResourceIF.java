package jp.co.cam.universal.configuration;


/**
 * [universal](commons)Properties reference class.
 * <br>See ResourceFactory for usage
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 20234.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 * @see jp.co.cam.universal.configuration.ResourceFactory
 */
public interface ResourceIF
{
    /** Default : basic settings : /common.properties  */
	public static final String DEFAULT_SETTINGS
     = "/common.properties";

    /** Key : Setting the default control class */
    String DEFAULT_CLASS
     = "controller";


    /**
     * This method returns the key name of settings.
     * @return Keys
     */
    String[] getKeys();


    /**
     * This method returns true if the key exists.
     * @param  pmCode Key
     * @return true = exist or false = not exist
     */
    boolean hasKey(
        final String pmCode
    );

    /**
     * This method returns the settings, supplemented with parameters.
     * <br>It's convert {n} in settings using parameters.
     * @param  pmCode Key
     * @param  pmPart Parameters
     * @return Setting
     */
    String get(
        final String pmCode
      , final String[] pmPart
    );

    /**
     * This method returns the settings.
     * @param  pmCode Key
     * @return Setting
     */
    String get(
        final String pmCode
    );


    /**
     * This method returns the JSON-Data.
     * @return JSON-Data
     */
    String toString();
}
