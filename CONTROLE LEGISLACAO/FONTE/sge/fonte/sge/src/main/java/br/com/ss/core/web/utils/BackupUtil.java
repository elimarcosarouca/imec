package br.com.ss.core.web.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class BackupUtil {

	
	private BackupUtil() { }
	
	
	public static void removeBackupFiles() {
		String appDir = MessageUtils.getMessageResourceString( MessageUtils.APP_DIR );
		String backupDir = MessageUtils.getMessageResourceString( MessageUtils.BACKUP_DIR );
		String numMaxBkp = MessageUtils.getMessageResourceString( MessageUtils.NUM_MAX_BACKUP );
		
		String fullBackupPath = appDir + File.separator + backupDir;
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
	
	
}
