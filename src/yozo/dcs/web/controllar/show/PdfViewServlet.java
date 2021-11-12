//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.controllar.show;

import yozo.dcs.web.listener.Config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(
        urlPatterns = {"/pdfview"}
)
public class PdfViewServlet extends HttpServlet {
    public PdfViewServlet() {
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String relativePath = request.getParameter("filePath");
        relativePath = Config.outputDir + File.separator + relativePath;
        File file = new File(relativePath);
        if (file.exists()) {
            String[] namelist = file.list();
            String[] var9 = namelist;
            int var8 = namelist.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                String filename = var9[var7];
                if (filename.toLowerCase().endsWith(".pdf") || filename.toLowerCase().endsWith(".ofd")) {
                    File targetfile = new File(relativePath + File.separator + filename);
                    InputStream fis = new BufferedInputStream(new FileInputStream(relativePath + File.separator + filename));
                    response.reset();
                    response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
                    response.addHeader("Content-Length", "" + targetfile.length());
                    response.setContentType("application/octet-stream");
                    response.addHeader("Accept-Ranges", "bytes");
                    OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                    byte[] buffer = new byte[1024];
                    boolean var14 = false;

                    while(fis.read(buffer) > 0) {
                        toClient.write(buffer);
                    }

                    fis.close();
                    toClient.flush();
                    toClient.close();
                    return;
                }
            }
        }

    }
}
