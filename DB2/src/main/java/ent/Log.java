package ent;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(catalog = "1", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l")
    , @NamedQuery(name = "Log.findByIdul", query = "SELECT l FROM Log l WHERE l.idul = :idul")
    , @NamedQuery(name = "Log.findBySessionid", query = "SELECT l FROM Log l WHERE l.sessionid = :sessionid")
    , @NamedQuery(name = "Log.findByUsername", query = "SELECT l FROM Log l WHERE l.username = :username")
    , @NamedQuery(name = "Log.findByDatefrom", query = "SELECT l FROM Log l WHERE l.datefrom = :datefrom")
    , @NamedQuery(name = "Log.findByDateto", query = "SELECT l FROM Log l WHERE l.dateto = :dateto")
    , @NamedQuery(name = "Log.findByLogoutByExpire", query = "SELECT l FROM Log l WHERE l.logoutByExpire = :logoutByExpire")
    , @NamedQuery(name = "Log.findByAzudat", query = "SELECT l FROM Log l WHERE l.azudat = :azudat")
    , @NamedQuery(name = "Log.findByFinished", query = "SELECT l FROM Log l WHERE l.finished = :finished")
    , @NamedQuery(name = "Log.findByForciblyloggedoff", query = "SELECT l FROM Log l WHERE l.forciblyloggedoff = :forciblyloggedoff")
    , @NamedQuery(name = "Log.findByRemoteaddress", query = "SELECT l FROM Log l WHERE l.remoteaddress = :remoteaddress")
    , @NamedQuery(name = "Log.findByLogtype", query = "SELECT l FROM Log l WHERE l.logtype = :logtype")
    , @NamedQuery(name = "Log.findByResource", query = "SELECT l FROM Log l WHERE l.resource = :resource")
    , @NamedQuery(name = "Log.findByDetails", query = "SELECT l FROM Log l WHERE l.details = :details")})
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idul;
    @Column(length = 50)
    private String sessionid;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String username;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datefrom;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateto;
    @Column(name = "logout_by_expire")
    private Boolean logoutByExpire;
    @Temporal(TemporalType.TIMESTAMP)
    private Date azudat;
    private Boolean finished;
    private Boolean forciblyloggedoff;
    @Column(length = 100)
    private String remoteaddress;
    @Column(length = 50)
    private String logtype;
    @Column(length = 100)
    private String resource;
    @Column(length = 200)
    private String details;

    public Log() {
    }

    public Log(Integer idul) {
        this.idul = idul;
    }

    public Log(Integer idul, String username) {
        this.idul = idul;
        this.username = username;
    }

    public Integer getIdul() {
        return idul;
    }

    public void setIdul(Integer idul) {
        this.idul = idul;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Date datefrom) {
        this.datefrom = datefrom;
    }

    public Date getDateto() {
        return dateto;
    }

    public void setDateto(Date dateto) {
        this.dateto = dateto;
    }

    public Boolean getLogoutByExpire() {
        return logoutByExpire;
    }

    public void setLogoutByExpire(Boolean logoutByExpire) {
        this.logoutByExpire = logoutByExpire;
    }

    public Date getAzudat() {
        return azudat;
    }

    public void setAzudat(Date azudat) {
        this.azudat = azudat;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getForciblyloggedoff() {
        return forciblyloggedoff;
    }

    public void setForciblyloggedoff(Boolean forciblyloggedoff) {
        this.forciblyloggedoff = forciblyloggedoff;
    }

    public String getRemoteaddress() {
        return remoteaddress;
    }

    public void setRemoteaddress(String remoteaddress) {
        this.remoteaddress = remoteaddress;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idul != null ? idul.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        return !((this.idul == null && other.idul != null) || (this.idul != null && !this.idul.equals(other.idul)));
    }

    @Override
    public String toString() {
        return "ent.Log[ idul=" + idul + " ]";
    }
    
}
