package lambdatest;

public class ResponseClass {
    String body;
    int statusCode;
    String isBase64Encoded;

    public ResponseClass() {
    }

    public ResponseClass(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        this.isBase64Encoded = "true";
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getIsBase64Encoded() {
        return isBase64Encoded;
    }

    public void setIsBase64Encoded(String isBase64Encoded) {
        this.isBase64Encoded = isBase64Encoded;
    }
}
