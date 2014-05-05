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
 * Class that exists purely to hold a single user associated with an item
 * along with a integer "type" indicating the nature of the relationship
 * between Item --> User (one directional relationship)
 *
 * This is used in the following cases
 * - users "watching" an Item and need to be notified on Status changes
 *
 * and can be used for other kinds of relationships in the future
 */
@Entity
@Table(name="ITEMUSER")
public class ItemUser implements Serializable {
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -7767740416445248366L;
	/*
	 * Instance members
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
    private long id;
	@Version
	@Column(name="VERSION")
	private int version;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID", nullable=true)
    private User user;
	@Column(name="TYPE")
    private int type;
    
    public ItemUser() {
        // zero arg constructor
    }
    
    public ItemUser(User user) {
        this.user = user;
    }
    
    public ItemUser(User user, int type) {
        this.user = user;
        this.type = type;
    }

    //=================================================
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        sb.append("]; user [").append(user);
        sb.append("]; type [").append(type);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemUser)) {
            return false;
        }
        final ItemUser iu = (ItemUser) o;
        return (user.equals(iu.getUser()) && type == iu.getType());
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + user.hashCode();
        hash = hash * 31 + type;
        return hash;
    }
    
}
