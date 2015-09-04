package com.kurukurupapa.pffsimu.domain.memoria;

import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.kurukurupapa.pffsimu.test.SlowTests;

@RunWith(Suite.class)
@SuiteClasses({ MemoriaTest.class })
@ExcludeCategory(SlowTests.class)
public class AllFastTests {

}
