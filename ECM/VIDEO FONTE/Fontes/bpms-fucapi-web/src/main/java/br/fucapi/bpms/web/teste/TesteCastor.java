package br.fucapi.bpms.web.teste;

import java.io.File;
import java.io.IOException;

import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import br.fucapi.bpms.web.dominio.dto.Feed;
import br.fucapi.bpms.web.dominio.dto.PropertyString;
import br.fucapi.bpms.web.xml.CastorUtil;

public class TesteCastor {

	public static void main(String[] args) throws MarshalException, ValidationException, IOException, MappingException {
		
		File xml = new File("C:\\teste.xml");
		System.out.println(xml.exists());
		
		File mappingFile = new File("E:\\Desenvolvimento\\Projetos\\BPMS_FUCAPI\\Fontes\\bpms-fucapi-web\\src\\main\\resources\\castor\\feed.xml");
		
		CastorUtil castorUtil = new CastorUtil();
		Feed convertFromXMLToObject = (Feed) castorUtil.convertFromXMLToObject(Feed.class, mappingFile, xml);
		
		System.out.println(convertFromXMLToObject.getEntrys().get(0).getObject().getProperties());
		
		for (PropertyString s : convertFromXMLToObject.getEntrys().get(0).getObject().getProperties().getPropertiesString()) {
			System.out.println(s.getValue());
		}
	}
	
}
