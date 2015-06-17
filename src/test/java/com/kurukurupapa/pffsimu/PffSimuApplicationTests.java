package com.kurukurupapa.pffsimu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kurukurupapa.pffsimu.PffSimuApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PffSimuApplication.class)
@WebAppConfiguration
public class PffSimuApplicationTests {

	@Test
	public void contextLoads() {
	}

}
