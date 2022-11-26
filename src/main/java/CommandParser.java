import java.nio.file.Paths;

public class CommandParser {

    private LocalStorage localStorage;

    public void command(String[] linija){
        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.TAKE))){
            localStorage = new LocalStorage();
            localStorage.startStorage(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.START))){
            localStorage = new LocalStorage();
            localStorage.startStorage(linija[1],linija[2]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.TAKE))){

        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.STARTLIM))){

        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MKDIR))){
            localStorage.createDir(linija[1],linija[2]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MKDIRS))){
            localStorage.createDir(linija[1],linija[2],Integer.parseInt(linija[3]));
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MKDIRLIM))){

        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.DELDIR))){
            localStorage.delete(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.RENAME))){
            localStorage.rename(linija[1],linija[2]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MOV))){
            localStorage.moveFile(linija[1],linija[2]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MOVIN))){
            localStorage.moveInside(linija[1],linija[2],Paths.get(linija[3]));
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MOVOUT))){
            localStorage.moveOut(linija[1],Paths.get(linija[2]));
        }

        //privremenooo
        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.LIST))){
            localStorage.listDir(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.SUBINFO))){
            localStorage.subdirectoriesInfo(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.SUBINFOAL))){
            localStorage.getFilesFromAllSubdirectories(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.DIRFILES))){
            localStorage.getAllFilesFromDirectory(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.FILEXT))){
            localStorage.getFilesByExtension(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.FILSUB))){
            localStorage.getFilesBySubstring(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.FILSUB))){
            localStorage.getFilesBySubstring(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.FILCON))){
            localStorage.containsFiles(linija[1],linija[2]);
        }
    }


    public LocalStorage getLocalStorage() {
        return localStorage;
    }

    public void setLocalStorage(LocalStorage localStorage) {
        this.localStorage = localStorage;
    }
}
