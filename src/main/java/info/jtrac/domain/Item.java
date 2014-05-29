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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

/**
 * This object represents a generic item which can be an issue, defect, task etc.
 * some logic for field accessors and conversion of keys to display values 
 * is contained in the AbstractItem class
 */
@Entity
@Table(name="ITEM")
@NamedQueries(
		{
			@NamedQuery(
			name = "findByDetail",
			query = "FROM Item i WHERE i.detail = :detail"
			)
		}
	)
public class Item extends AbstractItem {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = 2587433115333525338L;
	public static final String NAMEDQUERY_FINDBYDETAIL = "findByDetail";
	/* Instance members*/
	@Column(name="type")
    private Integer type;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name = "SPACE_ID",nullable=true)
    private Space space;
	@Column(name="sequenceNum")
    private long sequenceNum;
	@OneToMany(cascade=CascadeType.REFRESH, mappedBy="parent", fetch=FetchType.EAGER)
    private Set<History> history;
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name = "CHILD_ID", nullable=true)
    private Set<Item> children;
	@OneToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name = "ITEM_ID", nullable=true)
    private Set<Attachment> attachments;
    // should be ideally in form backing object but for convenience
	@Column(name="EDITREASON")
    private String editReason;
	private 
	/**
	 * Default constructor
	 */
	public Item(){
        this.children = new LinkedHashSet<Item>();
        this.history = new LinkedHashSet<History>();
        this.attachments = new LinkedHashSet<Attachment>();
	}
    @Override
    public String getRefId() {
        return getSpace().getPrefixCode() + "-" + sequenceNum;
    }    
    
    public Map<Integer, String> getPermittedTransitions(User user) {
        return user.getPermittedTransitions(space, getStatus().getState());        
    }
    
    public List<Field> getEditableFieldList(User user) {
        return user.getEditableFieldList(space, getStatus().getState());
    }
    
    public void add(History history) {
    	history.setParent(this);
        this.history.add(history);
    }
    
    public void add(Attachment attachment) {
        attachments.add(attachment);
    }
    
    public void addRelated(Item relatedItem, int relationType) {
        ItemItem itemItem = new ItemItem(this, relatedItem, relationType);        
        getRelatedItems().add(itemItem);
    }    
    
    /**
     * Lucene DocumentCreator implementation
     */
    public Document createDocument() {
        Document d = new Document();        
        d.add(new org.apache.lucene.document.Field("id", getId() + "", Store.YES, Index.NO));            
        d.add(new org.apache.lucene.document.Field("type", "item", Store.YES, Index.NO));        
        StringBuffer sb = new StringBuffer();
        if (getSummary() != null) {
            sb.append(getSummary());
        }        
        if (getDetail() != null) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append(getDetail());
        }
        d.add(new org.apache.lucene.document.Field("text", sb.toString(), Store.NO, Index.TOKENIZED));
        return d;
    }    
    
    public History getLatestHistory() {
        if (history == null) {
            return null;
        }
        History out = null;
        for(History h : history) {
            out = h;
        }
        return out;
    }       
    
    //===========================================================
    
    @Override
    public Space getSpace() {
        return space;
    }    
    
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setSpace(Space space) {
        this.space = space;
    }    
    
    public long getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(long sequenceNum) {
        this.sequenceNum = sequenceNum;
    }     
    
    public Set<History> getHistory() {
        return history;
    }

    public void setHistory(Set<History> history) {
        this.history = history;
    }

    public Set<Item> getChildren() {
        return children;
    }

    public void setChildren(Set<Item> children) {
        this.children = children;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }      

    public String getEditReason() {
        return editReason;
    }

    public void setEditReason(String editReason) {
        this.editReason = editReason;
    }   
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("; type [").append(type);
        sb.append("]; space [").append(space);
        sb.append("]; sequenceNum [").append(sequenceNum);
        sb.append("]");
        return sb.toString();
    }
    
}
