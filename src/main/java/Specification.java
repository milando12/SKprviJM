import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public abstract class Specification implements Serializable {

    /**
     * Starting to work with already existing storage.
     * @param path of the storage root directory.
     */
    public abstract void startStorage(Path path);

    /**
     * Creating new storage with defaul configuration.
     * @param path where the storage root directory lies.
     * @param name of storgae root directory.
     */
    public abstract void startStorage(Path path, String name);

    /**
     * Creating new storage with custom configuration.
     * @param path where the storage root directory lies.
     * @param name of storgae root directory.
     * @param size of storage in bytes.
     * @param fileCount number of files.
     * @param forbidenExtensions file extensions that are prohibited in this storge.
     */
    public abstract void startStorage(Path path, String name, Byte size
            , Integer fileCount, String... forbidenExtensions);

    /**
     * Creating new directories on given path
     * @param path where the new directories lies in storage
     * @param name names of new directory/ies
     * @return returns true if it was successful
     */
    public abstract boolean createDir(String path, String... name);

    /**
     * Creating given number of directories
     * @param path where the new directories lies in storage
     * @param name name on new directory
     * @param count how many new directories are going to be created- default value is 1
     * @return returns true if it was successful
     */
    public abstract boolean createDir(String path, String name, int count);

    /**
     * Creating given number of directories
     * @param path where the new directories lies in storage
     * @param name name on new directory
     * @param fileLimit maximum number of files that a directory can contain
     * @return returns true if it was successful
     */
    public abstract boolean createDir(String path, String name, Integer fileLimit);

    /**
     * Moving directory into storage
     * @param paths Absolute paths of directory/s which are being placed into storage
     * @param where Where in storage the directory/s from outside are going to be placed
     * @return
     */
    public abstract boolean moveIn(String where, String... paths);

    /**
     * Deleting file od directory
     * @param name Name in Storage of file or directory want
     * @param path Path in storage
     */
    public abstract void delete(String path, String name);

    /**
     * Moving file from one to another directory inside Storage
     * @param source path of file we want to move relative to root
     * @param destination path of directory where it is going to be placed
     */
    public abstract void moveFile(String source, String destination);
    //TODO promeni ako ne mogu ista imena
    // uradjeno

    /**
     * Downloading of file or folder from storage.
     * @param sourcePath path of file or folder that we want to download
     * @param destinationPath absolute path in out file system where the element is going to be placed
     */
    public abstract void downloadFile(String sourcePath, String destinationPath);

    /**
     * Renameing of file or directory
     * @param path path to file/directory we want to rename relative to root
     * @param newName name to be updated
     * @return
     */
    public abstract boolean rename(String path, String newName);
    //TODO promeni ako ne mogu ista imena

    //Pretrage

    /**
     * Gets info about directory.
     * @param directory
     * @return String containing names and info of all subdirectories of a given directory
     */
    public abstract String subdirectoriesInfo(String directory);

    /**
     * Returns files from all directories in a given directory.
     * @param path of directory.
     * @return list of files on depth of one.
     */
    public abstract List<File>  getFilesJM(String path);

    /**
     * Returns the List of all Files under given directory
     * @param path of directory.
     * @return List of all Files under given directory.
     */
    public abstract List<File> getFiles(String path);

    /**
     * Returns all files from the storage with a given extension.
     * @param extension
     * @return files from the storage with a given extension.
     */
    public abstract List<File> getFilesByExtension(String extension);

    /**
     * Returns files that contain a substring.
     * @param substring
     * @return
     */
    public abstract List<File> getFilesBySubstring(String substring);

    /**
     * Returns true if directory contains all files.
     * @param path of directory
     * @param names of files
     * @return
     */
    public abstract boolean contains(String path , String... names);

    /**
     * Returns the folder/s which contain fileName
     * @param fileName
     * @return
     */
    public abstract String locateFile(String fileName);

    /**
     * Returns the list of files created/modified in period between start and end.
     * @param start
     * @param end
     * @return
     */
    public abstract List<File> getFilesByPeriod(Date start, Date end);

}
