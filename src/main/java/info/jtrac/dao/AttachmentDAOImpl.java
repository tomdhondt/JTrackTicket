package main.java.info.jtrac.dao;

import org.hibernate.SessionFactory;

import main.java.info.jtrac.domain.Attachment;
/**
 * Class AttachmentDAOImpl is responsible to create a implementation for to manage the interaction to the database
 * @author Administrator - created at : 19-apr.-2014
 */
public class AttachmentDAOImpl extends AbstractDAOImpl<Attachment> implements IPersistenceDAOImpl<Attachment>{
	/**
	 * Default constructor for the class
	 */
	public AttachmentDAOImpl(){
		super.type = Attachment.class;
	}
	/**
	 * Default constructor for the class
	 */
	public AttachmentDAOImpl(SessionFactory sessionFactory){
		super.sessionFactory = sessionFactory;
		super.type = Attachment.class;
	}
}
