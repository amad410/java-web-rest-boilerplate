package Handlers;

import java.io.File;

public class FileHandler {
    File file;

    public File GetFile(String filePath){
        file = new File(System.getProperty("user.dir") + filePath);
        return file;
    }
}
