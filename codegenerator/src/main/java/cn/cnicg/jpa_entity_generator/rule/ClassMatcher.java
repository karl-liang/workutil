package cn.cnicg.jpa_entity_generator.rule;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public interface ClassMatcher {
	String getClassName();
	List<String> getClassNames();
	default boolean matches(String className) {
		if (StringUtils.isEmpty(getClassName()) && (getClassNames() == null || getClassNames().size() == 0)) {
			return true;
		}
		return (getClassName() != null && getClassName().equals(className))
				|| (getClassNames() != null && getClassNames().contains(className));
	}
}