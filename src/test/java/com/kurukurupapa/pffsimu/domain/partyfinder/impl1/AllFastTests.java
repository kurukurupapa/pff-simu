package com.kurukurupapa.pffsimu.domain.partyfinder.impl1;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.kurukurupapa.pffsimu.test.SlowTests;

@RunWith(Categories.class)
@SuiteClasses({ Dp01Test.class, Dp01UserDataTest.class, MemoTest.class })
@ExcludeCategory(SlowTests.class)
public class AllFastTests {

}
