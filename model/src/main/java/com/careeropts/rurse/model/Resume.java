package com.careeropts.rurse.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Resume {

    String fileName;
    DocType fileType;

    public Resume() {
    }

    public Resume(String fileName, DocType fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DocType getFileType() {
        return fileType;
    }

    public void setFileType(DocType fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (fileName != null ? !fileName.equals(resume.fileName) : resume.fileName != null) return false;
        if (fileType != resume.fileType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        return result;
    }

    public static enum DocType {
        WordDocument("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
        TextDocument("text/plain");

        private final String mimeType;

        private DocType(String mimeType) {
            this.mimeType = mimeType;
        }

        public String getMimeType() {
            return mimeType;
        }

        public static DocType fromMimeType(String mimeType) {
            if (mimeType == null)
                return null;

            for (DocType docType : DocType.values())
                if (docType.mimeType.equals(mimeType))
                    return docType;

            return null;
        }
    }
}
