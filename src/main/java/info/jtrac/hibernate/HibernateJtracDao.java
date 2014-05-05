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

package main.java.info.jtrac.hibernate;

import java.util.Collection;
import java.util.List;

import main.java.info.jtrac.JtracDao;
import main.java.info.jtrac.domain.AbstractItem;
import main.java.info.jtrac.domain.Attachment;
import main.java.info.jtrac.domain.Config;
import main.java.info.jtrac.domain.Counts;
import main.java.info.jtrac.domain.CountsHolder;
import main.java.info.jtrac.domain.Field;
import main.java.info.jtrac.domain.History;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.domain.ItemItem;
import main.java.info.jtrac.domain.ItemSearch;
import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.domain.State;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.domain.UserSpaceRole;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * DAO Implementation using Spring Hibernate template
 * note usage of the Spring "init-method" and "destroy-method" options
 */
public class HibernateJtracDao extends HibernateDaoSupport implements JtracDao {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private SchemaHelper schemaHelper;
    
    public void setSchemaHelper(SchemaHelper schemaHelper) {
        this.schemaHelper = schemaHelper;
    }
    
    public void storeItem(Item item) {        
        getHibernateTemplate().merge(item);
    }
    
    public Item loadItem(long id) {
        return (Item) getHibernateTemplate().get(Item.class, id);
    }
    
    public void storeHistory(History history) {        
        getHibernateTemplate().merge(history);
    }    
    
    public History loadHistory(long id) {
        return (History) getHibernateTemplate().get(History.class, id);
    }
    
    public List<Item> findItems(long sequenceNum, String prefixCode) {
        Object[] params = new Object[] {sequenceNum, prefixCode};
        return getHibernateTemplate().find("from Item item where item.sequenceNum = ? and item.space.prefixCode = ?", params);
    }
    
    public List<Item> findItems(ItemSearch itemSearch) {
        int pageSize = itemSearch.getPageSize();
        if (pageSize == -1) {
            List<Item> list = getHibernateTemplate().findByCriteria(itemSearch.getCriteria());
            itemSearch.setResultCount(list.size());
            return list;
        } else {
            // pagination
            int firstResult = pageSize * itemSearch.getCurrentPage();
            List<Item> list = getHibernateTemplate().findByCriteria(itemSearch.getCriteria(), firstResult, pageSize);
            DetachedCriteria criteria = itemSearch.getCriteriaForCount();
            criteria.setProjection(Projections.rowCount());
            Integer count = (Integer) getHibernateTemplate().findByCriteria(criteria).get(0);
            itemSearch.setResultCount(count);
            return list;
        }
    }
    
