package com.kurukurupapa.pff.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ com.kurukurupapa.pff.dp01.AllTests.class,
		com.kurukurupapa.pff.partyfinder2.AllTests.class,
		com.kurukurupapa.pff.ranking.AllTests.class })
public class AllTests {

}
