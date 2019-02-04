package randomaccessfile;

public class Index {
    private int key;
    private int byteOffset; //mkano f daata file
    public int setKey(int key){
       return this.key=key;
    }
    public int getKey(){
        return this.key;
    }
    public int setByteOffset(int byteOffset){
       return this.byteOffset=byteOffset;
    }
    public int getByteOffset(){
        return this.byteOffset;
    }
    
    
}
