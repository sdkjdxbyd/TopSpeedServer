package com.byd.james.topspeedserver.app;


import android.app.Activity;
import android.app.Application;


import java.util.HashSet;
import java.util.Set;
/******************************************
 * 类名称：App
 * 类描述：
 ******************************************/
public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //蒲公英crash上报
//        PgyCrashManager.register(this);
        //初始化内存泄漏检测
//        LeakCanary.install(this);
        //初始化过度绘制检测
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //初始化realm
        initRealm();
    }

    public void registerActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        //将创建的activity加入set集合
        allActivities.add(act);
    }

    public void unregisterActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        //退出app时调用的方法，使用加锁机制，退出时排队，确保线程安全
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null && !act.isFinishing())
                        act.finish();
                }
            }
        }
        //杀死进程强制退出
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    //初始化数据库操作类
    private void initRealm() {
       /*  realmConfiguration = new RealmConfiguration.Builder(this)
                .name(RealmHelper.DB_NAME)
                .schemaVersion(1)
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);*/
    }

}