package com.careeropts.rurse.dao.object;

import org.apache.solr.analysis.*;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "User")
@Indexed
@AnalyzerDef(name = "userAnalyzer",
        tokenizer = @TokenizerDef(factory = LowerCaseTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = StopFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "English")
                }),
                @TokenFilterDef(factory = LengthFilterFactory.class, params = {
                        @Parameter(name = "min", value = "2"),
                        @Parameter(name = "max", value = "100")
                })
        }
)
@Analyzer(definition = "userAnalyzer")
public class UserEntity {

    @Id
    @DocumentId
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, unique = true)
    Long id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    private boolean manager;

    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @IndexedEmbedded
    ResumeEntity resume;

    public UserEntity() {
    }

    public UserEntity(String email, String password, boolean manager, ResumeEntity resume) {
        this.email = email;
        this.password = password;
        this.manager = manager;
        this.resume = resume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public ResumeEntity getResume() {
        return resume;
    }

    public void setResume(ResumeEntity resume) {
        this.resume = resume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (manager != that.manager) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (resume != null ? !resume.equals(that.resume) : that.resume != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (manager ? 1 : 0);
        result = 31 * result + (resume != null ? resume.hashCode() : 0);
        return result;
    }
}
