import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;


public class Main {
    public static void main(String[] args) throws IOException,Exception {
       
        User user = null;
        FileInputStream filein = new FileInputStream("C:\\Users\\samei\\AP_LAB\\ApLab-By-Saba-Seyed-tabaei\\Sudoku\\New folder\\Youtube\\Serilization\\src\\UserInfo.ser");
        ObjectInputStream in = new ObjectInputStream(filein);
        user = (User) in.readObject();
        filein.close();
        System.out.println(user.name + " " + user.password);
    }   
}
