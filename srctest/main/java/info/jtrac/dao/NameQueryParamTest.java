package main.java.info.jtrac.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class NameQueryParamTest {
	@Test
	public void testSort() {
		List<NameQueryParam> list = new ArrayList<NameQueryParam>();
		list.add(new NameQueryParam(2,"varName","voorbeeld"));
		list.add(new NameQueryParam(3,"varName","voorbeeld"));
		list.add(new NameQueryParam(1,"varName","voorbeeld"));
		list.add(new NameQueryParam(5,"varName","voorbeeld"));
		Collections.sort(list);
		for(NameQueryParam nq : list){
			System.out.println(nq);
		}
	}

}
