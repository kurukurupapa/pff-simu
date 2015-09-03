package com.kurukurupapa.pff.ranking;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccessoryRankingTest.class, MagicRankingTest.class,
		MemoriaRankingTest.class, MemoriaRankingUserDataTest.class,
		WeaponRankingTest.class })
public class AllTests {

}