    public List<AbstractItem> findAllItems() {
        // return getHibernateTemplate().loadAll(AbstractItem.class);
        return (List<AbstractItem>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(AbstractItem.class);
                criteria.setFetchMode("space", FetchMode.JOIN);                              
                return criteria.list();
            }
        });        
    }
    
    public void removeItem(Item item) {
        getHibernateTemplate().delete(item);
    }    
    
    public void removeItemItem(ItemItem itemItem) {
        getHibernateTemplate().delete(itemItem);
    }    
    
    public void storeAttachment(Attachment attachment) {
        getHibernateTemplate().merge(attachment);
    }
    
    public void storeMetadata(Metadata metadata) {
        getHibernateTemplate().merge(metadata);
    }
    
    public Metadata loadMetadata(long id) {
        return (Metadata) getHibernateTemplate().get(Metadata.class, id);
    }
    
    public void storeSpace(Space space) {
        getHibernateTemplate().merge(space);
    }
    
    public Space loadSpace(long id) {
        return (Space) getHibernateTemplate().get(Space.class, id);
    }
    
    public UserSpaceRole loadUserSpaceRole(long id) {
        return (UserSpaceRole) getHibernateTemplate().get(UserSpaceRole.class, id);
    }    
    
    public SpaceSequence loadSpaceSequence(long id) {                
        return (SpaceSequence) getHibernateTemplate().get(SpaceSequence.class, id);           
    }    
    
    public void storeSpaceSequence(SpaceSequence spaceSequence) {
        getHibernateTemplate().saveOrUpdate(spaceSequence);
        // important to prevent duplicate sequence numbers, see JtracImpl#storeItem()
        getHibernateTemplate().flush();
    }
    
    public List<Space> findSpacesByPrefixCode(String prefixCode) {
        return getHibernateTemplate().find("from Space space where space.prefixCode = ?", prefixCode);
    }
    
    public List<Space> findAllSpaces() {
        return getHibernateTemplate().find("from Space space order by space.prefixCode");
    }
    
    public List<Space> findSpacesWhereGuestAllowed() {        
        return getHibernateTemplate().find("from Space space join fetch space.metadata where space.guestAllowed = true");       
    }
    
    public void removeSpace(Space space) {        
        getHibernateTemplate().delete(space);
    }    
    
    public void storeUser(User user) {
        getHibernateTemplate().merge(user);
    }
    
    public User loadUser(long id) {
        return (User) getHibernateTemplate().get(User.class, id);
    }
    
    public void removeUser(User user) {
        getHibernateTemplate().delete(user);
    }
    
    public List<User> findAllUsers() {
        return getHibernateTemplate().find("from User user order by user.name");
    }
    
    public List<User> findUsersMatching(final String searchText, final String searchOn) {   
        return (List<User>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Restrictions.ilike(searchOn, searchText, MatchMode.ANYWHERE));
                criteria.addOrder(Order.asc("name"));
                return criteria.list();
            }
        });
    }
    
    public List<User> findUsersByLoginName(String loginName) {
        return getHibernateTemplate().find("from User user where user.loginName = ?", loginName);
    }
    
    public List<User> findUsersByEmail(String email) {
        return getHibernateTemplate().find("from User user where user.email = ?", email);
    }
    
    public List<UserSpaceRole> findUserRolesForSpace(long spaceId) {
        // join fetch for user object
         return getHibernateTemplate().find("select usr from UserSpaceRole usr join fetch usr.user"
                 + " where usr.space.id = ? order by usr.user.name", spaceId);
    }
    
    public List<User> findUsersWithRoleForSpace(long spaceId, String roleKey) {
        return getHibernateTemplate().find("from User user"
                + " join user.userSpaceRoles as usr where usr.space.id = ?"
                + " and usr.roleKey = ? order by user.name", new Object[] {spaceId, roleKey});        
    }    
    
    public int loadCountOfHistoryInvolvingUser(User user) {
        Long count = (Long) getHibernateTemplate().find("select count(history) from History history where "
                + " history.loggedBy = ? or history.assignedTo = ?", new Object[] {user, user}).get(0);
        return count.intValue();        
    }
    
    //==========================================================================    
    
    public CountsHolder loadCountsForUser(User user) {
        Collection<Space> spaces = user.getSpaces();
        if (spaces.size() == 0) {
            return null;
        }
        CountsHolder ch = new CountsHolder();
        HibernateTemplate ht = getHibernateTemplate();        
        List<Object[]> loggedByList = ht.find("select item.space.id, count(item) from Item item" 
                + " where item.loggedBy.id = ? group by item.space.id", user.getId());
        List<Object[]> assignedToList = ht.find("select item.space.id, count(item) from Item item" 
                + " where item.assignedTo.id = ? group by item.space.id", user.getId());
        List<Object[]> statusList = ht.findByNamedParam("select item.space.id, count(item) from Item item" 
                + " where item.space in (:spaces) group by item.space.id", "spaces", spaces);
        for(Object[] oa : loggedByList) {
            ch.addLoggedByMe((Long) oa[0], (Long) oa[1]);
        }
        for(Object[] oa : assignedToList) {
            ch.addAssignedToMe((Long) oa[0], (Long) oa[1]);
        }
        for(Object[] oa : statusList) {
            ch.addTotal((Long) oa[0], (Long) oa[1]);
        }
        return ch;
    }
    
    public Counts loadCountsForUserSpace(User user, Space space) {
        HibernateTemplate ht = getHibernateTemplate();        
        List<Object[]> loggedByList = ht.find("select status, count(item) from Item item" 
                + " where item.loggedBy.id = ? and item.space.id = ? group by item.status", new Object[] {user.getId(), space.getId()});
        List<Object[]> assignedToList = ht.find("select status, count(item) from Item item" 
                + " where item.assignedTo.id = ? and item.space.id = ? group by item.status", new Object[] {user.getId(), space.getId()});
        List<Object[]> statusList = ht.find("select status, count(item) from Item item" 
                + " where item.space.id = ? group by item.status", space.getId());
        Counts c = new Counts(true);
        for(Object[] oa : loggedByList) {
            c.addLoggedByMe((Integer) oa[0], (Long) oa[1]);
        }
        for(Object[] oa : assignedToList) {
            c.addAssignedToMe((Integer) oa[0], (Long) oa[1]);
        }
        for(Object[] oa : statusList) {
            c.addTotal((Integer) oa[0], (Long) oa[1]);
        }
        return c;
    }
    
    //==========================================================================
    
    public List<User> findUsersForSpace(long spaceId) {
        return getHibernateTemplate().find("select distinct u from User u join u.userSpaceRoles usr" 
                + " where usr.space.id = ? order by u.name", spaceId);
    }
    
    public List<User> findUsersForSpaceSet(Collection<Space> spaces) {
        return getHibernateTemplate().findByNamedParam("select u from User u join u.userSpaceRoles usr" 
                + " where usr.space in (:spaces) order by u.name", "spaces", spaces);
    }
    
    public void removeUserSpaceRole(UserSpaceRole userSpaceRole) {        
        getHibernateTemplate().delete(userSpaceRole);
    }
    
    public List<Config> findAllConfig() {
        return getHibernateTemplate().loadAll(Config.class);
    }
    
    public void storeConfig(Config config) {
        getHibernateTemplate().merge(config);
    }
    
    public Config loadConfig(String param) {
        return (Config) getHibernateTemplate().get(Config.class, param);
    }

    public int loadCountOfRecordsHavingFieldNotNull(Space space, Field field) {
        Criteria criteria = getSession().createCriteria(Item.class);
        criteria.add(Restrictions.eq("space", space));
        criteria.add(Restrictions.isNotNull(field.getName().toString()));
        criteria.setProjection(Projections.rowCount());
        int itemCount = (Integer) criteria.list().get(0);
        // even when no item has this field not null currently, items may have history with this field not null
        // because of the "parent" difference, cannot use AbstractItem and have to do a separate Criteria query
        criteria = getSession().createCriteria(History.class);
        criteria.createCriteria("parent").add(Restrictions.eq("space", space));
        criteria.add(Restrictions.isNotNull(field.getName().toString()));
        criteria.setProjection(Projections.rowCount());
        return itemCount + (Integer) criteria.list().get(0);        
    }

    public int bulkUpdateFieldToNull(Space space, Field field) {
        int itemCount = getHibernateTemplate().bulkUpdate("update Item item set item." + field.getName() + " = null" 
                + " where item.space.id = ?", space.getId());
        logger.info("no of Item rows where " + field.getName() + " set to null = " + itemCount);
        int historyCount = getHibernateTemplate().bulkUpdate("update History history set history." + field.getName() + " = null"
                + " where history.parent in ( from Item item where item.space.id = ? )", space.getId());
        logger.info("no of History rows where " + field.getName() + " set to null = " + historyCount);
        return itemCount;
    }

    public int loadCountOfRecordsHavingFieldWithValue(Space space, Field field, int optionKey) {
        Criteria criteria = getSession().createCriteria(Item.class);
        criteria.add(Restrictions.eq("space", space));
        criteria.add(Restrictions.eq(field.getName().toString(), optionKey));
        criteria.setProjection(Projections.rowCount());
        int itemCount = (Integer) criteria.list().get(0);
        // even when no item has this field value currently, items may have history with this field value
        // because of the "parent" difference, cannot use AbstractItem and have to do a separate Criteria query
        criteria = getSession().createCriteria(History.class);
        criteria.createCriteria("parent").add(Restrictions.eq("space", space));
        criteria.add(Restrictions.eq(field.getName().toString(), optionKey));
        criteria.setProjection(Projections.rowCount());
        return itemCount + (Integer) criteria.list().get(0);        
    }

    public int bulkUpdateFieldToNullForValue(Space space, Field field, int optionKey) {
        int itemCount = getHibernateTemplate().bulkUpdate("update Item item set item." + field.getName() + " = null" 
                + " where item.space.id = ?"
                + " and item." + field.getName() + " = ?", new Object[] {space.getId(), optionKey});
        logger.info("no of Item rows where " + field.getName() + " value '" + optionKey + "' replaced with null = " + itemCount);
        int historyCount = getHibernateTemplate().bulkUpdate("update History history set history." + field.getName() + " = null"
                + " where history." + field.getName() + " = ?"
                + " and history.parent in ( from Item item where item.space.id = ? )", new Object[] {optionKey, space.getId()});
        logger.info("no of History rows where " + field.getName() + " value '" + optionKey + "' replaced with null = " + historyCount);
        return itemCount;        
    }
    
    public int loadCountOfRecordsHavingStatus(Space space, int status) {
        Criteria criteria = getSession().createCriteria(Item.class);
        criteria.add(Restrictions.eq("space", space));
        criteria.add(Restrictions.eq("status", status));
        criteria.setProjection(Projections.rowCount());
        int itemCount = (Integer) criteria.list().get(0);
        // even when no item has this status currently, items may have history with this status
        // because of the "parent" difference, cannot use AbstractItem and have to do a separate Criteria query
        criteria = getSession().createCriteria(History.class);
        criteria.createCriteria("parent").add(Restrictions.eq("space", space));
        criteria.add(Restrictions.eq("status", status));
        criteria.setProjection(Projections.rowCount());
        return itemCount + (Integer) criteria.list().get(0);
    }    
    
    public int bulkUpdateStatusToOpen(Space space, int status) {
        int itemCount = getHibernateTemplate().bulkUpdate("update Item item set item.status = " + State.OPEN 
                + " where item.status = ? and item.space.id = ?", new Object[] {status, space.getId()});
        logger.info("no of Item rows where status changed from " + status + " to " + State.OPEN + " = " + itemCount);
        int historyCount = getHibernateTemplate().bulkUpdate("update History history set history.status = " + State.OPEN 
                + " where history.status = ?"
                + " and history.parent in ( from Item item where item.space.id = ? )", new Object[] {status, space.getId()});
        logger.info("no of History rows where status changed from " + status + " to " + State.OPEN + " = " + historyCount);
        return itemCount;
    }    
    
    public int bulkUpdateRenameSpaceRole(Space space, String oldRoleKey, String newRoleKey) {
        return getHibernateTemplate().bulkUpdate("update UserSpaceRole usr set usr.roleKey = ?"
                + " where usr.roleKey = ? and usr.space.id = ?", new Object[] {newRoleKey, oldRoleKey, space.getId()});
    }
    
    public int bulkUpdateDeleteSpaceRole(Space space, String roleKey) {
        if (roleKey == null) {
            return getHibernateTemplate().bulkUpdate("delete UserSpaceRole usr where usr.space.id = ?", space.getId());            
        } else {
            return getHibernateTemplate().bulkUpdate("delete UserSpaceRole usr"
                    + " where usr.space.id = ? and usr.roleKey = ?", new Object[] {space.getId(), roleKey});
        }
    }
    
    public int bulkUpdateDeleteItemsForSpace(Space space) {
        int historyCount = getHibernateTemplate().bulkUpdate("delete History history where history.parent in"
                + " ( from Item item where item.space.id = ? )", space.getId());
        logger.debug("deleted " + historyCount + " records from history");
        int itemItemCount = getHibernateTemplate().bulkUpdate("delete ItemItem itemItem where itemItem.item in"
                + " ( from Item item where item.space.id = ? )", space.getId());
        logger.debug("deleted " + itemItemCount + " records from item_items");
        int itemCount = getHibernateTemplate().bulkUpdate("delete Item item where item.space.id = ?", space.getId());
        logger.debug("deleted " + itemCount + " records from items");
        return historyCount + itemItemCount + itemCount;
    }
    
    //==========================================================================
    
    /**
     * note that this is automatically configured to run on startup 
     * as a spring bean "init-method"
     */
    public void createSchema() {
        try {
            getHibernateTemplate().find("from Item item where item.id = 1");
        } catch (Exception e) {
            logger.warn("expected database schema does not exist, will create. Error is: " + e.getMessage());            
            schemaHelper.createSchema();
            User admin = new User();
            admin.setLoginName("admin");
            admin.setName("Admin");
            admin.setEmail("admin");
            admin.setPassword("21232f297a57a5a743894a0e4a801fc3");
            admin.addSpaceWithRole(null, "ROLE_ADMIN");
            logger.info("inserting default admin user into database");
            storeUser(admin);
            logger.info("schema creation complete");
            return;
        }
        logger.info("database schema exists, normal startup");        
    }    
    
}
