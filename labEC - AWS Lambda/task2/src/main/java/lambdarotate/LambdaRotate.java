package lambdarotate;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaRotate implements RequestHandler<RequestClass, ResponseClass> {
	StringBuilder buildStr;
	String rotated="";
	public ResponseClass handleRequest(RequestClass request, Context context){
		int n;
		String s;
		System.out.println("STR: " + request.STR);
		System.out.println("NUM: " + request.NUM);
		if (request.NUM == -1){
			return new ResponseClass("Invalid rotate count", 400);
		}
		
		n = request.NUM ;
		s = request.STR;
		
		if(s==null) {
			return new ResponseClass("Invalid rotate string", 400);	
		}
		if (n > (s.length())){
			return new ResponseClass("Invalid rotate count", 400);
		}
		if(s.length()>0){
			buildStr = new StringBuilder();
			rotated = buildStr.append(s.substring(0,n)).reverse().toString();
			rotated = rotated+s.substring(n);
			System.out.println("Rotated : " + rotated);
			return new ResponseClass(rotated,200);
		}
		
		return new ResponseClass("Something went wrong", 500);

	}
}
