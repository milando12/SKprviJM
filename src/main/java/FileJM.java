public class FileJM {
    String name;
    String path;
    String creationTime;
    String modificationTime;
    Integer size;
    boolean isDirectory;

    public FileJM(String name, String path, String creationTime, String modificationTime, Integer size, boolean isDirectory) {
        this.name = name;
        this.path = path;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
        this.size = size;
        this.isDirectory = isDirectory;
    }

    public FileJM(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    @Override
    public String toString() {
        return "FileJM{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", modificationTime='" + modificationTime + '\'' +
                ", size=" + size +
                ", isDirectory=" + isDirectory +
                '}';
    }
}
