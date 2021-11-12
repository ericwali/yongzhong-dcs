//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.listener;

import org.apache.log4j.PropertyConfigurator;
import yozo.dcs.web.log.LogUtil;
import yozo.dcs.web.service.convert.ConvertManager;
import yozo.dcs.web.service.httpclient.HttpClientService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

@WebListener
public class Init implements ServletContextListener {
    private static final int PERIOD_DAY = 86400000;

    public Init() {
    }
    @Override
    public void contextDestroyed(ServletContextEvent e) {
    }
    @Override
    public void contextInitialized(ServletContextEvent e) {
        ServletContext context = e.getServletContext();
        PropertyConfigurator.configure(context.getRealPath("/") + "/WEB-INF/log4j.properties");
        try {
            Config.init(context.getRealPath("/"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        ConvertManager.instance.init();

        try {
            HttpClientService.init();
        } catch (Exception var6) {
            LogUtil.error(var6);
        }

        String log = "dcs server started:::" + new Date() + LogUtil.lineSep + "inputDir:::" + Config.inputDir + LogUtil.lineSep + "outputDir:::" + Config.outputDir + LogUtil.lineSep + "convertPoolSize:::" + Config.convertPoolSize + LogUtil.lineSep + "convertTimeout:::" + Config.convertTimeout + LogUtil.lineSep + "uploadSize:::" + Config.uploadSize + LogUtil.lineSep + "staticPath:::" + Config.staticPath + LogUtil.lineSep + "clearDay:::" + Config.clearDay + LogUtil.lineSep + "htmlTitle:::" + Config.htmlTitle + LogUtil.lineSep + "tempDir:::" + Config.tempDir + LogUtil.lineSep + "wmImagePath:::" + Config.wmImagePath + LogUtil.lineSep;
        if (Config.clearDay > 0L) {
            System.out.println("清理周期:" + Config.clearDay);
            Long clearTime = Config.clearDay * 86400000L;
            Calendar cal = Calendar.getInstance();
            cal.set(5, cal.get(5) + 1);
            cal.set(11, 2);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
            (new Timer(true)).schedule(new ClearFileTask(Config.outputDir.getAbsolutePath(), clearTime), new Date(cal.getTimeInMillis()), 86400000L);
            log = log + new Date(cal.getTimeInMillis()) + " 开始执行清理任务，清理" + clearTime / 1000L / 3600L / 24L + "天前的文件" + LogUtil.lineSep;
        }

        LogUtil.info(log);
    }
}
