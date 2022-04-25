import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class Main {


    public static void main(String[] args) {
        File file = new File("r.0.0.mca");

        try(DataInputStream data = uncompress(new FileInputStream(file))) {

            byte[] header = data.readNBytes(8000);

            String b;
            while(true) {
                b = data.readUTF();
                System.out.println(b);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static DataInputStream uncompress(InputStream input) throws IOException {
        try {
            final GZIPInputStream inputGzipStream = new GZIPInputStream(input);
            return new DataInputStream(inputGzipStream);
        } catch (ZipException e) {
            //Data is not compressed
            //input.reset();
            return new DataInputStream(input);
        }
    }
}
