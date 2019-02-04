package randomaccessfile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Sayed abdul-Hakiem 20150130 && mohamed mselhy 20150226
 */
public class RandomAccessFile {

    public static void main(String[] args) throws IOException, FileNotFoundException {

        Controller controller = new Controller();
        controller.readKeysFromDataFile();
        while (true) {

            System.out.println("Press 1 to insert a record : ");
            System.out.println("Press 2 to delete a record :");
            System.out.println("Press 3 to update a new record : ");
            System.out.println("Press 4 to search for a record : ");

            Scanner in = new Scanner(System.in);
            int choice = in.nextInt();
            if (choice == 1) {
                controller.insertNewRecord();
            } else if (choice == 2) {
                controller.deleteRecord();
            } else if (choice == 3) {
                controller.UpdateRecord();
            } else if(choice==4){
                
                controller.searchForRecord();
            }
            
            System.out.println();
            System.out.println();
            System.out.println("press 5 if you want to continue : ");
            System.out.println("press 6 if you want to exit : ");
             Scanner input = new Scanner(System.in);
            int yOrN = input.nextInt();
            if(yOrN==6){
                System.out.println("BYE BYE !");
                controller.datafile.close();
                controller.indexfile.close();
                break;
                
            }
            /*System.out.println();
        System.out.println("Press A if you want to enter new choice :");
        Scanner inAgain=new Scanner(System.in);
        String Again=inAgain.next();*/
            //  readKeysFromDataFile();
            //  sortKeys();
            //  createIndexFile();
            //  insertNewRecord();   
            //  deleteRecord();
            //  updateRecord();
            //  searchForRecord();
        }
    }

}
