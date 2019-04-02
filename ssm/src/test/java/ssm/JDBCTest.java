package ssm;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class JDBCTest {
	public static void main(String[] args) throws InterruptedException {
        int threadNum =190;
        long tStart = System.currentTimeMillis();
        // 打印开始标记
        System.out.println(Thread.currentThread().getName() + "开始");
        for (int ii = 0; ii < threadNum; ii++) {// 开threadNum个线程
            Thread t = new WorkerThread();
            t.start();
        }
        while (true) {// 等待所有子线程执行完
            if (!WorkerThread.hasThreadRunning()) {
                break;
            }
            Thread.sleep(500);
        }
        // 打印结束标记
        System.out.println(Thread.currentThread().getName() + "结束.");
        long tEnd = System.currentTimeMillis();
        System.out.println("总共用时:" + (tEnd - tStart) + "millions");
    }
 
    public static class WorkerThread extends Thread {
        private static List<Thread> runningThreads = new ArrayList<Thread>();
 
        public WorkerThread() {
        }
 
        @Override
        public void run() {
            regist(this);// 线程开始时注册
            // 打印开始标记
            System.out.println(Thread.currentThread().getName() + "开始...");
            connectToMysql();
            unRegist(this);// 线程结束时取消注册
            // 打印结束标记
            System.out.println(Thread.currentThread().getName() + "结束.");
        }
 
        public void regist(Thread t) {
            synchronized (runningThreads) {
                runningThreads.add(t);
            }
        }
 
        public void unRegist(Thread t) {
            synchronized (runningThreads) {
                runningThreads.remove(t);
            }
        }
 
        public static boolean hasThreadRunning() {
            // 通过判断runningThreads是否为空就能知道是否还有线程未执行完
            return (runningThreads.size() > 0);
        }
         
        private void connectToMysql() {
            // TODO Auto-generated method stub
            // 驱动程序名
            String driver = "com.mysql.jdbc.Driver";
            // URL指向要访问的数据库名jdbc_test
            String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
            // MySQL配置时的用户名
            String user = "qian";
            // Java连接MySQL配置时的密码
            String password = "Qzb112233";
            try {
                // 加载驱动程序
                Class.forName(driver);
                // 连续数据库
                Connection conn = (Connection) DriverManager.getConnection(url,
                        user, password);
                if (!conn.isClosed())
                    System.out.println("Succeeded connecting to the Database!");
                // statement用来执行SQL语句
                Statement statement = (Statement) conn.createStatement();
                // 要执行的SQL语句
                String sql = "select * from adminuser";
                ResultSet rs = statement.executeQuery(sql);
                System.out.println("-----------------");
                System.out.println("执行结果如下所示:");
                System.out.println("-----------------");
                System.out.println(" 用户名" + "\t" + " 密码");
                System.out.println("-----------------");
                String name = null;
                while (rs.next()) {
                    System.out.println(rs.getString("username") + "\t" + name);
                }
                Thread.sleep(2000);
                rs.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                System.out.println("Sorry,can`t find the Driver!");
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
