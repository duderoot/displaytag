package org.displaytag.exception;

/**
 * <p>Exception thrown when an invalid value is given for a tag attribute</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class InvalidTagAttributeValueException extends BaseNestableJspTagException
{

    /**
     * Constructor for InvalidTagAttributeValueException.
     * @param source Class where the exception is generated
     * @param attributeName String attribute name
     * @param attributeValue String attribute value (invalid)
     */
    public InvalidTagAttributeValueException(Class source, String attributeName, String attributeValue)
    {
        super(source, "Invalid value for attribute \"" + attributeName + "\" value=\"" + attributeValue + "\"");
    }

    /**
     * @return SeverityEnum.ERROR
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.ERROR;
    }

}