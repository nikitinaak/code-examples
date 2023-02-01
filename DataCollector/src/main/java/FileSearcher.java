import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {
    List<File> fileList = new ArrayList<>();

    public void searchFiles(File file) throws NullPointerException {
        if (file.isDirectory()) {
            File[] folders = file.listFiles();
            for (File filesInFolder : folders) {
                searchFiles(filesInFolder);
            }
        } else {
            fileList.add(file);
        }
    }

    public List<File> getFileList() {
        return fileList;
    }
}