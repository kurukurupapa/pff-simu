package com.kurukurupapa.pffsimu.domain.fitness;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.kurukurupapa.pffsimu.test.SlowTests;

@RunWith(Categories.class)
@SuiteClasses({ AccessoryFitnessTest.class, FitnessCalculatorTest.class,
		WeaponFitnessTest.class })
@ExcludeCategory(SlowTests.class)
public class AllFastTests {

}
