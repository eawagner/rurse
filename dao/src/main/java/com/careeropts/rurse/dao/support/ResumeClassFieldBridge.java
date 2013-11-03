package com.careeropts.rurse.dao.support;

import com.careeropts.rurse.dao.object.ResumeEntity;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.tika.exception.TikaException;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import org.xml.sax.SAXException;

import java.io.IOException;

public class ResumeClassFieldBridge implements FieldBridge{

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {

        try {
            String resumeData = ResumeParser.parse((ResumeEntity) value);
            if (resumeData != null)
                document.add(new Field("resume", resumeData, luceneOptions.getStore(), luceneOptions
                        .getIndex(), luceneOptions.getTermVector()));


        } catch (TikaException | SAXException | IOException e) {
            System.out.println(e.getMessage());
            //TODO log
        }
    }
}
