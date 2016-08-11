package edu.uiuc.ncsa.swamp.api;

import edu.uiuc.ncsa.swamp.session.Session;

import java.util.Date;

import static edu.uiuc.ncsa.swamp.session.handlers.UserHandler.*;

/**
 * This models a user in the swamp.
 * <p>Created by Jeff Gaynor<br>
 * on 11/18/14 at  3:06 PM
 */
public class User extends SwampThing {
    public User(Session session) {
        super(session);
    }

    @Override
    protected SwampThing getNewInstance() {
        return new User(getSession());
    }

    @Override
    public String getIDKey() {return USER_UID_KEY;}
    public String getFirstName(){return getString(FIRST_NAME_KEY);}
    public void setFirstName(String firstName){put(FIRST_NAME_KEY, firstName);}
    public String getLastName(){return getString(LAST_NAME_KEY);}
    public void setLastName(String lastName){put(LAST_NAME_KEY, lastName);}
    public String getPreferredName(){return getString(PREFERRED_NAME_KEY);}
    public void setPreferredName(String preferredName){put(PREFERRED_NAME_KEY, preferredName);}
    public String getEmail(){return getString(EMAIL_KEY);}
    public void setEmail(String email){put(EMAIL_KEY, email);}
    public String getAddress(){return getString(ADDRESS_KEY);}
    public void setAddress(String address){put(ADDRESS_KEY, address);}
    public String getPhone(){return getString(PHONE_KEY);}
    public void setPhoneName(String phone){put(PHONE_KEY, phone);}
    public String getAffiliation(){return getString(AFFILIATION_KEY);}
    public void setAffiliation(String affiliation){put(AFFILIATION_KEY, affiliation);}
    public boolean isEmailVerified(){return getBoolean(EMAIL_VERIFIED_KEY);}
    public void setEmailVerified(boolean emailVerified){put(EMAIL_VERIFIED_KEY, emailVerified);}
    public boolean isAccountEnabled(){return getBoolean(ACCOUNT_ENABLED_KEY);}
    public void setAccountEnabled(boolean accountEnabled){put(ACCOUNT_ENABLED_KEY, accountEnabled);}
    public boolean isOwner(){return getBoolean(OWNER_KEY);}
    public void setOwner(boolean owner){put(OWNER_KEY, owner);}
    public boolean hasSSHAccess(){return getBoolean(SSH_ACCESS_KEY);}
    public void setSSHAccess(boolean sshAccess){put(SSH_ACCESS_KEY, sshAccess);}
    public boolean hasAdminAccess(){return getBoolean(ADMIN_ACCESS_KEY);}
    public void setAdminAccess(boolean adminAccess){put(ADMIN_ACCESS_KEY, adminAccess);}
    public String getLastURL(){return getString(LAST_URL_KEY);}
    public void setLastURL(String lastURL){put(LAST_URL_KEY, lastURL);}
    public Date getCreateDate(){return getDate(CREATE_DATE_KEY);}
    public void setCreateDate(Date date){put(CREATE_DATE_KEY, date);}
    public Date getUpdateDate(){return getDate(UPDATE_DATE_KEY);}
    public void setUpdateDate(Date date){put(UPDATE_DATE_KEY, date);}

    @Override
    public String toString() {
        return "User[uuid=" + getIdentifier() + ", last name=" + getLastName() + ", first name=" + getFirstName() + ", login name=" +
                getPreferredName() +
                ", email=" + getEmail() +
                ", phone=" + getPhone() +
                ", email verified? " + isEmailVerified() +
                ", account enabled? " + isAccountEnabled() +
                "]";
    }
}
