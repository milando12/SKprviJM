import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.commons.io.FileUtils;

public class LocalStorage extends Storage{

    private MyFile storageRoot;
    private boolean forGit = true;

    /**
     * Ispisivanje poruke za gresku
     */
    private void errorMessage(String function){
        System.err.println("Lose zadata putanja " + function);
    }

    /**
     * Proverava da li se misli na storageRoot
     */
    private boolean isPathForRoot(int lenght, String name){
        return lenght == 1 && name.equals(storageRoot.getName());
    }

    /**
     * Za prosledjenu putanju vraca fajl na toj putanji u obliku MyFile
     */
    private MyFile pathToMyFile(String path){
        String pool[] = path.split(":");
        if(isPathForRoot(pool.length, pool[0])){
            return storageRoot;
        }

        if(!(pool[0].equals(storageRoot.getName()))){   //ako nije dobro imenovan root
            errorMessage("pathToMyFile");
            return null;
        }

        MyFile pointer = storageRoot;
        for (int i = 1; i < pool.length; i++){
            if(pointer.childExists(pool[i])){
                pointer = pointer.findChildByName(pool[i]);
            }else {
                errorMessage("pathToMyFile");
                return null;
            }
        }
        return pointer;
    }

    /**
     * Pronalazi i ispisuje fajlove na osnovu toga koja funkcija je poziva
     */
    private void fileSearch(MyFile directory, String checker, String function){
        if(directory.getChildrenList().size() == 0){
            return;
        }

        for(int i = 0; i <= directory.getChildrenList().size()-1; i++) {
            if(directory.getChildrenList().get(i).isDirectory()){
                fileSearch(directory.getChildrenList().get(i),checker,function);        //ako je direktorijum ulazimo u njega
            }else {
                if (function.equalsIgnoreCase("getFilesJM") || function.equalsIgnoreCase("getFiles")) {
                    System.out.println(directory.getChildrenList().get(i).getName().toUpperCase());
                    break;
                }
                if (function.equalsIgnoreCase("getFilesByExtension") && directory.getChildrenList().get(i).getName().endsWith(checker)) {
                    System.out.println(directory.getChildrenList().get(i).getName().toUpperCase());
                    break;
                }
                if (function.equalsIgnoreCase("getFilesBySubstring") && directory.getChildrenList().get(i).getName().contains(checker)) {
                    System.out.println(directory.getChildrenList().get(i).getName().toUpperCase());
                    break;
                }
                if(function.equalsIgnoreCase("locateFile") && directory.getChildrenList().get(i).getName().contains(checker)){
                    System.out.println(checker + " is located in " + directory.getName().toUpperCase());
                    return;
                }
            }
        }
    }

    /**
     * Izlistava direktorijum na zadatoj putanji
     */
    public void listDirectory(String path){
        MyFile toList = pathToMyFile(path);
        if(toList.isDirectory()){
            if(toList.getChildrenList().isEmpty()){
                System.out.println(toList.getName() + " is Empty");
                return;
            }
            System.out.println("Izlistavanje direktorijuma " + toList.getName());
            for (MyFile child : toList.getChildrenList()){
                System.out.println(child.getName());
            }
        }else {
            errorMessage("listDirectory");
        }
    }

    @Override
    public void takeStorage(Path path) {
        File root = new File(String.valueOf(path));

        if (root.exists()){
            this.storageRoot = new MyFile(root.getName(),null,root,true);
            System.out.println("SysInfo: Direktorijum " + storageRoot.getName() + " je uspesno preuzet");
        }else {
            errorMessage("takeOverStorage");
        }
    }

    @Override
    public void startStorage(Path path, String name) {
        File root = new File(String.valueOf(path),name);        //pravimo folder

        if(root.mkdir()){
            this.storageRoot = new MyFile(name,null,root,true);     //odmah pravimo objekat nase klase
            System.out.println("SysInfo: Direktorijum " + storageRoot.getName() + " je uspesno kreiran");
        }else
            errorMessage("startStorage");
    }

    //TODO
    @Override
    public void startStorageLmtd(Path path, String s, Byte aByte, Integer integer, String... strings) {

    }

