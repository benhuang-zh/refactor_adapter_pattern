package refactor.adapter.xml;

public interface Adapter {
    void addValue(String value);

    Adapter add(String siblingNode);

    void addAttribute(String name, String value);
}
