import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import static org.apache.commons.io.comparator.LastModifiedFileComparator.LASTMODIFIED_COMPARATOR;
import static org.apache.commons.io.comparator.NameFileComparator.NAME_COMPARATOR;
import static org.apache.commons.io.comparator.SizeFileComparator.SIZE_COMPARATOR;

public class LocalStorage extends Storage{

    private MyFile storageRoot;
    private List<FileJM> filesJM;

    private void errorMessage(String function){
        System.err.println("Greska u: " + function);
    }

    /**
     * Za prosledjenu putanju vraca fajl na toj putanji u obliku MyFile
     */
    private MyFile pathToMyFile(String path){
        String pool[] = path.split(":");
        if(pool.length == 1 && pool[0].equals(storageRoot.getName())){
            return storageRoot;
        }

//        if(!(pool[0].equals(storageRoot.getName()))){   //ako nije dobro imenovan root
//            errorMessage("pathToMyFile");
//            return null;
//        }

        MyFile pointer = storageRoot;
        for (int i = 1; i < pool.length; i++){
            if(pointer.childExists(pool[i])){
                pointer = pointer.findChildByName(pool[i]);
            }else {
                errorMessage("pathToMyFile");
//                return null;
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
                    FileJM fileJM = new FileJM(directory.getChildrenList().get(i).getName(),directory.getChildrenList().get(i).getFile().getPath());
                    filesJM.add(fileJM);
                    System.out.println(directory.getChildrenList().get(i).getName());
                    continue;
                }
                if (function.equalsIgnoreCase("getFilesByExtension") && directory.getChildrenList().get(i).getName().endsWith(checker)) {
                    FileJM fileJM = new FileJM(directory.getChildrenList().get(i).getName(),directory.getChildrenList().get(i).getFile().getPath());
                    filesJM.add(fileJM);
                    System.out.println(directory.getChildrenList().get(i).getName());
                    continue;
                }
                if (function.equalsIgnoreCase("getFilesBySubstring") && directory.getChildrenList().get(i).getName().contains(checker)) {
                    FileJM fileJM = new FileJM(directory.getChildrenList().get(i).getName(),directory.getChildrenList().get(i).getFile().getPath());
                    filesJM.add(fileJM);
                    System.out.println(directory.getChildrenList().get(i).getName());
                    continue;
                }
                if(function.equalsIgnoreCase("locateFile") && directory.getChildrenList().get(i).getName().contains(checker)){
                    System.out.println(checker + " is located in " + directory.getName());
                    return;
                }
            }
        }
    }

    private static void praviDecu(MyFile roditelj){
        File[] files = roditelj.getFile().listFiles();
        for (File listaj : files){
            if(listaj.isDirectory()){
                MyFile novi = new MyFile(listaj.getName(),roditelj,listaj,true);
                praviDecu(novi);
            }else {
                MyFile novi = new MyFile(listaj.getName(),roditelj,listaj,false);
            }
        }
    }

    public void listDir(String path){
        MyFile toList = pathToMyFile(path);

        for (MyFile mf : toList.getChildrenList() ){
            System.out.println(mf.getName());
        }

    }


    @Override
    public void startStorage(String path) {
        File root = new File(String.valueOf(Paths.get(path)));

        if (root.exists()){
            this.storageRoot = new MyFile(root.getName(),null,root,true);
            this.filesJM = new ArrayList<>();
            praviDecu(storageRoot);
            System.out.println("SysInfo: Direktorijum " + storageRoot.getName() + " je uspesno preuzet");
        }else {
            errorMessage("takeStorage");
        }
    }

    @Override
    public void startStorage(String path, String name) {
        File root = new File(String.valueOf(Paths.get(path)),name);        //pravimo folder

        if(root.mkdir()){
            this.storageRoot = new MyFile(name,null,root,true);     //odmah pravimo objekat nase klase
            this.filesJM = new ArrayList<>();
            System.out.println("SysInfo: Direktorijum " + storageRoot.getName() + " je uspesno kreiran");
        }else
            errorMessage("startStorage");
    }

    //TODO
    @Override
    public void startStorage(String path, String name, long size, String... forbidenExtensions){
        this.filesJM = new ArrayList<>();
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
                File newDirectory = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(), newOne)));
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
    public boolean createDir(String path, String universalName, int i){
        for (int j = 1; j <=i; j++) {
            createDir(path, universalName.concat(String.valueOf(j)));
        }
        return true;
    }

