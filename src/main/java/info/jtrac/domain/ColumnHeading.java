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
import java.util.List;
import java.util.Map;

import main.java.info.jtrac.domain.FilterCriteria.Expression;
import main.java.info.jtrac.wicket.ComponentUtils;
import main.java.info.jtrac.wicket.JtracCheckBoxMultipleChoice;
import main.java.info.jtrac.wicket.yui.YuiCalendar;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.PropertyModel;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * used to render columns in the search results table
 * and also in the search filter screen
 */
public class ColumnHeading implements Serializable {
	/**
	 * Serial version ID
	 */
	private static final long serialVersionUID = -8576323592604775080L;	
	public static final String ID = "id";    
    public static final String SUMMARY = "summary";
    public static final String DETAIL = "detail";
    public static final String LOGGED_BY = "loggedBy";
    public static final String STATUS = "status";
    public static final String ASSIGNED_TO = "assignedTo";
    public static final String TIME_STAMP = "timeStamp";
    public static final String SPACE = "space";
    
    private Field field;
    private String name;
    private String label;
    private boolean visible = true;
    
    private FilterCriteria filterCriteria = new FilterCriteria();     
    
    public ColumnHeading(String name) {
        this.name = name;
        if(name.equals(DETAIL) || name.equals(SPACE)) {
            visible = false;
        }        
    }
    
    public ColumnHeading(Field field) {
        this.field = field;
        this.name = field.getName().getText();
        this.label = field.getLabel();
    }       
    
    public boolean isField() {
        return field != null;
    }
    
    public static List<ColumnHeading> getColumnHeadings(Space s) {
        List<ColumnHeading> list = new ArrayList<ColumnHeading>();
        list.add(new ColumnHeading(ID));
        list.add(new ColumnHeading(SUMMARY));        
        list.add(new ColumnHeading(DETAIL));                
        list.add(new ColumnHeading(STATUS));
        list.add(new ColumnHeading(LOGGED_BY));
        list.add(new ColumnHeading(ASSIGNED_TO));        
        for(Field f : s.getMetadata().getFieldList()) {
            list.add(new ColumnHeading(f));
        }
        list.add(new ColumnHeading(TIME_STAMP));
        return list;        
    }
    
    public static List<ColumnHeading> getColumnHeadings(User u) {
        List<ColumnHeading> list = new ArrayList<ColumnHeading>();
        list.add(new ColumnHeading(ID));
        list.add(new ColumnHeading(SPACE));        
        list.add(new ColumnHeading(SUMMARY));        
        list.add(new ColumnHeading(DETAIL));                            
        list.add(new ColumnHeading(LOGGED_BY));
        list.add(new ColumnHeading(ASSIGNED_TO));
        list.add(new ColumnHeading(TIME_STAMP));        
        return list;        
    }    
    
    public List<Expression> getValidFilterExpressions() {        
        return (List<Expression>) process(null, null);        
    }
    
    public Component getFilterUiFragment(Component c) {
        return (Fragment) process(c, null);
    }
    
    public void addRestrictions(DetachedCriteria criteria, ItemSearch itemSearch) {
        process(null, criteria);
    }
    
