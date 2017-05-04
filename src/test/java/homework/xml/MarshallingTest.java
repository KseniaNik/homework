package homework.xml;

import homework.model.*;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Created on 29.04.2017.
 */
public class MarshallingTest {

    @Test
    public void testMarshalling() throws Exception {

        JAXBContext context = JAXBContext.newInstance(
                Service.class, Article.class, Employee.class, Office.class, Order.class, ExportedDatabase.class
        );
        Marshaller marshaller = context.createMarshaller();

        Service sampleService = new Service(1, "service 1", 10);
        Office sampleOffice = new Office(1, "office 1", "address 1");
        Employee sampleEmployee = new Employee(
                1, "fn 1", "ln 1", "pn 1", new java.sql.Date(new Date().getTime()),
                "88005553535", new java.sql.Date(new Date().getTime()), 1
        );
        Order sampleOrder = new Order(
                1, "cfn", "cln", "cpn", "80123123",
                new java.sql.Date(new Date().getTime()), false, 1, 1, 1
        );
        Article sampleArticle = new Article(1, 1, "base", 123, "1,2,4");

        ExportedDatabase db = new ExportedDatabase(
                Collections.singletonList(sampleService),
                Collections.singletonList(sampleOffice),
                Collections.singletonList(sampleEmployee),
                Collections.singletonList(sampleOrder),
                Collections.singletonList(sampleArticle)
        );

        StringWriter writer = new StringWriter();
        marshaller.marshal(db, writer);
        String marshalled = writer.toString();

        StringReader reader = new StringReader(marshalled);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ExportedDatabase exportedDatabase = (ExportedDatabase) unmarshaller.unmarshal(reader);

        StringWriter writer2 = new StringWriter();
        marshaller.marshal(exportedDatabase, writer2);
        String marshalled2 = writer2.toString();

        assertEquals(marshalled, marshalled2);
    }

}
