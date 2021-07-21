//package com.example.demo.mid;
//
///**
// * @program: lingshipu
// * @description:
// * @author: QWS
// * @create: 2021-05-16 19:47
// */
//public class ParamIsNullException extends RuntimeException {
//    private final String parameterName;
//    private final String parameterType;
//
//    public ParamIsNullException(String parameterName, String parameterType) {
//        super("");
//        this.parameterName = parameterName;
//        this.parameterType = parameterType;
//    }
//
//    @Override
//    public String getMessage() {
//        return "Required " + this.parameterType + " parameter \'" + this.parameterName + "\' must be not null !";
//    }
//
//    public final String getParameterName() {
//        return this.parameterName;
//    }
//
//    public final String getParameterType() {
//        return this.parameterType;
//    }
//}
