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

import java.util.List;
import java.util.Map;

import main.java.info.jtrac.domain.AbstractItem;
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
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.domain.UserSpaceRole;

import org.acegisecurity.userdetails.UserDetailsService;
import org.apache.wicket.markup.html.form.upload.FileUpload;

/**
 * Jtrac main business interface (Service Layer)
 */
public interface Jtrac extends UserDetailsService {
    void storeItem(Item item, FileUpload fileUpload);
    void updateItem(Item item, User user);
    void storeHistoryForItem(long itemId, History history, FileUpload fileUpload);
    Item loadItem(long id);
    Item loadItemByRefId(String refId);
    History loadHistory(long id);
    List<Item> findItems(ItemSearch itemSearch);
    List<AbstractItem> findAllItems();
    void removeItem(Item item);
    void removeItemItem(ItemItem itemItem);
    //========================================================
    int loadCountOfRecordsHavingFieldNotNull(Space space, Field field);
    int bulkUpdateFieldToNull(Space space, Field field);
    int loadCountOfRecordsHavingFieldWithValue(Space space, Field field, int optionKey);
    int bulkUpdateFieldToNullForValue(Space space, Field field, int optionKey);
    int loadCountOfRecordsHavingStatus(Space space, int status);
    int bulkUpdateStatusToOpen(Space space, int status);
    int bulkUpdateRenameSpaceRole(Space space, String oldRoleKey, String newRoleKey);
    int bulkUpdateDeleteSpaceRole(Space space, String roleKey);
    //========================================================
    void storeUser(User user);
    void storeUser(User user, String password, boolean sendNotifications);
    void removeUser(User user);    
    User loadUser(long id);
    User loadUser(String loginName);
    List<User> findAllUsers();
    List<User> findUsersMatching(String searchText, String searchOn);
    List<User> findUsersForSpace(long spaceId);
    List<UserSpaceRole> findUserRolesForSpace(long spaceId);
    List<User> findUsersWithRoleForSpace(long spaceId, String roleKey);
    List<User> findUsersForUser(User user);
    List<User> findUnallocatedUsersForSpace(long spaceId);
    int loadCountOfHistoryInvolvingUser(User user);
    //========================================================
    CountsHolder loadCountsForUser(User user);
    Counts loadCountsForUserSpace(User user, Space space);
    //========================================================
    void storeSpace(Space space);
    Space loadSpace(long id);
    Space loadSpace(String prefixCode);
    List<Space> findAllSpaces();
    List<Space> findSpacesWhereGuestAllowed();
    List<Space> findUnallocatedSpacesForUser(long userId);
    void removeSpace(Space space);
    //========================================================
    void storeUserSpaceRole(User user, Space space, String roleKey);
    UserSpaceRole loadUserSpaceRole(long id);
    void removeUserSpaceRole(UserSpaceRole userSpaceRole);
    //========================================================
    void storeMetadata(Metadata metadata);
    Metadata loadMetadata(long id);
    //========================================================
    String generatePassword();
    String encodeClearText(String clearText);
    Map<String, String> getLocales();
    String getDefaultLocale();
    String getJtracHome();
    int getAttachmentMaxSizeInMb();
    int getSessionTimeoutInMinutes();
    //========================================================
    Map<String, String> loadAllConfig();
    void storeConfig(Config config);
    String loadConfig(String param);
    //========================================================
    void rebuildIndexes();
    void index(AbstractItem item);
    void clearIndexes();
    boolean validateTextSearchQuery(String text);
    //========================================================
    void executeHourlyTask();
    void executePollingTask();
    //========================================================
    String getReleaseVersion();
    String getReleaseTimestamp();
    
}
