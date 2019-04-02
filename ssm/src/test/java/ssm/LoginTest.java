package ssm;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class LoginTest {
public static void main(String[] args) throws MalformedURLException {
	final URL url=new URL("http://localhost:8080/ssm/login.jsp");
	final int concurrentNum=100;
	ExecutorService pool=Executors.newCachedThreadPool();
	for(int i=0;i<concurrentNum;i++) {
		pool.execute(new Runnable() {
			
			@Override
			public void run() {
		try {
			while(true) {
				URLConnection connection=url.openConnection();
				InputStream inputStream=connection.getInputStream();
				byte[]buff=new byte[1024];
				int len=-1;
				while((len=inputStream.read(buff))!=-1) {
					try {
						Thread.sleep(10);
						System.out.println(new String(buff));
					} catch (InterruptedException e) {
					e.printStackTrace();
					}
				}
			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
			}
		});
	}
}
}