//    @Override
//    public boolean createDir(String path, String universalName, int i) {
//        MyFile currentDirectory = pathToMyFile(path);
//
//        if(universalName.contains(".")){
//            for(int count = 1; count <= i; count++){
//                try {
//                    String name = universalName.concat(String.valueOf(count));
//                    File newFile = new File(currentDirectory.getFile(),name);
//                    FileUtils.touch(newFile);
//                    if (newFile.mkdir()){
//                        MyFile myFile = new MyFile(name,currentDirectory,newFile,false);
//                    }else {
//                        errorMessage("createDirs");
//                        return false;
//                    }
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        }else {
//            for(int count = 1; count <= i; count++){
//                try {
//                    String name = universalName.concat(String.valueOf(count));
//
//                    File newDirectory = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),name)));
//                    FileUtils.touch(newDirectory);
//                    if (newDirectory.mkdir()) {
//                        MyFile myFile = new MyFile(name,currentDirectory,newDirectory,true);
//                        return true;
//                    } else {
//                        errorMessage("createDirs");
//                        return false;
//                    }
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//        return false;
//    }

    @Override
    public boolean createDir(String path, String name, Integer fileLimit){
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

        MyFile parent = fileToRename.getParent();
        parent.removeChild(fileToRename);

        if(fileToRename.getFile().renameTo(fileWithNewName)){
            MyFile novajlija = new MyFile(fileWithNewName.getName(),parent,fileWithNewName,fileWithNewName.isDirectory());
            praviDecu(novajlija);
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
//        fileToMove.setParent(currentDirectory);

        if(fileToMove.isDirectory()){
            try {
                FileUtils.moveDirectoryToDirectory(fileToMove.getFile(),currentDirectory.getFile(),false);
                File changingFile = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),fileToMove.getName())));
                MyFile novi = new MyFile(changingFile.getName(),currentDirectory,changingFile,true);
                praviDecu(novi);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                FileUtils.moveFileToDirectory(fileToMove.getFile(),currentDirectory.getFile(),false);
                File changingFile = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),fileToMove.getName())));
                MyFile novi = new MyFile(changingFile.getName(),currentDirectory,changingFile,false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean moveInside(String where, String fileType,Path... paths) {
        MyFile currentDirectory = pathToMyFile(where);

        for (Path putanja : paths){
            File toPutin = new File(String.valueOf(putanja));          //fajl koji treba ubaciti

            if(toPutin.isDirectory()){
                try {
                    FileUtils.moveDirectoryToDirectory(toPutin,currentDirectory.getFile(), true);
                    File newPointerForFile = new File(String.valueOf(Paths.get(currentDirectory.getFile().getPath(),toPutin.getName())));
                    MyFile myFile = new MyFile(newPointerForFile.getName(),currentDirectory,newPointerForFile,true);
                    praviDecu(myFile);
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
        fileToGoOut.getParent().removeChild(fileToGoOut);
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
    public List<FileJM> subdirectoriesInfo(String path) {
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
            errorMessage("subdirectoriesInfo_WrongDirectory");
        }
        return null;
    }

    @Override
    public List<FileJM> getFilesFromAllSubdirectories(String putanja) {
        MyFile pointerMyFile = pathToMyFile(putanja);
        filesJM.removeAll(filesJM);

        if(pointerMyFile.isDirectory()){
            for (MyFile childToList : pointerMyFile.getChildrenList()){
                fileSearch(childToList,null,"getFilesJM");
            }
        }else {
            errorMessage("getFilesJM_WrongDirectory");
        }
        return null;
    }

    //treba da vrati fajlove i iz zadatog direktorijuma
    @Override
    public List<FileJM> getAllFilesFromDirectory(String putanja) {
        MyFile pointerMyFile = pathToMyFile(putanja);
        filesJM.removeAll(filesJM);

        if (pointerMyFile.isDirectory()){
            fileSearch(pointerMyFile,null,"getFiles");
        }else {
            errorMessage("getFiles_WrongDirectory");
        }
        return null;
    }

    @Override   //najbolje bi bilo da se ukuca sa tackom .exe .txt
    public List<FileJM> getFilesByExtension(String extension) {
        filesJM.removeAll(filesJM);
        fileSearch(storageRoot,extension,"getFilesByExtension");
        return null;
    }

    @Override
    public List<FileJM> getFilesBySubstring(String substring) {
        filesJM.removeAll(filesJM);
        fileSearch(storageRoot,substring,"getFilesBySubstring");
        return null;
    }

    @Override
    public boolean containsFiles(String putanja, String... names) {
        MyFile fileToCheck = pathToMyFile(putanja);
        int count = 0;

        if (fileToCheck.isDirectory()){
            for (MyFile detence : fileToCheck.getChildrenList()){
                if (!(detence.isDirectory())){
                    for (String trazenoIme : names){
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
    public List<String> locateFile(String fileToFind) {
        filesJM.removeAll(filesJM);
        fileSearch(storageRoot,fileToFind,"locateFile");
        return null;
    }


    private static void displayFileOrder(File[] files, boolean displayDirectory) {

        for (File file : files) {
            if (!file.isDirectory()) {
                System.out.printf("%-25s - %s%n", file.getName(),
                        FileUtils.byteCountToDisplaySize(file.length()));
            } else if (displayDirectory) {
                long size = FileUtils.sizeOfDirectory(file);
                String friendlySize = FileUtils.byteCountToDisplaySize(size);
                System.out.printf("%-25s - %s%n", file.getName(),
                        friendlySize);
            }
        }

        System.out.println("------------------------------------");
    }

    //obezbediti zadavanje različitih kriterijuma sortiranja, na primer po nazivu,
    //datumu kreiranje ili modifikacije, rastuće ili opadajuće,
    @Override // treba dodati opadajuci niz
    public List<FileJM> sortDirectory(List<FileJM> fileJMList, Sort sort, Sort ascdes) {
        int count = 0;
        File[] files = new File[fileJMList.size()];
        for (FileJM jmfile : fileJMList){
            File file = new File(jmfile.getPath());
            files[count++] = file;
        }

        if (ascdes.equals(Sort.ASC)){
            if(sort.equals(Sort.SIZE)){
                Arrays.sort(files,SIZE_COMPARATOR);
                displayFileOrder(files, true);
            }

            else if(sort.equals(Sort.DATE_MODIFIED)){
                Arrays.sort(files,LASTMODIFIED_COMPARATOR);
                displayFileOrder(files, true);
            }

            else if(sort.equals(Sort.NAME)){
                Arrays.sort(files,NAME_COMPARATOR);
                displayFileOrder(files, true);
            }
        }else if(ascdes.equals(Sort.DSC)){
            if(sort.equals(Sort.SIZE)){
                Arrays.sort(files,SIZE_COMPARATOR);
                displayFileOrder(files, true);
            }

            else if(sort.equals(Sort.DATE_MODIFIED)){
                Arrays.sort(files,LASTMODIFIED_COMPARATOR);
                displayFileOrder(files, true);
            }

            else if(sort.equals(Sort.NAME)){
                Arrays.sort(files,NAME_COMPARATOR);
                displayFileOrder(files, true);
            }
        }


        return null;
    }

    //treba da vrati fajlove iz direktorijuma za neki period
    @Override
    public List<FileJM> getFilesByPeriod(String path, String from, String to) {
        List <FileJM> listToReturn = new ArrayList<>();
        MyFile pointerMyFile = pathToMyFile(path);      //direktorijum koji proveravamo
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");  //date format
        Date dateFrom;
        Date dateTo;

        try {
            dateFrom = sdf.parse(from);
            dateTo = sdf.parse(to);
        } catch (ParseException e) {
            errorMessage("getFilesByPeriod_DataParsing");
            return null;
        }

        System.out.println("Ispis imena fajlova za period od: " + sdf.format(dateFrom) + " do: " + sdf.format(dateTo));
        for (MyFile fileKid : pointerMyFile.getChildrenList()){
            try {
                BasicFileAttributes atr = Files.readAttributes(Paths.get(fileKid.getFile().getPath()),BasicFileAttributes.class);
                Date fileCreationTime = new Date(atr.creationTime().toMillis());
                Date fileModificationTime = new Date(atr.lastModifiedTime().toMillis());

                if(fileCreationTime.compareTo(dateFrom) >= 0 && fileCreationTime.compareTo(dateTo) <= 0){
                    System.out.println(fileKid.getName());
                    FileJM fileJM = new FileJM(fileKid.getName(),fileKid.getFile().getPath());
                    listToReturn.add(fileJM);
                    continue;
                }

                if (fileModificationTime.compareTo(dateFrom) >= 0 && fileModificationTime.compareTo(dateTo) <= 0){
                    System.out.println(fileKid.getName());
                    //String name, String path, String creationTime, String modificationTime, Integer size, boolean isDirectory
                    FileJM fileJM = new FileJM(fileKid.getName(),fileKid.getFile().getPath());
                    listToReturn.add(fileJM);
                }

            } catch (IOException e) {
                errorMessage("getFilesByPeriod_BasicFileAtrError");
                return null;
            }
        }

        return listToReturn;
    }

}