    @Override
    public boolean createDir(String path, String... names) {
        MyFile currentDirectory = pathToMyFile(path);

        for (String newOne : names){
            if(newOne.contains(".")){
                try {
                    File newFile = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),newOne)));
                    FileUtils.touch(newFile);
                    MyFile myFile = new MyFile(newOne,currentDirectory,newFile,false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                File newDirectory = new File(currentDirectory.getFile(), newOne);
                if (newDirectory.mkdir()) {
                    MyFile myFile = new MyFile(newOne, currentDirectory,  newDirectory, true);
                } else {
                    errorMessage("createDir");
                }
            }
        }
        return false;
    }

    @Override
    public boolean createDirs(String path, String universalName, int i) {
        MyFile currentDirectory = pathToMyFile(path);

        if(universalName.contains(".")){
            for(int count = 1; count <= i; count++){
                try {
                    String name = universalName.concat(String.valueOf(count));
                    File newFile = new File(currentDirectory.getFile(),name);
                    FileUtils.touch(newFile);
                    if (newFile.mkdir()){
                        MyFile myFile = new MyFile(name,currentDirectory,newFile,false);
                    }else {
                        errorMessage("createDirs");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }else {
            for(int count = 1; count <= i; count++){
                try {
                    String name = universalName.concat(String.valueOf(count));
                    File newDirectory = new File(currentDirectory.getFile(),name);
                    FileUtils.touch(newDirectory);
                    if (newDirectory.mkdir()) {
                        MyFile myFile = new MyFile(name,currentDirectory,newDirectory,true);
                    } else {
                        errorMessage("createDirs");
                        return false;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public boolean createDirLmtd(String path, String name, Integer fileLimit){
        return false;
    }

    @Override
    public void delete(String pathInStorage) {
        MyFile forDelete = pathToMyFile(pathInStorage);

        if(forDelete.getFile().delete()){
            forDelete.getParent().removeChild(forDelete);
        }else {
            errorMessage("delete");
        }
    }

    @Override
    public boolean rename(String oldNamePath, String newName) {
        MyFile fileToRename = pathToMyFile(oldNamePath);        //fajl koji menja ime
        File fileWithNewName = new File(String.valueOf(Paths.get(fileToRename.getParent().getFile().getPath(),newName)));

        if(fileToRename.getFile().renameTo(fileWithNewName)){
            fileToRename.setFile(fileWithNewName);
            return true;
        }else {
            errorMessage("renameFile");
        }

        return false;
    }

    @Override
    public void moveFile(String fileToMovePath, String location) {
        MyFile currentDirectory = pathToMyFile(location);       //mesto gde stavljamo novi fajl
        MyFile fileToMove = pathToMyFile(fileToMovePath);       //fajl koji premestamo

        fileToMove.getParent().removeChild(fileToMove);
        fileToMove.setParent(currentDirectory);

        if(fileToMove.isDirectory()){
            try {
                FileUtils.moveDirectoryToDirectory(fileToMove.getFile(),currentDirectory.getFile(),false);
                File changingFile = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),fileToMove.getName())));
                fileToMove.setFile(changingFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                FileUtils.moveFileToDirectory(fileToMove.getFile(),currentDirectory.getFile(),false);
                File changingFile = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),fileToMove.getName())));
                fileToMove.setFile(changingFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean moveInside(String where, Path... paths) {
        MyFile currentDirectory = pathToMyFile(where);

        for (Path putanja : paths){
            File toPutin = new File(String.valueOf(putanja));          //fajl koji treba ubaciti

            if(toPutin.isDirectory()){
                try {
                    FileUtils.moveDirectoryToDirectory(toPutin,currentDirectory.getFile(), true);
                    File newPointerForFile = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),toPutin.getName())));
                    MyFile myFile = new MyFile(newPointerForFile.getName(),currentDirectory,newPointerForFile,true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                try {
                    FileUtils.moveFileToDirectory(toPutin,currentDirectory.getFile(), true);
                    File newPointerForFile = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),toPutin.getName())));
                    MyFile myFile = new MyFile(newPointerForFile.getName(),currentDirectory,newPointerForFile,false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return false;
    }

    @Override
    public void moveOut(String sourcePath, Path destinationPath){
        if (destinationPath.toString().contains(storageRoot.getName())){
            errorMessage("moveOut");
            return;
        }

        MyFile fileToGoOut = pathToMyFile(sourcePath);
        File location = new File(String.valueOf(Paths.get(String.valueOf(destinationPath))));

        if(fileToGoOut.isDirectory()){
            try {
                FileUtils.moveDirectoryToDirectory(fileToGoOut.getFile(),location,true);
                fileToGoOut.getParent().removeChild(fileToGoOut);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                FileUtils.moveFileToDirectory(fileToGoOut.getFile(),location,true);
                fileToGoOut.getParent().removeChild(fileToGoOut);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void subdirectoriesInfo(String path) {
        MyFile currentDirectory = pathToMyFile(path);

        System.out.println("Ispis podataka o deci:");
        if (currentDirectory.isDirectory()){
            for (MyFile myFile : currentDirectory.getChildrenList()){
                if (!(myFile.isDirectory())){
                    try {
                        BasicFileAttributes attributes = Files.readAttributes(Paths.get(myFile.getFile().getPath()),BasicFileAttributes.class);
                        System.out.println(myFile.getName().toUpperCase());
                        System.out.println("creationTime: " + attributes.creationTime());
                        System.out.println("lastAccessTime: " + attributes.lastAccessTime());
                        System.out.println("lastModifiedTime: " + attributes.lastModifiedTime());
                        System.out.println("isDirectory: " + attributes.isDirectory());
                        System.out.println("isOther: " + attributes.isOther());
                        System.out.println("isRegularFile: " + attributes.isRegularFile());
                        System.out.println("isSymbolicLink: " + attributes.isSymbolicLink());
                        System.out.println("size: " + attributes.size());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }else {
            errorMessage("subdirectoriesInfo");
        }
    }

    @Override
    public void getFilesFromAllSubdirectories(String putanja) {
        MyFile pointerMyFile = pathToMyFile(putanja);

        if(pointerMyFile.isDirectory()){
            for (MyFile childToList : pointerMyFile.getChildrenList()){
                fileSearch(childToList,null,"getFilesJM");
            }
        }else {
            errorMessage("getFilesJM");
        }
    }

    //treba da vrati fajlove i iz zadatog direktorijuma  easy:)
    @Override
    public void getAllFilesFromDirectory(String putanja) {
        MyFile pointerMyFile = pathToMyFile(putanja);

        if (pointerMyFile.isDirectory()){
            fileSearch(pointerMyFile,null,"getFiles");
        }else {
            errorMessage("getFiles");
        }
    }

    @Override   //najbolje bi bilo da se ukuca sa tackom .exe .txt
    public void getFilesByExtension(String extension) {
        fileSearch(storageRoot,extension,"getFilesByExtension");
    }

    @Override
    public void getFilesBySubstring(String substring) {
        fileSearch(storageRoot,substring,"getFilesBySubstring");
    }

    @Override
    public boolean containsFiles(String putanja, String... strings) {
        MyFile fileToCheck = pathToMyFile(putanja);
        int count = 0;

        if (fileToCheck.isDirectory()){
            for (MyFile detence : fileToCheck.getChildrenList()){
                if (!(detence.isDirectory())){
                    for (String trazenoIme : strings){
                        if(detence.getName().equals(trazenoIme)){
                            System.out.println(detence.getName());
                            count++;
                            break;
                        }
                    }
                }
            }

            if (count == 0){
                System.out.println("Ne posotji ni jedan fajl od zadatih");
            }
        }else {
            errorMessage("contains");
        }

        return false;
    }

    @Override
    public void locateFile(String fileToFind) {
        fileSearch(storageRoot,fileToFind,"locateFile");
    }

    //obezbediti zadavanje različitih kriterijuma sortiranja, na primer po nazivu,
    //datumu kreiranje ili modifikacije, rastuće ili opadajuće,
    //TODO
    @Override
    public void sortDirectory(String s, Sort sort, String s1) {

    }

    //TODO
    @Override
    public void getFilesByPeriod(Sort what, String from, String to) {

    }

}
