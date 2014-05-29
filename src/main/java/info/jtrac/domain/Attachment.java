/*
 * Copyright 2002-2005 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package main.java.info.jtrac.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Represents a file attachment.  Files will be stored on the local
 * file system, not within the database.
 * When an Attachment is first uploaded and stored, it is prefixed
 * with the value of the id generated on the database insert.
 * This filePrefix property is stored separately to smoothly
 * handle database migrations.  So even if a database export-import 
 * changes the id column values, the files within the attachments 
 * folder can be used as is, without resorting to mass renaming.
 */
@Entity
@Table(name="ATTACHMENT")
@NamedQueries(
		{
			@NamedQuery(
			name = "attachment_findByFileName",
			query = "FROM Attachment a WHERE s.fileName = :fileName"
			)
		}
	)
public class Attachment {
	/*
	 * Instance members
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
	private long id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PREVIOS_ID")
    private Attachment previous;
	@Column(name="FILE_PREFIX")
    private long filePrefix;
	@Column(name="FILE_NAME")
    private String fileName;
	/**
	 * Default Constructor for the class
	 */
	public Attachment(){}
	/**
	 * Constructor for the class
	 * @param fileName as String
	 */
	public Attachment(String fileName){
		this.fileName = fileName;
	}
	
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Attachment getPrevious() {
        return previous;
    }

    public void setPrevious(Attachment previous) {
        this.previous = previous;
    }

    public long getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(long filePrefix) {
        this.filePrefix = filePrefix;
    }
    
}
