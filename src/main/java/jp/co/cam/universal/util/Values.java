package jp.co.cam.universal.util;

import java.io.*;
import java.sql.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import jp.co.cam.universal.configuration.ResourceFactory;
import jp.co.cam.universal.util.io.FileIO;
import jp.co.cam.universal.util.text.Crypt;


/**
 * [universal](commons)This class is handling special data types.
 * <br>* Mutual conversion between BLOB/CLOB and File.
 * <br>* Null Object includion data types.
 * <br>
 * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2024.04
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 Review of Javadoc and comments.
 * <br>        finalize processing has been deprecated.
 * <br>* Supports JDK9
 * <br>2024.04 Simplification of functions due to OSS.
 * <br>########################################################################
 */
public final class Values
{
	/** Setting : Temporary folder */
    public static final String CONFIG_TEMPORARY_FOLDER = "temporary.path";


    /**
     * [universal](commons)This class is the wapper class of Blob.
     * <br>
     * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
     * @author Camel Engineering LLP J.Matsuo
     * @version REVIEWED ON 2024.04
     * <br>####################################################################
     * <br>(change history)
     * <br>2016.08 Review of Javadoc and comments.
     * <br>2024.04 Simplification of functions due to OSS.
     * <br>####################################################################
     */
    public static class BValue
    {
        /** PREFIX:Blob temporary files */
        public static final String TEMPORARY_FILE_HEADER = "_BLOB";

        /**
         * Get InputStream.
         * <br>(caution)Please close the InputStream after use.
         * @return InputStream
         * @throws FileNotFoundException File not exists or can't be read.
         */
        public InputStream getInputStream()
        	throws FileNotFoundException
        {
        	if (isClosed()) {
            	return null;
        	} else {
            	return new FileInputStream(getFile());
        	}
        }

        /**
         * Get all bytes data.
         * @return byte[]
         * @throws IOException can't be read.
         */
        public byte[] getBytes()
            throws IOException
        {
            // buffer
            final byte [] buff = new byte[1024];

            // length
            int len;

            try (
           		final ByteArrayOutputStream o = new ByteArrayOutputStream();
            	final InputStream i = getInputStream()
            )
            {
                while(0 < (len = i.read(buff)))
                {
                    o.write(buff, 0, len);
                }
                return o.toByteArray();
            }
        }


        /**
         * Get file object.
         * @return File
         */
        public File getFile()
        {
            if (isClosed()) {
                return null;
            }
            return _temp;
        }

        /**
         * Get file length.
         * @return length
         */
        public long getSize()
        {
            if (isClosed()) {
                return 0;
            }
            return _temp.length();
        }


        /**
         * Close.
         * <br>delete temporary file.
         */
        public void close()
        {
        	if (_temp != null) {

        		try {
	
	        		if (_temp.getName().startsWith(TEMPORARY_FILE_HEADER)) {
		            // temporary file
		
		                if (_temp.exists()) {
	
		                    if (!_temp.delete()) {
		                    // if it can't be deleted

		                    	_temp.deleteOnExit();
		                    }
		                }
		            }

        		} finally {
    	            _temp = null;
        		}
        	}
        }

        /**
         * Check if it’s closed.
         * @return true: closed or false: active
         */
        public boolean isClosed()
        {
            return (_temp == null || !_temp.exists());
        }


        @Override
        public String toString()
        {
        	if (isClosed()) {
        		return "";
        	} else {
        		return _temp.getPath();
        	}
        }

        /**
         * constructor.
         * @param  pmFile InputStream
         * @throws IOException can't create temporary file
         */
        public BValue(
            final InputStream pmFile
        )
            throws IOException
        {
            _temp = null;

            // temporary folder
            String root = ResourceFactory.getDefault().get(CONFIG_TEMPORARY_FOLDER);
            if (root == null || root.length() == 0) root = "./";

            do {
                _temp = new File(root, Crypt.createUniquePhrase(TEMPORARY_FILE_HEADER));
            } while(_temp.exists());

            // copy
            FileIO.copyStream(new FileOutputStream(_temp), pmFile);
        }

        /**
         * constructor.
         * @param  pmByte byte[]
         * @throws IOException can't create temporary file
         */
        public BValue(
            final byte[] pmByte
        )
            throws IOException
        {
            this(new ByteArrayInputStream(pmByte));
        }

