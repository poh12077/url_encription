//
//package com.solbox.delivery.secure;
//
//public class Main {
//	public static void main(String[] args) {
//		try {
//			int[] timeout = new int[10];
//			for (int i=0;i<10;i++) {
//				timeout[i]=(i+1)*1000;
//			}
//
//			Test test = new Test(timeout);
//			Thread[] threadArray= new Thread[10];
//		    for(int i=0;i<10;i++) {
//		    	threadArray[i] = new Thread(test);
//		    	threadArray[i].setName(Integer.toString(i));
//		    }
//
//		    for(int i=0;i<10;i++) {
//		    	threadArray[i].start();
//		    }
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//class Test implements Runnable {
//	int[] timeout;
//	public Test(int[] timeout) {
//		this.timeout = timeout;
//	}
//
//	public void run() {
//		long current_time=System.currentTimeMillis();
//
//		try {
//
//			Thread thread = Thread.currentThread();
//			System.out.println(thread.getName() + " thread start.");
//			String url = "/a/b/c/d/file_name.mp4";
//			String key = "test_encrypt_test";
//			long timeout=1000;
//			int skipDepth =1;
//			boolean isFileNameExcepted = true;
//
//			for (int i=0;i<10;i++) {
//				if(thread.getName().equals(Integer.toString(i))) {
//					timeout=this.timeout[i];
//				}
//			}
//
//			Encryption encryption = new Encryption(url,key,timeout);
//			encryption.enablePreview(10,5);
//			encryption.setClaim("allow_cc", "KR");
//			String result = encryption.urlEncoder(skipDepth, isFileNameExcepted);
//
////			  Date now = new Date();
//			System.out.println(thread.getName()+" "+timeout+" "+result);
//			 System.out.println(thread.getName() + " thread end.");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			long end_time=System.currentTimeMillis();
//			System.out.println( "------------  " + (end_time-current_time));
//		}
//	}
//}
//
//
///*
//package com.solbox.delivery.secure;
//
//public class Main {
//	public static void main(String[] args) {
//		try {
//			String url = "/a/b/c/d/file_name.mp4";
//			String key = "test_encrypt_test";
//			int timeout=10000;
//			Encryption encryption = new Encryption(url,key,timeout);
//
//			Test test = new Test(encryption);
//			Thread[] threadArray= new Thread[10];
//		    for(int i=0;i<10;i++) {
//		    	threadArray[i] = new Thread(test);
//		    }
//		    for(int i=0;i<10;i++) {
//		    	threadArray[i].start();
//		    }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//class Test implements Runnable {
//	Encryption encryption;
//	public Test( Encryption encryption) {
//		this.encryption=encryption;
//	}
//
//	public void run() {
//		try {
//			Thread thread = Thread.currentThread();
//			System.out.println(thread.getName() + " thread start.");
//			int skipDepth = 1;
//			boolean isFileNameExcepted = false;
//
//			encryption.enablePreview(10,5);
//			encryption.setClaim("allow_cc", "KR");
//			String result = encryption.urlEncoder(skipDepth, isFileNameExcepted);
//			System.out.println(thread.getName()+" "+result);
//			System.out.println(thread.getName() + " thread end.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//*/