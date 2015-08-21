package com.kurukurupapa.pff.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import com.kurukurupapa.pff.domain.AppException;
import com.kurukurupapa.pff.dp01.MemoriaFitness;
import com.kurukurupapa.pff.dp01.Party;

public class BaseTestCase {
	@Rule
	public TestName mTestName = new TestName();

	@Before
	public void setUp() throws Exception {
		printSep();
		print(getClass().getSimpleName() + " #" + mTestName.getMethodName());
	}

	protected void assertParty(String expected, List<Party> actual) {
		org.junit.Assert.assertEquals(expected, StringUtils.join(actual, "\n")
				+ "\n");
	}

	protected void assertParty(String expected, Party actual) {
		org.junit.Assert.assertEquals(expected, actual.toString() + "\n");
	}

	protected void assertPartyAsMultiLine(String expected, Party actual) {
		assertEquals(sortMultiLine(expected),
				sortMultiLine(actual.toMultiLineString()));
	}

	private String sortMultiLine(String text) {
		String[] lines = text.split("\n");
		Arrays.sort(lines);
		return StringUtils.join(lines, "\n");
	}

	protected void assertMemoriaFitness(String expected,
			List<MemoriaFitness> actual) {
		org.junit.Assert.assertEquals(expected, StringUtils.join(actual, "\n")
				+ "\n");
	}

	protected String readExpectedFile() {
		return readExpectedFile(getClass().getSimpleName(),
				mTestName.getMethodName());
	}

	protected String readExpectedFile(String classSimpleName) {
		return readExpectedFile(classSimpleName, mTestName.getMethodName());
	}

	protected String readExpectedFile(String classSimpleName, String methodName) {
		StringBuilder sb = new StringBuilder();
		String path = classSimpleName + "_" + methodName + ".txt";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					getClass().getResourceAsStream(path)));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			throw new AppException("テスト期待値ファイルの読み込みに失敗しました。パス=" + path, e);
		}
		return sb.toString();
	}

	protected void printSep() {
		print(StringUtils.repeat('-', 50));
	}

	protected void print(String msg) {
		System.out.println(msg);
	}
}
