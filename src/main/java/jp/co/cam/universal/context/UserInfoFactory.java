package jp.co.cam.universal.context;

import jp.co.cam.universal.configuration.ResourceFactory;
import jp.co.cam.universal.configuration.ResourceIF;
import jp.co.cam.universal.context.impl.DefaultUserInfo;


/**
 * [universal](commons)This class is the factory class for user information management class.
 * <pre>The created instance is shared by "UserContext" in the thread.
 * It'll create a guest user, If "UserContext" doesn't have an instance.
 * Please store user information management class in "UserContext", If you have it on session.
 * 
 * [How to use]
 * 
 * (Settings)
 * Set the user information management class in the settings file '/commom.properties'.
 * Please extend 'DefaultUserInfo' to suit your needs.
 * 
 * # user information management class
 * custom.userinfo.class=jp.co.cam.universal.context.impl.DefaultUserInfo
 * 
 * 
 * (JAVA)
 * 
 * // Container on Thread Local
 * UserContext context = UserContext.get();
 * 
 * try {
 * 
 *     // =====================================================================
 *     // This process loading user information saved in session.
 *     // =====================================================================
 *     UserInfoIF userInfo = session.getAttribute(UserInfoIF.CONTEXT_SAVE_KEY);
 * 
 *     if (userInfo == null) {
 *     // If not saved in session
 * 
 *         // Guest user
 *         userInfo = UserInfoFactory.get();
 * 
 *         // -----------------------------------------------------------------
 *         // This process initializes user information(Sign-in).
 *         // -----------------------------------------------------------------
 *         userInfo.setUserID("example-1");
 *         userInfo.setUserName("Jane Doe");
 * 
 *         // If locale is different from the standard locale on system
 *         userInfo.setLanguage("en");
 *         userInfo.setCountry("US");
 * 
 *         session.setAttribute(UserInfoIF.CONTEXT_SAVE_KEY, userInfo);
 * 
 *     } else {
 *         context.set(UserInfoIF.CONTEXT_SAVE_KEY, userInfo);
 *     }
 * 
 *     // You can get the user information management class,
 *     //  from the context while in the thread
 *     userInfo = (UserInfoIF)context.get();
 * 
 * 
 * } finally {
 * 
 *     // This process clears the context in consideration of thread reuse.
 *     context.clear();
 * }
 * </pre>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>######################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>######################################################################
 */
public class UserInfoFactory
{
    /** Setting : custom user information management class */
    public final static String SETTINGS_USER_INFO_CLASS = "custom.userinfo.class";


    /**
     * This method returns the user information management class.
     * <pre>
     * [expansion]
     * Please set the extension of this class to "custom.userinfo.class"
     *  in the basic settings '/common.properties'.
     * </pre>
     * @return UserInfoIF
     */
    public static UserInfoIF get()
    {
        final UserContext context = UserContext.get();

        UserInfoIF result = (UserInfoIF)context.get(UserInfoIF.CONTEXT_SAVE_KEY);

        if (result == null) {
        // ========================================================================================
        // initialize
        // ========================================================================================

        	String className = null;

        	try {

            	// --------------------------------------------------------------------------------
            	// Get settings
            	// --------------------------------------------------------------------------------
                final ResourceIF settings = ResourceFactory.getDefault();

                className = settings.get(SETTINGS_USER_INFO_CLASS);

                if (className == null || className.length() == 0) {
                // using default class

                	className = DefaultUserInfo.class.getName();
                }

	        	// --------------------------------------------------------------------------------
	        	// Instantiation a user information management class
	        	// --------------------------------------------------------------------------------
                result = (UserInfoIF)Class.forName(className).getConstructor(new Class[0]).newInstance();

            	// --------------------------------------------------------------------------------
            	// Storing in thread local container
            	// --------------------------------------------------------------------------------
                UserContext.get().set(UserInfoIF.CONTEXT_SAVE_KEY, result);

            } catch (final Exception ex) {
            	throw new IllegalStateException("Failed to create user information management class - " + className, ex);
            }
        }
        return result;
    }


    /** This class doesn't require instantiation. */
    private UserInfoFactory()
    {
    	super();
    }
}
