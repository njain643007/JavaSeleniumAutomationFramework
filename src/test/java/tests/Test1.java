package tests;

import java.io.IOException;

import org.testng.annotations.Test;

import common.base.BaseTest;

public class Test1 extends BaseTest {

	@Test
	public void test1() throws IOException {
		driver.get("https://google.com");
	}
}
