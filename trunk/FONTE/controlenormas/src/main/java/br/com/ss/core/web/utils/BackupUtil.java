package br.com.ss.core.web.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class BackupUtil {

	public static final String BACKUP_DIR = "backups_db";
	
	private BackupUtil() { }
	
	
	public static void removeBackupFiles() {
		String appDir = MessageUtils.getMessageResourceString( MessageUtils.APP_DIR );
		String numMaxBkp = MessageUtils.getMessageResourceString( MessageUtils.NUM_MAX_BACKUP );
		
		String fullBackupPath = appDir + File.separator + BACKUP_DIR;
		Integer numMaxBackups = new Integer(numMaxBkp);
		
		// lista os arquivos 
		List<String> backupList = listarBackups(fullBackupPath);
		
		// ordena os arquivos
		Collections.sort(backupList);
		
		// valida se deve remover arquivos de backups
		if ( backupList.size() > numMaxBackups ) {
			
			List<String> removeList = backupList.subList(0, numMaxBackups);
			removeFile(removeList, fullBackupPath);
			
		}
	}


	/**
	 * Lista os arquivos de backups
	 * @param dir
	 * @return
	 */
	private static List<String> listarBackups(String dir) {
		File appDir = new File(dir);
		List<String> backups = new ArrayList<String>();
		if ( appDir.exists() ) {
			String[] files = appDir.list();
			backups.addAll( Arrays.asList(files) );
		}
		return backups;
	}

	/**
	 * Remove o arquivos
	 * @param backupList
	 * @param numMaxBackups
	 */
	private static void removeFile(List<String> backupList, String parentDir) {
		
		for (String bkp: backupList) {
			File file = new File(parentDir, bkp);
			// remove o arquivo
			System.out.println("\t# Removendo arquivo de backup: " + file.getAbsolutePath());
			file.delete();
		}
		
	}
	
	
	public static void main(String a[]){
	     ArrayList<String> al = new ArrayList<String>();

	     //Addition of elements in ArrayList
	     al.add("Steve");
	     al.add("Justin");
	     al.add("Ajeet");
	     al.add("John");
	     al.add("Arnold");
	     al.add("Chaitanya");

	     System.out.println("Original ArrayList Content: "+al);

	     //Sublist to ArrayList
	     ArrayList<String> al2 = new ArrayList<String>(al.subList(0, 2));
	     System.out.println("SubList stored in ArrayList: "+al2);


	     System.out.println("Currently Content: "+al);
	     
	     //Sublist to List
	     List<String> list = al.subList(1, 4);
	     System.out.println("SubList stored in List: "+list);
	  }
	
	
}
