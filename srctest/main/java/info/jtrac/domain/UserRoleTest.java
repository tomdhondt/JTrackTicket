package main.java.info.jtrac.domain;

import org.junit.Test;

public class UserRoleTest {
	@Test
	public void test() {
		for(UserRole u : UserRole.getValues()){
			System.out.println(u.getCaption() + " - " + u.getRole());
		}
	}
}
