package it.unibo.clojure.stm;
import java.lang.annotation.*;

public class Annot {
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE, ElementType.METHOD})
	@Inherited
	public @interface AuthorInfo
	{
	public String name();
	public String email() default "";
	public String comment() default "";
	}
}
