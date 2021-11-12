// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TempShowService.java

package yozo.dcs.web.service.show;

import yozo.dcs.web.cons.DefaultResult;
import yozo.dcs.web.cons.IResult;
import yozo.dcs.web.vo.BranchFolder;
import yozo.dcs.web.vo.LeafFile;

import java.io.File;

public class TempShowService
{

    public TempShowService()
    {
    }

    public static IResult getZipFileJson(String path)
    {
        if(path == null || "".equals(path)) {
            return DefaultResult.failResult("\u64CD\u4F5C\u5931\u8D25");
        }
        File file = new File(path);
        if(file.exists() && file.isDirectory())
        {
            File subfile[] = file.listFiles();
            BranchFolder folder = null;
            File afile[];
            int k = (afile = subfile).length;
            for(int i = 0; i < k; i++)
            {
                File file2 = afile[i];
                if(file2.isDirectory())
                {
                    folder = new BranchFolder(file2.getName(), file2.getName(), "");
                    getList(folder, file2);
                }
            }

            if(folder == null) {
                return DefaultResult.failResult("\u7A7A\u6587\u4EF6\u5939");
            }
            k = (afile = subfile).length;
            for(int j = 0; j < k; j++)
            {
                File file2 = afile[j];
                if(!file2.isDirectory())
                {
                    String fileName = file2.getName();
                    if(fileName.toLowerCase().matches(".*(zip|rar|7z|gz)")) {
                        folder.setPosition(file2.getName());
                    }
                }
            }

            return DefaultResult.successResult(folder);
        } else
        {
            return DefaultResult.failResult("\u64CD\u4F5C\u5931\u8D25");
        }
    }

    private static void getList(BranchFolder folder, File f)
    {
        if(f != null && folder != null && f.isDirectory())
        {
            File fileArray[] = f.listFiles();
            if(fileArray != null)
            {
                for(int i = 0; i < fileArray.length; i++)
                    if(fileArray[i].isDirectory())
                    {
                        BranchFolder branchFolder = new BranchFolder(fileArray[i].getName(), (new StringBuilder(String.valueOf(folder.getPosition()))).append(File.separator).append(fileArray[i].getName()).toString(), "");
                        folder.addChildren(branchFolder);
                        getList(branchFolder, fileArray[i]);
                    } else
                    {
                        LeafFile lf = new LeafFile(fileArray[i].getName(), folder.getPosition(), (new StringBuilder(String.valueOf(Math.ceil((double)fileArray[i].length() / 1000D)))).append("kb").toString());
                        folder.addChildren(lf);
                    }

            }
        }
    }

    public static void main(String args[])
    {
        File file = new File("E:\\dcs\\sampledoc");
        File files[] = file.listFiles();
        System.out.println(files.length);
    }
}
