package ru.gpb.ccl.tools.nexus.explorer.tools;

public class Validator {

    public static String versionRegex = "([\\d+{1,3}\\.A-Za-z\\-]+)";

    //(c) - dependency constraint
    //(*) - dependencies omitted (listed previously)
    //(n) - Not resolved (configuration is not meant to be resolved)
    public static boolean isVersionValid(String version) {
        return version != null && !(version.equals(LEGEND.NOT_RESOLVED.value) || version.equals(LEGEND.DEPENDENCY_CONSTRAINT.value)) && version.matches(versionRegex);
    }

    public enum LEGEND {
        DEPENDENCY_CONSTRAINT("c"),
        DEPENDENCY_OMITTED("*"),
        NOT_RESOLVED("n");

        private final String value;

        LEGEND(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
