/**
 * 
 */
package com.yonder.test;

import java.io.Serializable;

/**
 * @author cyd
 * 2018年2月27日
 */
public class TransformationMessages {

    /**
     * 传递的数据（参数）
     */
    public static class TransformationJob implements Serializable {
        private final String text;  

        public TransformationJob(String text) {  
            this.text = text;  
        }  

        public String getText() {  
            return text;  
        }  
    }

    /**
     * 返回结果
     */
    public static class TransformationResult implements Serializable {  
        private final String text;  

        public TransformationResult(String text) {  
            this.text = text;  
        }  

        public String getText() {  
            return text;  
        }  

        @Override  
        public String toString() {  
            return "TransformationResult(" + text + ")";  
        }  
    }

    /**
     * 异常处理
     */
    public static class JobFailed implements Serializable {  
        private final String reason;  
        private final TransformationJob job;  

        public JobFailed(String reason, TransformationJob job) {  
            this.reason = reason;  
            this.job = job;  
        }  

        public String getReason() {  
            return reason;  
        }  

        public TransformationJob getJob() {  
            return job;  
        }  

        @Override  
        public String toString() {  
            return "JobFailed(" + reason + ")";  
        }  
    }  

    /**
     * 用于服务端向客户端注册
     */
    public static final int BACKEND_REGISTRATION = 1;

}  