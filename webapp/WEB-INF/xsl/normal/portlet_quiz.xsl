<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

		<xsl:template match="portlet">
			<xsl:choose>
				<xsl:when test="string(quiz-active)='true'">
						<div class="portlet append-bottom">
							<h3>
								<xsl:value-of disable-output-escaping="yes" select="portlet-name" />
							</h3>
							<div class="portlet-content -lutece-border-radius">
								<xsl:apply-templates select="quiz-portlet" />
							</div>
						</div>
				</xsl:when>
				<xsl:otherwise>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:template>
		
		<xsl:template match="quiz-portlet">
			<xsl:apply-templates select="quiz-portlet-content" />
		</xsl:template>
		
		<xsl:template match="quiz-portlet-content">
			<xsl:value-of disable-output-escaping="yes" select="." />
		</xsl:template>
</xsl:stylesheet>
