package cn.cnicg.sdgenerator.support.maker.values;

import static cn.cnicg.sdgenerator.support.maker.values.CommonValues.SPACE;

/**
 * Created by carlos on 08/04/17.
 */
public enum ScopeValues {

    PUBLIC("public" + SPACE),
    PRIVATE("private" + SPACE);

    private String value;

    ScopeValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
