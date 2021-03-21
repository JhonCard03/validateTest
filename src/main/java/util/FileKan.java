package util;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.*;
import java.util.Arrays;

public class FileKan {

    public static String  lastFileModi() {
        String sdir = System.getProperty("user.home")+"\\Downloads";
        File dir = new File(sdir);
        if (dir.isDirectory())
        {
            File[] dirFiles = dir.listFiles((FileFilter) FileFilterUtils.fileFileFilter());
            if (dirFiles != null && dirFiles.length > 0)
            { Arrays.sort(dirFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                //System.out.println(dirFiles[0]);
                String lastFile = String.valueOf(dirFiles[0]);
                return lastFile;

            }
        }
        return null;
    }


    public  String readFile(String url) throws FileNotFoundException, IOException {


        String data ="";
        try{

            FileReader f = new FileReader(url);
            BufferedReader b = new BufferedReader(f);
            String cadena = "";
            String temp = "";

            while ((cadena = b.readLine()) != null) {
                temp = temp + cadena;
               // System.out.println(temp);
            }
            System.out.println(temp);
            data = temp;
            b.close();

        }

        catch(Exception e){

        }
        return data;
    }
}
