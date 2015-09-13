package org.caowei.notification.model.nea;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class PSIResult {

	final static Logger logger = Logger.getLogger(PSIResult.class);
	final static XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
	
	private String region;
	private String psi3Hours;
	private String psi24Hours;
	private String rawTimestamp;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	

	public String getPsi3Hours() {
		return psi3Hours;
	}
	public void setPsi3Hours(String psi3Hours) {
		this.psi3Hours = psi3Hours;
	}
	public String getPsi24Hours() {
		return psi24Hours;
	}
	public void setPsi24Hours(String psi24Hours) {
		this.psi24Hours = psi24Hours;
	}
	public String getRawTimestamp() {
		return rawTimestamp;
	}
	public void setRawTimestamp(String rawTimestamp) {
		this.rawTimestamp = rawTimestamp;
	}
	
	/**
	 * Result XML String into a List of PSIResult
	 * @param xmlResult
	 * @return
	 */
	public static final List<PSIResult> resolveFromXml(String xmlResult) {
		
		if (xmlResult == null || xmlResult.trim().length() == 0)
			throw new IllegalArgumentException("XML result is null.");
		
		List<PSIResult> results = new ArrayList<PSIResult>(); 
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(xmlResult.getBytes("UTF-8")));
			
			results = resolveFromXml(doc);
		
		} catch (Exception e){
			logger.error("Error Occurs while parse xml result " + xmlResult,e);
		}
		
		return results;
	}
	
	/**
	 * Resolve XML result into A List of PSIResult
	 * @param xmlDoc
	 * @return
	 */
	public static final List<PSIResult> resolveFromXml(Document xmlDoc){
		List<PSIResult> psiResults = new ArrayList<PSIResult>();
		
		try {

			XPath xpath = XPATH_FACTORY.newXPath();
			XPathExpression expr = xpath.compile("/channel/item/region");
			NodeList regions = (NodeList) expr.evaluate(xmlDoc, XPathConstants.NODESET);
			
			for (int i=0; i<regions.getLength(); i++){
				Node regionItem = regions.item(i);
				
				PSIResult psiResult = readOneResultForRegion(regionItem);
				
				if (psiResult != null){
					psiResults.add(psiResult);
				}
				
			}
			
			
		} catch (Exception e){
			logger.error("Error Occurs while parse xml result ",e);
		}
		
		return psiResults;
	}
	
	private static PSIResult readOneResultForRegion(Node nodeItem) {
		PSIResult result = null;
		
		try {
			String region    = getStringValue(nodeItem,"./id/text()");
			String timestamp = getStringValue(nodeItem,"./record/@timestamp");
			String psi3Hour  = getStringValue(nodeItem,"./record/reading[@type='NPSI_PM25_3HR']/@value");
			String psi24Hour = getStringValue(nodeItem,"./record/reading[@type='NPSI']/@value");
			
			result = new PSIResult();
			result.setRawTimestamp(timestamp);
			result.setRegion(region);
			result.setPsi3Hours(psi3Hour);
			result.setPsi24Hours(psi24Hour);
			
			logger.debug("Read record " + result);
		
		} catch (Exception e){
			result = null;
			logger.error("Error Occurs while read Region PSI record",e);
		}

		return result;
	}
	
	private static final String getStringValue(Node node,String path) throws XPathExpressionException{
		
		XPath xpath = XPATH_FACTORY.newXPath();
		XPathExpression expr = xpath.compile(path);
		return (String)expr.evaluate(node, XPathConstants.STRING);
		
	}
	
	public static final String toString(List<PSIResult> results){
		
		if (results == null || results.size() == 0){
			return "NA";
		}
		StringBuilder sb = new StringBuilder();
		
		for (PSIResult psiItem : results){
			sb.append(psiItem.toString());
			sb.append("             ");
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "PSIResult [region=" + region + ", psi3Hours=" + psi3Hours + ", psi24Hours=" + psi24Hours
				+ ", rawTimestamp=" + rawTimestamp + "]";
	}
		
	public static void main(String[] args) throws Exception {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new FileReader("d:/Temp/psi.txt")));
		
		PSIResult.resolveFromXml(doc);
	}

}
