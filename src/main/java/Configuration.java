import java.util.List;
import java.util.Map;

public class Configuration {
    private List<String> extensions;
    private long size;
    private Map<String, Integer> fileLimits;

    public Configuration(){

    }

    public Configuration(List<String> extensions, long size) {
        this.extensions = extensions;
        this.size = size;
    }

    public Configuration(List<String> extensions, long size, Map<String, Integer> fileLimits) {
        this.extensions = extensions;
        this.size = size;
        this.fileLimits = fileLimits;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<String> extensions) {
        this.extensions = extensions;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Map<String, Integer> getFileLimits() {
        return fileLimits;
    }

    public void setFileLimits(Map<String, Integer> fileLimits) {
        this.fileLimits = fileLimits;
    }
}
