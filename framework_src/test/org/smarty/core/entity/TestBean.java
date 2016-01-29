package org.smarty.core.entity;

import java.text.MessageFormat;
import org.smarty.core.io.ModelSerializable;

/**
 * Created by Administrator on 2016/1/29.
 */
public class TestBean implements ModelSerializable {
	private int result;
	private int generatedKey;
	private int id;
	private String testName;
	private String testVal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestVal() {
		return testVal;
	}

	public void setTestVal(String testVal) {
		this.testVal = testVal;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getGeneratedKey() {
		return generatedKey;
	}

	public void setGeneratedKey(int generatedKey) {
		this.generatedKey = generatedKey;
	}

	@Override
	public String toString() {
		String f = "[result={0},generatedKey={1},id={2},testName={3},testVal={4}]";
		Object[] args = new Object[]{result, generatedKey, id, testName, testVal};
		return MessageFormat.format(f, args);
	}
}