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
	@Override
    public void beforeAll(ExtensionContext context) {
        System.out.println("Tests of Class: "+ context.getRequiredTestClass().getSimpleName().replace("Test", ""));
    }
	
	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
	}
	
 	@Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("\tTest disabled: " + getTestMethodSignature(context));
        if(isParameterizedMethod(context)) {
    		System.out.println("\t\tParam: " + getTestMethodCurrParameters(context));
		}
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("\tTest succeeded: " + getTestMethodSignature(context));
        if(isParameterizedMethod(context)) {
    		System.out.println("\t\tParam: " + getTestMethodCurrParameters(context));
		}
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("\tTest aborted: " + getTestMethodSignature(context));
        if(isParameterizedMethod(context)) {
    		System.out.println("\t\tParam: " + getTestMethodCurrParameters(context));
    	}
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
		System.out.println("\tTest failed: " + getTestMethodSignature(context));
		if(isParameterizedMethod(context)) {
    		System.out.println("\t\tParam: " + getTestMethodCurrParameters(context));
		}
		System.out.println("\t\t" + cause.getMessage());
		System.out.println("\t\t" + getCauseLine(context, cause));
    }
    
	@Override
    public void afterAll(ExtensionContext context) {
        System.out.println("___________________________________________________________________________________");
    }
	
	@Override
    public void afterEach(ExtensionContext context) {
        System.out.println("");
    }
	
	
	
    private static String getTestMethodSignature(ExtensionContext context) {
    	Method method = context.getTestMethod().get();
        Parameter[] parameters = method.getParameters();
        StringBuilder parametersString = new StringBuilder("(");
        for (Parameter parameter : parameters) {
			parametersString.append(parameter);
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
    
}

