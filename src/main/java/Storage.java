import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

public abstract class Storage implements Serializable {

    /**
     * Starting to work with already existing storage.
     * @param path of the storage root directory.
     */
    //kaci se na direktorijum koji vec postoji
    public abstract void startStorage(Path path);

    /**
     * Creating new storage with defaul configuration.
     * @param path where the storage root directory lies.
     * @param name of storgae root directory.
     */
    //kreiramo nas direktorijum koji ce nam biti storage
    public abstract void startStorage(Path path, String name);

    /**
     * Creating new storage with custom configuration.
     * @param path where the storage root directory lies.
     * @param name of storgae root directory.
     * @param size of storage in bytes.
     * @param fileCount number of allowed files.
     * @param forbidenExtensions file extensions that are prohibited in this storge.
     */
    //kreiramo nas direktorijum koji ce nam biti storage ali sa ogranicenjima
    public abstract void startStorageLmtd(Path path, String name, Byte size
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
    public abstract boolean createDir(String path, String name, int count);

    /**
     * Creating directory with limits
     * @param path where the new directories lies in storage
     * @param name name on new directory
     * @param fileLimit maximum number of files that a directory can contain
     * @return returns true if it was successful
     */
    //kreiranje direktorijuma sa organicenjima
    public abstract boolean createDir(String path, String name, Integer fileLimit);

    /**
     * Deleting file od directory
     * @param path Path to file we want ot delete
     */
    //brisanje fajlova
    public abstract void delete(String path);

    /**
     * Renameing of file or directory
     * @param path path to file/directory we want to rename relative to root
     * @param newName name to be updated
     * @return if renaming was successful
     */
    //preimenovanje fajla ili direktorijuma
    public abstract boolean rename(String path, String newName);

    /**
     * Moving file from one to another directory inside Storage
     * @param source path of file we want to move relative to root
     * @param destination path of directory where it is going to be placed
     */
    //premestanje fajlova u okviru Storage-a
    public abstract void moveFile(String source, String destination);

    /**
     * Moving directory into storage
     * @param where Where in storage the directory/s from outside are going to be placed
     * @param paths Absolute paths of directory/s which are being placed into storage
     * @return
     */
    //iz spoljasnjeg dela u Storage
    public abstract boolean moveInside(String where, Path... paths);

    /**
     * Downloading a file or directory from storage.
     * @param sourcePath path of file or folder in Storage we want to download
     * @param destinationPath absolute path in out file system where the element is going to be placed
     */
    //fajl se salje izvan skladista
    public abstract void moveOut(String sourcePath, Path destinationPath);

    ////////////////////////////////////////////PRETRAZIVANJE///////////////////////////////////////////////////////////

    /**
     * Gives info about files in given directory
     * @param directory directory we want to list
     */
    //vratiti sve fajlove u zadatom direktorijumu (vraća se naziv i metapodaci)
    public abstract String subdirectoriesInfo(String directory);

    /**
     * Gives all files from subdirectories.
     * @param path of directory.
     */
    //vrati sve fajlove iz svih direktorijuma u nekom direktorijumu
    public abstract List<File> getFilesFromAllSubdirectories(String path);

    /**
     * Gives list of all Files in Directory and his subdirectories
     * @param path of directory.
     */
    //vrati sve fajlove u zadatom direktorijumu i svim poddirektorijumima
    public abstract List<File> getAllFilesFromDirectory(String path);

    /**
     * Gives list of all files with specified extension
     * @param extension extension of wanted files
     */
    //vrati fajlove sa određenom ekstenzijom,
    public abstract List<File> getFilesByExtension(String extension);

    /**
     * Gives list of all files with specified substring
     * @param substring word that file must contain
     */
    //vrati fajlove koji u svom imenu sadrže, počinju, ili se završavaju nekim zadatim podstringom
    public abstract List<File> getFilesBySubstring(String substring);

    /**
     * Returns true if directory contains all files.
     * @param path of directory
     * @param names of files
     * @return if directory contains given list of files
     */
    //vratiti da li određeni direktorijum sadrži fajl sa određenim imenom, ili više fajlova sa zadatom listom imena
    public abstract boolean containsFiles(String path , String... names);

    /**
     * Gives name of directory where file whit that name is located
     * @param fileName name of File we want to search
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
    //vrati fajlove koji su kreirani/modifikovani u nekom periodu, u nekom direktorijumu
    //lakse je da mi parsiramo datum nego da ga prosledjujemo
    public abstract List<File> getFilesByPeriod(Sort what, String from, String to);

}
