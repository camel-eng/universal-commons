package jp.co.cam.universal.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Hashtable;
import java.util.Map;

import jp.co.cam.universal.configuration.impl.DefaultResource;
import jp.co.cam.universal.context.UserInfoFactory;
import jp.co.cam.universal.context.UserInfoIF;
import jp.co.cam.universal.util.io.FileRepository;


/**
 * [universal](commons)This class is the factory class for properties reference class.
 * <pre>This class provides easy access to properties files,
 * And loaded files are stored, so It can be used quickly for repeated use.
 * 
 * [How to use]
 * 
 * case 1:
 * 
 * // This method loads '/common.properties' depending on the user's country and language(*1).
 * ResourceIF common = ResourceFactory.getDefault();
 * 
 * System.out.println(common.get("{key}"));
 * 
 * 
 * case 2:
 * 
 * // This method loads a properties file depending on the　user's country and language(*1).
 * ResourceIF properties = ResourceFactory.get("{properties file path}");
 * 
 * System.out.println(properties.get("{key}"));
 * 
 * 
 * (*1)UserInfo is used to retrieve the user's country and language.
 * </pre>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>        Bug fixes:overflow
 * <br>########################################################################
 * @see jp.co.cam.universal.context.UserInfoFactory
 * @see jp.co.cam.universal.context.UserInfoIF
 */
public class ResourceFactory
{
    /** Singleton instance. */
    private final static ResourceFactory instance
     = new ResourceFactory();

    /** Resource cache. */
    private final Map<String, ResourceIF> _cache
     = new Hashtable<>();


    /**
     * This method returns the specified properties, depending on the user's country and language.
     * <br>* Using user's language and country.
     * @param  pmProperties File path
     * @return ResourceIF
     * @throws IOException Throws in the following cases:
     * <br>- Failed to create properties reference class.
     * <br>- Failed to load property file.
     * <br>- File not found or Access denied.
     */
    public static ResourceIF get(
        final String pmProperties
    )
       	throws IOException
    {
        final UserInfoIF userInfo = UserInfoFactory.get();

        return ResourceFactory.get(pmProperties, userInfo.getLanguage(), userInfo.getCountry());
    }

    /**
     * This method returns the specified properties.
     * @param  pmProperties File path
     * @param  pmLanguage   Language
     * @param  pmCountry    Country
     * @return ResourceIF
     * @throws IOException Throws in the following cases:
     * <br>- Failed to create properties reference class.
     * <br>- Failed to load property file.
     * <br>- File not found or Access denied.
     */
    public static ResourceIF get(
        final String pmProperties
      , final String pmLanguage
      , final String pmCountry
    )
    	throws IOException
    {
    	return ResourceFactory.instance.getResource(pmProperties, pmLanguage, pmCountry);
    }


    /**
     * This method returns the basic settings.
     * <br>That requires a classpath to the basic settings file '/common.properties'.
     * @return ResourceIF
     * @throws IOException Throws in the following cases:
     * <br>- Failed to create properties reference class.
     * <br>- Failed to load property file.
     * <br>- File not found or Access denied.
     */
    public static ResourceIF getDefault()
       	throws IOException
    {
    	return ResourceFactory.instance.getResource(ResourceIF.DEFAULT_SETTINGS, null, null);
    }


    /**
     * This method releases all stored properties.
     * <br>Please execute this method, when there is a change in the loaded properties.
     */
    public static void clear()
    {
        synchronized (ResourceFactory.instance)
        {
        	ResourceFactory.instance._cache.clear();
        }
    }


    /** This class doesn't require instantiation. */
    private ResourceFactory()
    {
    	super();
    }


