package org.deta.boot.vpc.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
//import java.net.ServerSocket;
import java.util.Properties;
import org.deta.boot.vpc.process.TimeProcess;
import org.deta.boot.vpc.sleeper.Sleeper;
import org.deta.boot.vpc.sleeper.SleeperHall;
import org.deta.vpcs.hall.DatabaseLogHall;
import org.lyg.cache.DetaDBBufferCacheManager;
import org.lyg.common.utils.DetaUtil;
import org.lyg.stable.StableData;
public class ServerInitController {
	private static ServerSocket server;
	private static Properties properties;
	private static int port;
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream
					(new File("src/main/resources/property.proterties")));
			System.out.println("----����VPCS���ݿ��������Դ����:�ɹ���");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initService() throws IOException {
		try {
			port = Integer.parseInt(properties.getProperty(StableData.TCP_PORT));
			server = new ServerSocket(port);
			System.out.println("----����VPCS���ݿ�������˿�����:" + port);
			DetaUtil.initDB();
			System.out.println("----����VPCS���ݿ������DMAȷ��:�ɹ���");
			RequestFilterController.initBlockList();
			System.out.println("----����VPCS���ݿ������IP���˷�������:�ɹ���");
			DetaDBBufferCacheManager.reflection();
			System.out.println("----����VPCS���ݿ�����������������ӳ�����:�ɹ���");
			DatabaseLogHall.createBinLogHall();
			System.out.println("----����VPCS���ݿ�����������������ӳ�����:�ɹ���");
			//			BootBackup.bootBackupByUsingGzip(CacheManager
			//.getCacheInfo("LogPath").getValue().toString()+"/zipCover");
			//			UnZip.unZipWithPath("C:/DetaLog/zipCover/zip_1549583065203.zip"
			//, "C:/DetaLog/zipCover/cover");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void haoHiYooFaker(SleeperHall sleeperHall) {
		sleeperHall.callSkivvy(); 
	}

	public static void initServer() throws IOException {
		System.out.println("----DETA VPCS--1.8");
		System.out.println("----Author: ������");
		System.out.println("----�����������������޹�˾��Դ��Ŀ");
		TimeProcess timeProcess=new TimeProcess();
		timeProcess.begin();
		SleeperHall sleeperHall = new SleeperHall();
		initService();
		timeProcess.end();
		System.out.println("----����VPCS���ݿ����������һ������-�ܺ�ʱ:" 
		+ timeProcess.duration()+ "����");
		while(true){
			if(sleeperHall.getThreadsCount() < StableData.SLEEPERS_RANGE){
				Sleeper sleeper = new Sleeper();
				try {
					sleeper.hugPillow(sleeperHall, server.accept()
							, sleeper.hashCode());
					sleeper.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				haoHiYooFaker(sleeperHall);
			}
		}
	}
}