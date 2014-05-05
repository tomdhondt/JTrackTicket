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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Class that exists purely to denormalize the Space entity in the database.
 * A Space has a one-to-one relationship with SpaceSequence
 * Since the "nextSeqNum" property is going to be updated on every new 
 * Item insert, this is kept in a separate "sequence" kind of table to 
 * reduce the number of updates that happen for the Space object / table
 */
@Entity
@Table(name="SPACESEQUENCE")
public class SpaceSequence implements Serializable {
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 6637867292551908839L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
    private long id;
	@Column(name="NEXTSEQNUM")
    private long nextSeqNum = 1;
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval=true, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
    private Space space;
    public long next() {
        return nextSeqNum++;
    }
    
    public long getNextSeqNum() {
        return nextSeqNum;
    }

    public void setNextSeqNum(long nextSeqNum) {
        this.nextSeqNum = nextSeqNum;
    }
    public void setSpace(Space space) {
        this.space = space;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Space getSpace() {
        return space;
    }

}
