package br.fucapi.ads.modelo.dominio;

import flexjson.JSONDeserializer;

public class UUIDNodeRef {

	private String uuidPasta;
	private String nodeRef;
	
	public String getUuidPasta() {
		return uuidPasta;
	}
	public void setUuidPasta(String uuidPasta) {
		this.uuidPasta = uuidPasta;
	}
	public String getNodeRef() {
		return nodeRef;
	}
	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}
	
	public static UUIDNodeRef fromJsonToUUIDNodeRef(String json) {
		return new JSONDeserializer<UUIDNodeRef>().use( null, UUIDNodeRef.class).deserialize(json);
	}
}