        /**
         * constructor.
         * @param  pmBlob Blob
         * @throws IOException  can't create temporary file
         * @throws SQLException can't read data
         */
        public BValue(
            final Blob pmBlob
        )
            throws IOException, SQLException
        {
            this(pmBlob.getBinaryStream());

            // [2016.07]Error on DB2
            //pmBlob.free();
        }

        /**
         * constructor.
         * @param  pmFile File
         */
        public BValue(
            final File pmFile
        )
        {
            _temp = pmFile;
        }


        /** File */
        private File _temp;
    }


    /**
     * [universal](commons)This class is the wapper class of Clob.
     * <br>
     * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
     * @author Camel Engineering LLP J.Matsuo
     * @version REVIEWED ON 2024.04
     * <br>####################################################################
     * <br>(change history)
     * <br>2016.08 Review of Javadoc and comments.
     * <br>2024.04 Simplification of functions due to OSS.
     * <br>####################################################################
     */
    public static class CValue
    {
        /** PREFIX:Clob temporary files */
        public static final String TEMPORARY_FILE_HEADER = "_CLOB";

        /**
         * Get InputStreamReader.
         * <br>(caution)Please close the InputStream after use.
         * @param  pmChar Charset
         * @return InputStream
         * @throws FileNotFoundException File not exists or can't be read.
         */
        public InputStreamReader getInputStreamReader(
        	Charset pmChar
        )
            throws IOException
        {
        	if (isClosed()) {
        		return null;
        	} else {
                return new InputStreamReader(getInputStream(), pmChar);
        	}
        }

        /**
         * Get InputStreamReader.
         * <br>(caution)Please close the InputStream after use.
         * @return InputStream(UTF-8)
         * @throws FileNotFoundException File not exists or can't be read.
         */
        public InputStreamReader getInputStreamReader()
            throws IOException
        {
            return getInputStreamReader(StandardCharsets.UTF_8);
        }

        /**
         * Get InputStream.
         * <br>(caution)Please close the InputStream after use.
         * @return InputStream
         * @throws FileNotFoundException File not exists or can't be read.
         */
        public InputStream getInputStream()
            throws IOException
        {
        	if (isClosed()) {
        		return null;
        	} else {
        		return new FileInputStream(getFile());
        	}
        }

        /**
         * Get all bytes data.
         * @return byte[]
         * @throws IOException can't be read.
         */
        public byte[] getBytes()
            throws IOException
        {
            // buffer
            final byte [] buff = new byte[1024];

            // length
            int len;

            try (
            	final ByteArrayOutputStream o = new ByteArrayOutputStream();
               	final InputStream i = getInputStream()
            )
            {
                while(0 < (len = i.read(buff)))
                {
                    o.write(buff, 0, len);
                }
                return o.toByteArray();
            }
        }


        /**
         * Get file.
         * @return File
         */
        public File getFile()
        {
            if (isClosed()) {
                return null;
            }
            return _temp;
        }

        /**
         * Get file length.
         * @return length
         */
        public long getSize()
        {
            if (isClosed()) {
                return 0;
            }
            return _temp.length();
        }


        /**
         * Close.
         * <br>delete temporary file.
         */
        public void close()
        {
        	if (_temp != null) {

        		try {
	
	        		if (_temp.getName().startsWith(TEMPORARY_FILE_HEADER)) {
		            // temporary file
		
		                if (_temp.exists()) {
	
		                    if (!_temp.delete()) {
		                    // if it can't be deleted

		                    	_temp.deleteOnExit();
		                    }
		                }
		            }

        		} finally {
    	            _temp = null;
        		}
        	}
        }

        /**
         * Check if it’s closed.
         * @return true: closed or false: active
         */
        public boolean isClosed()
        {
            return (_temp == null || !_temp.exists());
        }


        @Override
        public String toString()
        {
        	if (isClosed()) {
        		return "";
        	} else {
        		return _temp.getPath();
        	}
        }


        /**
         * constructor.
         * @param  pmFile InputStream
         * @throws IOException can't create temporary file
         */
        public CValue(
            final InputStream pmFile
        )
            throws IOException
        {
            _temp = null;

            // temporary folder
            String root = ResourceFactory.getDefault().get(CONFIG_TEMPORARY_FOLDER);
            if (root == null || root.length() == 0) {
                root = "./";
            }

            do {
                _temp = new File(root, Crypt.createUniquePhrase(TEMPORARY_FILE_HEADER));
            } while(_temp.exists());

            // copy
            FileIO.copyStream(new FileOutputStream(_temp), pmFile);
        }

