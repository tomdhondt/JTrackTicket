package info.jtrac.domain;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import main.java.info.jtrac.domain.Space;
import main.java.info.jtrac.domain.User;

import org.acegisecurity.GrantedAuthority;

public class UserTest extends TestCase {    
    
    public void testGetAuthoritiesFromUserSpaceRoles() {      
        
        Space s1 = new Space();
        s1.setPrefixCode("SPACE-ONE");                             

        User u = new User();
        u.setLoginName("test");        
        
        u.addSpaceWithRole(s1, "ROLE_ONE-ONE");
        u.addSpaceWithRole(s1, "ROLE_ONE-TWO");
        u.addSpaceWithRole(null, "ROLE_ADMIN");
        u.setId(1);
        
        GrantedAuthority[] gas = u.getAuthorities();
        
        Set<String> set = new HashSet<String>();
        for(GrantedAuthority ga : gas) {
            set.add(ga.getAuthority());
        }        
                
        assertEquals(4, gas.length);
        
        assertTrue(set.contains("ROLE_USER"));
        assertTrue(set.contains("ROLE_ONE-ONE_SPACE-ONE"));
        assertTrue(set.contains("ROLE_ONE-TWO_SPACE-ONE"));
        assertTrue(set.contains("ROLE_ADMIN"));
     
    }
    
    public void testCheckIfAdminForAllSpaces() {
        User u = new User();
        u.setLoginName("test");
        u.addSpaceWithRole(null, "ROLE_ADMIN");
        assertTrue(u.isAdminForAllSpaces());
    }
    
}
