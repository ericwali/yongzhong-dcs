//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.show;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import yozo.dcs.web.service.zxing.MatrixToImageWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Hashtable;

@WebServlet(
        urlPatterns = {"/code"}
)
public class ZxcodeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ZxcodeServlet() {
    }
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String text = this.encodeUrl(request.getParameter("codeUrl"));
        int width = 300;
        int height = 300;
        String format = "JPEG";
        Hashtable<EncodeHintType, String> hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        try {
            BitMatrix bitMatrix = (new MultiFormatWriter()).encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), format, response.getOutputStream());
        } catch (WriterException var10) {
            var10.printStackTrace();
        }

    }

    private String encodeUrl(String url) throws UnsupportedEncodingException {
        if (url == null) {
            return "";
        } else {
            int encodeIndex = url.indexOf("=");
            if (encodeIndex > 0) {
                String path1 = url.substring(0, encodeIndex + 1);
                String path2 = url.substring(encodeIndex + 1);
                return path1 + URLEncoder.encode(path2, "UTF-8");
            } else {
                return url;
            }
        }
    }
}
