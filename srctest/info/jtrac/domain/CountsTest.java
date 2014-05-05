package info.jtrac.domain;

import main.java.info.jtrac.domain.Counts;
import junit.framework.TestCase;

public class CountsTest extends TestCase {
    
    public void testCountsLogic() {
        Counts c = new Counts(false);
        c.addAssignedToMe(Counts.ASSIGNED_TO_ME, 5);        
        assertEquals(0, c.getTotal());
        assertEquals(5, c.getAssignedToMe());
    }    
}
