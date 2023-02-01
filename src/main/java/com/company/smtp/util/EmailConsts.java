package com.company.smtp.util;

public final class EmailConsts {

    public static final String TEMPLATE_PATH = "email/basic_layout.ftlh";

    public enum Template {
        VERIFICATION, WELCOME, INACTIVE;
        public String getSubject(String lang) {
            return Subject.valueOf(this+"_"+lang.toUpperCase()).getValue();
        }
        public String getTemplateName(){
            return TemplateName.valueOf(this.toString()).getValue();
        }
    }

    private enum TemplateName {
        VERIFICATION("verification"),
        WELCOME("welcome"),
        INACTIVE("inactive");

        private final String value;
        TemplateName(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    private enum Subject {
        VERIFICATION_KO("[회사] 인증 메일입니다."),
        WELCOME_KO("[회사] 회원가입을 축하드립니다."),
        INACTIVE_KO("[회사] 1년 이상 미접속으로 인한 휴면 회원 전환 사전 안내드립니다."),

        VERIFICATION_EN("[Company] This is a verification email."),
        WELCOME_EN("[Company] Congratulations on becoming a member."),
        INACTIVE_EN("[Company] We notify you in advance of switching to dormant members due to inactivity for more than 1 year.");

        private final String value;
        Subject(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
