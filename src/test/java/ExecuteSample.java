import example.SampleUserInfo;
import jp.co.cam.universal.configuration.ResourceFactory;
import jp.co.cam.universal.configuration.ResourceIF;
import jp.co.cam.universal.context.UserContext;
import jp.co.cam.universal.context.UserInfoFactory;
import jp.co.cam.universal.context.UserInfoIF;
import jp.co.cam.universal.debug.DebuggerFactory;
import jp.co.cam.universal.debug.DebuggerIF;
import jp.co.cam.universal.util.text.format.datetime.DateTimeFactory;
import jp.co.cam.universal.util.text.format.datetime.DateTimeFormatIF;
import jp.co.cam.universal.util.text.format.number.NumberFactory;
import jp.co.cam.universal.util.text.format.number.NumberFormatIF;

public class ExecuteSample
{
	public static void main(String[] args)
	{
		try {

			// log output api
			final DebuggerIF logs = DebuggerFactory.get();

			// user information
			final UserInfoIF user = UserInfoFactory.get();

			// settings
			final ResourceIF conf = ResourceFactory.getDefault();

			logs.debug("==============================");
			logs.debug("Debugger");
			logs.debug("==============================");
			logs.debug("Debugger", conf.get(DebuggerFactory.SETTINGS_LOGGER_CLASS));

			logs.exception("exception log", "ON");
			logs.setException(null);
			logs.exception("exception log", "OFF");
			logs.setException(System.err);

			logs.warning("warning log", "ON");
			logs.setWarning(null);
			logs.warning("warning log", "OFF");
			logs.setWarning(System.out);

			logs.debug("debug log", "ON");
			logs.setDebug(null);
			logs.debug("debug log", "OFF");
			logs.setDebug(System.out);

			logs.info("info log", "ON");
			logs.setInfo(null);
			logs.info("info log", "OFF");
			logs.setInfo(System.out);

			logs.debug("==============================");
			logs.debug("User Information");
			logs.debug("==============================");
			logs.debug("UserInfo", conf.get(UserInfoFactory.SETTINGS_USER_INFO_CLASS));

			logs.debug("USER ID",   user.getUserID());
			logs.debug("USER NAME", user.getUserName());

			if (user instanceof SampleUserInfo) {

				((SampleUserInfo)user).setItem1("OK");

				logs.debug("ITEM1", ((SampleUserInfo)user).getItem1());

			} else {
				throw new IllegalStateException("User Information - " + user.getClass().getName());
			}


			logs.debug("==============================");
			logs.debug("multi-language", "datetime");
			logs.debug("==============================");

			// datetime format api
			DateTimeFormatIF datetimeFormat;

			logs.debug("------------------------------");
			logs.debug("yyyy/MM/dd");
			logs.debug("------------------------------");
			user.setLanguage("en");
			user.setCountry("DE");

			datetimeFormat = DateTimeFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("DE", datetimeFormat.parse("2024-09-01 23:59:59.999"));
			logs.debug("DE", datetimeFormat.parse("2024.09.01 26:59:59.999"));
			logs.debug("DE", datetimeFormat.parse("Sep/01,2024 23:59:59.999"));
			logs.debug("DE", datetimeFormat.parse("01/Sep/2024 23:59:59.999"));
			logs.debug("DE", datetimeFormat.formatDate(datetimeFormat.parse("01/Sep,2024 23:59:59.999")));

			user.setCountry("DK");

			datetimeFormat = DateTimeFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("DK", datetimeFormat.parse("2024-09-01 23:59:59.999"));
			logs.debug("DK", datetimeFormat.parse("2024.09.01 26:59:59.999"));
			logs.debug("DK", datetimeFormat.parse("Sep/01,2024 23:59:59.999"));
			logs.debug("DK", datetimeFormat.parse("01/Sep/2024 23:59:59.999"));
			logs.debug("DK", datetimeFormat.formatDate(datetimeFormat.parse("01/Sep,2024 23:59:59.999")));

			user.setCountry("SE");

			datetimeFormat = DateTimeFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("SE", datetimeFormat.parse("2024-09-01 23:59:59.999"));
			logs.debug("SE", datetimeFormat.parse("2024.09.01 26:59:59.999"));
			logs.debug("SE", datetimeFormat.parse("Sep/01,2024 23:59:59.999"));
			logs.debug("SE", datetimeFormat.parse("01/Sep/2024 23:59:59.999"));
			logs.debug("SE", datetimeFormat.formatDate(datetimeFormat.parse("01/Sep,2024 23:59:59.999")));

			user.setLanguage("ja");
			user.setCountry("JP");

			datetimeFormat = DateTimeFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("JP", datetimeFormat.parse("2024-09-01 23:59:59.999"));
			logs.debug("JP", datetimeFormat.parse("2024.09.01 26:59:59.999"));
			logs.debug("JP", datetimeFormat.parse("Sep/01,2024 23:59:59.999"));
			logs.debug("JP", datetimeFormat.parse("01/Sep/2024 23:59:59.999"));
			logs.debug("JP", datetimeFormat.formatDate(datetimeFormat.parse("01/Sep,2024 23:59:59.999")));

			logs.debug("------------------------------");
			logs.debug("MMM/dd/yyyy");
			logs.debug("------------------------------");
			user.setLanguage("en");
			user.setCountry("US");

			datetimeFormat = DateTimeFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("US", datetimeFormat.parse("Sep/01,2024 23:59:59.999"));
			logs.debug("US", datetimeFormat.parse("Sep/01,2024 26:59:59.999"));
			logs.debug("US", datetimeFormat.parse("Sep/01,2024 23:59:59.999"));
			logs.debug("US", datetimeFormat.parse("01/Sep/2024 23:59:59.999"));
			logs.debug("US", datetimeFormat.formatDate(datetimeFormat.parse("01/Sep,2024 23:59:59.999")));

			logs.debug("------------------------------");
			logs.debug("dd/MMM/yyyy");
			logs.debug("------------------------------");
			user.setCountry("UK");
			user.setLanguage("en");

			datetimeFormat = DateTimeFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("UK", datetimeFormat.parse("01/Sep,2024 23:59:59.999"));
			logs.debug("UK", datetimeFormat.parse("01/Sep/2024 26:59:59.999"));
			logs.debug("UK", datetimeFormat.parse("Sep/01,2024 23:59:59.999"));
			logs.debug("UK", datetimeFormat.parse("01/Sep/2024 23:59:59.999"));
			logs.debug("UK", datetimeFormat.formatDate(datetimeFormat.parse("01/Sep,2024 23:59:59.999")));

			logs.debug("==============================");
			logs.debug("multi-language", "number");
			logs.debug("==============================");

			// number format api
			NumberFormatIF numberFormat;

			user.setCountry("ES");
			user.setLanguage("en");

			numberFormat = NumberFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("ES", numberFormat.formatCurrency(1000));
			logs.debug("ES", numberFormat.formatCurrency(1000.20));
			logs.debug("ES", numberFormat.parseDecimal(numberFormat.formatCurrency(1000)));

			user.setCountry("FR");
			user.setLanguage("en");

			numberFormat = NumberFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("FR", numberFormat.formatCurrency(1000));
			logs.debug("FR", numberFormat.formatCurrency(1000.20));
			logs.debug("FR", numberFormat.parseDecimal(numberFormat.formatCurrency(1000)));


			user.setCountry("CH");
			user.setLanguage("en");

			numberFormat = NumberFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("CH", numberFormat.formatCurrency(1000));
			logs.debug("CH", numberFormat.formatCurrency(1000.20));
			logs.debug("CH", numberFormat.parseDecimal(numberFormat.formatCurrency(1000)));

			user.setCountry("UK");
			user.setLanguage("en");

			numberFormat = NumberFactory.get(user.getLanguage(), user.getCountry());

			logs.debug("UK", numberFormat.formatCurrency(1000));
			logs.debug("UK", numberFormat.formatCurrency(1000.20));
			logs.debug("UK", numberFormat.parseDecimal(numberFormat.formatCurrency(1000)));
 
		} catch (final Exception ex) {
			ex.printStackTrace();
		} finally {
			UserContext.get().clear();
		}
	}
}