        /**
         * constructor.
         * @param  pmFile Reader
         * @throws IOException can't create temporary file
         */
        public CValue(
            final Reader pmFile
        )
            throws IOException
        {
            _temp = null;

            // temporary folder
            String root = ResourceFactory.getDefault().get(CONFIG_TEMPORARY_FOLDER);
            if (root == null || root.length() == 0) {
                root = "./";
            }

            do {
                _temp = new File(root, Crypt.createUniquePhrase(TEMPORARY_FILE_HEADER));
            } while(_temp.exists());

            FileIO.copyStream(new OutputStreamWriter(new FileOutputStream(_temp)), pmFile);
        }

        /**
         * constructor.
         * @param  pmByte byte[]
         * @throws IOException can't create temporary file
         */
        public CValue(
            final byte[] pmByte
        )
            throws IOException
        {
            this(new ByteArrayInputStream(pmByte));
        }

        /**
         * constructor.
         * @param  pmClob Clob
         * @throws IOException  can't create temporary file
         * @throws SQLException can't read data
         */
        public CValue(
            final Clob pmClob
        )
            throws IOException, SQLException
        {
            this(pmClob.getCharacterStream());

            // [2016.07]Error on DB2
            // pmClob.free();
        }

        /**
         * constructor.
         * @param  pmFile File
         */
        public CValue(
            final File pmFile
        )
        {
            _temp = pmFile;
        }


        /** File */
        private File _temp;
    }


    /**
     * [universal](commons)This class resolves the data type of null values.     * <br>
     * <br>Copyright(c) 1999-2024 Camel Engineering LLP All Rights Reserved.
     * @author Camel Engineering LLP J.Matsuo
     * @version REVIEWED ON 2024.04
     * <br>####################################################################
     * <br>(change history)
     * <br>2016.08 Review of Javadoc and comments.
     * <br>2024.04 Simplification of functions due to OSS.
     * <br>####################################################################
     */
    public static class NValue
    {
        /**
         * Check if data is numeric.
         * @return true numeric or false other
         */
        public boolean isNumeric()
        {
            return (isDecimal() || isInteger());
        }

        /**
         * Check if data is decimal.
         * @return true decimal or false other
         */
        public boolean isDecimal()
        {
            switch (_type) {
            case Types.DECIMAL:
            case Types.NUMERIC:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.BIGINT:
            case Types.INTEGER:
                return true;
            }
            return false;
        }

        /**
         * Check if data is integer.
         * @return true integer or false other
         */
        public boolean isInteger()
        {
            switch (_type) {
            case Types.SMALLINT:
            case Types.TINYINT:
            case Types.BIT:
                return true;
            }
            return false;
        }


        /**
         * Check if data is binary.
         * @return true binary or false other
         */
        public boolean isBytes()
        {
            switch (_type) {
            case Types.LONGVARBINARY:
            case Types.VARBINARY:
            case Types.BINARY:
            case Types.ROWID:
                return true;
            default:
                return false;
            }
        }

        /**
         * Check if data is char.
         * @return true char or false other
         */
        public boolean isChar()
        {
            switch (_type) {
            case Types.NVARCHAR:
            case Types.VARCHAR:
            case Types.NCHAR:
            case Types.CHAR:
            case Types.LONGNVARCHAR:
                return true;
            default:
                return false;
            }
        }

        /**
         * Check if data is timestamp.
         * @return true timestamp or false other
         */
        public boolean isTimestamp()
        {
            return _type == Types.TIMESTAMP;
        }

        /**
         * Check if data is date.
         * @return true date or false other
         */
        public boolean isDate()
        {
            return (_type == Types.DATE);
        }

        /**
         * Check if data is time.
         * @return true time or false other
         */
        public boolean isTime()
        {
            return (_type == Types.TIME);
        }

        /**
         * Check if data is blob.
         * @return true blob or false other
         */
        public boolean isBlob()
        {
            return (_type == Types.BLOB);
        }

        /**
         * Check if data is clob.
         * @return true clob or false other
         */
        public boolean isClob()
        {
            return (_type == Types.NCLOB || _type == Types.CLOB);
        }

        /**
         * Get data type.
         * @return Type No
         */
        public int getType()
        {
            return _type;
        }


        @Override
        public String toString()
        {
            return "null";
        }


        /**
         * constructor.
         * @param  pmType Type No
         */
        public NValue(
            final int pmType
        )
        {
            _type = pmType;
        }

        /** Type No */
        private final int _type;
    }
}
