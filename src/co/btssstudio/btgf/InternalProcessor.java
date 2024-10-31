package co.btssstudio.btgf;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 *	Methods marked with this annotation will be executed before the engine's internal processors executing.
 *	Usage:
 *	/@InternalProcessor(stage)
 *	access static void processor(){}
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InternalProcessor {
	StageType stage();
}
