package pl.coderslab.model;

public class Attachment {
	
	private long id; 
	private String name;
	private String mimeType;
	private byte[] contents;
	private long attachedToId;
	

	public Attachment(long id, String name, String mimeType,  byte[] contents, long attachedToId) {
		this.id = id;
		this.name = name;
		this.mimeType = mimeType;
		this.contents = contents;
		this.attachedToId = attachedToId;
	}

	public Attachment(String name, String mimeType, byte[] contents, long attachedToId) {
		this(0L, name, mimeType, contents, attachedToId);
	}

	public Attachment() {
		this(0L, "", "", null, 0L);
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public long getAttachedToId() {
		return attachedToId;
	}

	public void setAttachedToId(long attachedToId) {
		this.attachedToId = attachedToId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}
}
