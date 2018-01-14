package pl.coderslab.dao;

import java.util.Collection;

import pl.coderslab.model.Attachment;

public interface AttachmentDao {
	
	int save(Attachment attachment, String attachedTo);
	boolean delete(long id);
	Collection<Attachment> loadAttachmentByAttachedToId(long attachedToId, String attachedTo);
	Attachment loadAttachmentById(int id, String attachedTo);

}
