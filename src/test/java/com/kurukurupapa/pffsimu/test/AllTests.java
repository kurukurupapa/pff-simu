package com.kurukurupapa.pffsimu.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ com.kurukurupapa.pffsimu.domain.AllTests.class,
		com.kurukurupapa.pffsimu.domain.fitness.AllTests.class,
		com.kurukurupapa.pffsimu.domain.memoria.AllTests.class,
		com.kurukurupapa.pffsimu.domain.partyfinder.impl1.AllTests.class,
		com.kurukurupapa.pffsimu.domain.partyfinder.impl2.AllTests.class,
		com.kurukurupapa.pffsimu.domain.ranking.AllTests.class })
public class AllTests {

}
