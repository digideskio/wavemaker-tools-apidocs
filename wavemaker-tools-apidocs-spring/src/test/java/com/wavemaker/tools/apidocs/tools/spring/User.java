/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tools.apidocs.tools.spring;
// Generated 19 Nov, 2014 12:15:13 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * User generated by hbm2java
 */
@Entity
@Table(name="USER"
    ,schema="PUBLIC"
    ,catalog="PUBLIC"
)
public class User  implements java.io.Serializable {


     private Integer userid;
     private String username;
     private String password;
     private String role;
     private Integer tenantid;

    public User() {
    }

    public User(String username, String password, String role, Integer tenantid) {
       this.username = username;
       this.password = password;
       this.role = role;
       this.tenantid = tenantid;
    }

     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="USERID", unique=true, nullable=false)
    public Integer getUserid() {
        return this.userid;
    }
    
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    
    @Column(name="USERNAME", length=20)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Column(name="PASSWORD", length=20)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name="ROLE", length=20)
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    
    @Column(name="TENANTID")
    public Integer getTenantid() {
        return this.tenantid;
    }
    
    public void setTenantid(Integer tenantid) {
        this.tenantid = tenantid;
    }




}

