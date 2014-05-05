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

import main.java.info.jtrac.domain.ItemSearch;

import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * item search form page
 */
public class ItemSearchFormPage extends BasePage {        
        
    public ItemSearchFormPage() {        
        add(new ItemSearchFormPanel("panel"));
        add(new WebMarkupContainer("relate").setVisible(false));
    }       
    
    public ItemSearchFormPage(ItemSearch itemSearch) {                        
        add(new ItemSearchFormPanel("panel", itemSearch));
        setCurrentItemSearch(itemSearch);
        add(new ItemRelatePanel("relate", false));
    }    
    
}
