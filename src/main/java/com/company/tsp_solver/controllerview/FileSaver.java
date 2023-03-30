package com.company.tsp_solver.controllerview;

import com.company.tsp_solver.Model;
import javafx.stage.FileChooser;

import java.io.*;

public class FileSaver {
    protected void saveFile() {
        if(Model.instance.getLoadedFile() == null) {
            saveFileAs();
        } else {
            OutputStream out;
            ObjectOutputStream objectOut = null;
            try {
                out = new FileOutputStream(Model.instance.getLoadedFile());
                objectOut = new ObjectOutputStream(out);
            } catch (IOException e) {
                System.out.println("Exception occured during saving of file");
                return;
            }
            try {
                objectOut.writeObject(Model.instance.points);
            } catch (IOException e) {
                System.out.println("Exception occured during writing objects into file");
            }
            try {
                out.close();
                objectOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void saveFileAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("graph.txt");
        File fileToSave = fileChooser.showSaveDialog(Model.instance.getStage());
        OutputStream out;
        ObjectOutputStream objectOut = null;
        try {
            out = new FileOutputStream(fileToSave);
            objectOut = new ObjectOutputStream(out);
        } catch (IOException e) {
            System.out.println("Exception occured during saving of file");
            return;
        }
        try {
            objectOut.writeObject(Model.instance.points);
        } catch (IOException e) {
            System.out.println("Exception occured during writing objects into file");
            e.printStackTrace();
            return;
        }
        try {
            out.close();
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model.instance.setLoadedFile(fileToSave);
    }
}
