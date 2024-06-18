package jp.co.cam.universal.debug.impl;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jp.co.cam.universal.context.UserInfoFactory;
import jp.co.cam.universal.context.UserInfoIF;
import jp.co.cam.universal.debug.DebuggerIF;


/**
 * [universal](commons)This class is the abstract class of log output class.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>        Unify datetime digits.
 * <br>        'Thread.getId()' change to 'Thread.threadId()'
 * <br>* Supports JDK19
 * <br>########################################################################
 */
public abstract class AbstractDebugger
    implements DebuggerIF
{
    /** printStream:exception log */
    protected PrintStream _out1 = System.err;

    /** printStream:warning log */
    protected PrintStream _out2 = System.out;

    /** printStream:info log */
    protected PrintStream _out3 = System.out;

    /** printStream:debug log */
    protected PrintStream _out4 = System.out;


    /** time the stopwatch started  */
    protected final Map<String, Timestamp> _timer
     = new HashMap<>();


    /**
     * This method creates log text.
     * @param  pmText Text
     * @param  pmParm Parameters
     * @return log
     */
    static StringBuilder createLog(
        final String pmText
      , final Object... pmParm
    )
    {
        final StringBuilder result = new StringBuilder();

        // ----------------------------------------------------------------------------------------
        // Datetime(yyyy-MM-dd HH:mm:ss.SSS)
        // ----------------------------------------------------------------------------------------
        String time = new Timestamp(System.currentTimeMillis()).toString();

        if (time.length() < 23) {
        	time += "000";
        	time = time.substring(0, 23);
        }

        result.append("[").append(time).append("]");
        result.append("\t");

        // ----------------------------------------------------------------------------------------
        // Thread　id
        // ----------------------------------------------------------------------------------------
    	// [JDK19]Thread.getId() change to Thread.threadId()
        final String id = "0000000000000000000" + String.valueOf(Thread.currentThread().getId());
        // final String id = "0000000000000000000" + String.valueOf(Thread.currentThread().threadId());

        result.append("(").append(id.substring(id.length() - 19)).append(")");

        // ----------------------------------------------------------------------------------------
        // User information
        // ----------------------------------------------------------------------------------------

        // user information
        final UserInfoIF user = UserInfoFactory.get();

        result.append(user.getUserID());

        if (user.getLanguage() != null || user.getCountry() != null) {
            result.append("@");
            result.append(user.getLanguage()).append("_").append(user.getCountry());
        }
        result.append("\t");

        // ----------------------------------------------------------------------------------------
        // Log
        // ----------------------------------------------------------------------------------------
        if (pmText != null) result.append(pmText);

        // ----------------------------------------------------------------------------------------
        // Parameters
        // ----------------------------------------------------------------------------------------
        if (pmParm != null && pmParm.length != 0) {

            result.append(" - ");

            result.append("{");
            for (int i = 0; i < pmParm.length; i++)
            {
                if (i != 0) result.append(", ");

                if (pmParm[i] == null || !pmParm[i].getClass().isArray()) {
                    result.append(String.valueOf(pmParm[i]));
                } else {
                    result.append(Arrays.asList(pmParm[i]));
                }

            }
            result.append("}");
        }
        return result;
    }


    @Override
    public void setException(
    	final PrintStream pmOut
    )
    {
        _out1 = pmOut;
    }

    @Override
    public void setWarning(
    	final PrintStream pmOut
    )
    {
        _out2 = pmOut;
    }

    @Override
    public void setDebug(
    	final PrintStream pmOut
    )
    {
        _out4 = pmOut;
    }

    @Override
    public void setInfo(
    	final PrintStream pmOut
    )
    {
        _out3 = pmOut;
    }


    @Override
    public void startTimer(
        final String pmID
    )
    {
        _timer.put(pmID, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void stopTimer(
        final String pmID
    )
    {
        if (!_timer.containsKey(pmID)) {
        	throw new IllegalArgumentException("No such stopwatch ID - " + pmID);
        }

        info((System.currentTimeMillis() - _timer.remove(pmID).getTime()) + "(ms)");
    }
}
