/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.localization;

import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.views.jsp.TagUtils;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.LocaleProvider;
import com.opensymphony.xwork.TextProvider;
import com.opensymphony.xwork.util.OgnlValueStack;


/**
 * Webwork implementation of a resource provider and locale resolver.
 * @author Richard HALLIER
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class I18nWebworkAdapter implements LocaleResolver, I18nResourceProvider
{

    /**
     * prefix/suffix for missing entries.
     */
    public static final String UNDEFINED_KEY = "???"; //$NON-NLS-1$

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(I18nWebworkAdapter.class);

    /**
     * @see LocaleResolver#resolveLocale(HttpServletRequest)
     */
    public Locale resolveLocale(HttpServletRequest request)
    {

        Locale result = null;
        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        Iterator iterator = stack.getRoot().iterator();
        while (iterator.hasNext())
        {
            Object o = iterator.next();

            if (o instanceof LocaleProvider)
            {
                LocaleProvider lp = (LocaleProvider) o;
                result = lp.getLocale();

                break;
            }
        }

        if (result == null)
        {
            log.debug("Missing LocalProvider actions, init locale to default");
            result = Locale.getDefault();
        }

        return result;
    }

    /**
     * @see I18nResourceProvider#getResource(String, String, Tag, PageContext)
     */
    public String getResource(String resourceKey, String defaultValue, Tag tag, PageContext pageContext)
    {

        // if resourceKey isn't defined either, use defaultValue
        String key = (resourceKey != null) ? resourceKey : defaultValue;

        String message = null;
        OgnlValueStack stack = TagUtils.getStack(pageContext);
        Iterator iterator = stack.getRoot().iterator();

        while (iterator.hasNext())
        {
            Object o = iterator.next();

            if (o instanceof TextProvider)
            {
                TextProvider tp = (TextProvider) o;
                message = tp.getText(key, null, null);

                break;
            }
        }

        // if user explicitely added a titleKey we guess this is an error
        if (message == null && resourceKey != null)
        {
            log.debug("Missing resource for key [" + resourceKey + "]");
            message = UNDEFINED_KEY + resourceKey + UNDEFINED_KEY;
        }

        return message;
    }
}
