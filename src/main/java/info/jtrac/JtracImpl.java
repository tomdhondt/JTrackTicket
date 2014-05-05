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

package main.java.info.jtrac;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;



import main.java.info.jtrac.domain.AbstractItem;
import main.java.info.jtrac.domain.Attachment;
import main.java.info.jtrac.domain.Config;
import main.java.info.jtrac.domain.Counts;
import main.java.info.jtrac.domain.CountsHolder;
import main.java.info.jtrac.domain.Field;
import main.java.info.jtrac.domain.History;
import main.java.info.jtrac.domain.Item;
import main.java.info.jtrac.domain.ItemItem;
import main.java.info.jtrac.domain.ItemRefId;
import main.java.info.jtrac.domain.ItemSearch;
import main.java.info.jtrac.domain.Metadata;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.SpaceSequence;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.domain.UserSpaceRole;
import main.java.info.jtrac.lucene.IndexSearcher;
import main.java.info.jtrac.lucene.Indexer;
import main.java.info.jtrac.mail.MailSender;
import main.java.info.jtrac.util.AttachmentUtils;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Jtrac Service Layer implementation
 * This is where all the business logic is
 * For data persistence this delegates to JtracDao
 */
public class JtracImpl implements Jtrac {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private JtracDao dao;
    private PasswordEncoder passwordEncoder;
    private MailSender mailSender;
    private Indexer indexer;
    private IndexSearcher indexSearcher;
    private MessageSource messageSource;

    private Map<String, String> locales;
    private String defaultLocale = "en";
    private String releaseVersion;
    private String releaseTimestamp;
    private String jtracHome;
    private int attachmentMaxSizeInMb = 5;
    private int sessionTimeoutInMinutes = 30;

    public void setLocaleList(String[] array) {
        locales = new LinkedHashMap<String, String>();
        for(String localeString : array) {
            Locale locale = StringUtils.parseLocaleString(localeString);
            locales.put(localeString, localeString + " - " + locale.getDisplayName());
        }
        logger.info("available locales configured " + locales);
    }

