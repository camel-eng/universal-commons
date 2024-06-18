package jp.co.cam.universal.util.io;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

import jp.co.cam.universal.configuration.ResourceFactory;


/**
 * [universal](commons)Provides file find functionality.
 * <br>
 * <br>Copyright(c) 2016-2023 Camel Engineering LLP All Rights Reserved.
 * @author Camel Engineering LLP J.Matsuo
 * @version REVIEWED ON 2023.12
 * <br>########################################################################
 * <br>(change history)
 * <br>2016.08 New addition.
 * <br>2023.12 Reorganize before publishing.
 * <br>2024.04 Changed file search priority.
 * <br>########################################################################
 */
public class FileRepository
{
	/**
	 * Find for the specified file.
	 * @param  pmPath directory path
	 * @param  pmFile file name
	 * @return InputStream
	 * @throws IOException If file access fails.
	 */
	public static InputStream find(
		final String pmPath
	  , final String pmFile
	)
		throws IOException
	{
        if (pmFile == null || pmFile.length() == 0) {
        	throw new IllegalArgumentException("file name is necessary");
        }

        // return:InputStream
        InputStream ret;

        // ========================================================================================
        // file search
        // ========================================================================================

        // base directory
        String repository = ResourceFactory.getDefault().get("settings.directory.repository");
        
        // directory path
        String path = pmPath;

        // file name
        String file= pmFile;

        if (repository != null && repository.length() != 0) {

        	repository = repository.replace("\\", "/");
        	if (!repository.endsWith("/")) {
        		repository += "/";
        	}

        } else {
        	repository = "";
        }

        if (path != null && path.length() != 0) {

        	path = path.replace("\\", "/");
        	if (!path.endsWith("/")) {
        		path += "/";
        	}

        } else {
        	path = "";
        }

        file = file.replace("\\", "/");
        
        // ----------------------------------------------------------------------------------------
        // 1.{settings.directory.repository} + pmPath + pmFile
        // ----------------------------------------------------------------------------------------
        if (repository.length() != 0) {

        	// file path
        	String temp;

        	if (file.startsWith("/")) {
        		temp = repository + path + file.substring(1);
        	} else {
        		temp = repository + path + file;
        	}

        	// check full path
        	if (new File(temp).exists()) {
            	return new FileInputStream(temp);
            }

        	// find under classpath
            ret = FileRepository.class.getClassLoader().getResourceAsStream(temp);
            if (ret != null) return ret;
            ret = FileRepository.class.getResourceAsStream(temp);
            if (ret != null) return ret;
        }

        // ----------------------------------------------------------------------------------------
        // 2.pmPath + pmFile
        // ----------------------------------------------------------------------------------------
        if (path.length() != 0) {

        	// file path
        	String temp;

        	if (file.startsWith("/")) {
        		temp = path + file.substring(1);
        	} else {
        		temp = path + file;
        	}

        	// check full path
        	if (new File(temp).exists()) {
            	return new FileInputStream(temp);
            }

        	// find under classpath
            ret = FileRepository.class.getClassLoader().getResourceAsStream(temp);
            if (ret != null) return ret;
            ret = FileRepository.class.getResourceAsStream(temp);
            if (ret != null) return ret;
        }

        // ----------------------------------------------------------------------------------------
        // 2.pmFile
        // ----------------------------------------------------------------------------------------

    	// check full path
    	if (new File(file).exists()) {
        	return new FileInputStream(file);
        }

    	// find under classpath
        ret = FileRepository.class.getClassLoader().getResourceAsStream(file);
        if (ret != null) return ret;
        ret = FileRepository.class.getResourceAsStream(file);
        if (ret != null) return ret;


        throw new FileNotFoundException("file not found - " + file);
	}

	/**
	 * Find for the specified file.
	 * @param  pmFile file name
	 * @return InputStream
	 * @throws IOException If file access fails.
	 */
	public static InputStream find(
		final String pmFile
	)
		throws IOException
	{
        return FileRepository.find(null, pmFile);
	}
}
