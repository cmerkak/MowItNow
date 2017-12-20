package core;

import org.junit.Test;

import conf.Launcher;

public class LauncherTest {

	private Launcher launcher = new Launcher();

	@Test
	public void testApplication() {
		launcher.executer();
	}
}
