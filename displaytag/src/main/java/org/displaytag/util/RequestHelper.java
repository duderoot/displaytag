package org.displaytag.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A RequestHelper object is used to read parameters from the request. Main feature are handling of numeric parameters
 * and the ability to create Href objects from the current request.
 * @author fgiust
 * @version $Revision$ ($Author$)
 * @see org.displaytag.util.Href
 */
public class RequestHelper
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(RequestHelper.class);

    /**
     * original HttpServletRequest.
     */
    private HttpServletRequest request;

    /**
     * Construct a new RequestHelper for the given request.
     * @param servletRequest HttpServletRequest
     */
    public RequestHelper(HttpServletRequest servletRequest)
    {
        this.request = servletRequest;
    }

    /**
     * Read a String parameter from the request.
     * @param key String parameter name
     * @return String parameter value
     */
    public final String getParameter(String key)
    {
        // actually simply return the parameter, this behaviour could be changed
        return this.request.getParameter(key);
    }

    /**
     * Read a Integer parameter from the request.
     * @param key String parameter name
     * @return Integer parameter value or null if the parameter is not found or it can't be transformed to an Integer
     */
    public final Integer getIntParameter(String key)
    {
        String value = this.request.getParameter(key);

        if (value != null)
        {
            try
            {
                return new Integer(value);
            }
            catch (NumberFormatException e)
            {
                // It's ok to ignore, simply return null
                log.debug("Invalid \"" + key + "\" parameter from request: value=\"" + value + "\"");
            }
        }

        return null;
    }

    /**
     * Returns a Map containing all the parameters in the request.
     * @return Map
     */
    public final Map getParameterMap()
    {

        Map map = new HashMap();

        // get the parameters names
        Enumeration parametersName = this.request.getParameterNames();

        while (parametersName.hasMoreElements())
        {
            // ... get the value
            String paramName = (String) parametersName.nextElement();

            // put key/value in the map
            try
            {
                map.put(paramName, URLEncoder.encode(this.request.getParameter(paramName), "UTF-8"));
            }
            catch (UnsupportedEncodingException e)
            {
                log.warn("UnsupportedEncodingException while trying to encode a parameter using UTF-8", e);
            }
        }

        // return the Map
        return map;
    }

    /**
     * return the current Href for the request (base url and parameters).
     * @return Href
     */
    public final Href getHref()
    {
        Href href = new Href(this.request.getRequestURI());
        href.setParameterMap(getParameterMap());
        return href;
    }

}