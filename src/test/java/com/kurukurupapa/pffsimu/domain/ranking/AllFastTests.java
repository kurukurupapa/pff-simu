package com.kurukurupapa.pffsimu.domain.ranking;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.kurukurupapa.pffsimu.test.SlowTests;

@RunWith(Categories.class)
@SuiteClasses({ AccessoryRankingTest.class, MagicRankingTest.class,
		MemoriaRankingTest.class, MemoriaRankingUserDataTest.class,
		WeaponRankingTest.class })
@ExcludeCategory(SlowTests.class)
public class AllFastTests {

}
