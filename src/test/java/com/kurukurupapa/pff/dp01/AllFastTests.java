package com.kurukurupapa.pff.dp01;

import org.junit.experimental.categories.Categories;

import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.kurukurupapa.pff.test.SlowTests;

@RunWith(Categories.class)
@SuiteClasses({ AccessoryFitnessTest.class, AccessoryRankingTest.class,
		Dp01UserDataTest.class, Dp01Test.class, FitnessCalculatorTest.class,
		MagicRankingTest.class, MemoriaRankingTest.class,
		MemoriaRankingUsereDataTest.class, MemoriaTest.class, MemoTest.class,
		WeaponFitnessTest.class, WeaponRankingTest.class })
@ExcludeCategory(SlowTests.class)
public class AllFastTests {

}
