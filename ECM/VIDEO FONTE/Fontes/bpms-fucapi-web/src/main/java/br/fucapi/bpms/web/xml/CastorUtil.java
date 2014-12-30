package br.fucapi.bpms.web.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.XMLContext;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;


@Component("castorUtil")
public class CastorUtil {
	
	public Object convertFromXMLToObject(Class<?> clazz, String mappingFile, String xmlContent)
			throws IOException, MappingException, MarshalException, ValidationException {
		Mapping mapping = new Mapping();
		mapping.loadMapping(mappingFile);
		
		XMLContext context = new XMLContext();
		context.addMapping(mapping);
		
		
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		unmarshaller.setClass(clazz);
		
		return unmarshaller.unmarshal(new InputSource(new ByteArrayInputStream(xmlContent.getBytes("utf-8"))));
	}
	
	public Object convertFromXMLToObject(Class<?> clazz, File mappingFile, File xmlFile) throws IOException, MappingException, MarshalException, ValidationException{
		Mapping mapping = new Mapping();
		mapping.loadMapping(mappingFile.getAbsolutePath());
		
		XMLContext context = new XMLContext();
		context.addMapping(mapping);
		
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		unmarshaller.setClass(clazz);	
		
		FileReader fileReader = new FileReader(xmlFile);
		try{
			return unmarshaller.unmarshal(fileReader);
		}finally{
			fileReader.close();
		}
	}
}
	
	
