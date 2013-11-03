package com.careeropts.rurse.dao.object;

import com.careeropts.rurse.dao.support.ResumeClassFieldBridge;
import org.hibernate.search.annotations.ClassBridge;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static org.hibernate.search.annotations.Index.YES;
import static org.hibernate.search.annotations.Store.NO;

@Entity
@Table(name = "Resume")
@ClassBridge(name = "resumeBridge", index = YES, store = NO, impl = ResumeClassFieldBridge.class)
public class ResumeEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
    Long id;

    @Column(nullable = false)
    String fileName;

    @Column(nullable = false)
    String fileType;

    @Lob
    @Column(nullable = false)
    byte[] data;

    public ResumeEntity() {
    }

    public ResumeEntity(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}