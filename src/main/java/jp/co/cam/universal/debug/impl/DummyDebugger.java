package jp.co.cam.universal.debug.impl;


/**
 * [universal](commons)This class is the dummy class of log output class.
 * <br>output exception log only.
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
public class DummyDebugger
    extends CommonDebugger
{
    @Override
    public void warning(
        final String pmText
      , final Object... pmParm
    )
    {

    }

    @Override
    public void debug(
        final String pmText
      , final Object... pmParm
    )
    {

    }

    @Override
    public void info(
        final String pmText
      , final Object... pmParm
    )
    {

    }
}
