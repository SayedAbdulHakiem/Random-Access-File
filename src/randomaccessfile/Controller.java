package randomaccessfile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Controller {

    RandomAccessFile datafile;
    RandomAccessFile indexfile;
    long numberOfRecords;

    Controller() throws FileNotFoundException, IOException {

        this.datafile = new RandomAccessFile("datafile.bin", "rw");
        this.indexfile = new RandomAccessFile("indexFile.bin", "rw");
        numberOfRecords = (datafile.length() + 1) / 12;
    }
    Index[] indexes;
    Product[] products;

    public void readKeysFromDataFile() throws IOException {
        datafile.seek(0);
        this.indexes = new Index[(int) numberOfRecords];
        this.products = new Product[(int) numberOfRecords];

        for (int i = 0; i < numberOfRecords; i++) {
            datafile.seek(i * 12);

            products[i] = new Product();
            indexes[i] = new Index();
            
            products[i].setId(datafile.readInt());
            indexes[i].setKey(products[i].getId());
            indexes[i].setByteOffset(i * 12);
            products[i].setPrice(datafile.readInt());
            products[i].setQuantity(datafile.readInt());
            System.out.println(products[i].getId());
            System.out.println(products[i].getPrice());
            System.out.println(products[i].getQuantity());
        }
        sortKeys(this.indexes);
        CreateIndexFile(this.indexes);
    }

    public void sortKeys(Index[] arr) {
        int tempKey, tempByteOfset;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i].getKey() < arr[j].getKey()) {
                    tempKey = arr[i].getKey();
                    arr[i].setKey(arr[j].getKey());
                    arr[j].setKey(tempKey);
                                       
                    tempByteOfset = arr[i].getByteOffset();
                    arr[i].setByteOffset(arr[j].getByteOffset());
                    arr[j].setByteOffset(tempByteOfset);
                }
            }
        }
        for (int x = 0; x < arr.length; x++) {

            System.out.println("Key        :" + arr[x].getKey());
            System.out.println("ByteOffset :" + arr[x].getByteOffset());
        }
    }

    public void CreateIndexFile(Index[] arrOfIndex) throws IOException {

        for (int s = 0; s < arrOfIndex.length; s++) {
            indexfile.writeInt(arrOfIndex[s].getKey());
            indexfile.writeInt(arrOfIndex[s].getByteOffset());
        }
    }

    public void insertNewRecord() throws IOException {
        datafile.seek(datafile.length());
        System.out.print("Enter the product's Id : ");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        datafile.writeInt(id);

        System.out.print("Enter the product's Price : ");
        int price = in.nextInt();
        datafile.writeInt(price);

        System.out.print("Enter the product's Quantity : ");
        int quantity = in.nextInt();
        datafile.writeInt(quantity);
        readKeysFromDataFile();
    }

    public void deleteRecord() throws FileNotFoundException, IOException {
        System.out.print("Enter a key of the record you want to delete ! : ");
        Scanner in = new Scanner(System.in);
        int key = in.nextInt();
        int position = binarySearch(indexes, 0, (int) numberOfRecords, key);
        indexfile.seek((position * 8) + 4);
        int byteOfset = indexfile.readInt();
        datafile.seek(byteOfset);
        datafile.writeChars("#");
    }

    public void UpdateRecord() throws IOException {
        System.out.print("Enter the product's Id that you want to update : ");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        System.out.print("Enter the product's new Price : ");
        int newPrice = in.nextInt();
        System.out.print("Enter the product's new Quantity : ");
        int newQuantity = in.nextInt();
        
        int position = binarySearch(indexes, 0, (int) numberOfRecords, id);
        indexfile.seek((position * 8) + 4);
        int byteOfset = indexfile.readInt();
        datafile.seek(byteOfset + 4);
        datafile.writeInt(newPrice);
        datafile.writeInt(newQuantity);
    }
    
    public void searchForRecord( ) throws IOException{
        System.out.print("Enter the product's Id that you want to get its details : ");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        int position = binarySearch(indexes, 0, (int) numberOfRecords, id);
         if (position== -1) {
                    System.out.println("NOT FOUND!");
                    return;
        }
         
        indexfile.seek(position * 8);        
        int byteOfset = indexfile.readInt();
        datafile.seek(byteOfset);
        System.out.println("Product_ID       :"+datafile.readInt());
        System.out.println("Product_Price    :"+datafile.readInt());
        System.out.println("Product_Quantity :"+datafile.readInt());
        
       
        
    }

    int binarySearch(Index arr[], int left, int right, int wantedValue) {
        if (right >= left) {
            int mid = left + (right - left) / 2;

            if (arr[mid].getKey() == wantedValue) {
                return mid;
            }
            if (arr[mid].getKey() > wantedValue) {
                return binarySearch(arr, left, mid - 1, wantedValue);
            }   
            return binarySearch(arr, mid + 1, right, wantedValue);
        }   
        return -1;
    }
}
