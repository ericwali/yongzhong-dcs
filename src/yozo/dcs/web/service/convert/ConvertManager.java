//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package yozo.dcs.web.service.convert;

import yozo.dcs.web.listener.Config;

import java.util.concurrent.ArrayBlockingQueue;

public class ConvertManager {
    public static final ConvertManager instance = new ConvertManager();
    private ArrayBlockingQueue<ConvertInstance> pool;

    public ConvertManager() {
    }

    public void init() {
        this.pool = new ArrayBlockingQueue(Config.convertPoolSize);

        for(int i = 1; i <= Config.convertPoolSize; ++i) {
            this.pool.add(new ConvertInstance(i));
            System.out.println("queue num:" + i);
        }

    }

    public int Size() {
        return this.pool.size();
    }

    public ConvertInstance get() {
        try {
            return (ConvertInstance)this.pool.take();
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public void free(ConvertInstance instance) {
        try {
            this.pool.put(instance);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

    }
}
