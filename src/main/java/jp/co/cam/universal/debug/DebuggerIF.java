package jp.co.cam.universal.debug;

import java.io.*;


/**
 * [universal](commons)This class is the log output class.
 * <br>If you will change the log output function to any API,
 * <br>please implements this interface.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 * @see jp.co.cam.universal.debug.DebuggerFactory
 */
public interface DebuggerIF
{
    /** Preserved keys in thread local */
    String CONTEXT_SAVE_KEY = DebuggerIF.class.getName();


    /**
     * This method sets the exception log output API.
     * @param  pmOut PrintStream
     */
    void setException(
    	final PrintStream pmOut
    );

    /**
     * This method outputs the exception log.
     * @param pmText log
     * @param pmParm parameters
     */
    void exception(
        final String pmText
      , final Object... pmParm
    );


    /**
     * This method sets the warning log output API.
     * @param  pmOut PrintStream
     */
    void setWarning(
    	final PrintStream pmOut
    );

    /**
     * This method outputs the warning log.
     * @param pmText log
     * @param pmParm parameters
     */
    void warning(
        final String pmText
      , final Object... pmParm
    );


    /**
     * This method sets the debug log output API.
     * @param  pmOut PrintStream
     */
    void setDebug(
    	final PrintStream pmOut
    );

    /**
     * This method outputs the debug log.
     * @param pmText log
     * @param pmParm parameters
     */
    void debug(
        final String pmText
      , final Object... pmParm
    );


    /**
     * This method sets the information log output API.
     * @param  pmOut PrintStream
     */
    void setInfo(
    	final PrintStream pmOut
    );

    /**
     * This method outputs the information log.
     * @param pmText log
     * @param pmParm parameters
     */
    void info(
        final String pmText
      , final Object... pmParm
    );


    /**
     * This method starts the stopwatch.
     * <br>Outputs elapsed time from startup to info log.
     * @param  pmID ID
     */
    void startTimer(
        final String pmID
    );

    /**
     * This method stops the stopwatch.
     * <br>Outputs elapsed time from startup to info log.
     * @param  pmID ID
     */
    void stopTimer(
        final String pmID
    );
}
