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

package main.java.info.jtrac.wicket;

import main.java.info.jtrac.Jtrac;
import main.java.info.jtrac.domain.ItemSearch;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.User;

import org.apache.wicket.Component;
import org.apache.wicket.model.StringResourceModel;

/**
 * this common class helps to make things easier for the sub-classes
 * of BasePage and BasePanel to perform common routines and keeps
 * code in one place, reducing duplication
 */
public class ComponentUtils {           
    
    public static Jtrac getJtrac(Component c) {
        return ((JtracApplication) c.getApplication()).getJtrac();
    }          
    
    public static User getPrincipal(Component c) {
        return ((JtracSession) c.getSession()).getUser();
    }
    
    public static void setCurrentSpace(Component c, Space space) {
        ((JtracSession) c.getSession()).setCurrentSpace(space);
    }      
    
    public static Space getCurrentSpace(Component c) {
        return ((JtracSession) c.getSession()).getCurrentSpace();
    }     
    
    public static void setCurrentItemSearch(Component c, ItemSearch itemSearch) {
        ((JtracSession) c.getSession()).setItemSearch(itemSearch);
    }      
    
    public static ItemSearch getCurrentItemSearch(Component c) {
        return ((JtracSession) c.getSession()).getItemSearch();
    }    
    
    /**
     * conditional flip of session if same user id
     */
    public static void refreshPrincipal(Component c, User user) {
        if(user.getId() == getPrincipal(c).getId()) {
            refreshPrincipal(c);
        }
    }
    
    public static void refreshPrincipal(Component c) {        
        User temp = getJtrac(c).loadUser(getPrincipal(c).getId());
        // loadUserByUsername forces hibernate eager load
        ((JtracSession) c.getSession()).setUser((User) getJtrac(c).loadUserByUsername(temp.getLoginName()));        
    }
    
    /**
     * localization helper
     */
    public static String localize(Component c, String key) {
        return c.getLocalizer().getString(key, null);
    }
    
    public static String localize(Component c, String key, Object... params) {
        // integer params cause problems, go with String only
        StringResourceModel m = new StringResourceModel(key, null, null, params);
        m.setLocalizer(c.getLocalizer());
        return m.getString();
    }    
    
}
