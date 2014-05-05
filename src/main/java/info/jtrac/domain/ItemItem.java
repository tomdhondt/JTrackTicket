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

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Class that exists purely to hold a single item related to another item
 * along with a integer "type" indicating the nature of the relationship
 * between Item --> Item (one directional relationship)
 *
 * This is used in the following cases
 * - item is a duplicate of another
 * - item depends on another
 *
 * and can be used for other kinds of relationships in the future
 */
@Entity
@Table(name="ITEMITEM")
public class ItemItem implements Serializable {    
    /**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 2446643405511182094L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
    private long id;
	@Version
	@Column(name="VERSION")
	private int version;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name="ITEM_ID", nullable=true)
    private Item item;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name="RELATED_ITEM_ID", nullable=true)
    private Item relatedItem;
	@Column(name="TYPE")
    private int type;

    public static final int RELATED = 0;
    public static final int DUPLICATE_OF = 1;
    public static final int DEPENDS_ON = 2;
    
    // this returns i18n keys
    public static String getRelationText(int type) {
        if (type == RELATED) {
            return "relatedTo";
        } else if (type == DUPLICATE_OF) {
            return "duplicateOf";
        } else if (type == DEPENDS_ON) {
            return "dependsOn";
        } else {
            throw new RuntimeException("unknown type: " + type);
        }
    }
    
    public ItemItem() {
        // zero arg constructor
    }
    
    public ItemItem(Item item, Item relatedItem, int type) { 
        this.item = item;
        this.relatedItem = relatedItem;
        this.type = type;
    }    
    
    public String getRelationText() {
        return getRelationText(type);
    }
    
    //=================================================
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    public Item getRelatedItem() {
        return relatedItem;
    }

    public void setRelatedItem(Item relatedItem) {
        this.relatedItem = relatedItem;
    }    

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }    
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("id [").append(id);
        sb.append("]; item [").append(item);
        sb.append("]; type [").append(type);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemItem)) {
            return false;
        }
        final ItemItem ii = (ItemItem) o;
        return (id == ii.getId());
    }
    
    @Override
    public int hashCode() {
        return (int) id;
    }
    
}
