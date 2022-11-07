import java.io.Serializable;
import java.nio.file.Path;

public abstract class Specification implements Serializable {

    /**
     * Starting to work with already existing storage.
     * @param path of the storage root directory.
     */
    public abstract void takeStorage(Path path);

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
    //kreiranje direktorijuma na određenoj putanji u skladištu
    public abstract boolean createDir(String path, String... name);

    /**
     * Creating given number of directories
     * @param path where the new directories lies in storage
     * @param name name of new directory
     * @param count how many new directories are going to be created- default value is 1
     * @return returns true if it was successful
     */
    //kreiranje odredjenog broja direktorijuma
    public abstract boolean createDirs(String path, String name, int count);

    /**
     * Creating directory with limits
     * @param path where the new directories lies in storage
     * @param name name on new directory
     * @param fileLimit maximum number of files that a directory can contain
     * @return returns true if it was successful
     */
    //kreiranje direktorijuma sa organicenjima
    public abstract boolean createDirLmtd(String path, String name, Integer fileLimit);

    /**
     * Moving directory into storage
     * @param where Where in storage the directory/s from outside are going to be placed
     * @param paths Absolute paths of directory/s which are being placed into storage
     * @return
     */
    //iz spoljasnjeg dela u Storage
    public abstract boolean moveInside(String where, String... paths);

    /**
     * Deleting file od directory
     * @param path Path in storage
     * @param name Name in Storage of file or directory want
     */
    //brisanje fajlova
    public abstract void delete(String path, String name);

    /**
     * Moving file from one to another directory inside Storage
     * @param source path of file we want to move relative to root
     * @param destination path of directory where it is going to be placed
     */
    //premestanje fajlova u okviru Storage-a
    public abstract void moveFile(String source, String destination);

    /**
     * Downloading a file or directory from storage.
     * @param sourcePath path of file or folder that we want to download
     * @param destinationPath absolute path in out file system where the element is going to be placed
     */
    //fajl se salje izvan skladista
    public abstract void moveOut(String sourcePath, String destinationPath);

    /**
     * Renameing of file or directory
     * @param path path to file/directory we want to rename relative to root
     * @param newName name to be updated
     * @return if renaming was successful
     */
    //preimenovanje fajla ili direktorijuma
    public abstract boolean rename(String path, String newName);

    ////////////////////////////////////////////PRETRAZIVANJE///////////////////////////////////////////////////////////

    /**
     * Gives info about files in given directory
     * @param directory directory we want to list
     */
    //vratiti sve fajlove u zadatom direktorijumu (vraća se naziv i metapodaci)
    public abstract void subdirectoriesInfo(String directory);

    /**
     * Returns files from all directories in a given directory.
     * @param path of directory.
     */
    //ulazi u direktorijume i ispisuje fajlove
    //vrati sve fajlove iz svih direktorijuma u nekom direktorijumu
    public abstract void getFilesFromSubdirectories(String path);

    /**
     * Gives list of all Files in Directory and his subdirectories
     * @param path of directory.
     */
    //vrati sve fajlove u zadatom direktorijumu i svim poddirektorijumima
    public abstract void getAllFilesFromDirectory(String path);

    /**
     * Gives list of all files with specified extension
     * @param extension extension of wanted files
     */
    //vrati fajlove sa određenom ekstenzijom,
    public abstract void getFilesByExtension(String extension);

    /**
     * Gives list of all files with specified substring
     * @param substring word that file must contain
     */
    //vrati fajlove koji u svom imenu sadrže, počinju, ili se završavaju nekim zadatim podstringom
    public abstract void getFilesBySubstring(String substring);

    /**
     * Returns true if directory contains all files.
     * @param path of directory
     * @param names of files
     * @return if directory contains given list of files
     */
    //vratiti da li određeni direktorijum sadrži fajl sa određenim imenom, ili više fajlova sa zadatom listom imena
    public abstract boolean contains(String path , String... names);

    /**
     * Returns the folder/s which contain fileName
     * @param fileName name of File we want to search
     * @return name of directory where wanted file lies
     */
    //vratiti u kom folderu se nalazi fajl sa određenim zadatim imenom
    public abstract String locateFile(String fileName);

    /**
     * Sorts directory by given criterion
     * @param path Path to directory in Storage
     * @param what criterion
     * @param ascdes order
     */
    //obezbediti zadavanje različitih kriterijuma sortiranja, na primer po nazivu,
    //datumu kreiranje ili modifikacije, rastuće ili opadajuće,
    public abstract void sortDirectory(String path,Sort what,String ascdes);


    /**
     * Returns the list of files created/modified in given period
     * @param what type to sort
     * @param from Date from
     * @param to Date to
     */
    //vrati fajlove koji su kreirani/modifikovani u nekom periodu, u nekom
    //direktorijumu,
    public abstract void getFilesByPeriod(Sort what, String from, String to);

}
