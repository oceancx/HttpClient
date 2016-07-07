package com.oceancx.task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) throws ParseException {
		new Test();
	}

	public Test() {

		String a = null;
		List<String> list = new ArrayList<String>();
		list.add(a);

		list.add(a);
		a = "ssdf";
		list.add(a);

		System.out.println("" + list);
	}

	private void dump(String[] string) {
		for (String s : string) {
			System.out.println(s);
		}
	}

	private void changeInt(int a) {
		a = 100;
	}

	private void change(String[] strings) {
		strings[0] = "changesssss";
	}

}
