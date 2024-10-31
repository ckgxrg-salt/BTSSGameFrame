package co.btssstudio.btgf.event;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 *	Classes marked with this annotation will be consider as listening groups that will be registered automatically.
 *	Usage:
 *	/@ListeningGroup(busID = "busid")
 *	access class listeninggroup {
 *		/@Listener ...
 *	}
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListeningGroup {
	String busID();
}
