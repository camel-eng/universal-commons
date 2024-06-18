package jp.co.cam.universal.util.text.format.number.lang;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <pre>
 * Swiss Confederation(Type Swiss Confederation)
 * 
 * The main differences between Germany and France
 * Germany 1.000 = 1000
 * France  1.000 = 1.0
 * This happens because for Germany the separator are dots.
 * In addition, French separator are white spaces.
 * 
 * [SAMPLE]
 * 1,001,000.20 ---- 1001000.2
 * 1.001.000,20 ---- 1001000.2
 * 1'001'000,20 ---- 1001000.2
 * 1'001'000.20 ---- 1001000.2
 * 1 001 000,20 ---- 1001000.2
 * 1 001 000.20 ---- 1001000.2
 * 1001000,20 ------ 1001000.2
 * 1001000.20 ------ 1001000.2
 * 1.000      ------ 1 (!)caution
 * 1,000      ------ 1
 * 1.00       ------ 1
 * 1,00       ------ 1
 * </pre>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024
 * <br>########################################################################
 * <br>(change history)
 * <br>2024.04 Added mone countries
 * <br>########################################################################
 */
public class NumberFormat_CH
    extends NumberFormat_FR
{
	@Override
    public String formatCurrency(
        final Number pmData
      , final int    pmScale
    )
    {
    	// french number strings
        String data = super.formatCurrency(pmData, pmScale);

        if (data != null) {
        	data = data.replace(" ", "'");
        	data = data.replace(" ", "'");
        	data = data.replace(" ", "'");
        }
    	return data;
    }


    @Override
    public String format(
        final Number pmData
      , final String pmText
    )
    {
    	// french number strings
        String data = super.format(pmData, pmText);

        if (data != null) {
        	data = data.replace(" ", "'");
        }
    	return data;
    }
}
