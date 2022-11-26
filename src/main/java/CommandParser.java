import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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


        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.STARTLIM))){
            localStorage = new LocalStorage();
            if (linija.length >= 5){
                List <String> ekstenzije = new ArrayList<>();
                for (int i = 4; i < linija.length;i++)
                    ekstenzije.add(linija[i]);
                localStorage.startStorage(linija[1],linija[2], Long.parseLong(linija[3]), String.valueOf(ekstenzije));
            }else
                localStorage.startStorage(linija[1],linija[2], Long.parseLong(linija[3]),linija[4]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MKDIR))){
            localStorage.createDir(linija[1],linija[2]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MKDIRS))){
            localStorage.createDir(linija[1],linija[2],Integer.parseInt(linija[3]));
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.MKDIRLIM))){
            localStorage.createDirLim(linija[1],linija[2],Integer.parseInt(linija[3]));
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

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.FILCON))){
            localStorage.containsFiles(linija[1],linija[2]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.FILLOC))){
            localStorage.locateFile(linija[1]);
        }

        if(linija[0].equalsIgnoreCase(String.valueOf(Commands.FILPER))){
            localStorage.getFilesByPeriod(linija[1],linija[2],linija[3]);
        }

        if (linija[0].equalsIgnoreCase(String.valueOf(Commands.FILSORT))){
            if (linija[1].equalsIgnoreCase(String.valueOf(Commands.FILPER))){
                localStorage.sortDirectory(localStorage.getFilesByPeriod(linija[2],linija[3],linija[4]),Sort.valueOf(linija[5]),Sort.valueOf(linija[6]));
            }

            if (linija[1].equalsIgnoreCase(String.valueOf(Commands.FILSUB))){
                localStorage.sortDirectory(localStorage.getFilesBySubstring(linija[2]),Sort.valueOf(linija[3]),Sort.valueOf(linija[4]));
            }

            if (linija[1].equalsIgnoreCase(String.valueOf(Commands.FILEXT))){
                localStorage.sortDirectory(localStorage.getFilesByExtension(linija[2]),Sort.valueOf(linija[3]),Sort.valueOf(linija[4]));
            }

            if (linija[1].equalsIgnoreCase(String.valueOf(Commands.DIRFILES))){
                localStorage.sortDirectory(localStorage.getAllFilesFromDirectory(linija[2]),Sort.valueOf(linija[3]),Sort.valueOf(linija[4]));
            }

            if (linija[1].equalsIgnoreCase(String.valueOf(Commands.DIRFILES))){
                localStorage.sortDirectory(localStorage.getAllFilesFromDirectory(linija[2]),Sort.valueOf(linija[3]),Sort.valueOf(linija[4]));
            }

            if (linija[1].equalsIgnoreCase(String.valueOf(Commands.SUBINFOAL))){
                localStorage.sortDirectory(localStorage.getFilesFromAllSubdirectories(linija[2]),Sort.valueOf(linija[3]),Sort.valueOf(linija[4]));
            }
        }
    }


}
