import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import static org.apache.commons.io.comparator.LastModifiedFileComparator.LASTMODIFIED_COMPARATOR;
import static org.apache.commons.io.comparator.NameFileComparator.NAME_COMPARATOR;
import static org.apache.commons.io.comparator.PathFileComparator.PATH_COMPARATOR;
import static org.apache.commons.io.comparator.SizeFileComparator.SIZE_COMPARATOR;

public class Main {

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

    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);

        String path = scanner.nextLine();

        File file = new File(String.valueOf(Paths.get(path)));
        System.out.println(file.getParent());

        FileUtils.sizeOf(file);
        System.out.println(FileUtils.sizeOf(file)); //BYtes
        System.out.println(FileUtils.sizeOf(file)/1024);    //KB
        System.out.println(FileUtils.sizeOf(file)/1048576); //MB


        File[] files = file.listFiles();
        System.out.println("Ascending order.");
        //Arrays.sort(files, SIZE_COMPARATOR);
        //Arrays.sort(files, NAME_COMPARATOR);
        //Arrays.sort(files, PATH_COMPARATOR);
        //Arrays.sort(files, LASTMODIFIED_COMPARATOR);
        displayFileOrder(files, true);

    }




//        LocalStorage storage = new LocalStorage();
////        storage.startStorage(Path.of("C:\\Fakultet\\5. Semestar\\Softverske Komponente\\takeOver"));
//        storage.startStorage(Paths.get("C:","Fakultet","5. semestar","Softverske Komponente"),"Kica");
//
//
//        storage.createDir(null,"janko2", "janko3");
//
//        storage.createDir(null,"brojac",5);
//
//        storage.delete(null,"brojac3");
//
//        storage.listDirectory(null);
//
//
//        storage.listDirectory(null);
//
//        storage.createDir(null,"klon1","klon2","seka persa","moja djoka");
//
//        storage.createDir(null,"probaTexta.txt","kica.jpg","let's go.exe");
//
//        storage.listDirectory(null);
//
//        storage.moveFile("klon1","Kica brojac5");
//
//
////        storage.rollBack();
////
////        storage.listDirectory();
//
////        storage.renameFile("brojac5","KaoNov");
////
////        storage.listDirectory();
//
//        storage.subdirectoriesInfo(null);
//
//        System.out.println("/////////////////////");
//
//        storage.getFilesJM("Kica");
//
//        System.out.println("/////////////////////");
//
//        storage.getFiles("Kica");
//
//        System.out.println("/////////////////////");
//
//        storage.getFilesByExtension(".jpg");
//
//        System.out.println("/////////////////////");
//
//        storage.getFilesBySubstring("go");
//
//        System.out.println("najvonije testiranje");
//
//        storage.contains("Kica:janko2","klasicni.exe","klasicni.jpg","neki levi","klasicni.txt","kica.jpg");
//
//        storage.locateFile("probaTexta.txt");
//
//        storage.moveIn(Paths.get("C:\\Users\\Janko\\Desktop\\zaKicu"),"Kica");
//
//
//        storage.listDirectory(null);
//
//        storage.renameFile("zaKicu","noviKica2.0");
//
//        storage.listDirectory(null);


}
