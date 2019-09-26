package com.daimeng.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlTransMappingAnnotation {
	public abstract String value() default "";//defalt 表示默认值
}
