package com.supelpawel.user.dto;

import com.supelpawel.account.model.Account;
import com.supelpawel.role.model.Role;
import com.supelpawel.user.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private Long id;
  @NotNull
  @Size(min = 4)
  private String username;
  @NotNull
  private int age;
  @NotNull
  @Size(min = 4)
  private String password;
  @NotNull
  @PESEL
  private String pesel;
  private boolean enabled;
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Account> accounts = new ArrayList<>();
  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public static UserDto from(User user) {
    UserDto userDto = new UserDto();

    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setAge(user.getAge());
    userDto.setPassword(user.getPassword());
    userDto.setPesel(user.getPesel());
    userDto.setEnabled(user.isEnabled());
    userDto.setAccounts(user.getAccounts());
    userDto.setRoles(user.getRoles());
    return userDto;
  }

  @Override
  public String toString() {
    return "UserDto{" +
        "username='" + username + '\'' +
        ", age=" + age +
        '}';
  }
}