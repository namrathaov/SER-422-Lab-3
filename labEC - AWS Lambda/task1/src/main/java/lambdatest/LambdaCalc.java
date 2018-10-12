package lambdatest;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Arrays;

public class LambdaCalc implements RequestHandler<RequestClass, ResponseClass> {

    public ResponseClass handleRequest(RequestClass request, Context context){
        System.out.println("Operator: " + request.operator);
        System.out.println("Value 1: " + request.value1);
        System.out.println("Value 2: " + request.value2);
        String[] validOperators = {"+","-","/","*","%"};
        if (!Arrays.asList(validOperators).contains(request.operator)){
            return new ResponseClass("Invalid operator", 400);
        }

        if (request.operator.equals("+")){
            return new ResponseClass(request.value1 + request.value2 + "", 200);
        } else if (request.operator.equals("-")){
            return new ResponseClass(request.value1 - request.value2 + "", 200);
        } else if (request.operator.equals("*")){
            return new ResponseClass(request.value1 * request.value2 + "", 200);
        } else if (request.operator.equals("%")){
            return new ResponseClass(request.value1 % request.value2 + "", 200);
        } else if (request.operator.equals("/")) {
            if (request.value2 == 0.0f){
                return new ResponseClass("Divide by 0!", 400);
            }
            return new ResponseClass(request.value1 / request.value2 + "", 200);
        }

        return new ResponseClass("Something went wrong", 500);

    }
}
