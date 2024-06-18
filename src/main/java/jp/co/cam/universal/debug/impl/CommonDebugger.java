package jp.co.cam.universal.debug.impl;


/**
 * [universal](commons)This class is the standard class of log output class.
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
public class CommonDebugger
    extends AbstractDebugger
{
    @Override
    public void exception(
        final String pmText
      , final Object... pmParm
    )
    {
        if (super._out1 != null) {
            super._out1.println(createLog(pmText, pmParm));
        }
    }

    @Override
    public void warning(
        final String pmText
      , final Object... pmParm
    )
    {
        if (super._out2 != null) {
            super._out2.println(createLog(pmText, pmParm));
        }
    }

    @Override
    public void debug(
        final String pmText
      , final Object... pmParm
    )
    {
        if (super._out4 != null) {
            super._out4.println(createLog(pmText, pmParm));
        }
    }

    @Override
    public void info(
        final String pmText
      , final Object... pmParm
    )
    {
        if (super._out3 != null) {
            super._out3.println(createLog(pmText, pmParm));
        }
    }
}
