package core;

import org.junit.Assert;
import org.junit.Test;

import conf.Application;

public class ApplicationTest {

	private Application app = new Application();

	@Test
	public void testApplication() {
		try{
		app.executer();
		}catch(Exception exp){
			Assert.fail();
		}
	}
}
