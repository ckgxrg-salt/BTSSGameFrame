package co.btssstudio.btgf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *	Classes marked with this annotation will be consider as the ONLY main class of the game.
 *	Usage:
 *	/@MainClass
 *	public class MainClass ...
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MainClass {
}
