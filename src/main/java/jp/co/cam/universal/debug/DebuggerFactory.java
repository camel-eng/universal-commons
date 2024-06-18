package jp.co.cam.universal.debug;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;

import jp.co.cam.universal.configuration.ResourceFactory;
import jp.co.cam.universal.configuration.ResourceIF;
import jp.co.cam.universal.context.UserContext;

import jp.co.cam.universal.debug.impl.CommonDebugger;


/**
 * [universal](commons)This class is the factory class for Log output class.
 * <pre>Create a wrapper class for log output API.
 * (Please set the '/common.properties' file to switch implementation classes)
 * 
 * [How to use]
 * 
 * (Settings)
 * # exception : log output path
 * exception.logger.path=/logs/error.log
 * 
 * # switching : whether to output the exception log
 * exception.logger.flag=true
 * 
 * # warning : log output path
 * warning.logger.path=/logs/debug.log
 * 
 * # warning : whether to output the warning log
 * warning.logger.flag=true
 * 
 * # debug : log output path
 * debug.logger.path=/logs/debug.log
 * 
 * # debug : whether to output the debug log
 * debug.logger.flag=true
 * 
 * # info : log output path
 * info.logger.path=/logs/debug.log
 * 
 * # info : whether to output the info log
 * info.logger.flag=true
 * 
 * 
 * (JAVA)
 * 
 * // log output API
 * DebuggerIF log = DebuggerFactory.get();
 * 
 * log.debug("Sample log");
 * 
 * </pre>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2017.09 Change Class.newInstance() to Constructor.newInstance()
 * <br>* Supports JDK9
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>        Fixed to not return IOException when generating API.
 * <br>########################################################################
 */
public final class DebuggerFactory
{
    /** Setting : Log output class */
    public static final String SETTINGS_LOGGER_CLASS = "logger.class";


    /** Setting : Output file path */
    public static final String SETTINGS_LOGGER_PATH = ".logger.path";

    /** Setting : ON/OFF */
    public static final String SETTINGS_LOGGER_FLAG = ".logger.flag";


    /** Singletone instance */
    public static final DebuggerFactory me = new DebuggerFactory();

    /** Output stream cache. */
    private final Map<String, PrintStream> _out
     = new Hashtable<>();


    /**
     * This method returns the debugger.
     * @return DebuggerIF
     */
    public static DebuggerIF get()
    {
        // ========================================================================================
        // if already used in thread
        // ========================================================================================

        // thread local
        final UserContext context = UserContext.get();

        // result:DebuggerIF
        DebuggerIF ret = (DebuggerIF)context.get(DebuggerIF.CONTEXT_SAVE_KEY);

        if (ret == null) {
        // ========================================================================================
        // initialize
        // ========================================================================================

        	ret = generateDebugger();

	        context.set(DebuggerIF.CONTEXT_SAVE_KEY, ret);
        }
        return ret;
    }


    /**
     * This method generates a debugger.
     * @return DebuggerIF
     */
    @SuppressWarnings("unchecked")
	static DebuggerIF generateDebugger()
    {
        DebuggerIF result;

        try {

        	final ResourceIF settings = ResourceFactory.getDefault();

            String className = settings.get(SETTINGS_LOGGER_CLASS);
            if (className == null || className.length() == 0) {
            	className = CommonDebugger.class.getName();
            }

            final Class<DebuggerIF> debuggerClass = (Class<DebuggerIF>)Class.forName(className);
            result = debuggerClass.getConstructor(new Class[0]).newInstance();

        } catch (final Exception ex) {

        	result = new CommonDebugger();
        	result.exception("Failed to generate log output API", ex);
        }

    	try {
    		initialize(result, "exception");
    	} catch (final Exception wr) {
    		result.setException(System.err);
		}

    	try {
    		initialize(result, "warning");
    	} catch (final Exception wr) {
    		result.setException(System.out);
		}

    	try {
    		initialize(result, "debug");
    	} catch (final Exception wr) {
    		result.setException(System.out);
		}

    	try {
    		initialize(result, "info");
    	} catch (final Exception wr) {
    		result.setException(System.out);
		}

        return result;
    }

    /**
     * This method returns the debugger.
     * initialize debug log.
     * @param pmObj DebuggerIF
     * @throws IOException Throws in the following cases:
     * <br>- Failed to reference settings(please check /common.properties).
     * <br>- Failed to create log output file.
     */
    static void initialize(
    	final DebuggerIF pmObj
      , final String pmType
    )
    	throws IOException
    {
    	final ResourceIF settings = ResourceFactory.getDefault();

        PrintStream output = System.out;

        if (Boolean.valueOf(settings.get(pmType + SETTINGS_LOGGER_FLAG))) {

            final String filePath = settings.get(pmType + SETTINGS_LOGGER_PATH);

            if (filePath != null && 0 < filePath.length()) {
            	output = me.getPrintStream(filePath);
            }

        } else {
        	output = null;
        }

        switch (pmType)
        {
			case "exception":
			{
		        pmObj.setException(output);
		        break;
			}
			case "warning":
			{
		        pmObj.setWarning(output);
		        break;
			}
			case "debug":
			{
		        pmObj.setDebug(output);
			}
			case "info":
			{
		        pmObj.setInfo(output);
		        break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value - " + pmType);
		}
    }


    /**
     * This method returns the output API.
     * @param  pmPath Output file path
     * @return PrintStream
     * @throws IOException File not found or Access denied.
     */
    PrintStream getPrintStream(
        final String pmPath
    )
    	throws IOException
    {
        if (!_out.containsKey(pmPath)) {

            synchronized (_out)
            {
                if (!_out.containsKey(pmPath)) {
                    _out.put(pmPath, new PrintStream(new FileOutputStream(pmPath, true)));
                }
            }
        }
        return _out.get(pmPath);
    }


    /** This class doesn't require instantiation. */
    private DebuggerFactory()
    {
    	super();
    }
}
