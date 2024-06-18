package jp.co.cam.universal.context;

import java.sql.Timestamp;
import java.util.Map;

import jp.co.cam.universal.util.DataMap;


/**
 * [universal](commons)This class is the data container with Thread Local.
 * <br>The same instance can be obtained within one thread.
 * <br>* We recommend performing a clear method before and after use.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 'Thread.getId()' change to 'Thread.threadId()'
 * <br>        Simplification of functions due to OSS.
 * <br>* Supports JDK19
 * <br>########################################################################
 */
public final class UserContext
{
    /** Key : Current thread id */
    public static final String CURRENT_THREAD_ID = "@CurrentThreadID";

    /** Key : Last initialized datetime */
    public static final String THREAD_TIME = "@ThreadTime";


    /** Thread Local */
    static final ThreadLocal<UserContext> me = new ThreadLocal<UserContext>()
    {
        @Override
        protected synchronized UserContext initialValue()
        {
            return new UserContext();
        }
    };

    /** Stored data */
    private final Map<String, Object> _values
     = new DataMap.SynchronizeDataMap<>();


    /**
     * This method returns the user context.
     * @return UserContext
     */
    public static UserContext get()
    {
        return me.get();
    }


    /**
     * This method returns the stored text.
     * @param  pmKey Key
     * @return Value
     */
    public String getProperty(
        final String pmKey
    )
    {
        return (String)get(pmKey);
    }

    /**
     * This method stores the text.
     * @param  pmKey Key
     * @param  pmVal Value
     */
    public void setProperty(
        final String pmKey
      , final String pmVal
    )
    {
        set(pmKey, pmVal);
    }


    /**
     * This method returns the stored data.
     * @param  pmKey Key
     * @return Value
     */
    public Object get(
        final String pmKey
    )
    {
    	verifyOwnership();

        return _values.get(pmKey);
    }

    /**
     * This method stores the data.
     * @param  pmKey Key
     * @param  pmVal Value
     */
    public void set(
        final String pmKey
      , final Object pmVal
    )
    {
    	verifyOwnership();

        if (pmVal == null) {
            _values.remove(pmKey);
        } else {
            _values.put(pmKey, pmVal);
        }
    }


    /**
     * This method clears all data.
     */
    public void clear()
    {
    	verifyOwnership();

    	_values.clear();

    	// [JDK19]Thread.getId() change to Thread.threadId()
        _values.put(CURRENT_THREAD_ID, Thread.currentThread().getId());
        // _values.put(CURRENT_THREAD_ID, Thread.currentThread().threadId());
        _values.put(THREAD_TIME,       new Timestamp(System.currentTimeMillis()));
    }


    /** This class doesn't require instantiation. */
    private UserContext()
    {
        clear();
    }


    /**
     * This method verify thread ownership.
     */
    private void verifyOwnership()
    {
        if (this._values.containsKey(CURRENT_THREAD_ID)) {
       	// [specification]For owners only

        	// [JDK19]Thread.getId() change to Thread.threadId()
            if ((Long)this._values.get(CURRENT_THREAD_ID) != Thread.currentThread().getId()) {
                throw new IllegalStateException("You can't use from different threads");
            }
            // if ((Long)this._values.get(CURRENT_THREAD_ID) != Thread.currentThread().threadId()) {
        	//     throw new IllegalStateException("You can't use from different threads");
        	// }
        }
    }
}
