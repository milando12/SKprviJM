import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        LocalStorage storage = new LocalStorage();
//        storage.startStorage(Path.of("C:\\Fakultet\\5. Semestar\\Softverske Komponente\\takeOver"));
        storage.startStorage(Paths.get("C:","Fakultet","5. semestar","Softverske Komponente"),"Kica");


        storage.createDir(null,"janko2", "janko3");

        storage.createDir(null,"brojac",5);

        storage.delete(null,"brojac3");

        storage.listDirectory(null);


        storage.listDirectory(null);

        storage.createDir(null,"klon1","klon2","seka persa","moja djoka");

        storage.createDir(null,"probaTexta.txt","kica.jpg","let's go.exe");

        storage.listDirectory(null);

        storage.moveFile("klon1","Kica brojac5");


//        storage.rollBack();
//
//        storage.listDirectory();

//        storage.renameFile("brojac5","KaoNov");
//
//        storage.listDirectory();

        storage.subdirectoriesInfo(null);

        System.out.println("/////////////////////");

        storage.getFilesJM("Kica");

        System.out.println("/////////////////////");

        storage.getFiles("Kica");

        System.out.println("/////////////////////");

        storage.getFilesByExtension(".jpg");

        System.out.println("/////////////////////");

        storage.getFilesBySubstring("go");

        System.out.println("najvonije testiranje");

        storage.contains("Kica:janko2","klasicni.exe","klasicni.jpg","neki levi","klasicni.txt","kica.jpg");

        storage.locateFile("probaTexta.txt");

        storage.moveIn(Paths.get("C:\\Users\\Janko\\Desktop\\zaKicu"),"Kica");


        storage.listDirectory(null);

        storage.renameFile("zaKicu","noviKica2.0");

        storage.listDirectory(null);

    }
}
