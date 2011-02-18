<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:ns="http://api.freelancer.com/schemas/xml-0.1">
	<xsl:template match="/">
		<html>
			<head>
				<style type="text/css">
					table {
						border-collapse: collapse;
						background-color: rgb(250, 240, 230);
					}
					table th {
						padding: 1px;
						background-color: white;
						-moz-border-radius: 0px 0px 0px 0px;
					}
					table td {
						border-width: 2px;
						padding: 3px;
						border-style: solid;
						border-color: white;
					}
				</style>
			</head>
			<body>
				<table>
					<tr>
						<th>Name</th>
						<th>Company</th>
						<th>Address</th>
						<th>State</th>
						<th>Country</th>
						<th>Postal Code</th>
					</tr>
					<tr>
						<td><xsl:value-of select="ns:xml-result/ns:fullname" /></td>
						<td><xsl:value-of select="ns:xml-result/ns:company_name" /></td>
						<td><xsl:value-of select="ns:xml-result/ns:addressline1" /></td>
						<td><xsl:value-of select="ns:xml-result/ns:state" /></td>
						<td><xsl:value-of select="ns:xml-result/ns:country" /></td>
						<td><xsl:value-of select="ns:xml-result/ns:postalcode" /></td>
					</tr>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>