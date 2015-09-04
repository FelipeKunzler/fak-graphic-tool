package fak.graphicTool;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is responsible to update its respective component's text
 * The field that uses this annotation HAS to have a propertie in the file
 * "messages_xx.properties" in the following syntax: "ClassName.fieldName.text"
 * 
 * @param hasToolTip True in case it has to set ToolTip as well
 * 		  necessary syntax in messages file: "ClassName.fieldName.toolTipText"
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface UsesMessagesText {
	boolean hasToolTip() default false;
}
