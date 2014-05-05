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

import main.java.info.jtrac.domain.Counts;
import main.java.info.jtrac.domain.ItemSearch;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.domain.UserSpaceRole;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

/**
 * panel for showing the total (un-expanded) statistics for
 * a single space, will be replaced by expanded view through ajax
 */
public class DashboardRowPanel extends BasePanel {    
    
    public DashboardRowPanel(String id, final UserSpaceRole usr, final Counts counts) {
        
        super(id);
        setOutputMarkupId(true);
        
        final Space space = usr.getSpace();
        final User user = usr.getUser();
        
        add(new Label("space", space.getName()));
        
        if(usr.isAbleToCreateNewItem()) {
            add(new Link("new") {
                public void onClick() {
                    setCurrentSpace(space);
                    setResponsePage(ItemFormPage.class);
                }
            });
        } else {
            add(new Label("new").setVisible(false));
        }

        add(new Link("search") {
            public void onClick() {
                setCurrentSpace(space);
                setResponsePage(ItemSearchFormPage.class);
            }
        });        
        
        add(new IndicatingAjaxLink("link") {
            public void onClick(AjaxRequestTarget target) {
                Counts tempCounts = counts;                
                // avoid hitting the database again if re-expanding
                if (!tempCounts.isDetailed()) {                    
                    tempCounts = getJtrac().loadCountsForUserSpace(user, space);                    
                }
                DashboardRowExpandedPanel dashboardRow = new DashboardRowExpandedPanel("dashboardRow", usr, tempCounts);
                DashboardRowPanel.this.replaceWith(dashboardRow);
                target.addComponent(dashboardRow);
            }
        });          
        
        if(user.getId() > 0) {        
            add(new Link("loggedByMe") {
                public void onClick() {
                    setCurrentSpace(space);
                    ItemSearch itemSearch = new ItemSearch(space, this);
                    itemSearch.setLoggedBy(user);
                    setCurrentItemSearch(itemSearch);
                    setResponsePage(ItemListPage.class);                    
                }
            }.add(new Label("loggedByMe", new PropertyModel(counts, "loggedByMe"))));        


            add(new Link("assignedToMe") {
                public void onClick() {
                    setCurrentSpace(space);
                    ItemSearch itemSearch = new ItemSearch(space, this);
                    itemSearch.setAssignedTo(user);
                    setCurrentItemSearch(itemSearch);
                    setResponsePage(ItemListPage.class);                    
                }
            }.add(new Label("assignedToMe", new PropertyModel(counts, "assignedToMe"))));
        } else {
            add(new WebMarkupContainer("loggedByMe").setVisible(false));
            add(new WebMarkupContainer("assignedToMe").setVisible(false));            
        }
        
        add(new Link("total") {
            public void onClick() {
                setCurrentSpace(space);
                ItemSearch itemSearch = new ItemSearch(space, this); 
                setCurrentItemSearch(itemSearch);
                setResponsePage(ItemListPage.class);                
            }
        }.add(new Label("total", new PropertyModel(counts, "total"))));
      
    }
    
}
