package com.lfchaim.springmongocustomerclient.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerUtil {

	public static void main(String[] args) {
		CustomerUtil obj = new CustomerUtil();
		String full = obj.getFullName();
		System.out.println(full);
	}
	
	public String jsonFromMap(Map<String,Object> map) {
		String ret = "";
		try{ret = new ObjectMapper().writeValueAsString(map);}catch(Exception e){}
		return ret;
	}
	
	public String getEmail( String fullName ) {
		StringBuffer ret = new StringBuffer();
		for( int i = 0; i < fullName.length(); i++ ) {
			if( fullName.charAt(i) == ' ' )
				ret.append(".");
			else
				ret.append(fullName.charAt(i));
		}
		return ret.toString();
	}
	
	public String getFullName() {
		Random rand = new Random(System.currentTimeMillis());
		List<String> listFirst = listData("name_first.txt");
		List<String> listMiddle = listData("name_middle.txt");
		List<String> listLast = listData("name_last.txt");
		String fullName = listFirst.get(rand.nextInt(listFirst.size()))+" "+listMiddle.get(rand.nextInt(listMiddle.size()))+" "+listLast.get(rand.nextInt(listLast.size()));
		return fullName;
	}
	
	public List<String> listData(String fileName){
		FileReader fr = null;
		BufferedReader br = null;
		List<String> ret = new ArrayList<>();
		try {
			fr = new FileReader(new File(fileName));
			br = new BufferedReader(fr);
			String line = null;
			while( (line = br.readLine()) != null ) {
				ret.add(line);
			}
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			try {br.close();}catch(Exception e) {}
			try {fr.close();}catch(Exception e) {}
		}
		return ret;
	}
	
	public void writeLog(String fileName, String msg) {
		FileWriter f = null;
		BufferedWriter b = null;
		List<String> ret = new ArrayList<>();
		try {
			f = new FileWriter(new File(fileName),true);
			b = new BufferedWriter(f);
			b.write(msg+"\n");
			b.flush();
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			try {b.close();}catch(Exception e) {}
			try {f.close();}catch(Exception e) {}
		}
	}
	
	public boolean delete(String fileName) {
		boolean ret = false;
		ret = new File(fileName).delete();
		if( !ret )
			new File(fileName).deleteOnExit();
		return ret;
	}
}
