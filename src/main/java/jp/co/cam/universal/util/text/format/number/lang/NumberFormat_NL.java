package jp.co.cam.universal.util.text.format.number.lang;


/**
 * [universal](commons)Provides number format conversion functionality.
 * <pre>
 * kingdom of the Netherlands(Type Spain)
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
 * 1 001 000,20 ---- 1001000.2
 * 1 001 000.20 ---- 1001000.2
 * 1001000,20 ------ 1001000.2
 * 1001000.20 ------ 1001000.2
 * 1.000      ------ 1000 (!)caution
 * 1,000      ------ 1
 * 1.00       ------ 1
 * 1,00       ------ 1
 * </pre>
 * <br>Copyright(c) 1999-2016 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2016
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>########################################################################
 */
public class NumberFormat_NL
    extends NumberFormat_ES
{

}
