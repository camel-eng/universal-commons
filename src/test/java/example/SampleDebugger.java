package example;

import jp.co.cam.universal.debug.impl.CommonDebugger;


/**
 * (examples)How to extend the log output class.
 * It's also possible to implement any interface.
 * 
 * Please edit '/common.properties' to switch the new class.
 */
public class SampleDebugger
    extends CommonDebugger
{
    @Override
    public void exception(
        final String pmText
      , final Object... pmParm
    )
    {
    	super.exception(pmText, pmParm);
    }

    @Override
    public void warning(
        final String pmText
      , final Object... pmParm
    )
    {
    	super.warning(pmText, pmParm);
    }

    @Override
    public void info(
        final String pmText
      , final Object... pmParm
    )
    {
    	super.info(pmText, pmParm);
    }


    @Override
    public void debug(
        final String pmText
      , final Object... pmParm
    )
    {
    	super.debug(pmText, pmParm);
    }
}
