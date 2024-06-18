package jp.co.cam.universal.util.io;

import java.io.*;


/**
 * [universal](commons)Provides file I/O functionality.
 * <br>
 * <br>Copyright(c) 1999-2020 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2020
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>########################################################################
 */
public final class FileIO
{
    /**
     * Convert from InputStream To ByteArrayInputStream.
     * @param  pmIn InputStream
     * @return ByteArrayInputStream
     * @throws IOException can't read data
     */
    public ByteArrayInputStream conversionByteStream(
        final InputStream pmIn
    )
        throws IOException
    {
        // buffer
        final byte[] buff = new byte[1024];

        // length
        int size;

        try (
        	final ByteArrayOutputStream o = new ByteArrayOutputStream();
        	final InputStream i = pmIn
        )
        {
            while (0 < (size = i.read(buff)))
            {
                o.write(buff, 0, size);
            }
            return new ByteArrayInputStream(o.toByteArray());
        }
    }


    /**
     * Copy data.
     * @param  pmOut output
     * @param  pmIn  input
     * @return read bytes size
     * @throws IOException can't read data or can't write data
     */
    public static long copyStream(
        final OutputStream pmOut
      , final InputStream  pmIn
    )
        throws IOException
    {
        // buffer
        final byte[] buff = new byte[8192];

        // total length
        long read = 0;

        // length
        int len;

        try (
        	final OutputStream o = pmOut;
        	final InputStream  i = pmIn
        )
        {
            if (o == null) {

                while (-1 < (len = i.read(buff))) read += len;

            } else {

                while (-1 < (len = i.read(buff)))
                {
                    o.write(buff, 0, len);
                    read += len;
                }
                o.flush();
            }
        }
        return read;
    }

    /**
     * Copy data.
     * @param  pmOut output
     * @param  pmIn  input
     * @return read bytes size
     * @throws IOException can't read data or can't write data
     */
    public static long copyStream(
        final Writer pmOut
      , final Reader pmIn
    )
        throws IOException
    {
        // total length
        long read = 0;

        // byte
        int b;

        try (
            final Writer o = pmOut;
            final Reader i = pmIn
        )
        {
            if (o == null) {

                while (-1 < i.read()) read++;

            } else {

                while (-1 < (b = i.read()))
                {
                    o.write(b);
                    read++;
                }
                o.flush();
            }
        }
        return read;
    }

    /**
     * Delete folders and files.
     * @param  pmFile folder or file
     * @return true: deleted / false: failure
     */
    public static boolean delete(
        final File pmFile
    )
    {
        if (pmFile == null || (!pmFile.exists())) return true;

        if (pmFile.isDirectory()) {

            // sub files
            final File[] list = pmFile.listFiles();

            // result
            boolean flag = true;

            if (list != null) {

                for (final File file : list)
                {
                    if (!FileIO.delete(file)) {
                        flag = false;
                    }
                }
            }
            return flag;

        } else {

            if (!pmFile.delete()) {
            // if it can't be deleted

            	pmFile.deleteOnExit();

            	return false;
            }
        	return true;
        }
    }
}
