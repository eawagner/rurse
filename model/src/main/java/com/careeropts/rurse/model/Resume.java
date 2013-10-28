package com.careeropts.rurse.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resume {

    Long id;
    String fileName;
    DocType fileType;

    public Resume() {
    }

    public Resume(Long id, String fileName, DocType fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (id != null ? !id.equals(resume.id) : resume.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        return result;
    }

    public static enum DocType {
        WORD("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
        TEXT("text/plain");

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
