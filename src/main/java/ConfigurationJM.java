import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationJM {
    private List<String> extensions;

    private Long maxSize;
    private Long currSize;

    private Map<String, Integer> maxFileLimits;
    private Map<String, Integer> currFileLimits;

    public ConfigurationJM(){
        this.maxFileLimits= new HashMap<>();
        this.currFileLimits= new HashMap<>();
        this.currSize= Long.valueOf(0);
    }

    public ConfigurationJM(List<String> extensions, Long maxSize) {
        this.maxFileLimits= new HashMap<>();
        this.currFileLimits= new HashMap<>();
        this.extensions = extensions;
        this.maxSize = maxSize;
        this.currSize= Long.valueOf(0);

    }

    public ConfigurationJM(List<String> extensions, Long maxSize, Map<String, Integer> maxFileLimits) {
        this.maxFileLimits= new HashMap<>();
        this.currFileLimits= new HashMap<>();
        this.extensions = extensions;
        this.maxSize = maxSize;
        this.currSize= Long.valueOf(0);

    }

    public boolean canAddFile( Long fileSize, String fileExtension, String parentDirectory){
        for (String e:extensions) {
            if (fileExtension.equals(e)) return false;
        }

        if (currSize+fileSize> maxSize) return false;

        if (!maxFileLimits.containsKey(parentDirectory)) return true;
        if (currFileLimits.get(parentDirectory)+1> maxFileLimits.get(parentDirectory)) return false;

        return true;
    }

    public boolean canAddDirectory(String parentDirectory){
        if (!maxFileLimits.containsKey(parentDirectory)) return true;
        else if (currFileLimits.get(parentDirectory)+1> maxFileLimits.get(parentDirectory)) return false;

        return true;
    }

    public void addMaxFileLimit(String path, Integer limit){
        if (maxFileLimits== null) maxFileLimits= new HashMap<>();

        maxFileLimits.put(path,limit);
        currFileLimits.put(path,0);
    }

    public void incCurrFileLimit(String parentPath){
        if (currFileLimits== null) currFileLimits= new HashMap<>();

        if (currFileLimits.containsKey(parentPath))
            currFileLimits.computeIfPresent(parentPath, (k, v) -> v + 1);
        else currFileLimits.put(parentPath, 1);
    }

    public void decCurrFileLimit(String parentPath){
        if (currFileLimits== null) return;

        if (currFileLimits.containsKey(parentPath))
            currFileLimits.computeIfPresent(parentPath, (k, v) -> v - 1);
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<String> extensions) {
        this.extensions = extensions;
    }

    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    public Map<String, Integer> getMaxFileLimits() {
        return maxFileLimits;
    }

    public void setMaxFileLimits(Map<String, Integer> maxFileLimits) {
        this.maxFileLimits = maxFileLimits;
    }

    public Long getCurrSize() {
        return currSize;
    }

    public void setCurrSize(Long currSize) {
        this.currSize = currSize;
    }

    public Map<String, Integer> getCurrFileLimits() {
        return currFileLimits;
    }

    public void setCurrFileLimits(Map<String, Integer> currFileLimits) {
        this.currFileLimits = currFileLimits;
    }
}
