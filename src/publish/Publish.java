package publish;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;

public class Publish {
    public String createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileName;
    }

    public void publish(String fileName) throws IOException {
        try {

            FileInputStream inputStream = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(inputStream);
//                    ArrayList<Data> arr = (ArrayList<Data>) ois.readObject();
            Object obj = ois.readObject();
            ArrayList<Data> arr = (ArrayList<Data>) obj;
            ois.close();
            Data data = new Data(getTime());
            arr.add(data);
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(arr);
            oos.close();
        } catch (EOFException e) {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            ArrayList<Data> arr = new ArrayList<>();
            Data data = new Data(getTime());
            arr.add(data);
            oos.writeObject(arr);
//                oos.flush();
            oos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}