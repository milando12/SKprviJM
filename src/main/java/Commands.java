public enum Commands {
    LIST,
    START,  //pokretanje Storage-a
    TAKE,
    STARTLIM,
    MKDIR,  //pravi novi dikretorijum u trenutnom direktorijumu
    MKDIRS, //pravi vise novih direktorijuma
    MKDIRLIM,
    DELDIR,  //brisemo fajl iz trenutnog direktorijuma
    RENAME,
    MOV,
    MOVIN,
    MOVOUT,
    SUBINFO, //subdirectoriesInfo
    SUBINFOAL, //getFilesFromAllSubdirectories
    DIRFILES, //getAllFilesFromDirectory
    FILEXT, //getFilesByExtension
    FILSUB, //getFilesBySubstring
    FILCON, //containsFiles
    FILLOC, //locateFile
    FILPER, //getFilesByPeriod
    FILSORT //sortDirectory
}
