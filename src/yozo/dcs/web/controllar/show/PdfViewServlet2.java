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
        urlPatterns = {"/pdfview2"}
)
public class PdfViewServlet2 extends HttpServlet {
    public PdfViewServlet2() {
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long pos = 0L;
        long end = 0L;
        long size = 0L;
        long overwrite = 0L;
        String relativePath = request.getParameter("filePath");
        relativePath = Config.outputDir + File.separator + relativePath;
        File file = new File(relativePath);
        if (file.exists()) {
            String[] namelist = file.list();
            String[] var18 = namelist;
            int var17 = namelist.length;

            for(int var16 = 0; var16 < var17; ++var16) {
                String filename = var18[var16];
                if (filename.endsWith(".pdf") || filename.endsWith(".ofd")) {
                    File targetfile = new File(relativePath + File.separator + filename);
                    Throwable var20 = null;
                    Object var21 = null;

                    try {
                        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(targetfile));

                        try {
                            BufferedOutputStream os = new BufferedOutputStream(response.getOutputStream());

                            try {
                                long fSize = targetfile.length();
                                byte[] buffer = new byte[4096];
                                response.setBufferSize(40960);
                                response.setHeader("Accept-Ranges", "bytes");
                                response.setHeader("Content-Length", String.valueOf(fSize));
                                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                                response.setContentType("application/octet-stream");
                                if (request.getHeader("Range") != null) {
                                    response.setStatus(206);
                                    String[] num = request.getHeader("Range").replaceAll("bytes=", "").split("-");
                                    pos = (long)Integer.parseInt(num[0]);
                                    if (num[1] != null && !"".equals(num[1])) {
                                        end = (long)Integer.parseInt(num[1]);
                                    } else {
                                        end = fSize;
                                    }

                                    if (end > pos && fSize >= end) {
                                        size = end - pos;
                                        String contentRange = "bytes " + pos + "-" + end + "/" + fSize;
                                        response.setHeader("Content-Range", contentRange);
                                        response.setHeader("Content-Length", String.valueOf(size + 1L));
                                        fis.skip(pos);
                                    }
                                }

                                boolean ok = false;

                                while(true) {
                                    if (ok) {
                                        os.flush();
                                        break;
                                    }

                                    int len = fis.read(buffer, 0, buffer.length);
                                    if (len > -1) {
                                        overwrite += (long)len;
                                        if (size > 0L && overwrite >= size + 1L) {
                                            len = (int)((long)len - (overwrite - size - 1L));
                                            ok = true;
                                        }

                                        os.write(buffer, 0, len);
                                    } else {
                                        ok = true;
                                    }
                                }
                            } finally {
                                if (os != null) {
                                    os.close();
                                }

                            }
                        } catch (Throwable var39) {
                            if (var20 == null) {
                                var20 = var39;
                            } else if (var20 != var39) {
                                var20.addSuppressed(var39);
                            }

                            if (fis != null) {
                                fis.close();
                            }

                            throw var20;
                        }

                        if (fis != null) {
                            fis.close();
                        }
                    } catch (Throwable var40) {
                        if (var20 == null) {
                            var20 = var40;
                        } else if (var20 != var40) {
                            var20.addSuppressed(var40);
                        }

                        try {
                            throw var20;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
