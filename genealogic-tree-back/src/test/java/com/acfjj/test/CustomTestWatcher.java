package com.acfjj.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class CustomTestWatcher implements TestWatcher, BeforeAllCallback, BeforeEachCallback, AfterAllCallback, AfterEachCallback {
	private String failBef = "\u2717"; 
	private String successBef = "\u2713"; 
	private String neutralBef = "\u2013";
	private int failureCount = 0;
	private int successCount = 0;
	private int abordCount = 0;
	
	@Override
    public void beforeAll(ExtensionContext context) {
		sysOut("");
		sysOut("_______________________________________________________________________________________");
		sysOut("");
        blueSysOut("\tTests of Class: "+ context.getRequiredTestClass().getSimpleName().replace("Test", ""));
        
    }
	
	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
	}
	
 	@Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
 		orangeSysOut("\t  " + neutralBef + " Test disabled: " + getTestMethodSignature(context));
        if(isParameterizedMethod(context)) {
        	orangeSysOut("\t\tParam: " + getTestMethodCurrParameters(context));
		}
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
    	greenSysOut("\t  " + successBef + " Test succeeded: " + getTestMethodSignature(context));
        if(isParameterizedMethod(context)) {
    		greenSysOut("\t\tParam: " + getTestMethodCurrParameters(context));
		}
        successCount++;
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        orangeSysOut("\t   " + neutralBef + " Test aborted: " + getTestMethodSignature(context));
        if(isParameterizedMethod(context)) {
        	orangeSysOut("\t\tParam: " + getTestMethodCurrParameters(context));
    	}
        abordCount++;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
		redSysOut("\t  " + failBef + " Test failed: " + getTestMethodSignature(context));
		if(isParameterizedMethod(context)) {
    		redSysOut("\t\tParam: " + getTestMethodCurrParameters(context));
		}
		redSysOut("\t\t" + cause.getMessage());
		redSysOut("\t\t" + getCauseLine(context, cause));
		failureCount++;
    }
    
	@Override
    public void afterAll(ExtensionContext context) {
		sysOut("");
		if (failureCount > 0) {
            sysOut(redMsg("\t" + failBef + " " + context.getRequiredTestClass().getSimpleName() + " tests status:") + testStatusStr() + " " + redMsg(failBef));
        } else {
        	sysOut(greenMsg("\t" + successBef + " " + context.getRequiredTestClass().getSimpleName() + " tests status:") + testStatusStr() + " " + greenMsg(successBef));
        }
		sysOut("");
    }
	
	public String testStatusStr() {
		 StringBuilder testStatusStr = new StringBuilder();
		 if(successCount > 0) {
			 testStatusStr.append(greenMsg(" " + successCount + " succeeded"));
		 }
		 if(failureCount > 0) {
			 testStatusStr.append(redMsg(" " + failureCount + " failed"));
		 }
		 if(abordCount > 0) {
			 testStatusStr.append(orangeMsg(" " + abordCount + " aborded"));
		 }
		 return testStatusStr.toString();
	}
	
	@Override
    public void afterEach(ExtensionContext context) {
		sysOut("");
        sleep(500);
    }	
	
    private static String getTestMethodSignature(ExtensionContext context) {
    	Method method = context.getTestMethod().get();
        Parameter[] parameters = method.getParameters();
        StringBuilder parametersString = new StringBuilder("(");
        for (Parameter parameter : parameters) {
        	String parameterType = parameter.getType().getName();
        	parameterType = parameterType.substring(parameterType.lastIndexOf('.') + 1);
			parametersString.append(parameterType + " " + parameter.getName());
			if(!parameter.equals(parameters[parameters.length -1])) {
				parametersString.append(", ");
			}
		}
        return method.getName() + parametersString + ")";
    }
    
    private Boolean isParameterizedMethod(ExtensionContext context) {
    	Method method = context.getTestMethod().get();
    	Annotation[] annotations = method.getAnnotations();
    	for (Annotation annotation : annotations) {
			if(annotation.toString().contains("ParameterizedTest")) {
				return true;
			}
		}    	
    	return false;
    }
    
    private String getTestMethodCurrParameters(ExtensionContext context) {
    	String displayName = context.getDisplayName();
    	int indexOfBracket = displayName.indexOf(']');
    	String currentParam = displayName.substring(indexOfBracket + 1).trim();
		return "(" + currentParam + ")";	
    }
    
    private StackTraceElement getCauseLine(ExtensionContext context, Throwable cause) {
    	StackTraceElement[] stackTrace = cause.getStackTrace();
    	StackTraceElement causeElement = null;
	    for (StackTraceElement element : stackTrace) {
	    	if(element.toString().contains(context.getTestMethod().get().getName())) {
	    		causeElement = element;
	    	}
	    }
	    return causeElement;
    }
    
    public static String redMsg(String msg) {
    	return "\033[0;31m" + msg + "\033[0m";
    }
    public static void redSysOut(String msg) {
        sysOut(redMsg(msg));
    }
    public static String greenMsg(String msg) {
    	return "\033[0;32m" + msg + "\033[0m";
    }
    public static void greenSysOut(String msg) {
        sysOut(greenMsg(msg));
    }
    public static String orangeMsg(String msg) {
    	return "\033[0;33m" + msg + "\033[0m";
    }
    public static void orangeSysOut(String msg) {
        sysOut(orangeMsg(msg));
    }
    public static String blueMsg(String msg) {
    	return "\033[0;34m" + msg + "\033[0m";
    }
    public static void blueSysOut(String msg) {
        sysOut(blueMsg(msg));
    }
    
    public static void sysOut(String msg) {
        System.out.println(msg);
    }
    
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

