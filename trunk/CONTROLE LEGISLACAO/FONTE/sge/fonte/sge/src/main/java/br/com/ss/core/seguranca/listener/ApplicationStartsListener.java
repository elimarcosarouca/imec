package br.com.ss.core.seguranca.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.ss.core.web.utils.BackupUtil;

public class ApplicationStartsListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// Remove arquivos de backups desnecessarios
		BackupUtil.removeBackupFiles();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// nothing
	}

}
