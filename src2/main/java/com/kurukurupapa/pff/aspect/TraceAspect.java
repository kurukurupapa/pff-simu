package com.kurukurupapa.pff.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * トレースログを出力するAspectです。
 */
@Aspect
@Component
public class TraceAspect {

    /**
     * ロガー
     */
    private Logger logger;

    /**
     * コンストラクタ
     */
    public TraceAspect() {
        logger = Logger.getLogger(TraceAspect.class);
    }

    /**
     * 開始ログを出力します。
     *
     * @param jp
     *            JoinPoint
     */
    @Before("execution(* jp.co.javaapptemplate.service.*ServiceImpl.*(..))")
    public void before(JoinPoint jp) {
        print(getName(jp) + "開始");
    }

    /**
     * 終了ログを出力します。
     *
     * @param jp
     *            JoinPoint
     */
    @After("execution(* jp.co.javaapptemplate.service.*ServiceImpl.*(..))")
    public void after(JoinPoint jp) {
        print(getName(jp) + "終了");
    }

    /**
     * クラス名、メソッド名を取得します。
     *
     * @param jp
     *            JoinPoint
     * @return クラス名、メソッド名を連携した文字列
     */
    private String getName(JoinPoint jp) {
        Signature sig = jp.getSignature();
        return sig.getDeclaringTypeName() + "#" + sig.getName() + "メソッド";
    }

    /**
     * ログ出力します。
     *
     * @param msg
     *            メッセージ
     */
    private void print(String msg) {
        logger.trace(msg);
    }

}
