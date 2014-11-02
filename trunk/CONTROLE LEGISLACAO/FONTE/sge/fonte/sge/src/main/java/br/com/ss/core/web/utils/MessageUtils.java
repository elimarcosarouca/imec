package br.com.ss.core.web.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class MessageUtils {

	public static final String ID_SISTEMA = "id_sistema";

	public static final String ID_EMPRESA = "id_empresa";
	
	public static final String MESSAGE_BUNDLE = "messages";

	public static final String APP_DIR = "app_dir";

	public static final String BACKUP_DIR = "backups_db";
	
	public static final String NUM_MAX_BACKUP = "num_max_backup";
	

	private MessageUtils() {}
	

	public static String getMessageResourceString( String key, Object ...  params) {
		return getMessageResourceString(MESSAGE_BUNDLE, key, params);
	}
	
	public static String getMessageResourceString( String bundleName, String key, Object ... params) {
		String text = null;
		ResourceBundle bundle = ResourceBundle.getBundle( bundleName );
		
		try {
			text = bundle.getString(key);
		} catch(MissingResourceException e) {
			text = "?? key " + key + " not found ??";
		}
		
		if(params != null) {
			MessageFormat mf = new MessageFormat(text);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}
	
	
}
