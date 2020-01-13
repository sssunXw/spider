package util;

import entity.Constants;
import entity.TableDetail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * <pre>
 * 功能说明:
 * </pre>
 *
 * @author sxw
 * @date 2019/6/7
 */

public class FileUtil {

    public static void writeTableToFile(String path, TableDetail tableDetail) throws IOException {

        File file = new File(Constants.FILE_DIR + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(Constants.FILE_DIR + path + tableDetail.getTransferName() + ".txt");

        // if file doesnt exists, then create it
        if (!file2.exists()) {
            file2.createNewFile();
        }

        FileWriter fw = new FileWriter(file2.getAbsoluteFile());
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            tableDetail.getFields().forEach(e -> {
                try {
                    bw.write(e + ";");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            bw.newLine();
            for (List<String> datum : tableDetail.getData()) {
                for (String s : datum) {
                    bw.write(s + ";");
                }
                bw.newLine();
            }
        }

        System.out.println("Done");

    }

}
