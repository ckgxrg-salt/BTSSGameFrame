package co.btssstudio.btgf.event;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 *	Methods marked with this annotation will be consider as listeners while registering theirs class.
 *	Usage:
 *	/@Listener
 *	access static void listener(EventToListen arg){}
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
}
