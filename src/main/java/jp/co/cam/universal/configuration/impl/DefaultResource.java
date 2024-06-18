package jp.co.cam.universal.configuration.impl;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.Map;

import jp.co.cam.universal.configuration.ResourceIF;
import jp.co.cam.universal.util.DataMap;


/**
 * [universal](commons)This class is the default class for properties reference class.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 */
public class DefaultResource
    implements ResourceIF
{
    /** Properties */
    protected final Map<String, String> _properties = new DataMap.SynchronizeDataMap<>();


    /**
     * This method converts {n} in the strings with parameters.
	 * @param  pmText text
	 * @param  pmPart parameters
	 * @return converted text
	 */
    public static String conversionParameter(
        final String   pmText
      , final Object[] pmPart
    )
    {
    	if (pmText == null || pmPart == null || pmPart.length == 0) {
            return pmText;
    	}
    	return MessageFormat.format(pmText, pmPart);
    }


    @Override
    public String[] getKeys()
    {
        // List of keys
        final String[] list = _properties.keySet().toArray(new String[0]);

        java.util.Arrays.sort(list);

        return list;
    }

    @Override
    public boolean hasKey(
        final String pmCode
    )
    {
        return _properties.containsKey(pmCode);
    }


    @Override
    public String get(
        final String pmCode
      , final String[] pmPart
    )
    {
        return conversionParameter(get(pmCode), pmPart);
    }

    @Override
    public String get(
        final String pmCode
    )
    {
        return _properties.get(pmCode);
    }

    @Override
    public String toString()
    {
    	String result = null;

    	String val;

    	for (final String key : _properties.keySet())
    	{
    		val = (String)_properties.get(key).trim();
			val = "'" + val.replace("'", "\\'") + "'";

			if (result == null) {
				result += (key + ":" + val);
			} else {
				result += (", " + key + ":" + val);
			}
    	}
    	return "{" + result + "}";
    }


    /**
     * Constructor.
     * @param  pmConf properties
     */
    public DefaultResource(
        final Properties pmConf
    )
    {
        if (pmConf == null) return;

        for (Object tmp : pmConf.keySet())
    	{
       		_properties.put((String)tmp, pmConf.getProperty((String)tmp));
    	}
    }
}
