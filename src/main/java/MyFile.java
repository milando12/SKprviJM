import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyFile {

    private String name;
    private MyFile parent;
    private File file;
    private boolean isDirectory;
    private List<MyFile> childrenList;
    private boolean hasLimits = false;
    private int limit;

    public MyFile(String name, MyFile parent, File file, boolean isDirectory) {
        this.name = name;
        this.parent = parent;
        this.file = file;
        this.isDirectory = isDirectory;

        if(isDirectory){
            this.childrenList = new ArrayList<>();
        }

        if (parent != null){
            parent.getChildrenList().add(this);
        }
    }

    public MyFile findChildByName(String childName){
        if (isDirectory){
            for(MyFile child : childrenList){
                if(child.getName().equals(childName)){
                    return child;
                }
            }
            return null;
        }
        return null;
    }

    public boolean childExists(String childName){
        if (isDirectory){
            for (MyFile child : childrenList){
                if(child.getName().equals(childName)){
                    return true;
                }
            }
        }
        return false;
    }

    public void addChild(MyFile child){
        if (isDirectory){
            childrenList.add(child);
        }
    }

    public void removeChild(MyFile child){
        if (isDirectory){
            childrenList.remove(child);
        }
    }

    public boolean checkLimits(){
        if (this.getChildrenList().size()+1 <= limit){
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyFile getParent() {
        return parent;
    }

    public void setParent(MyFile parent) {
        this.parent = parent;
        parent.addChild(this);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public List<MyFile> getChildrenList() {
        return childrenList;
    }

    public boolean isHasLimits() {
        return hasLimits;
    }

    public void setHasLimits(boolean hasLimits) {
        this.hasLimits = hasLimits;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
