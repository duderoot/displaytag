package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Testcase for #944056.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PaginationLinksTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public PaginationLinksTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "pagination-links.jsp";
    }

    /**
     * Checks generated pagination links.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName
            + "?initiator=AVINASH&wfid=&approvedTDate=&initiatedFDate=&status=default&d-3824-p=2"
            + "&initiatedTDate=04/28/2004&approvedFDate=&method=search&approver=");

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        WebLink[] links = response.getLinks();

        assertEquals("Wrong number of pagination links", 18, links.length);

        String firstLink = links[0].getURLString();

        //remove prefix
        firstLink = firstLink.substring(firstLink.indexOf(getJspName()), firstLink.length());

        assertEquals(
            "First link is bad.",
            "pagination-links.jsp?initiator=AVINASH&wfid=&approvedTDate=&initiatedFDate=&d-2106-p=2"
                + "&status=default&initiatedTDate=04%2F28%2F2004&d-3824-p=2&approvedFDate=&method=search&approver=",
            firstLink);
    }
}