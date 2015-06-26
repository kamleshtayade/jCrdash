package com.spring.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Crdetail.
 */
@Entity
@Table(name = "CRDETAIL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="crdetail")
public class Crdetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "change_id")
    private String changeId;

    @Column(name = "change_summary")
    private String changeSummary;

    @Column(name = "ch_schd_fiscal_week")
    private String chSchdFiscalWeek;

    @Column(name = "ch_schd_fiscal_mth")
    private String chSchdFiscalMth;

    @Column(name = "ci_name")
    private String ciName;

    @Column(name = "ci_priority")
    private String ciPriority;

    @Column(name = "dependent_ci_name")
    private String dependentCiName;

    @Column(name = "parent_ci_name")
    private String parentCiName;

    @Column(name = "ci_type")
    private String ciType;

    @Column(name = "change_status")
    private String changeStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public String getChangeSummary() {
        return changeSummary;
    }

    public void setChangeSummary(String changeSummary) {
        this.changeSummary = changeSummary;
    }

    public String getChSchdFiscalWeek() {
        return chSchdFiscalWeek;
    }

    public void setChSchdFiscalWeek(String chSchdFiscalWeek) {
        this.chSchdFiscalWeek = chSchdFiscalWeek;
    }

    public String getChSchdFiscalMth() {
        return chSchdFiscalMth;
    }

    public void setChSchdFiscalMth(String chSchdFiscalMth) {
        this.chSchdFiscalMth = chSchdFiscalMth;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }

    public String getCiPriority() {
        return ciPriority;
    }

    public void setCiPriority(String ciPriority) {
        this.ciPriority = ciPriority;
    }

    public String getDependentCiName() {
        return dependentCiName;
    }

    public void setDependentCiName(String dependentCiName) {
        this.dependentCiName = dependentCiName;
    }

    public String getParentCiName() {
        return parentCiName;
    }

    public void setParentCiName(String parentCiName) {
        this.parentCiName = parentCiName;
    }

    public String getCiType() {
        return ciType;
    }

    public void setCiType(String ciType) {
        this.ciType = ciType;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Crdetail crdetail = (Crdetail) o;

        if ( ! Objects.equals(id, crdetail.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Crdetail{" +
                "id=" + id +
                ", changeId='" + changeId + "'" +
                ", changeSummary='" + changeSummary + "'" +
                ", chSchdFiscalWeek='" + chSchdFiscalWeek + "'" +
                ", chSchdFiscalMth='" + chSchdFiscalMth + "'" +
                ", ciName='" + ciName + "'" +
                ", ciPriority='" + ciPriority + "'" +
                ", dependentCiName='" + dependentCiName + "'" +
                ", parentCiName='" + parentCiName + "'" +
                ", ciType='" + ciType + "'" +
                ", changeStatus='" + changeStatus + "'" +
                '}';
    }
}