    public void setDao(JtracDao dao) {
        this.dao = dao;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setIndexSearcher(IndexSearcher indexSearcher) {
        this.indexSearcher = indexSearcher;
    }

    public void setIndexer(Indexer indexer) {
        this.indexer = indexer;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setReleaseTimestamp(String releaseTimestamp) {
        this.releaseTimestamp = releaseTimestamp;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public void setJtracHome(String jtracHome) {
        this.jtracHome = jtracHome;
    }

    public String getJtracHome() {
        return jtracHome;
    }    

    public int getAttachmentMaxSizeInMb() {
        return attachmentMaxSizeInMb;
    }    

    public int getSessionTimeoutInMinutes() {
        return sessionTimeoutInMinutes;
    }

    /**
     * this has not been factored into the util package or a helper class
     * because it depends on the PasswordEncoder configured
     */
    public String generatePassword() {
        byte[] ab = new byte[1];
        Random r = new Random();
        r.nextBytes(ab);
        return passwordEncoder.encodePassword(new String(ab), null).substring(24);
    }

    /**
     * this has not been factored into the util package or a helper class
     * because it depends on the PasswordEncoder configured
     */
    public String encodeClearText(String clearText) {
        return passwordEncoder.encodePassword(clearText, null);
    }

    public Map<String, String> getLocales() {
        return locales;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * this is automatically called by spring init-method hook on
     * startup, also called whenever config is edited to refresh
     * TODO move config into a settings class to reduce service clutter     
     */
//    public void init() {
//        Map<String, String> config = loadAllConfig();
//        initMailSender(config);
//        initDefaultLocale(config.get("locale.default"));
//        initAttachmentMaxSize(config.get("attachment.maxsize"));
//        initSessionTimeout(config.get("session.timeout"));
//    }
    
//    private void initMailSender(Map<String, String> config) {
//        this.mailSender = new MailSender(config, messageSource, defaultLocale);
//    }    
    
    private void initDefaultLocale(String localeString) {
        if (localeString == null || !locales.containsKey(localeString)) {
            logger.warn("invalid default locale configured = '" + localeString + "', using " + this.defaultLocale);            
        } else {
            this.defaultLocale = localeString;
        }
        logger.info("default locale set to '" + this.defaultLocale + "'");       
    }    

    private void initAttachmentMaxSize(String s) {
        try {
            this.attachmentMaxSizeInMb = Integer.parseInt(s);
        } catch(Exception e) {
            logger.warn("invalid attachment max size '" + s + "', using " + attachmentMaxSizeInMb);
        }
        logger.info("attachment max size set to " + this.attachmentMaxSizeInMb + " MB");
    }
    
    private void initSessionTimeout(String s) {
        try {
            this.sessionTimeoutInMinutes = Integer.parseInt(s);
        } catch(Exception e) {
            logger.warn("invalid session timeout '" + s + "', using " + this.sessionTimeoutInMinutes);
        }
        logger.info("session timeout set to " + this.sessionTimeoutInMinutes + " minutes");
    }
    
    //==========================================================================

    private Attachment getAttachment(FileUpload fileUpload) {
        if(fileUpload == null) {
            return null;
        }
        logger.debug("fileUpload not null");
        String fileName = AttachmentUtils.cleanFileName(fileUpload.getClientFileName());
        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        dao.storeAttachment(attachment);
        attachment.setFilePrefix(attachment.getId());
        return attachment;
    }

    private void writeToFile(FileUpload fileUpload, Attachment attachment) {
        if(fileUpload == null) {
            return;
        }
        File file = AttachmentUtils.getFile(attachment, jtracHome);
        try {
            fileUpload.writeTo(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void storeItem(Item item, FileUpload fileUpload) {
        History history = new History(item);
        Attachment attachment = getAttachment(fileUpload);
        if(attachment != null) {
            item.add(attachment);
            history.setAttachment(attachment);
        }
        Date now = new Date();
        item.setTimeStamp(now);
        history.setTimeStamp(now);
        item.add(history);
        SpaceSequence spaceSequence = dao.loadSpaceSequence(item.getSpace().getSpaceSequence().getId());
        item.setSequenceNum(spaceSequence.next());
        // the synchronize for this storeItem method and the hibernate flush() call in the dao implementation
        // are important to prevent duplicate sequence numbers
        dao.storeSpaceSequence(spaceSequence);
        // this will at the moment execute unnecessary updates (bug in Hibernate handling of "version" property)
        // se http://opensource.atlassian.com/projects/hibernate/browse/HHH-1401
        // TODO confirm if above does not happen anymore
        dao.storeItem(item);        
        writeToFile(fileUpload, attachment);
        indexer.index(item);
        indexer.index(history);
//        if (item.isSendNotifications()) {
//            mailSender.send(item);
//        }
    }

    public void updateItem(Item item, User user) {
        logger.debug("update item called");
        History history = new History(item);
        history.setAssignedTo(null);
        history.setStatus(null);
        history.setLoggedBy(user);
        history.setComment(item.getEditReason());
        history.setTimeStamp(new Date());
        item.add(history);
        dao.storeItem(item);  // merge edits + history        
//        // TODO index?
//        if (item.isSendNotifications()) {
//            mailSender.send(item);
//        }
    }

    public synchronized void storeHistoryForItem(long itemId, History history, FileUpload fileUpload) {
        Item item = dao.loadItem(itemId);
        // first apply edits onto item record before we change the item status
        // the item.getEditableFieldList routine depends on the current State of the item
        for(Field field : item.getEditableFieldList(history.getLoggedBy())) {
            Object value = history.getValue(field.getName());
            if (value != null) {
                item.setValue(field.getName(), value);
            }
        }
        if (history.getStatus() != null) {
            item.setStatus(history.getStatus());
            item.setAssignedTo(history.getAssignedTo()); // this may be null, when closing
        }
//        item.setItemUsers(history.getItemUsers());
        history.setTimeStamp(new Date());
        Attachment attachment = getAttachment(fileUpload);
        if(attachment != null) {
            item.add(attachment);
            history.setAttachment(attachment);
        }
        item.add(history);
        dao.storeItem(item);        
        writeToFile(fileUpload, attachment);
        indexer.index(history);
//        if (history.isSendNotifications()) {
//            mailSender.send(item);
//        }
    }

    public Item loadItem(long id) {
        return dao.loadItem(id);
    }

    public Item loadItemByRefId(String refId) {
        ItemRefId itemRefId = new ItemRefId(refId); // throws runtime exception if invalid id
        List<Item> items = dao.findItems(itemRefId.getSequenceNum(), itemRefId.getPrefixCode());
        if (items.size() == 0) {
            return null;
        }
        return items.get(0);
    }

    public History loadHistory(long id) {
        return dao.loadHistory(id);
    }

    public List<Item> findItems(ItemSearch itemSearch) {
        String searchText = itemSearch.getSearchText();
        if (searchText != null) {
//            List<Long> hits = indexSearcher.findItemIdsContainingText(searchText);
//            if (hits.size() == 0) {
//                itemSearch.setResultCount(0);
//                return Collections.<Item>emptyList();
//            }
//            itemSearch.setItemIds(hits);
        }
        return dao.findItems(itemSearch);
    }

    public void removeItem(Item item) {
//        if(item.getRelatingItems() != null) {
//            for(ItemItem itemItem : item.getRelatingItems()) {
//                removeItemItem(itemItem);
//            }
//        }
//        if(item.getRelatedItems() != null) {
//            for(ItemItem itemItem : item.getRelatedItems()) {
//                removeItemItem(itemItem);
//            }
//        }
        dao.removeItem(item);
    }

    public void removeItemItem(ItemItem itemItem) {
        dao.removeItemItem(itemItem);
    }

    public int loadCountOfRecordsHavingFieldNotNull(Space space, Field field) {
        return dao.loadCountOfRecordsHavingFieldNotNull(space, field);
    }

    public int bulkUpdateFieldToNull(Space space, Field field) {
        return dao.bulkUpdateFieldToNull(space, field);
    }

    public int loadCountOfRecordsHavingFieldWithValue(Space space, Field field, int optionKey) {
        return dao.loadCountOfRecordsHavingFieldWithValue(space, field, optionKey);
    }

    public int bulkUpdateFieldToNullForValue(Space space, Field field, int optionKey) {
        return dao.bulkUpdateFieldToNullForValue(space, field, optionKey);
    }

    public int loadCountOfRecordsHavingStatus(Space space, int status) {
        return dao.loadCountOfRecordsHavingStatus(space, status);
    }

    public int bulkUpdateStatusToOpen(Space space, int status) {
        return dao.bulkUpdateStatusToOpen(space, status);
    }

    public int bulkUpdateRenameSpaceRole(Space space, String oldRoleKey, String newRoleKey) {
        return dao.bulkUpdateRenameSpaceRole(space, oldRoleKey, newRoleKey);
    }

    public int bulkUpdateDeleteSpaceRole(Space space, String roleKey) {
        return dao.bulkUpdateDeleteSpaceRole(space, roleKey);
    }

    // =========  Acegi UserDetailsService implementation ==========
    public UserDetails loadUserByUsername(String loginName) {
        List<User> users = null;
        if (loginName.indexOf("@") != -1) {
            users = dao.findUsersByEmail(loginName);
        } else {
            users = dao.findUsersByLoginName(loginName);
        }
        if (users.size() == 0) {
            throw new UsernameNotFoundException("User not found for '" + loginName + "'");
        }
        logger.debug("loadUserByUserName success for '" + loginName + "'");
        User user = users.get(0);
        // if some spaces have guest access enabled, allocate these spaces as well
        Set<Space> userSpaces = user.getSpaces();
        for(Space s : findSpacesWhereGuestAllowed()) {
            if(!userSpaces.contains(s)) {
                user.addSpaceWithRole(s, "ROLE_GUEST");
                
            }
        }
        for(UserSpaceRole usr : user.getSpaceRoles()) {
            logger.debug("UserSpaceRole: " + usr);
            // this is a hack, the effect of the next line would be to
            // override hibernate lazy loading and get the space and associated metadata.
            // since this only happens only once on authentication and simplifies a lot of
            // code later because the security principal is "fully prepared",
            // this is hopefully pardonable.  The downside is that there may be as many extra db hits
            // as there are spaces allocated for the user.  Hibernate caching should alleviate this
            usr.isAbleToCreateNewItem();
        }        
//        return user;
        return null;
    }

    public User loadUser(long id) {
        return dao.loadUser(id);
    }

    public User loadUser(String loginName) {
        List<User> users = dao.findUsersByLoginName(loginName);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    public void storeUser(User user) {
        dao.storeUser(user);
    }

    public void storeUser(User user, String password, boolean sendNotifications) {
        if (password == null) {
            password = generatePassword();
        }
        user.setPassword(encodeClearText(password));
        storeUser(user);
//        if(sendNotifications) {
//            mailSender.sendUserPassword(user, password);
//        }
    }

    public void removeUser(User user) {
        dao.removeUser(user);
    }

    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    public List<User> findUsersMatching(String searchText, String searchOn) {
        return dao.findUsersMatching(searchText, searchOn);
    }
    
    public List<User> findUsersForSpace(long spaceId) {
        return dao.findUsersForSpace(spaceId);
    }

    public List<UserSpaceRole> findUserRolesForSpace(long spaceId) {
        return dao.findUserRolesForSpace(spaceId);
    }

    public List<User> findUsersWithRoleForSpace(long spaceId, String roleKey) {
        return dao.findUsersWithRoleForSpace(spaceId, roleKey);
    }

    public List<User> findUsersForUser(User user) {
        Set<Space> spaces = user.getSpaces();
        if(spaces.size() == 0) {
            // this will happen when a user has no spaces allocated
            return Collections.emptyList();
        }
        // must be a better way to make this unique?
        List<User> users = dao.findUsersForSpaceSet(spaces);
        Set<User> userSet = new LinkedHashSet<User>(users);
        return new ArrayList<User>(userSet);
    }

    public List<User> findUnallocatedUsersForSpace(long spaceId) {
        List<User> users = findAllUsers();
        Space space = loadSpace(spaceId);
        Set<String> roleKeys = space.getMetadata().getRoles().keySet();
        Set<UserSpaceRole> userSpaceRoles = new HashSet(findUserRolesForSpace(spaceId));
        List<User> unallocated = new ArrayList<User>();
        // spaces have multiple roles, find users that have not been
        // allocated all roles for the given space
        for(User user : users) {
            for(String roleKey : roleKeys) {
                UserSpaceRole usr = new UserSpaceRole(user, space, roleKey);
                if(!userSpaceRoles.contains(usr)) {
                    unallocated.add(user);
                    break;
                }
            }
        }
        return unallocated;
    }

    public int loadCountOfHistoryInvolvingUser(User user) {
        return dao.loadCountOfHistoryInvolvingUser(user);
    }

    //==========================================================================

    public CountsHolder loadCountsForUser(User user) {
        return dao.loadCountsForUser(user);
    }

    public Counts loadCountsForUserSpace(User user, Space space) {
        return dao.loadCountsForUserSpace(user, space);
    }

    //==========================================================================

    public void storeUserSpaceRole(User user, Space space, String roleKey) {
        user.addSpaceWithRole(space, roleKey);
        dao.storeUser(user);
    }

    public void removeUserSpaceRole(UserSpaceRole userSpaceRole) {
        User user = userSpaceRole.getUser();
        user.removeSpaceWithRole(userSpaceRole.getSpace(), userSpaceRole.getRoleKey());
        // dao.storeUser(user);
        dao.removeUserSpaceRole(userSpaceRole);
    }

    public UserSpaceRole loadUserSpaceRole(long id) {
        return dao.loadUserSpaceRole(id);
    }

    //==========================================================================

    public Space loadSpace(long id) {
        return dao.loadSpace(id);
    }

    public Space loadSpace(String prefixCode) {
        List<Space> spaces = dao.findSpacesByPrefixCode(prefixCode);
        if (spaces.size() == 0) {
            return null;
        }
        return spaces.get(0);
    }

    public void storeSpace(Space space) {        
        dao.storeSpace(space);
    }

    public List<Space> findAllSpaces() {
        return dao.findAllSpaces();
    }

    public List<Space> findSpacesWhereGuestAllowed() {
        return dao.findSpacesWhereGuestAllowed();
    }

    public List<Space> findUnallocatedSpacesForUser(long userId) {
        List<Space> spaces = findAllSpaces();
        User user = loadUser(userId);
        Set<UserSpaceRole> usrs = user.getUserSpaceRoles();
        List<Space> unallocated = new ArrayList<Space>();
        // spaces have multiple roles, find spaces that have roles
        // not yet assigned to the user
        for(Space space : spaces) {
            for(String roleKey : space.getMetadata().getRoles().keySet()) {
                UserSpaceRole usr = new UserSpaceRole(user, space, roleKey);
                if(!usrs.contains(usr)) {
                    unallocated.add(space);
                    break;
                }
            }
        }
        return unallocated;
    }

    public void removeSpace(Space space) {
        logger.info("proceeding to delete space: " + space);
        dao.bulkUpdateDeleteSpaceRole(space, null);
        dao.bulkUpdateDeleteItemsForSpace(space);
        dao.removeSpace(space);
        logger.info("successfully deleted space");
    }

    //==========================================================================

    public void storeMetadata(Metadata metadata) {
        dao.storeMetadata(metadata);
    }

    public Metadata loadMetadata(long id) {
        return dao.loadMetadata(id);
    }

    //==========================================================================

    public Map<String, String> loadAllConfig() {
        List<Config> list = dao.findAllConfig();
        Map<String, String> allConfig = new HashMap<String, String>(list.size());
        for (Config c : list) {
            allConfig.put(c.getParam(), c.getValue());
        }
        return allConfig;
    }

    // TODO must be some nice generic way to do this
    public void storeConfig(Config config) {        
        dao.storeConfig(config);
        if(config.isMailConfig()) {
//            initMailSender(loadAllConfig());
        } else if(config.isLocaleConfig()) {
            initDefaultLocale(config.getValue());
        } else if(config.isAttachmentConfig()) {
            initAttachmentMaxSize(config.getValue());
        } else if(config.isSessionTimeoutConfig()) {
            initSessionTimeout(config.getValue());
        }
    }

    public String loadConfig(String param) {
        Config config = dao.loadConfig(param);
        if (config == null) {
            return null;
        }
        String value = config.getValue();
        if (value == null || value.trim().equals("")) {
            return null;
        }
        return value;
    }

    //========================================================

    public void rebuildIndexes() {
        clearIndexes();
        List<AbstractItem> items = dao.findAllItems();
        for (AbstractItem item : items) {
            indexer.index(item);
        }
    }

    public List<AbstractItem> findAllItems() {
        // this returns all Item and all History records for indexing
        return dao.findAllItems();
    }

    public void clearIndexes() {        
        File file = new File(jtracHome + "/indexes");
        for (File f : file.listFiles()) {
            f.delete();
        }        
    }

    public void index(AbstractItem item) {
        indexer.index(item);
    }

    public boolean validateTextSearchQuery(String text) {
//        return indexSearcher.validateQuery(text);
    	return false;
    }

    //==========================================================================
    
    public void executeHourlyTask() {
        logger.debug("hourly task called");
    }
    
    /* configured to be called every five minutes */
    public void executePollingTask() {
        logger.debug("polling task called");
    }
    
    //==========================================================================

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public String getReleaseTimestamp() {
        return releaseTimestamp;
    }

}
