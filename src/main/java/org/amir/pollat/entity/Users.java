package org.amir.pollat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME")
    @NotEmpty
    private String username;

    @NotEmpty
    @Column(name = "FIRST_NAME")
    private String firstname;

    @NotEmpty
    @Column(name = "LAST_NAME")
    private String lastname;
    @JsonIgnore // is used at field level to mark a property or list of properties to be ignored.
//    Use @JsonIgnore on the class member and its getter, and @JsonProperty on its setter. A sample illustration would help to do this:
//    ```
//    class User {
//
//    // More fields here
//    @JsonIgnore
//    private String password;
//
//    @JsonIgnore
//    public String getPassword() {
//        return password;
//    }
//
//    @JsonProperty
//    public void setPassword(final String password) {
//        this.password = password;
//    }
//}
//    ```
    @NotEmpty
    @Column(name = "PASSWORD")
    private String password;

    @Column(name="ADMIN", columnDefinition = "char(3)")
    @Type(type="yes_no") //    Type Annotations are annotations that can be placed anywhere you use a type. This includes the new operator, type casts, implements clauses and throws clauses. Type Annotations allow improved analysis of Java code and can ensure even stronger type checking
    @NotEmpty
    private boolean admin;
}
