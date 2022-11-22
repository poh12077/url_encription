package com.solbox.delivery.secure;

import org.json.JSONObject;
import java.util.Random;

public class Encryption {

	private String key = null;
	private long timeout;
	private String url = null;
	private String[] urlArray = null;
	private JSONObject jsonObject = new JSONObject();
	private String skippedPath= "/";;
	private String fileName="";
	
	public String getKey() {
		return key;
	}
	
	private void setKey(String key) throws NullPointerException {
		if(key==null) {
			throw new NullPointerException();
		}
		this.key=key;
	}
	
	public long getTimeout() {
		return timeout;
	}
	
	private void setTimeout(long timeout) throws Exception {
		if (timeout <= 0 || 9223372036854775807L < timeout ) {
			throw new Exception("timeout error");
		}
		this.timeout=timeout;
	}
	
	public String getUrl() {
		return url;
	}
	
	private void setUrl(String url) throws Exception {
		if(url==null) {
			throw new NullPointerException();
		}else if(url=="" || url =="/" ) {
			throw new Exception("url error");
		}
		if (url.charAt(0) != '/') {
			url = "/" + url;
		}
		this.url=url;
		this.urlArray = url.split("/");
	}
	
	public JSONObject getJsonObject() {
		return this.jsonObject;
	}
	
	private void setJsonObject(JSONObject jsonObject) {
		this.jsonObject=jsonObject;
	}
	
	public String getSkippedPath() {
		return skippedPath;
	}
	
	private void setSkippedPath(String skippedPath) {
		this.skippedPath=skippedPath;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	private void setFileName(String fileName) {
		this.fileName=fileName;
	}
	
	public Encryption(String url, String key, long timeout) throws Exception {
		setKey(key);
		setUrl(url);
		setTimeout(timeout);
	}

	public String urlEncoder(int skipDepth, boolean isFileNameExcepted) {
		try {
			processInput(skipDepth,isFileNameExcepted);

			SHA256 sha256 = new SHA256();
			byte[] cipherKey = sha256.encrypt(this.key);

			AES256 aes256 = new AES256();
			String cipherText = aes256.encrypt(this.jsonObject.toString(), cipherKey);

			String encodedUrl = this.skippedPath + cipherText + this.fileName;
			return makeSuccessResult(encodedUrl);

		} catch (Exception e) {
			e.printStackTrace();
			return makeFailResult();
		}
	}

	String makeSuccessResult( String encodedUrl) throws Exception  {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", "success");
		jsonResult.put("url", encodedUrl);
		return jsonResult.toString();
	}
	
	String makeFailResult() {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("result", "fail");
		return jsonResult.toString();
	}
	
	private void processInput(int skipDepth, boolean isFileNameExcepted) throws Exception {
		makeSkippedPath(skipDepth);
		makeFileName(isFileNameExcepted);
		setDefaultClaim(skipDepth, isFileNameExcepted);
	}
	
	private void makeSkippedPath(int skipDepth) throws Exception {
		
		if (skipDepth < 0 || urlArray.length - 2 < skipDepth) {
			throw new Exception("skipDepth must be more than 0 and less than path depth ");
		}
		for (int i = 1; i < skipDepth + 1; i++) {
			skippedPath += urlArray[i] + "/";
		}
	}
	
	private void makeFileName(boolean isFileNameExcepted) throws Exception {
		
		if (isFileNameExcepted) {
			fileName = "/" + urlArray[urlArray.length - 1];
		}else {
			int index = urlArray[urlArray.length - 1].lastIndexOf(".");
			if (index== -1) {
				throw new Exception("there is no file extension");
			}
			String extension = urlArray[urlArray.length - 1].substring(index);
			fileName = extension;
		}
	}
	
	private void setSeqClaim() throws Exception {
		Random random = new Random();
		int seq = random.nextInt(1000);
		jsonObject.put("seq", seq);
	}

	private void setExpClaim() throws Exception {
		long exp = System.currentTimeMillis() / 1000;
		exp += getTimeout();
		jsonObject.put("exp", exp );
	}

	private void setPathClaim(int skipDepth, boolean isFileNameExcepted ) throws Exception {
		
		String path = "/";

		for (int i = 1 + skipDepth; i < urlArray.length - 1; i++) {
			path += urlArray[i] + "/";
		}
		path = path.substring(0, path.length() - 1);
		if (!isFileNameExcepted) {
			int index = urlArray[urlArray.length - 1].lastIndexOf(".");
			if (index== -1) {
				throw new Exception("there is no file extension");
			}
			path += ( "/" + urlArray[urlArray.length - 1].substring(0,index) );
		}
		jsonObject.put("path", path );
	}
	
	private void setDefaultClaim(int skipDepth, boolean isFileNameExcepted) throws Exception {
		setExpClaim();
		setPathClaim(skipDepth, isFileNameExcepted );
		setSeqClaim();
	}

	public boolean setClaim(String key, Object value) {
		try {
			jsonObject.put(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public  boolean enablePreview(int duration) {
		try {
			if( duration<0 && duration!=-1 ) {
				throw new Exception("duration is wrong");
			}
			jsonObject.put("duration", duration);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public  boolean enablePreview(int duration, int playstart) {
		try {
			if( duration<0 && duration!=-1 ) {
				throw new Exception("duration is wrong");
			}
			if( playstart<0 ) {
				throw new Exception("playstart is wrong");
			}
			jsonObject.put("duration", duration);
			jsonObject.put("playstart", playstart);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
