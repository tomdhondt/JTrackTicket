package main.java.info.jtrac.mail;
import org.junit.Test;

public class EmailTest {
	@Test
	public void testSendEmail() {
		Email email = new Email();
		email.sendItemStatusMail(null);
	}
}
