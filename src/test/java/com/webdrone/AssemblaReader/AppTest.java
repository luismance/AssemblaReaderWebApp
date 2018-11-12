package com.webdrone.AssemblaReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.webdrone.assembla.dto.TicketChangesDto;
import com.webdrone.assembla.dto.TicketChangesListDto;
import com.webdrone.util.ExpressionLanguageResultEnum;
import com.webdrone.util.ExpressionLanguageUtils;
import com.webdrone.util.RESTServiceUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *                     name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws ParseException
	 */
	public void testApp() throws ParseException {
		String ticketChangesXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<ticket-comments type=\"array\">\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1313219333</id>\r\n" + "        <comment nil=\"true\"/>\r\n"
				+ "        <ticket-id type=\"integer\">191480123</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-10-26T14:03:22Z</created-on>\r\n"
				+ "        <updated-at type=\"dateTime\">2017-10-26T14:03:22Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Test\r\n" + "  - Fixed\r\n" + "</ticket-changes>\r\n"
				+ "        <user-name>Aurélien Ponçon</user-name>\r\n" + "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n"
				+ "    <ticket-comment>\r\n" + "        <id type=\"integer\">1313219333</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">191480123</ticket-id>\r\n"
				+ "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-10-26T14:03:22Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-10-26T14:03:22Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Test\r\n" + "  - Fixed\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1313219333</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">191480123</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-10-26T14:03:22Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-10-26T14:03:22Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Test\r\n"
				+ "  - Fixed\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1313219333</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">191480123</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-10-26T14:03:22Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-10-26T14:03:22Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Test\r\n"
				+ "  - Fixed\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "<ticket-comment>\r\n"
				+ "        <id type=\"integer\">1286154593</id>\r\n" + "        <comment></comment>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>a7NqWcrGGr54kCacwqjQYw</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-09-15T07:32:53Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-09-15T07:32:53Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - milestone_id\r\n"
				+ "  - '1.23'\r\n" + "  - 1.23.01\r\n" + "</ticket-changes>\r\n" + "        <user-name>Sophie Perrineau</user-name>\r\n"
				+ "        <user-avatar-url>https://assets3.assembla.com/assets/avatars/small/12-d6abd4e625a0aa5e2cef614318dd1f2f.png</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256221263</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:51:18Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:51:18Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - total_invested_hours\r\n"
				+ "  - '0.5'\r\n" + "  - '1.0'\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256206593</id>\r\n" + "        <comment></comment>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:32:58Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:32:58Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - total_invested_hours\r\n"
				+ "  - '0.0'\r\n" + "  - '0.5'\r\n" + "</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n"
				+ "        <user-avatar-url>https://assets0.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256206473</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:32:49Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:32:49Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Test\r\n"
				+ "  - Fixed\r\n" + "</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n"
				+ "        <user-avatar-url>https://assets0.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256189123</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:12:09Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:12:09Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Review\r\n"
				+ "  - Test\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256188973</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:11:57Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:11:57Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n"
				+ "  - aurelien.poncon\r\n" + "  - cyriaque.nemchi\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256188883</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:11:51Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:11:51Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n"
				+ "  - cyriaque.nemchi\r\n" + // assign change 4
				"  - aurelien.poncon\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256187203</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:10:07Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:10:07Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Review\r\n"
				+ "  - Fixed\r\n" + // status change 4
				"</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n" + "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256185853</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T15:08:33Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:08:33Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - cyriaque.nemchi\r\n" + // assign change 4
				"  - aurelien.poncon\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256184603</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:06:59Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:06:59Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Test\r\n"
				+ "  - Review\r\n" + // status change 4
				"</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n" + "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256184513</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T15:06:52Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:06:52Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - cyriaque.nemchi\r\n" + // assign change 3
				"  - aurelien.poncon\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256184263</id>\r\n" + "        <comment>Comme le message d'erreur l'indique, une des lignes est incorrecte : \r\n" + "ligne 382 du fichier 30_init-roles.sql à remplacer par :\r\n"
				+ "INSERT INTO RoleRightGroup (id,description,masque) VALUES ('ROLE_RG_CHECKSTARTSERVICE_ACTION', 'Modification CheckStartservice sur Report', 0);</comment>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T15:06:35Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:06:35Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Accepted\r\n" + "  - Test\r\n" + // status change 3
				"</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n" + "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256178773</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T14:59:49Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T14:59:49Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - aurelien.poncon\r\n" + // assign change 2
				"  - cyriaque.nemchi\r\n" + "</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n"
				+ "        <user-avatar-url>https://assets0.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256177833</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T14:58:45Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T14:58:45Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - New\r\n"
				+ "  - Accepted\r\n" + // status change 2
				"</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n" + "        <user-avatar-url>https://assets1.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256176483</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T14:57:21Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T14:57:21Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - \r\n" + "  - aurelien.poncon\r\n" + // assign change 1
				"</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n" + "        <user-avatar-url>https://assets3.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256175973</id>\r\n" + "        <comment></comment>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T14:56:56Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T14:56:56Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - New\r\n" + "  - New\r\n" + // status change 1
				"</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n" + "        <user-avatar-url>https://assets0.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n"
				+ "    </ticket-comment>" + "</ticket-comments>";

		String ticketCommentsXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + "<ticket-comments type=\"array\">\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256188973</id>\r\n" + "        <comment nil=\"true\"/>\r\n"
				+ "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T15:11:57Z</created-on>\r\n"
				+ "        <updated-at type=\"dateTime\">2017-07-26T15:11:57Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - aurelien.poncon\r\n" + "  - cyriaque.nemchi\r\n" + "</ticket-changes>\r\n"
				+ "        <user-name>Aurélien Ponçon</user-name>\r\n" + "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n"
				+ "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256187203</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T15:10:07Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:10:07Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Review\r\n" + "  - Fixed\r\n" + // status change 5
				"</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n" + "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256185853</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T15:08:33Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:08:33Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - cyriaque.nemchi\r\n" + // assign change 4
				"  - aurelien.poncon\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256184603</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T15:06:59Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:06:59Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Test\r\n"
				+ "  - Review\r\n" + // status change 4
				"</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n" + "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256184513</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T15:06:52Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:06:52Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - cyriaque.nemchi\r\n" + // assign change 3
				"  - aurelien.poncon\r\n" + "</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n"
				+ "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256184263</id>\r\n" + "        <comment>Comme le message d'erreur l'indique, une des lignes est incorrecte : \r\n" + "ligne 382 du fichier 30_init-roles.sql à remplacer par :\r\n"
				+ "INSERT INTO RoleRightGroup (id,description,masque) VALUES ('ROLE_RG_CHECKSTARTSERVICE_ACTION', 'Modification CheckStartservice sur Report', 0);</comment>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>c-6zL6CmSr54oracwqEsg8</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T15:06:35Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T15:06:35Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - Accepted\r\n" + "  - Test\r\n" + // status change 3
				"</ticket-changes>\r\n" + "        <user-name>Aurélien Ponçon</user-name>\r\n" + "        <user-avatar-url>https://s3.amazonaws.com/assembla-avatars/13e9ebe2/c-6zL6CmSr54oracwqEsg8:1501073363_small</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256178773</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T14:59:49Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T14:59:49Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - aurelien.poncon\r\n" + // assign change 2
				"  - cyriaque.nemchi\r\n" + "</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n"
				+ "        <user-avatar-url>https://assets0.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n" + "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n"
				+ "        <id type=\"integer\">1256177833</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n" + "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n"
				+ "        <created-on type=\"dateTime\">2017-07-26T14:58:45Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T14:58:45Z</updated-at>\r\n" + "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - New\r\n"
				+ "  - Accepted\r\n" + // status change 2
				"</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n" + "        <user-avatar-url>https://assets1.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256176483</id>\r\n" + "        <comment nil=\"true\"/>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T14:57:21Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T14:57:21Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - assigned_to_id\r\n" + "  - \r\n" + "  - aurelien.poncon\r\n" + // assign change 1
				"</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n" + "        <user-avatar-url>https://assets3.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n"
				+ "    </ticket-comment>\r\n" + "    <ticket-comment>\r\n" + "        <id type=\"integer\">1256175973</id>\r\n" + "        <comment></comment>\r\n" + "        <ticket-id type=\"integer\">185255053</ticket-id>\r\n"
				+ "        <user-id>dhIzg4aM8r54ordmr6CpXy</user-id>\r\n" + "        <created-on type=\"dateTime\">2017-07-26T14:56:56Z</created-on>\r\n" + "        <updated-at type=\"dateTime\">2017-07-26T14:56:56Z</updated-at>\r\n"
				+ "        <ticket-changes>---\r\n" + "- - status\r\n" + "  - New\r\n" + "  - New\r\n" + // status change 1
				"</ticket-changes>\r\n" + "        <user-name>cyriaque.nemchi</user-name>\r\n" + "        <user-avatar-url>https://assets0.assembla.com/assets/avatars/small/7-77aedade176aaa6560f69812a26d1822.png</user-avatar-url>\r\n"
				+ "    </ticket-comment>" + "</ticket-comments>";

		TicketChangesListDto ticketChangesList = (TicketChangesListDto) RESTServiceUtil.unmarshaller(TicketChangesListDto.class, ticketCommentsXml);

		if (ticketChangesList != null) {
			// Reverse list because assembla returns the changes in reverse
			List<TicketChangesDto> ticketChangesReversed = new ArrayList<TicketChangesDto>();
			List<TicketChangesDto> newTicketChangesList = ticketChangesList.getTicketChanges();

			for (int i = newTicketChangesList.size() - 1; i >= 0; i--) {
				ticketChangesReversed.add(newTicketChangesList.get(i));
			}

			List<String> expressions = new ArrayList<String>();
			// new unassigned to new assigned
			expressions.add("((old_status == \"new\" && old_assigned_to_id == \"\") && \r\n" + "(new_status == \"new\" && new_assigned_to_id != \"\") && \r\n" + "((new_updated_at - ticket_created) < 60) && ticket_priority == 1)");
			// new assigned to accepted assigned
			expressions.add("((old_status == \"new\" && old_assigned_to_id != \"\") && \r\n" + "(new_status == \"accepted\" && new_assigned_to_id != \"\") && \r\n" + "((new_updated_at - ticket_created) < 60) && ticket_priority == 1");

			expressions.add("((old_status==\"accepted\" && old_assigned_to_id!=\"\" ) && \r\n" + "(new_assigned_to==\"test\" && new_assigned_to_id!=\"\") && \r\n" + "((new_updated_at - ticket_created) < 60) &&\r\n" + " ticket_priority == 1");
			expressions.add("((old_status==\"accepted\" && old_assigned_to_id!=\"\" ) && \r\n" + "(new_assigned_to==\"blocked\" && new_assigned_to_id!=\"\") && \r\n" + "((new_updated_at - ticket_created) < 60) &&\r\n" + " ticket_priority == 1");
			expressions.add("((old_status==\"test\" && old_assigned_to_id!=\"\" ) && \r\n" + "(new_assigned_to==\"review\" && new_assigned_to_id!=\"\") && \r\n" + "((new_updated_at - ticket_created) < 60) &&\r\n" + " ticket_priority == 1");
			expressions.add("((old_status==\"review\" && old_assigned_to_id!=\"\" ) && \r\n" + "(new_assigned_to==\"fixed\" && new_assigned_to_id!=\"\") && \r\n" + "((new_updated_at - ticket_created) < 60) &&\r\n" + " ticket_priority == 1");

			Map<String, String> fieldMap = new HashMap<String, String>();
			for (TicketChangesDto ticketChanges : ticketChangesReversed) {
				if (ticketChanges.getTicketChanges().contains("- - ")) {
					String[] fields = ticketChanges.getTicketChanges().replace("---\n", "").split("- - ");
					for (int i = 1; i < fields.length; i++) {
						String[] fieldArray = fields[i].split("  - ");
						String fieldName = fieldArray[0].replace("- - ", "").replace("\n", "");
						String previousValue = fieldArray[1].replace("\n", "");
						String newValue = fieldArray[2].replace("\n", "");

						System.out.println("Ticket Change : " + fieldName + "," + previousValue + " > " + newValue);

						fieldMap.put("ticket_created", System.currentTimeMillis() + "");
						fieldMap.put("ticket_priority", "1");

						int expressionIndex = 0;

						/* DATE VALUE PROCESSING START */
						if (previousValue.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\dZ")) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
							previousValue = TimeUnit.MILLISECONDS.toMinutes(sdf.parse(previousValue).getTime()) + "";
						}

						if (newValue.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\dZ")) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
							newValue = TimeUnit.MILLISECONDS.toMinutes(sdf.parse(newValue).getTime()) + "";
						}

						fieldMap.put("new_updated_at", ticketChanges.getUpdatedAt().toDate().getTime() + "");
						/* DATE VALUE PROCESSING START */

						if (fieldMap.get("old_" + fieldName) != null && fieldMap.get("new_" + fieldName) != null) {
							for (String key : fieldMap.keySet()) {
								System.out.println("Field Map : " + key + " : " + fieldMap.get(key));
							}
							System.out.println("Expression : " + expressions.get(expressionIndex));
							ExpressionLanguageResultEnum evalResult = ExpressionLanguageUtils.evaluate(fieldMap, expressions.get(expressionIndex));
							if (evalResult == ExpressionLanguageResultEnum.COMPLETE_FALSE) {
								System.out.println("[" + ticketChanges.getId() + "]EVAL RESULT : " + evalResult);
								fieldMap = new HashMap<String, String>();
								if (expressions.size() == 0) {
									break;
								}
							} else if (evalResult == ExpressionLanguageResultEnum.COMPLETE_TRUE) {
								System.out.println("[" + ticketChanges.getId() + "]EVAL RESULT : " + evalResult);
								fieldMap = new HashMap<String, String>();
							} else {
								System.out.println("[" + ticketChanges.getId() + "]EVAL RESULT : " + evalResult);
							}
							fieldMap = new HashMap<String, String>();
							expressionIndex++;
						} else {
							fieldMap.put("old_" + fieldName, previousValue.toLowerCase());
							fieldMap.put("new_" + fieldName, newValue.toLowerCase());
						}

					}
				}
			}

		}
		System.out.println("Unit test");

		assertTrue(true);
	}
}