    // TODO use some elegant factory pattern here if possible
    // TODO reduce redundant code
    // this routine is a massive if-then that has 3 responsibilities
    // based on column type:
    // 1) return the possible expressions (equals, greater-than etc) to show on filter UI for selection
    // 2) or return the wicket ui fragment that will be shown over ajax (based on selected expression)
    // 3) or add restrictions to the hibernate detached criteria that will be used to query the database
    // putting all these things into one place, makes it easy to maintain, as the 3 responsibilities
    // are closely interdependent
    private Object process(Component c, DetachedCriteria criteria) {        
        boolean forFragment = c != null;        
        List<Expression> list = new ArrayList<Expression>();
        Fragment fragment = null;
        List values = filterCriteria.getValues();
        Object value = filterCriteria.getValue();
        Object value2 = filterCriteria.getValue2();
        Expression expression = filterCriteria.getExpression();
        if(isField()) {
            switch(field.getName().getType()) {
                case 1:
                case 2:
                case 3:
                    list.add(Expression.IN);                    
                    if(forFragment) {
                        fragment = new Fragment("fragParent", "multiSelect");
                        final Map<String, String> options = field.getOptions();
                        JtracCheckBoxMultipleChoice choice = new JtracCheckBoxMultipleChoice("values", new ArrayList(options.keySet()), new IChoiceRenderer() {
                            /**
							 * 
							 */
							private static final long serialVersionUID = 1L;
							public Object getDisplayValue(Object o) {
                                return options.get(o);
                            }
                            public String getIdValue(Object o, int i) {
                                return o.toString();
                            }
                        });                        
                        fragment.add(choice);
                        choice.setModel(new PropertyModel(this, "filterCriteria.values"));                        
                    }
                    if(filterHasValueList(criteria)) {
                        List<Integer> keys = new ArrayList<Integer>(values.size());
                        for(Object o : values) {
                            keys.add(new Integer(o.toString()));
                        }
                        criteria.add(Restrictions.in(name, keys));
                    }
                    break; // drop down list
                case 4: // decimal number
                    list.add(Expression.EQ);
                    list.add(Expression.NOT_EQ);
                    list.add(Expression.GT);
                    list.add(Expression.LT);
                    list.add(Expression.BETWEEN);
                    if(forFragment) {
                        fragment = new Fragment("fragParent", "textField");
                        TextField textField = new TextField("value", Double.class);
                        textField.setModel(new PropertyModel(this, "filterCriteria.value"));
                        fragment.add(textField);
                        if(expression == Expression.BETWEEN) {
                            TextField textField2 = new TextField("value2", Double.class);
                            textField.setModel(new PropertyModel(this, "filterCriteria.value2"));
                            fragment.add(textField2);                            
                        } else {
                            fragment.add(new WebMarkupContainer("value2").setVisible(false));
                        }
                    }
                    if(filterHasValue(criteria)) {
                        switch(expression) {
                            case EQ: criteria.add(Restrictions.eq(name, value)); break;
                            case NOT_EQ: criteria.add(Restrictions.not(Restrictions.eq(name, value))); break;
                            case GT: criteria.add(Restrictions.gt(name, value)); break;
                            case LT: criteria.add(Restrictions.lt(name, value)); break;
                            case BETWEEN: 
                                criteria.add(Restrictions.gt(name, value));
                                criteria.add(Restrictions.lt(name, value2));
                                break;
                            default:                            
                        }                        
                    }                    
                    break;
                case 6: // date
                    list.add(Expression.EQ);
                    list.add(Expression.NOT_EQ);
                    list.add(Expression.GT);
                    list.add(Expression.LT);
                    list.add(Expression.BETWEEN);
                    if(forFragment) {
                        fragment = new Fragment("fragParent", "dateField");                        
                        YuiCalendar calendar = new YuiCalendar("value", new PropertyModel(this, "filterCriteria.value"), false);                                                
                        fragment.add(calendar);
                        if(filterCriteria.getExpression() == Expression.BETWEEN) {
                            YuiCalendar calendar2 = new YuiCalendar("value2", new PropertyModel(this, "filterCriteria.value2"), false);                                                
                            fragment.add(calendar2);                            
                        } else {
                            fragment.add(new WebMarkupContainer("value2").setVisible(false));
                        }
                    }
                    if(filterHasValue(criteria)) {
                        switch(expression) {
                            case EQ: criteria.add(Restrictions.eq(name, value)); break;
                            case NOT_EQ: criteria.add(Restrictions.not(Restrictions.eq(name, value))); break;
                            case GT: criteria.add(Restrictions.gt(name, value)); break;
                            case LT: criteria.add(Restrictions.lt(name, value)); break;
                            case BETWEEN: 
                                criteria.add(Restrictions.gt(name, value));
                                criteria.add(Restrictions.lt(name, value2));
                                break;
                            default:                            
                        }                        
                    }                     
                    break;
                case 5: // free text
                    list.add(Expression.CONTAINS);
                    if(forFragment) {
                        fragment = new Fragment("fragParent", "textField");
                        TextField textField = new TextField("value", String.class);
                        textField.setModel(new PropertyModel(this, "filterCriteria.value"));
                        fragment.add(textField);
                        fragment.add(new WebMarkupContainer("value2").setVisible(false));
                    }
                    if(filterHasValue(criteria)) {
                        criteria.add(Restrictions.ilike(name, (String) value, MatchMode.ANYWHERE));
                    }
                    break;
                default:
                    throw new RuntimeException("Unknown Column Heading " + name);
            }
        } else {
            if(name.equals(ID)) {
                list.add(Expression.EQ);
                if(forFragment) {
                    fragment = new Fragment("fragParent", "textField");
                        TextField textField = new TextField("value", String.class);
                        textField.setModel(new PropertyModel(this, "filterCriteria.value"));
                        fragment.add(textField);
                        fragment.add(new WebMarkupContainer("value2").setVisible(false));
                }
                // should never come here for criteria: see ItemSearch#getRefId()
            } else if(name.equals(SUMMARY)) {
                list.add(Expression.CONTAINS);
                if(forFragment) {
                    fragment = new Fragment("fragParent", "textField");
                        TextField textField = new TextField("value", String.class);
                        textField.setModel(new PropertyModel(this, "filterCriteria.value"));
                        fragment.add(textField);
                        fragment.add(new WebMarkupContainer("value2").setVisible(false));
                }
                if(filterHasValue(criteria)) {
                    criteria.add(Restrictions.ilike(name, (String) value, MatchMode.ANYWHERE));
                }                
            } else if(name.equals(DETAIL)) {
                list.add(Expression.CONTAINS);
                if(forFragment) {
                    fragment = new Fragment("fragParent", "textField");
                        TextField textField = new TextField("value", String.class);
                        textField.setModel(new PropertyModel(this, "filterCriteria.value"));
                        fragment.add(textField);
                        fragment.add(new WebMarkupContainer("value2").setVisible(false));
                }
                // should never come here for criteria: see ItemSearch#getSearchText()
            } else if(name.equals(STATUS)) {
                list.add(Expression.IN);                
                if(forFragment) {
                    fragment = new Fragment("fragParent", "multiSelect");                    
                    final Map<Integer, String> options = ComponentUtils.getCurrentSpace(c).getMetadata().getStates();
                    options.remove(State.NEW);
                    JtracCheckBoxMultipleChoice choice = new JtracCheckBoxMultipleChoice("values", new ArrayList(options.keySet()), new IChoiceRenderer() {
                        /**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						public Object getDisplayValue(Object o) {
                            return options.get(o);
                        }
                        public String getIdValue(Object o, int i) {
                            return o.toString();
                        }
                    });                    
                    fragment.add(choice);
                    choice.setModel(new PropertyModel(this, "filterCriteria.values"));
                }
                if(filterHasValueList(criteria)) {
                    criteria.add(Restrictions.in(name, filterCriteria.getValues()));
                }                
            } else if(name.equals(ASSIGNED_TO) || name.equals(LOGGED_BY)) {
                list.add(Expression.IN);                
                if(forFragment) {
                    fragment = new Fragment("fragParent", "multiSelect");
                    List<User> users = null;
                    Space s = ComponentUtils.getCurrentSpace(c);
                    if(s == null) {
                        User u = ComponentUtils.getPrincipal(c);
                        users = ComponentUtils.getJtrac(c).findUsersForUser(u);
                    } else {
                        users = ComponentUtils.getJtrac(c).findUsersForSpace(s.getId());
                    }
                    JtracCheckBoxMultipleChoice choice = new JtracCheckBoxMultipleChoice("values", users, new IChoiceRenderer() {
                        /**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						public Object getDisplayValue(Object o) {
                            return ((User) o).getName();
                        }
                        public String getIdValue(Object o, int i) {
                            return ((User) o).getId() + "";
                        }
                    });
                    fragment.add(choice);
                    choice.setModel(new PropertyModel(this, "filterCriteria.values"));
                }
                if(filterHasValueList(criteria)) {
                    criteria.add(Restrictions.in(name, filterCriteria.getValues()));
                }                
            } else if(name.equals(TIME_STAMP)) {
                list.add(Expression.BETWEEN);
                list.add(Expression.GT);
                list.add(Expression.LT);                
                if(forFragment) {
                    fragment = new Fragment("fragParent", "dateField");                    
                    YuiCalendar calendar = new YuiCalendar("value", new PropertyModel(this, "filterCriteria.value"), false);                    
                    fragment.add(calendar);
                    if(expression == Expression.BETWEEN) {
                        YuiCalendar calendar2 = new YuiCalendar("value2", new PropertyModel(this, "filterCriteria.value2"), false);                                                
                        fragment.add(calendar2);                            
                    }  else {
                        fragment.add(new WebMarkupContainer("value2").setVisible(false));
                    }                   
                }
                if(filterHasValue(criteria)) {
                    switch(expression) {
                        case GT: criteria.add(Restrictions.gt(name, value)); break;
                        case LT: criteria.add(Restrictions.lt(name, value)); break;
                        case BETWEEN: 
                            criteria.add(Restrictions.gt(name, value));
                            criteria.add(Restrictions.lt(name, value2));
                            break;
                        default:                            
                    }                        
                }                 
            } else if(name.equals(SPACE)) {
                list.add(Expression.IN);
                if(forFragment) {
                    fragment = new Fragment("fragParent", "multiSelect");
                    List<Space> spaces = new ArrayList(ComponentUtils.getPrincipal(c).getSpaces());
                    JtracCheckBoxMultipleChoice choice = new JtracCheckBoxMultipleChoice("values", spaces, new IChoiceRenderer() {
                        /**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						public Object getDisplayValue(Object o) {
                            return ((Space) o).getName();
                        }
                        public String getIdValue(Object o, int i) {
                            return ((Space) o).getId() + "";
                        }
                    });
                    fragment.add(choice);
                    choice.setModel(new PropertyModel(this, "filterCriteria.values"));
                }
                // should never come here for criteria: see ItemSearch#getSelectedSpaces()
            } else {
                throw new RuntimeException("Unknown Column Heading " + name);
            }
        }
        if(forFragment) {
            return fragment;            
        } else {
            return list;
        }
    }
    
    private boolean filterHasValueList(DetachedCriteria criteria) {
        if(criteria != null) {
            if(filterCriteria.getExpression() != null
                && filterCriteria.getValues() != null 
                && filterCriteria.getValues().size() > 0) {
                return true;
            } else {
                filterCriteria.setExpression(null);
                return false;
            }
        }
        return false;
    }
    
    private boolean filterHasValue(DetachedCriteria criteria) {  
        if(criteria != null) {
            Object value = filterCriteria.getValue();
            if(filterCriteria.getExpression() != null && value != null && value.toString().trim().length() > 0) {
                return true;
            } else {
                filterCriteria.setExpression(null);                                
                return false;
            }                       
        }
        return false;
    }  
    
    //==========================================================================
    
    public Field getField() {
        return field;
    }
    
    public String getName() {
        return name;
    }    

    public String getLabel() {
        return label;
    }

    public FilterCriteria getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(FilterCriteria filterCriteria) {
        this.filterCriteria = filterCriteria;
    }    

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ColumnHeading)) {
            return false;
        }
        final ColumnHeading ch = (ColumnHeading) o;
        return ch.getName().equals(name);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name [").append(name);        
        sb.append("]; filterCriteria [").append(filterCriteria);
        sb.append("]");
        return sb.toString();
    }
    
}
