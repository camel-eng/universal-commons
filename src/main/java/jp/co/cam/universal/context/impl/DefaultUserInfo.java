package jp.co.cam.universal.context.impl;


/**
 * [universal](commons)This class is the default class of user information management class.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>######################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2017.09 Change Class.newInstance() to Constructor.newInstance()
 * <br>* Supports JDK9
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>######################################################################
 * @see jp.co.cam.universal.context.UserContext
 */
public class DefaultUserInfo
    extends AbstractUserInfo
{
    /** UID */
    private static final long serialVersionUID = -2023L;


    @Override
    public String getUserID()
    {
        return _userID;
    }

    @Override
    public void setUserID(
        final String pmCode
    )
    {
        if (pmCode == null || pmCode.length() == 0) {
            _userID = GUEST_USER;
        } else {
            _userID = pmCode;
        }
    }


    @Override
    public String getUserName()
    {
        return _userName;
    }

    @Override
    public void setUserName(
        final String pmName
    )
    {
        if (pmName == null || pmName.length() == 0) {
            _userName = GUEST_USER;
        } else {
            _userName = pmName;
        }
    }


    @Override
    public void clear()
    {
    	super.clear();
    	
    	setUserID(null);
    	setUserName(null);
    }


    @Override
	public Object getCommandValue(
		final String pmCmd
	)
	{
    	if (pmCmd == null || pmCmd.length() == 0) {
    		return null;
    	}

    	// uppercase
    	String cmd = pmCmd.trim().toUpperCase();

    	// {@context:command}
    	if (cmd.startsWith("{") && cmd.endsWith("}")) {
    		cmd = cmd.substring(1, cmd.length() - 1);
    		cmd = cmd.trim();
    	}
    	// @context:command
    	if (cmd.startsWith("@CONTEXT:")) {
    		cmd = cmd.substring("@CONTEXT:".length());
    		cmd = cmd.trim();
    	}

    	// command
    	switch (cmd)
    	{
            case KEY_USER_DISP:
            {
                return getUserID() + " " + getUserName();
            }
            case KEY_USER_CODE:
            {
                return getUserID();
            }
            case KEY_USER_NAME:
            {
                return getUserName();
            }
            case KEY_LANGUAGE:
            {
                return getLanguage();
            }
            case KEY_COUNTRY:
            {
                return getCountry();
            }
            default:
            {
                return getInfinityDataMap().get(pmCmd);
            }
		}
	}


    /**
     * Constructor.
     * @param  pmCode user code
     * @param  pmName user name
     */
    public DefaultUserInfo(
    	final String pmCode
      , final String pmName
    )
    {
        setUserID(pmCode);
    	setUserName(pmName);
    }

    /** Constructor. */
    public DefaultUserInfo()
    {
        this(null, null);
    }


    /** User ID */
    private String _userID;

    /** User Name */
    private String _userName;
}
