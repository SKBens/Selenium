package utils;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
	private static Map<String, Object> scenarioContext = new HashMap<>();

	public static void setContext(Context key, Object value) {
		scenarioContext.put(key.toString(), value);
	}

	public static String getContext(Context key) {
		return (String) scenarioContext.get(key.toString());
	}

	public Boolean isContains(Context key) {
		return scenarioContext.containsKey(key.toString());
	}
}
