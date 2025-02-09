package com.amazonaws.dynamo.CrudOperations;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
public class WriteFile {
    private String path;
    private boolean append_to_file;
    public WriteFile(String path,boolean append_to_file){
        this.path=path;
        this.append_to_file=append_to_file;
    }

    public void writeToFile(String textLine) throws IOException {
        FileWriter write = new FileWriter(path,append_to_file);
        PrintWriter print_line = new PrintWriter(write);
        print_line.printf("%s "+"%n",textLine);
        print_line.close();

    }
}
