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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

/**
 * Standard User entity with attributes such as name, password etc.
 * The parent relationship is used for easy grouping of users and
 * flexible inheritance of permission schemes TODO.  The user type
 * determines if this is a normal user or a user group.  Only
 * user groups can have child references.
 *
 * We also tie in to the Acegi security framework and implement
 * the Acegi UserDetails interface so that Acegi can take care
 * of Authentication and Authorization
 */
@Entity
@Table(name="USERDB")
@NamedQueries(
		{
			@NamedQuery(
			name = "findByLoginName",
			query = "FROM User u WHERE u.loginName = :loginName"
			)
		}
	)
//public class User implements UserDetails, Serializable {
public class User implements  Serializable {
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 3209224798300962218L;
	/* Instance members */
    public static final int SEARCH_NAME = 0;
    public static final int SEARCH_LOGIN_NAME = 1;
    public static final int SEARCH_EMAIL = 2;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID", nullable=false, unique=true)
    private long id;
	@Column(name="TYPE")
    private Integer type;
	@Column(name="ACTIVE")
	private boolean active;
	@Enumerated(EnumType.STRING)
	@Column(name="USERROLE")
	private UserRole userRole;
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PARENT_ID")
    private User parent;
	@Column(name="LOGINNAME")
    private String loginName;
	@Column(name="NAME")
    private String name;
	@Column(name="PASSWORD")
    private String password;
	@Column(name="EMAIL")
    private String email;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name = "METADATA_ID")
    private Metadata metadata;
	@Column(name="LOCALE")
    private String locale;
	@Column(name="LOCKED")
    private boolean locked;
	@OneToMany(fetch=FetchType.LAZY, mappedBy= "user", cascade=CascadeType.ALL)
    private Set<UserSpaceRole> userSpaceRoles = new HashSet<UserSpaceRole>();    
    //=============================================================
	/**
	 * Default constructor for the class
	 */
   public User(){
	   this.active = true;
   }
    public void addSpaceWithRole(Space space, String roleKey) {        
        userSpaceRoles.add(new UserSpaceRole(this, space, roleKey));        
    }
    
    public void removeSpaceWithRole(Space space, String roleKey) {
        userSpaceRoles.remove(new UserSpaceRole(this, space, roleKey));        
    }
    
    /**
     * when the passed space is null this has a special significance
     * it will return roles that are 'global'
     */
    public List<String> getRoleKeys(Space space) {
        List<String> roleKeys = new ArrayList<String>();
        for(UserSpaceRole usr : userSpaceRoles) {
            Space s = usr.getSpace();
            if (s == space || (s != null && s.equals(space))) {
                roleKeys.add(usr.getRoleKey());
            }
        }
        return roleKeys;
    }        
    
    public Map<Integer, String> getPermittedTransitions(Space space, int status) {
        return space.getMetadata().getPermittedTransitions(getRoleKeys(space), status);
    }
    
    public List<Field> getEditableFieldList(Space space, int status) {
        return space.getMetadata().getEditableFields(getRoleKeys(space), status);
    }
    
    public Set<Space> getSpaces() {
        Set<Space> spaces = new HashSet<Space>(userSpaceRoles.size());
        for (UserSpaceRole usr : userSpaceRoles) {
            if (usr.getSpace() != null) {
                spaces.add(usr.getSpace());
            }
        }
        return spaces;
    }    
    
    public int getSpaceCount() {
        return getSpaces().size();
    }
    
    public boolean isAdminForAllSpaces() {
        return getRoleKeys(null).contains("ROLE_ADMIN");
    }
    
    /** 
     * this returns 'valid' spaceRoles, where space not null and role not ROLE_ADMIN
     * also sort by Space name for showing on the dashboard
     * TODO multiple roles per space
     */
    public Collection<UserSpaceRole> getSpaceRoles() {
        Map<String, UserSpaceRole> map = new TreeMap<String, UserSpaceRole>();        
        for(UserSpaceRole usr : userSpaceRoles) {
            if(usr.getSpace() != null && !usr.getRoleKey().equals("ROLE_ADMIN")) {
                map.put(usr.getSpace().getName(), usr);
            }
        }
        return map.values();
    }        
    
    public boolean isGuestForSpace(Space space) {
        if (id == 0) {
            return true;
        }
        for(UserSpaceRole usr : getUserSpaceRolesBySpaceId(space.getId())) {
            if(usr.getRoleKey().equals("ROLE_GUEST")) {
                return true;
            }
        }
        return false;
    }
    
    
    private Collection<UserSpaceRole> getUserSpaceRolesBySpaceId(long spaceId) {
        List<UserSpaceRole> list = new ArrayList<UserSpaceRole>();
        for (UserSpaceRole usr : userSpaceRoles) {
            if (usr.getSpace() != null && usr.getSpace().getId() == spaceId) {
                list.add(usr);
            }
        }
        return list;
    }    
    
    //============ ACEGI UserDetails implementation ===============
    
    public boolean isAccountNonExpired() {
        return true;
    }
    
    public boolean isAccountNonLocked() {
        return !isLocked();
    }
    
    public GrantedAuthority[] getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // grant full access only if not a Guest
        if (id > 0) {
            authorities.add(new UserSpaceRole(this, null, "ROLE_USER"));
        }
        for (UserSpaceRole usr : userSpaceRoles) {            
            authorities.add(usr);
        }
        return authorities.toArray(new GrantedAuthority[authorities.size()]);
    }
    
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    public boolean isEnabled() {
        return true;
    }
    
    public String getUsername() {
        return getLoginName();
    }
    
    public String getPassword() {
        return password;
    } 
    
    //=============================================================    
    
    public Set<UserSpaceRole> getUserSpaceRoles() {
        return userSpaceRoles;
    }

    public void setUserSpaceRoles(Set<UserSpaceRole> userSpaceRoles) {
        this.userSpaceRoles = userSpaceRoles;
    }    
    
    public User getParent() {
        return parent;
    }
    
    public void setParent(User parent) {
        this.parent = parent;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }    
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public Metadata getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    /**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public String getLoginName() {
        return loginName;
    }
    
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    
    /**
	 * @return the userRole
	 */
	public UserRole getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("id [").append(id);
        sb.append("]; loginName [").append(loginName);
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User u = (User) o;
        return u.getLoginName().equals(loginName);
    }
    
    @Override
    public int hashCode() {
        if(loginName == null) {
            return 0;
        }
        return loginName.hashCode();
    }
    
}
