package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for basic displaytag functionalities.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class BasicTableTagTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public BasicTableTagTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/autocolumns.jsp";
    }

    /**
     * Verifies that the generated page contains a table with the expected number of columns.
     * @throws Exception any axception thrown during test.
     */
    public void testAutoGeneratedColumns() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Bad number of generated columns.", 4, tables[0].getColumnCount());
    }
}