    /**
     * This method returns the dspecified properties.
     * @param  pmProperties File path
     * @param  pmLanguage   Language
     * @param  pmCountry    Country
     * @return ResourceIF
     * @throws IOException
     *  <br>Failed to create properties reference class.
     *  <br>Failed to load property file.
     *  <br>File not found or Access denied.
     */
    private ResourceIF getResource(
        final String pmProperties
      , final String pmLanguage
      , final String pmCountry
    )
        throws IOException
    {
        // ========================================================================================
        // Create key of store file
        // ========================================================================================
    	if (pmProperties == null || pmProperties.length() == 0) {
    		throw new IllegalArgumentException("The first argument (File path) is necessary");
    	}

        final String storeName;

        if (pmLanguage != null && pmCountry != null) {
            storeName = pmProperties + "_" + pmLanguage + "_" + pmCountry;
        } else
        if (pmLanguage != null) {
            storeName = pmProperties + "_" + pmLanguage;
        } else
        if (pmCountry != null) {
            storeName = pmProperties + "_" + pmCountry;
        } else {
            storeName = pmProperties;
        }

        // ========================================================================================
        // Get properties file
        // ========================================================================================
        if (!this._cache.containsKey(storeName)) {
        // Initialize

        	synchronized (this)
            {
                if (!this._cache.containsKey(storeName)) {
                // Initialize

                    Properties properties = null;

                    // ----------------------------------------------------------------------------
                    // Search for files according to language/country.
                    // ----------------------------------------------------------------------------
                    if (pmLanguage != null && pmCountry != null) {
                    	try {
                    		properties = this.load(pmProperties + "_" + pmLanguage.toLowerCase() + "_" + pmCountry.toLowerCase());
                    	} catch (final FileNotFoundException try1) {
                    		properties = null;
                    	}
                    }
                    if (properties == null && pmLanguage != null) {
                    	try {
                            properties = this.load(pmProperties + "_" + pmLanguage.toLowerCase());
                    	} catch (final FileNotFoundException try2) {
                    		properties = null;
                    	}
                    }
                    if (properties == null && pmCountry != null) {
                    	try {
                            properties = this.load(pmProperties + "_" + pmCountry.toLowerCase());
                    	} catch (final FileNotFoundException try3) {
                    		properties = null;
                    	}
                    }
                    if (properties == null) {
                    	try {
                            properties = this.load(pmProperties);
                    	} catch (final FileNotFoundException try4) {
                    		properties = null;
                    	}
                    }

                    if (properties == null) {
                    	throw new FileNotFoundException("File not found - " + pmProperties + "(language:" + pmLanguage + ", country:" + pmCountry + ")");
                    }

                    // ----------------------------------------------------------------------------
                    // Create a properties reference class
                    // ----------------------------------------------------------------------------
                    final String className = (String)properties.get(ResourceIF.DEFAULT_CLASS);

                    final ResourceIF result;

                    if (className == null || className.length() == 0) {
                    	result = new DefaultResource(properties);
                    } else {

                    	try {

                    		result = (ResourceIF)Class.forName(className)
							        .getConstructor(Properties.class)
							        .newInstance(properties);

                    	} catch (Exception ex) {
							throw new IOException("Failed to create properties reference class - " + className);
						}
                    }
            		_cache.put(storeName, result);

            		return result;
                }
            }
        }
        return _cache.get(storeName);
    }

    
    /**
     * This method reads properties.
     * @param  pmFile InputStream
     * @return Properties
     * @throws IOException Failed to load property file.
     */
    private Properties load(
        final InputStream pmFile
    )
    	throws IOException
    {
        final Properties data = new Properties();

        // [specification] Files only support "UTF-8" character encoding.
        try (final InputStreamReader read = new InputStreamReader(pmFile, StandardCharsets.UTF_8)) {

            data.load(read);
        }
        return data;
    }

    /**
     * This method reads properties.
     * @param  pmPath File path
     * @return Properties
     * @throws IOException Failed to load property file.
     */
    private Properties load(
        final String pmPath
    )
        throws IOException
    {
        final String filePath;

    	if (pmPath.endsWith(".properties")) {
            filePath = pmPath;
        } else {
        	filePath = pmPath + ".properties";
        }
    	
    	if (ResourceIF.DEFAULT_SETTINGS.equals(filePath)) {
    	// Use a fixed path

    		InputStream input;

    		input = FileRepository.class.getClassLoader().getResourceAsStream(filePath);
    		if (input != null) return load(input);

    		input = FileRepository.class.getResourceAsStream(filePath);
    		if (input != null) return load(input);

    		throw new FileNotFoundException(ResourceIF.DEFAULT_SETTINGS);

    	} else {
    		return load(FileRepository.find(filePath));
    	}
    }
}
