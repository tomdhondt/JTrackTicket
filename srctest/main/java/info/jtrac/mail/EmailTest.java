package main.java.info.jtrac.mail;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import javax.mail.search.DateTerm;

import org.junit.Test;

public class EmailTest {

	@Test
	public void testSendEmail() {
		Email email = new Email();
		email.createItemStatusMail(null, "tom.dhondt@hotmail.com", "tom_dhont@hotmail.com", Calendar.getInstance().getTime().toString());
	}

}
