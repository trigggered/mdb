
package test.mdb.core.ws.client.stub.webgateway;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the test.mdb.core.ws.client.stub.webgateway package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Open_QNAME = new QName("http://impl.gateway.mdb/", "Open");
    private final static QName _TestInvoke_QNAME = new QName("http://impl.gateway.mdb/", "testInvoke");
    private final static QName _Main_QNAME = new QName("http://impl.gateway.mdb/", "main");
    private final static QName _OpenResponse_QNAME = new QName("http://impl.gateway.mdb/", "OpenResponse");
    private final static QName _MainResponse_QNAME = new QName("http://impl.gateway.mdb/", "mainResponse");
    private final static QName _TestInvokeResponse_QNAME = new QName("http://impl.gateway.mdb/", "testInvokeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: test.mdb.core.ws.client.stub.webgateway
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TestInvoke }
     * 
     */
    public TestInvoke createTestInvoke() {
        return new TestInvoke();
    }

    /**
     * Create an instance of {@link OpenResponse }
     * 
     */
    public OpenResponse createOpenResponse() {
        return new OpenResponse();
    }

    /**
     * Create an instance of {@link Open }
     * 
     */
    public Open createOpen() {
        return new Open();
    }

    /**
     * Create an instance of {@link Main }
     * 
     */
    public Main createMain() {
        return new Main();
    }

    /**
     * Create an instance of {@link MainResponse }
     * 
     */
    public MainResponse createMainResponse() {
        return new MainResponse();
    }

    /**
     * Create an instance of {@link TestInvokeResponse }
     * 
     */
    public TestInvokeResponse createTestInvokeResponse() {
        return new TestInvokeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Open }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.gateway.mdb/", name = "Open")
    public JAXBElement<Open> createOpen(Open value) {
        return new JAXBElement<Open>(_Open_QNAME, Open.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestInvoke }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.gateway.mdb/", name = "testInvoke")
    public JAXBElement<TestInvoke> createTestInvoke(TestInvoke value) {
        return new JAXBElement<TestInvoke>(_TestInvoke_QNAME, TestInvoke.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Main }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.gateway.mdb/", name = "main")
    public JAXBElement<Main> createMain(Main value) {
        return new JAXBElement<Main>(_Main_QNAME, Main.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OpenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.gateway.mdb/", name = "OpenResponse")
    public JAXBElement<OpenResponse> createOpenResponse(OpenResponse value) {
        return new JAXBElement<OpenResponse>(_OpenResponse_QNAME, OpenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.gateway.mdb/", name = "mainResponse")
    public JAXBElement<MainResponse> createMainResponse(MainResponse value) {
        return new JAXBElement<MainResponse>(_MainResponse_QNAME, MainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestInvokeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.gateway.mdb/", name = "testInvokeResponse")
    public JAXBElement<TestInvokeResponse> createTestInvokeResponse(TestInvokeResponse value) {
        return new JAXBElement<TestInvokeResponse>(_TestInvokeResponse_QNAME, TestInvokeResponse.class, null, value);
    }

}
