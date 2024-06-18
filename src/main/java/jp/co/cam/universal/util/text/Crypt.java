package jp.co.cam.universal.util.text;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.xml.bind.DatatypeConverter;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import jp.co.cam.universal.context.UserInfoFactory;


/**
 * [universal](commons)Provides encryption and decryption functionality.
 * <br>
 * <br>Copyright(c) 1999-2023 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2023.12
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>2017.09 We added that library to use javax.xml.bind.
 * <br>* Supports JDK9
 * <br>2023.12 Reorganize before publishing.
 * <br>########################################################################
 */
public final class Crypt
{
	/** algorithms */
	public enum TYPES_SHA
	{
		/** SHA-1 */
		SHA1  ("SHA-1"),

		/** SHA-256 */
	    SHA256("SHA-256"),

		/** SHA-512 */
	    SHA512("SHA-512");

		/**
		 * Get name.
		 * @return name
		 */
		public String toString()
	    {
	        return this._name;
	    }

		/**
		 * constructor.
		 * @param  pmName name
		 */
		TYPES_SHA(final String pmName)
	    {
	        this._name = pmName;
	    }

		/** name */
	    private final String _name;
	}

	/**
     * Generating a unique key.
     * <br>prefix + timestamp(10) + hashcode(10) + random(10) + user code
     * @param  pmHead prefix
     * @return unique key
     */
    public static String createUniquePhrase(
        final String pmHead
    )
    {
        // return : unique key
        final StringBuilder ret = new StringBuilder((pmHead== null) ? "TMP" : pmHead);

        // Temporary data for editing
        String tmp;

        synchronized (Crypt.class)
        {
            // current timestamp(17)
            tmp = new Timestamp(new Date().getTime()).toString();
            tmp = tmp.replace("-", "").replace(" ", "");
       		tmp = tmp.replace(":", "").replace(".", "");
            ret.append(tmp);

            // hashcode(10)
            tmp = "0000000000" + Thread.currentThread().hashCode();
            tmp = tmp.substring(tmp.length() - 10);
            ret.append("_");
            ret.append(tmp);

            // random(10)
            tmp = createPassPhrase(10);
            ret.append("_");
            ret.append(tmp);

            // user code
            ret.append("-");
            ret.append(UserInfoFactory.get().getUserID());
        }
        return ret.toString();
    }

    /**
     * Generating a unique key.
     * <br>TMP + timestamp(10) + hashcode(10) + random(10) + user code
     * @return unique key
     */
    public static String createUniquePhrase()
    {
        return createUniquePhrase(null);
    }

    /**
     * Generating random number.
     * @param  pmSize length
     * @return random number
     */
    public static String createPassPhrase(
        final int pmSize
    )
    {
        try {

            // ====================================================================================
            // generate random number
            // ====================================================================================

            // generator
            final SecureRandom random
             = SecureRandom.getInstance("SHA1PRNG", "SUN");

            // bytes
            final byte[] buff = new byte[pmSize];

            // calc
            BigDecimal data = new BigDecimal(1);

            // parts
            StringBuilder part;

            random.nextBytes(buff);

            for (final byte temp : buff)
            {
                if (temp == 0) continue;

                data = data.multiply(new BigDecimal(temp).abs());
            }

            // ====================================================================================
            // Cut text by specified length
            // ====================================================================================
            part = new StringBuilder(data.toString());

            while (part.length() < pmSize)
            {
                part.insert(0, "0");
            }
            return part.substring(0, pmSize);

        } catch (final NoSuchAlgorithmException | NoSuchProviderException ex) {
			throw new IllegalStateException("No such algorithm and provider(algorithm:SHA1PRNG, provider:SUN)", ex);
        }
    }

    /**
     * encoding.
     * @param  pmText text
     * @return sign
     */
    public static String encode(
        final String pmText
    )
    {
        // base datas
        final byte[] base = pmText.getBytes();

        // length
        final int size = base.length;

        // invert datas
        final byte[] temp = new byte[size];

        for (int i = 0; i < size; i++)
        {
            temp[i] = base[(size - i) - 1];
        }
        return DatatypeConverter.printHexBinary(temp);
    }

    /**
     * decoding.
     * @param  pmText sign
     * @return text
     */
    public static String decode(
        final String pmText
    )
    {
        // base datas
        final byte[] buff = DatatypeConverter.parseHexBinary(pmText);

        // length
        final int size = buff.length;

        // invert datas
        final byte[] temp = new byte[size];

        for (int i = 0; i < size; i++)
        {
            temp[i] =  buff[(size - i) - 1];
        }
        return new String(temp);
    }

    /**
     * Convert to hex.
     * @param  pmText text
     * @return hex
     */
    public static String toHash(
        final String pmText
    )
    {
        // algorithm
        final MessageDigest digest;

        // bytes
        byte[] buff;

        try {

            buff = encode(pmText).getBytes();

            // [expansion] Change algorithm.
            digest = MessageDigest.getInstance(TYPES_SHA.SHA512.toString());
            digest.update(buff, 0, buff.length);
            buff = digest.digest();
            digest.reset();

            // return : hex
            final StringBuilder ret = new StringBuilder();

            for (final byte i : buff)
            {
            	// hex
                final String tmp = Integer.toHexString(i & 0xff);

                if (tmp.length() == 1) {
                	ret.append('0').append(tmp);
                } else {
                	ret.append(tmp);
                }
            }
            return ret.toString();

        } catch (final NoSuchAlgorithmException ex) {
			throw new IllegalStateException("No such algorithm - SHA-512", ex);
        }
    }


    /**
     * Generate hex(SHA-***).
     * @param  pmText text
     * @param  pmType algorithm(SHA-1/SHA-256/SHA-512)
     * @return hex
     */
    public static String createHexOfSHA(
    	final String    pmText
      , final TYPES_SHA pmType
    )
    {
    	// algorithm
    	final MessageDigest digest;

    	try {

    		digest = MessageDigest.getInstance(pmType.toString());
	    	digest.reset();
	
	    	digest.update(pmText.getBytes(StandardCharsets.UTF_8));

	    	return String.format("%040x", new BigInteger(1, digest.digest()));

		} catch (final NoSuchAlgorithmException ex) {
			throw new IllegalArgumentException("No such algorithm - " + pmType, ex);
		}
    }

    /** constructor. */
    private Crypt()
    {
    	super();
    }
}
