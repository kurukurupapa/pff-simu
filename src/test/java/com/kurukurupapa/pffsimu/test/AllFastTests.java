package com.kurukurupapa.pffsimu.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ com.kurukurupapa.pffsimu.domain.AllFastTests.class,
		com.kurukurupapa.pffsimu.domain.fitness.AllFastTests.class,
		com.kurukurupapa.pffsimu.domain.memoria.AllFastTests.class,
		com.kurukurupapa.pffsimu.domain.partyfinder.impl1.AllFastTests.class,
		com.kurukurupapa.pffsimu.domain.partyfinder.impl2.AllFastTests.class,
		com.kurukurupapa.pffsimu.domain.ranking.AllFastTests.class })
public class AllFastTests {

}
