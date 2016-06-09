package hedspi.tienlv.grapp.common;

import java.io.File;

public class Test {
	public static void main(String[] args){
		File f = new File("D:\\aaa.txt");
		if(f.exists()){
			System.out.println("exist");
		}else{
			System.out.println("no exist");
		}
	}
}
