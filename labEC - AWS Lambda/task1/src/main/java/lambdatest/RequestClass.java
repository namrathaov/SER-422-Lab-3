package lambdatest;

public class RequestClass {
    String operator;
    float value1;
    float value2;

    public RequestClass() {
    }

    public RequestClass(String operator, float value1, float value2) {
        this.operator = operator;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public float getValue1() {
        return value1;
    }

    public void setValue1(float value1) {
        this.value1 = value1;
    }

    public float getValue2() {
        return value2;
    }

    public void setValue2(float value2) {
        this.value2 = value2;
    }
}
