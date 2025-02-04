package web.CayvaCaCanh.Controller.Method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class method {

    // LƯU PRODUCT
    public String saveProductRecord(String productId) {
        String s = null;
        String str = null;
        try {
            // Chạy lệnh Python với mã sản phẩm
            String cmd = "python E:\\CayvaCaCanh\\src\\main\\resources\\python\\add-product-to-csv.py "  + productId;
            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // Đọc kết quả đầu ra của lệnh
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                str = s;
            }

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }
        return str;
    }

    // LƯU ĐÁNH GIÁ
    public String saveRatingRecord(String rate) {
        String s = null;
        String str = null;
        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            String cmd = "python E:\\CayvaCaCanh\\src\\main\\resources\\python\\add-rating-to-csv.py "  + rate;
            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);

                str = s;
            }

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            // System.exit(-1);
        }
        return str;

    }

    // ĐỀ XUẤTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    public String getRecommendation(String idkH) {
        String s = null;
        String str = null;
        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            String cmd = "python E:\\CayvaCaCanh\\src\\main\\resources\\python\\CollaborativeFilteringUser.py " + idkH;
            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);

                str = s;
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s +"\n");
            }

            // System.exit(0);
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            // System.exit(-1);
        }
        return str;

    }

    // ĐỀ XUẤTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
}
