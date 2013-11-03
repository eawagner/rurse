package com.careeropts.rurse.dao.support;


import com.careeropts.rurse.dao.object.ResumeEntity;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.tika.config.TikaConfig.getDefaultConfig;

public class ResumeParser {

    private static TikaConfig TIKI_CONFIG = getDefaultConfig();

    public static String parse(ResumeEntity resume) throws TikaException, SAXException, IOException {
        if (resume == null ||
                resume.getFileName() == null ||
                resume.getFileType() == null ||
                resume.getData() == null)
            return null;

        try (InputStream input = new ByteArrayInputStream(resume.getData())){
            Metadata metadata = new Metadata();
            metadata.add(Metadata.RESOURCE_NAME_KEY, resume.getFileName());
            metadata.add(Metadata.CONTENT_TYPE, resume.getFileType());

            BodyContentHandler contentHandler = new BodyContentHandler();

            TIKI_CONFIG.getParser().parse(input, contentHandler, metadata, new ParseContext());

            return contentHandler.toString();
        }
    }

}
