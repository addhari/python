package assignment;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class Xml {
    
    public static void main(String argv[]) {
        Xml xml=new Xml();
        xml.readXml();
        xml.updateXml();
        xml.writeToXmlFile();
        xml.JaxB();
        
    }
    
    String fileName="C:\\Users\\Harry\\Downloads\\test\\data.xml";
    
     String fileName1="C:\\Users\\Harry\\Downloads\\test\\data1.xml";
        void JaxB()
        {
            Customer customer = new Customer();
	  customer.setId(100);
	  customer.setName("mkyong");
	  customer.setAge(29);

	  try {

		File file = new File(fileName);
		JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(customer, file);
		jaxbMarshaller.marshal(customer, System.out);

	      } catch (JAXBException e) {
		e.printStackTrace();
	      }

	}

    void writeToXmlFile()
    {
         try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("company");
		doc.appendChild(rootElement);

		// staff elements
		Element staff = doc.createElement("Staff");
		rootElement.appendChild(staff);

		// set attribute to staff element
		Attr attr = doc.createAttribute("id");
		attr.setValue("1");
		staff.setAttributeNode(attr);

		// shorten way
		// staff.setAttribute("id", "1");

		// firstname elements
		Element firstname = doc.createElement("firstname");
		firstname.appendChild(doc.createTextNode("yong"));
		staff.appendChild(firstname);

		// lastname elements
		Element lastname = doc.createElement("lastname");
		lastname.appendChild(doc.createTextNode("mook kim"));
		staff.appendChild(lastname);

		// nickname elements
		Element nickname = doc.createElement("nickname");
		nickname.appendChild(doc.createTextNode("mkyong"));
		staff.appendChild(nickname);

		// salary elements
		Element salary = doc.createElement("salary");
		salary.appendChild(doc.createTextNode("100000"));
		staff.appendChild(salary);

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(fileName));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  
	}
    }
    
    void updateXml()
    {
        try {
          
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(fileName);
            Node company = doc.getFirstChild();
            Node staff = doc.getElementsByTagName("staff").item(0);
            NamedNodeMap attr = staff.getAttributes();
            Node nodeAttr = attr.getNamedItem("id");
            nodeAttr.setTextContent("2");
            Element age = doc.createElement("age");
            age.appendChild(doc.createTextNode("28"));
            staff.appendChild(age);
            NodeList list = staff.getChildNodes();
            
            for (int i = 0; i < list.getLength(); i++) {
                
                Node node = list.item(i);
                
                if ("salary".equals(node.getNodeName())) {
                    node.setTextContent("2000000");
                }
                
                if ("firstname".equals(node.getNodeName())) {
                    staff.removeChild(node);
                }
                
            }
            
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            
            System.out.println("Done");
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }
    
    void readXml()
    {
        
        try
        {
            File fXmlFile = new File(fileName);
            System.out.println(fXmlFile.getAbsoluteFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("staff");
            System.out.println("");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                
                Node nNode = nList.item(temp);
                
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    
                    Element eElement = (Element) nNode;
                    System.out.println("Staff id : " + eElement.getAttribute("id"));
                    System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                    System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                    System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
                    System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}